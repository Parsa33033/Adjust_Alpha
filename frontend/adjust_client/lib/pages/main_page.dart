import 'dart:typed_data';

import 'package:adjust_client/actions/shoping_action.dart';
import 'package:adjust_client/actions/tutorial_action.dart';
import 'package:adjust_client/components/dashboard.dart';
import 'package:adjust_client/config/adjust_colors.dart';
import 'package:adjust_client/pages/menu_page.dart';
import 'package:adjust_client/pages/shoping_page.dart';
import 'package:adjust_client/pages/tutorial_page.dart';
import 'package:adjust_client/pages/tutorial_video_page.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:adjust_client/states/client_state.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:flutter_zoom_drawer/flutter_zoom_drawer.dart';
import 'package:redux/redux.dart';

import '../main.dart';

final ZoomDrawerController zoomDrawerController = ZoomDrawerController();
class MainPage extends StatefulWidget {
  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {

  Widget _content;
  GlobalKey _bottomNavigationKey = GlobalKey();


  String firstName;
  String lastName;
  double token;
  double score;
  Image _image;


  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    getShopingItems(context);
    getTokenItems(context);

    _content = mainMenu();
    ClientState clientState = store.state.clientState;
    firstName = clientState.firstName;
    lastName = clientState.lastName;
    token = clientState.token;
    score = clientState.score;
    if (clientState.image == null) {
      _image = Image.asset("assets/adjust_logo1.png");
    } else {
      Uint8List imageByte = Uint8List.fromList(clientState.image);
      _image = Image.memory(imageByte);
    }
  }

  @override
  Widget build(BuildContext context) {
    return ZoomDrawer(
      controller: zoomDrawerController,
      menuScreen: MenuPage(image: _image),
      mainScreen: mainPage(),
      borderRadius: 24.0,
      showShadow: false,
      angle: 0.0,
      backgroundColor: Colors.grey[300],
      slideWidth: MediaQuery.of(context).size.width*(true ? -.45: 0.65),
    );
  }

  Widget mainPage() {
    return SafeArea(
      child: Scaffold(
          bottomNavigationBar: CurvedNavigationBar(
            key: _bottomNavigationKey,
            backgroundColor: LIGHT_GREY,
            color: GREEN,
            index: 1,
            items: <Widget>[
              Icon(Icons.open_in_browser, size: 30),
              CircleAvatar(
                  radius: 30,
                  child: Image.asset("assets/adjust_logo1.png")
              ),
              Icon(Icons.shopping_cart, size: 30),
            ],
            onTap: (index) {
              //Handle button tap
              if (index == 0) {
                setState(() {
                  _content = Container(child: Center(child: Text("0"),),);
                });
              } else if (index == 1) {
                setState(() {
                  _content = mainMenu();
                });
              } else {
                setState(() {
                  _content = ShopingPage();
                });
              }
            },
          ),
          body: StoreConnector<AppState, AppState>(
            converter: (Store store) => store.state,
            builder: (BuildContext context, AppState state) {
              return Container(
                  color: LIGHT_GREY,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
                      Expanded(
                          flex: 25,
                          child: Dashboard(firstName: firstName, lastName: lastName, token: token, score: score, image: _image,)
                      ),
                      Expanded(
                          flex: 5,
                          child: Container(
                            padding: EdgeInsets.only(top: 20),
                            child: Divider(
                              color: SHADOW,
                              endIndent: 20,
                              indent: 20,
                              thickness: 2,
                            ),
                          )
                      ),
                      Expanded(
                          flex: 70,
                          child: _content
                      ),
                    ],
                  ));
            },
          )),
    );
  }

  Widget mainMenu() {
    return Container(
      padding: EdgeInsets.only(bottom: 60, left: 20, right: 20, top: 20),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          Expanded(
            flex: 5,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: <Widget>[
                Expanded(
                  flex: 5,
                  child: menuItem("برنامه ی تمرینی من", "assets/workout_icon.png", GREEN, null),
                ),
                Expanded(
                  flex: 5,
                  child: menuItem("برنامه ی تغذیه من", "assets/nutrition_icon.png", RED, null),
                )
              ],
            ),
          ),
          Expanded(
            flex: 5,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: <Widget>[
                Expanded(
                  flex: 5,
                  child: menuItem("متخصص من", "assets/game_icon.png", ORANGE, null),
                ),
                Expanded(
                  flex: 5,
                  child: menuItem("آموزش", "assets/tutorial_icon.png", YELLOW, () {
                    Navigator.of(context).push(MaterialPageRoute(builder: (context) => TutorialPage()));
                  }),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget menuItem(String text, String imageAsset, Color color, Function func) {
    return InkWell(
      child: Container(
        padding: EdgeInsets.only(top: 15),
        margin: EdgeInsets.all(10),
        decoration: BoxDecoration(
          color: WHITE,
          boxShadow: [BoxShadow(
              color: SHADOW,
              offset: Offset(2, 2),
              spreadRadius: 5,
              blurRadius: 5
          )],
          borderRadius: BorderRadius.all(Radius.circular(10)),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            Expanded(
              flex: 60,
              child: Image(
                image: AssetImage(imageAsset),
              ),
            ),
            Expanded(
              flex: 10,
              child: Divider(
                thickness: 1,
                color: color,
                indent: 10,
                endIndent: 10,
              ),
            ),
            Expanded(
              flex: 30,
              child: Container(
                padding: EdgeInsets.all(7),
                decoration: BoxDecoration(
                    color: color,
                    borderRadius: BorderRadius.only(bottomLeft: Radius.circular(10), bottomRight: Radius.circular(5))
                ),
                child: Center(
                  child: Directionality(
                    textDirection: TextDirection.rtl,
                    child: Text(text, style: TextStyle(fontFamily: "Iransans", fontSize: 14, color: WHITE),),
                  ),
                )
              ),
            )
          ],
        ),
      ),
      onTap: () {
        func();
      },
    );
  }
}
