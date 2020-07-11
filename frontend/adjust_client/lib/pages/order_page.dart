import 'dart:ffi';

import 'package:adjust_client/actions/shoping_action.dart';
import 'package:adjust_client/components/adjust_dialog.dart';
import 'package:adjust_client/components/adjust_raised_button.dart';
import 'package:adjust_client/components/adjust_text_field.dart';
import 'package:adjust_client/components/preloader.dart';
import 'package:adjust_client/config/adjust_colors.dart';
import 'package:adjust_client/constants/words.dart';
import 'package:adjust_client/dto/cart_dto.dart';
import 'package:adjust_client/dto/order_dto.dart';
import 'package:adjust_client/dto/shoping_item_dto.dart';
import 'package:adjust_client/main.dart';
import 'package:adjust_client/model/shoping_item.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:persian_number_utility/persian_number_utility.dart';
import 'package:redux/redux.dart';
import 'package:string_validator/string_validator.dart';

class OrderPage extends StatefulWidget {
  @override
  _OrderPageState createState() => _OrderPageState();
}

class _OrderPageState extends State<OrderPage> {
  final _formKey = GlobalKey<FormState>();

  TextEditingController emailTextFieldController;
  TextEditingController firstNameTextFieldController;
  TextEditingController lastNameTextFieldController;
  TextEditingController phoneNumberTextFieldController;
  TextEditingController countryTextFieldController;
  TextEditingController stateTextFieldController;
  TextEditingController cityTextFieldController;
  TextEditingController address1TextFieldController;
  TextEditingController address2TextFieldController;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    emailTextFieldController = TextEditingController();
    firstNameTextFieldController = TextEditingController();
    lastNameTextFieldController = TextEditingController();
    phoneNumberTextFieldController = TextEditingController();
    countryTextFieldController = TextEditingController();
    stateTextFieldController = TextEditingController();
    cityTextFieldController = TextEditingController();
    address1TextFieldController = TextEditingController();
    address2TextFieldController = TextEditingController();

