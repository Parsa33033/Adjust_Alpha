

import 'dart:convert';
import 'dart:typed_data';
import 'dart:math';

import 'package:adjust_client/actions/shoping_action.dart';
import 'package:adjust_client/components/adjust_dialog.dart';
import 'package:adjust_client/components/adjust_info_button.dart';
import 'package:adjust_client/components/adjust_raised_button.dart';
import 'package:adjust_client/config/adjust_colors.dart';
import 'package:adjust_client/constants/words.dart';
import 'package:adjust_client/model/shoping_item.dart';
import 'package:adjust_client/pages/order_page.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:persian_number_utility/persian_number_utility.dart';
import 'package:redux/redux.dart';

class CartPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Directionality(
          textDirection: TextDirection.rtl,
          child: Text("سبد خرید", style: TextStyle(fontFamily: "Iransans", fontSize: 20, color: WHITE),),
        ),
        backgroundColor: GREEN,
        elevation: 4,
        leading: InkWell(
          child: Icon(Icons.arrow_back),
          onTap: () {Navigator.of(context).pop();},
        ),
      ),
      body: StoreConnector<AppState, AppState>(
        converter: (Store store) => store.state,
        builder: (BuildContext context, AppState state) {
          List<Color> colors = [GREEN, RED, ORANGE, YELLOW,];
          double amount = 0;
          state.cartState.items.forEach((element) { amount += element.price;});
          return Container(
            color: LIGHT_GREY,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: <Widget>[
                Expanded(
                    flex: 8,
                    child: ListView.builder(
                      itemCount: state.cartState.items.length,
                        itemBuilder: (BuildContext context, int pos) {
                          Color color = colors[Random().nextInt(colors.length)];
                          ShopingItem e = state.cartState.items[pos];
                          Uint8List imageByte = base64Decode(e.image);
                          Image image = Image.memory(imageByte);
                      return Dismissible(
                        key: Key(e.name),
                        onDismissed: (direction) {
                          state.cartState.items.removeAt(pos);
                          StoreProvider.of<AppState>(context).dispatch(RemoveFromCartAction(payload: state.cartState));
                        },
                        child: AdjustInfoButton(
                            width: 130,
                            height: 130,
                            title: e == null ? "" : "هزینه: " + NumberUtility.changeDigit(e.price.round().toString() + " تومان", NumStrLanguage.Farsi ),
                            description: "",
                            name: e == null ? "" : e.name,
                            fontSize: 14,
                            isVertical: false,
                            primaryColor: WHITE,
                            primaryColorLight: WHITE,
                            secondaryColor: FONT,
                            image: image,
                            func: () async {

                            },
                          ),
                      );
                    }),
                ),
                Expanded(
                  flex: 2,
                  child: Container(
                    color: WHITE,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: <Widget>[
                        Expanded(
                          flex: 3,
                          child: Container(
                            padding: EdgeInsets.all(10),
                            child: Directionality(
                              textDirection: TextDirection.rtl,
                              child: Text(NumberUtility.changeDigit(amount.toString(), NumStrLanguage.Farsi) + " تومان", style: TextStyle(fontFamily: "Iransans", fontSize: 20, color: FONT),),
                            ),
                          ),
                        ),
                        Expanded(
                          flex: 7,
                          child: Container(
                            padding: EdgeInsets.all(10),
                            child: AdjustRaisedButton(
                              height: 50,
                              width: 200,
                              textDirection: TextDirection.rtl,
                              text: ORDER,
                              fontColor: WHITE,
                              primaryColor: GREEN,
                              secondaryColor: GREEN,
                              onPressed: () {
                                if (state.cartState.items.length <= 0) {
                                  showAdjustDialog(context, CART_EMPTY, false, null);
                                } else {
                                  Navigator.of(context).push(MaterialPageRoute(builder: (context) => OrderPage()));
                                }
                              },
                            ),
                          )
                        ),
                      ],
                    ),
                  ),
                )
              ],
            ),
          );
        },
      ),
    );
  }
}