import 'dart:convert';
import 'dart:io';

import 'package:adjust_client/actions/authentication_action.dart';
import 'package:adjust_client/config/database.dart';
import 'package:adjust_client/constants/urls.dart';
import 'package:adjust_client/domains/user_domain.dart';
import 'package:adjust_client/dto/user_dto.dart';
import 'package:adjust_client/main.dart';
import 'package:adjust_client/model/user.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:adjust_client/states/authentication_state.dart';
import 'package:adjust_client/states/user_state.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'package:jaguar_query_sqflite/jaguar_query_sqflite.dart';

class RegisterUserAction {
  UserState payload;

  RegisterUserAction({this.payload});
}

Future<int> registerUser(BuildContext context, ManagedUserDTO userDTO) async {
  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent(
        "Authorization", () => "Basic " + base64.encode(utf8.encode(API_KEY)))
    ..putIfAbsent("Content-Type", () => "application/json");

  String content = jsonEncode(userDTO.toJson());
  print("user is registering with: ${content}");

  http.Response response = await http.post(REGISTER_URL,
      headers: headers, body: content, encoding: Encoding.getByName("UTF-8"));
  if (response.statusCode == HttpStatus.ok) {
    // save jwt in storage
    String jwt = jsonDecode(response.body)["id_token"];
    FlutterSecureStorage storage = FlutterSecureStorage();
    await storage.write(key: "jwt", value: jwt);

    //save userDTO in database as UserDomain
    Database db = Database();
    SqfliteAdapter adapter = await db.get();
    UserDomainBean userDomainBean = UserDomainBean(adapter);
    userDomainBean.createTable(ifNotExists: true);

    UserDomain userDomain = UserDomain(
        id: "1",
        email: userDTO.login,
        login: userDTO.login,
        firstName: "",
        lastName: "",
        imageUrl: "",
        langKey: "fa");

    final tempUser = await userDomainBean.find("1");
    if (tempUser == null) {
      await userDomainBean.insert(userDomain);
    } else {
      await userDomainBean.remove("1");
      await userDomainBean.insert(userDomain);
    }

    String login = userDTO.login;
    String email = userDTO.email;

    UserState userState = UserState(login, null, null, email, null, null);
    AuthenticationState authenticationState = AuthenticationState(jwt: jwt);

    StoreProvider.of<AppState>(context)
        .dispatch(RegisterUserAction(payload: userState));
    StoreProvider.of<AppState>(context)
        .dispatch(AuthenticateAction(payload: authenticationState));

    //return 1 for success
    return 1;
  }
  return 0;
}

Future<int> getUser(BuildContext context) async {
  String jwt =
      StoreProvider.of<AppState>(context).state.authenticationState.jwt;
  if (jwt == null) {
    FlutterSecureStorage storage = FlutterSecureStorage();
    jwt = await storage.read(key: "jwt");
  }

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt);

  http.Response response = await http.get(ACCOUNT, headers: headers);
  if (response.statusCode == HttpStatus.ok) {
    UserDTO userDTO = UserDTO.fromJson(jsonDecode(response.body));
    print("user fetched with: ${userDTO.toJson()}");
    User user = userDTO;
    setUserState(context, user);
//    await setUserDomain(context, user);
    return 1;
  }
  return 0;
}



Future<int> sendPasswordRecoveryEmail(String email) async {
  http.Response response = await http.post(RECOVER_PASS, body: email);
  if (response.statusCode == HttpStatus.ok) {
    return 1;
  }
  return 0;
}
