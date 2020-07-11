import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:adjust_client/config/database.dart';
import 'package:adjust_client/constants/urls.dart';
import 'package:adjust_client/domains/client_domain.dart';
import 'package:adjust_client/dto/client_dto.dart';
import 'package:adjust_client/dto/user_dto.dart';
import 'package:adjust_client/model/client.dart';
import 'package:adjust_client/model/user.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:adjust_client/states/client_state.dart';
import 'package:enum_to_string/enum_to_string.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'package:jaguar_query_sqflite/jaguar_query_sqflite.dart';

class CreateClientAction {
  ClientState payload;

  CreateClientAction({this.payload});
}

class UpdateClientAction {
  ClientState payload;

  UpdateClientAction({this.payload});
}

Future<int> updateClient(BuildContext context, ClientDTO clientDTO) async {
  String jwt =
      StoreProvider.of<AppState>(context).state.authenticationState.jwt;
  if (jwt == null) {
    FlutterSecureStorage storage = FlutterSecureStorage();
    jwt = await storage.read(key: "jwt");
  }

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt)
    ..putIfAbsent("Content-Type", () => "application/json");

  String content = jsonEncode(clientDTO.toJson());
  print("client is updating with: ${content}");

  http.Response response = await http.put(CLIENT_URL,
      headers: headers, body: content, encoding: Encoding.getByName("UTF-8"));
  if (response.statusCode == HttpStatus.ok) {
    Map<String, dynamic> m = jsonDecode(utf8.decode(response.bodyBytes));

    if (m["image"] != null) {
      List l = List.of(base64Decode(m["image"]));
      List<int> imageByte = List<int>.from(l);
      m["image"] = imageByte;
    }
    print("client update response is: ${m}");

    ClientDTO client = ClientDTO.fromJson(m);
    setClientState(context, client);
//    await setClientDomain(context, client);

    return 1;
  }
  return 0;
}

Future<int> getClient(BuildContext context) async {
  String jwt = StoreProvider.of<AppState>(context).state.authenticationState.jwt;
  if (jwt == null) {
    FlutterSecureStorage storage = FlutterSecureStorage();
    jwt = await storage.read(key: "jwt");
  }

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt);
  http.Response response = await http.get(CLIENT_URL, headers: headers);
  if (response.statusCode == HttpStatus.ok) {
    Map<String, dynamic> m = jsonDecode(utf8.decode(response.bodyBytes));

    if (m["image"] != null) {
      List l = List.of(base64Decode(m["image"]));
      List<int> imageByte = List<int>.from(l);
      m["image"] = imageByte;
    }

    ClientDTO clientDTO = ClientDTO.fromJson(m);
    Client client = clientDTO;
    setClientState(context, client);
//    await setClientDomain(context, client);
    return 1;
  }
  return 0;
}