    AppState state = store.state;
    setAppState(state);
  }

  void setAppState(AppState state) {
    setState(() {
      emailTextFieldController.text = state.userState.email;
      firstNameTextFieldController.text = state.clientState.firstName;
      lastNameTextFieldController.text = state.clientState.lastName;
      countryTextFieldController.text = "ایران";
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: StoreConnector<AppState, AppState>(
      converter: (Store store) => store.state,
      builder: (BuildContext context, AppState state) {
        return Container(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              Expanded(
                flex: 30,
                child: Container(
                  child: Stack(
                    children: <Widget>[
                      Positioned(
                        top: 0,
                        right: 0,
                        bottom: 100,
                        left: 0,
                        child: Container(
                          color: GREEN,
                        ),
                      ),
                      Positioned(
                        bottom: 0,
                        left: 0,
                        right: 0,
                        height: 180,
                        child: Container(
                          child: Image.asset("assets/adjust_logo.png"),
                        ),
                      ),
                      Positioned(
                          left: 20,
                          top: 50,
                          child: Container(
                            height: 50,
                            width: 50,
                            child: InkWell(
                              child: Icon(
                                Icons.arrow_back,
                                color: WHITE,
                                size: 50,
                              ),
                              onTap: () {
                                Navigator.of(context).pop();
                              },
                            ),
                          )),
                    ],
                  ),
                ),
              ),
              Expanded(
                flex: 70,
                child: Container(
                  child: SingleChildScrollView(
                      child: Form(
                    key: _formKey,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: <Widget>[
                        AdjustTextField(
                            textDirection: TextDirection.ltr,
                            controller: emailTextFieldController,
                            hintText: EMAIL,
                            enabled: false,
                            icon: Icon(
                              Icons.email,
                              color: GREEN,
                            ),
                            isPassword: false,
                            primaryColor: GREEN,
                            validator: (String value) {
                              if (!isEmail(value)) {
                                return WRONG_EMAIL;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        AdjustTextField(
                            textDirection: TextDirection.rtl,
                            textAlign: TextAlign.right,
                            controller: firstNameTextFieldController,
                            hintText: FIRST_NAME,
                            enabled: false,
                            icon: Icon(
                              Icons.person,
                              color: RED,
                            ),
                            isPassword: false,
                            primaryColor: RED,
                            validator: (String value) {
                              if (value == null || value == "") {
                                return EMPTY;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        AdjustTextField(
                            textDirection: TextDirection.rtl,
                            textAlign: TextAlign.right,
                            controller: lastNameTextFieldController,
                            hintText: LAST_NAME,
                            enabled: false,
                            icon: Icon(
                              Icons.person,
                              color: ORANGE,
                            ),
                            isPassword: false,
                            primaryColor: ORANGE,
                            validator: (String value) {
                              if (value == null || value == "") {
                                return EMPTY;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        AdjustTextField(
                            textDirection: TextDirection.rtl,
                            textAlign: TextAlign.right,
                            controller: phoneNumberTextFieldController,
                            hintText: PHONE_NUMBER,
                            enabled: true,
                            icon: Icon(
                              Icons.phone,
                              color: YELLOW,
                            ),
                            isPassword: false,
                            primaryColor: YELLOW,
                            validator: (String value) {
                              if (value == null || value == "") {
                                return EMPTY;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        AdjustTextField(
                          textDirection: TextDirection.rtl,
                          textAlign: TextAlign.right,
                          controller: countryTextFieldController,
                          hintText: COUNTRY,
                          enabled: true,
                          icon: Icon(
                            Icons.location_on,
                            color: SHADOW,
                          ),
                          isPassword: false,
                          primaryColor: SHADOW,
                          validator: (String value) {
                            if (value == null || value == "") {
                              return EMPTY;
                            }
                            return null;
                          },
                          padding: 0,
                          margin: 20,
                        ),
                        AdjustTextField(
                            textDirection: TextDirection.rtl,
                            textAlign: TextAlign.right,
                            controller: stateTextFieldController,
                            hintText: STATE,
                            enabled: true,
                            icon: Icon(
                              Icons.location_on,
                              color: SHADOW,
                            ),
                            isPassword: false,
                            primaryColor: SHADOW,
                            validator: (String value) {
                              if (value == null || value == "") {
                                return EMPTY;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        AdjustTextField(
                            textDirection: TextDirection.rtl,
                            textAlign: TextAlign.right,
                            controller: cityTextFieldController,
                            hintText: CITY,
                            enabled: true,
                            icon: Icon(
                              Icons.location_on,
                              color: SHADOW,
                            ),
                            isPassword: false,
                            primaryColor: SHADOW,
                            validator: (String value) {
                              if (value == null || value == "") {
                                return EMPTY;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        AdjustTextField(
                            textDirection: TextDirection.rtl,
                            textAlign: TextAlign.right,
                            controller: address1TextFieldController,
                            hintText: ADDRESS +
                                NumberUtility.changeDigit(
                                    " 1".toString(), NumStrLanguage.Farsi),
                            enabled: true,
                            icon: Icon(
                              Icons.location_on,
                              color: SHADOW,
                            ),
                            isPassword: false,
                            primaryColor: SHADOW,
                            validator: (String value) {
                              if (value == null || value == "") {
                                return EMPTY;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        AdjustTextField(
                            textDirection: TextDirection.rtl,
                            textAlign: TextAlign.right,
                            controller: address2TextFieldController,
                            hintText: ADDRESS +
                                NumberUtility.changeDigit(
                                    " 2".toString(), NumStrLanguage.Farsi),
                            enabled: true,
                            icon: Icon(
                              Icons.location_on,
                              color: SHADOW,
                            ),
                            isPassword: false,
                            primaryColor: SHADOW,
                            validator: (String value) {
                              if (value == null || value == "") {
                                return EMPTY;
                              }
                              return null;
                            },
                            padding: 0,
                            margin: 20),
                        Container(
                          padding: EdgeInsets.all(20),
                          child: AdjustRaisedButton(
                              text: ORDER,
                              textDirection: TextDirection.rtl,
                              primaryColor: GREEN,
                              secondaryColor: GREEN,
                              height: 50,
                              width: MediaQuery.of(context).size.width,
                              onPressed: () async {
                                if (_formKey.currentState.validate()) {
                                  List<ShopingItemDTO> items = state
                                      .cartState.items
                                      .map((e) => ShopingItemDTO(
                                      null,
                                      e.name,
                                      e.description,
                                      e.token,
                                      e.price,
                                      e.image,
                                      e.imageContentType,
                                      e.orderable,
                                      e.cartId)).toList();
                                  CartDTO cartDto = CartDTO(
                                      null,
                                      emailTextFieldController.text,
                                      firstNameTextFieldController.text,
                                      lastNameTextFieldController.text,
                                      items);
                                  OrderDTO orderDTO = OrderDTO(
                                      null,
                                      emailTextFieldController.text,
                                      firstNameTextFieldController.text,
                                      lastNameTextFieldController.text,
                                      phoneNumberTextFieldController.text,
                                      emailTextFieldController.text,
                                      countryTextFieldController.text,
                                      stateTextFieldController.text,
                                      cityTextFieldController.text,
                                      address1TextFieldController.text,
                                      address2TextFieldController.text,
                                      null,
                                      cartDto);
                                  showAdjustDialog(context,
                                      orderDTO.toJson().toString(), true, () {
                                          order(context, orderDTO);
                                      });
                                }
                              }),
                        )
                      ],
                    ),
                  )),
                ),
              )
            ],
          ),
        );
      },
    ));
  }

  @override
  void dispose() {
    // TODO: implement dispose
    emailTextFieldController.dispose();
    firstNameTextFieldController.dispose();
    lastNameTextFieldController.dispose();
    super.dispose();
  }
}
