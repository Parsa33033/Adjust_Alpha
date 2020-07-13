import 'dart:convert';
import 'dart:io';

import 'package:adjust_client/constants/urls.dart';
import 'package:adjust_client/dto/tutorial_dto.dart';
import 'package:adjust_client/main.dart';
import 'package:adjust_client/model/tutorial.dart';
import 'package:http/http.dart' as http;
import 'package:adjust_client/states/app_state.dart';
import 'package:adjust_client/states/tutorial_state.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class GetTutorialsAction {
  TutorialListState payload;

  GetTutorialsAction({this.payload});
}

class GetClientTutorialsAction {
  ClientTutorialsState payload;

  GetClientTutorialsAction({this.payload});
}

class GetTutorialAction {
  TutorialState payload;

  GetTutorialAction({this.payload});
}

Future<int> getTutorials(BuildContext context) async {
  String jwt = store.state.authenticationState.jwt;
  if (jwt == null) {
    FlutterSecureStorage storage = FlutterSecureStorage();
    jwt = await storage.read(key: "jwt");
  }

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt);

  http.Response response = await http.get(TUTORIAL_URL, headers: headers);
  if (response.statusCode == HttpStatus.ok) {
    List l = jsonDecode(utf8.decode(response.bodyBytes));
    List<TutorialDTO> items = l.map((e) {
      e["thumbnail"] = base64Decode(e["thumbnail"]);
      TutorialDTO tutorialDTO = TutorialDTO.fromJson(e);
      return tutorialDTO;
    }).toList();
    TutorialListState tutorialListState = TutorialListState(items);
    StoreProvider.of<AppState>(context)
        .dispatch(GetTutorialsAction(payload: tutorialListState));
    return 1;
  }
  return 0;
}

Future<int> buyTutorial(BuildContext context, int videoId) async {
  String jwt = store.state.authenticationState.jwt;
  if (jwt == null) {
    FlutterSecureStorage storage = FlutterSecureStorage();
    jwt = await storage.read(key: "jwt");
  }

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt)
    ..putIfAbsent("Content-Type", () => "application/json");

  http.Response response = await http.post(BUY_TUTORIAL_URL, headers: headers, body: videoId.toString(), encoding: Encoding.getByName("UTF-8"));
  if (response.statusCode == HttpStatus.ok) {
    await getClientTutorials(context);
//    Map m = jsonDecode(utf8.decode(response.bodyBytes));
//    m["thumbnail"] = base64Decode(m["thumbnail"]);
//    TutorialDTO tutorialDTO = TutorialDTO.fromJson(m);
//    List<Tutorial> l = store.state.clientTutorialsState.items;
//    l.add(Tutorial(
//        tutorialDTO.id,
//        tutorialDTO.title,
//        tutorialDTO.description,
//        tutorialDTO.text,
//        tutorialDTO.thumbnail,
//        tutorialDTO.thumbnailContentType,
//        tutorialDTO.tokenPrice,
//        tutorialDTO.videoId,
//        tutorialDTO.clientId));
//    ClientTutorialsState clientTutorialsState = ClientTutorialsState(l);
//    store.dispatch(GetClientTutorialsAction(payload: clientTutorialsState));
    return 1;
  }
  return 0;
}

Future<int> getClientTutorials(BuildContext context) async {
  String jwt = store.state.authenticationState.jwt;
  if (jwt == null) {
    FlutterSecureStorage storage = FlutterSecureStorage();
    jwt = await storage.read(key: "jwt");
  }

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt)
    ..putIfAbsent("Content-Type", () => "application/json");

  http.Response response = await http.get(GET_CLIENT_TUTORIALS_URL, headers: headers);
  if (response.statusCode == HttpStatus.ok) {
    print(utf8.decode(response.bodyBytes));
    List l = jsonDecode(utf8.decode(response.bodyBytes));
    List<TutorialDTO> items = l.map((e) {
      e["thumbnail"] = base64Decode(e["thumbnail"]);
      return TutorialDTO.fromJson(e);
    }).toList();
    ClientTutorialsState tutorialListState = ClientTutorialsState(items);
    store.dispatch(GetClientTutorialsAction(payload: tutorialListState));
    return 1;
  }
  return 0;
}

// not compelete
//Future<int> getTutorial(BuildContext context, int id) async {
//  String jwt =
//      StoreProvider.of<AppState>(context).state.authenticationState.jwt;
//  if (jwt == null) {
//    FlutterSecureStorage storage = FlutterSecureStorage();
//    jwt = await storage.read(key: "jwt");
//  }
//
//  Map<String, String> headers = Map<String, String>()
//    ..putIfAbsent("Authorization", () => "Bearer " + jwt);
//
//  http.Response response = await http.get(TUTORIAL_URL, headers: headers);
//  if (response.statusCode == HttpStatus.ok) {
//    Map res = jsonDecode(utf8.decode(response.bodyBytes));
//    res["text"] = base64Decode(res["text"]);
//    res["thumbnail"] = base64Decode(res["thumbnail"]);
//    TutorialDTO tutorialDTO =
//        TutorialDTO.fromJson(res);
//    TutorialState tutorialState = TutorialState(
//        tutorialDTO.id,
//        tutorialDTO.title,
//        tutorialDTO.description,
//        tutorialDTO.text,
//        tutorialDTO.textContentType,
//        tutorialDTO.thumbnail,
//        tutorialDTO.thumbnailContentType,
//        tutorialDTO.tokenPrice,
//        tutorialDTO.videoId,
//        tutorialDTO.clientId);
//    StoreProvider.of<AppState>(context).dispatch(GetTutorialsAction(payload: tutorialState));
//    return 1;
//  }
//  return 0;
//}
