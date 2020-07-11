import 'package:adjust_client/states/authentication_state.dart';
import 'package:adjust_client/states/cart_state.dart';
import 'package:adjust_client/states/client_state.dart';
import 'package:adjust_client/states/shoping_state.dart';
import 'package:adjust_client/states/token_state.dart';
import 'package:adjust_client/states/user_state.dart';

AppState appStateInit = AppState(
    userState: userStateInit,
    authenticationState: authenticationStateInit,
    clientState: clientStateInit,
    shopingState: shopingStateInit,
    tokenState: tokenStateInit,
    cartState: cartStateInit
 );

class AppState {
  UserState userState;
  AuthenticationState authenticationState;
  ClientState clientState;
  ShopingState shopingState;
  TokenState tokenState;
  CartState cartState;

  AppState({this.userState, this.authenticationState, this.clientState, this.shopingState, this.tokenState, this.cartState});
}
