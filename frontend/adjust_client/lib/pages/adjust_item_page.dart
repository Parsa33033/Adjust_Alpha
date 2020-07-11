

import 'package:adjust_client/components/adjust_raised_button.dart';
import 'package:adjust_client/config/adjust_colors.dart';
import 'package:adjust_client/constants/words.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AdjustItemPage extends StatelessWidget {

  Image image;
  String name;
  String title;
  String description;
  Function onButtonPressed;
  bool isInfo;
  String buttonText;
  AdjustItemPage({this.image, this.name, this.title, this.description, this.onButtonPressed, this.isInfo, this.buttonText});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            Expanded(
              flex: 4,
              child: Container(
                child: Stack(
                  children: <Widget>[
                    Positioned(
//                      bottom: 80,
                    top: 0,
                      right: 0,
                      child: Container(
                        width: MediaQuery.of(context).size.width,
                        child: Image.asset("assets/bg1.png"),
                      ),
                    ),
                    Positioned(
                      bottom: -10,
                      left: 20,
                      child: Container(
                        height: 200,
                        width: 200,
                        child: Hero(
                          tag: this.name,
                          child: this.image,
                        ),
                      ),
                    ),
                    Positioned(
                        top: 20,
                        right: 30,
                        child: Container(
                          padding: EdgeInsets.all(30),
                          width: MediaQuery.of(context).size.width,
                          child: Directionality(
                            textDirection: TextDirection.rtl,
                            child: Text(this.name, style: TextStyle(fontFamily: "Iransans", fontSize: 25, color: WHITE),),
                          ),
                        )
                    ),
                  ],
                ),
              ),
            ),
            Expanded(
              flex: 6,
              child: Container(
                child: SingleChildScrollView(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        padding: EdgeInsets.all(20),
                        child: Center(
                          child: Directionality(
                            textDirection: TextDirection.rtl,
                            child: Text(this.title, style: TextStyle(fontFamily: "Iransans", fontSize: 25, color: FONT),),
                          ),
                        )
                      ),
                      Container(
                        padding: EdgeInsets.all(20),
                        child: Directionality(
                          textDirection: TextDirection.rtl,
                          child: Text(this.description, style: TextStyle(fontFamily: "Iransans", fontSize: 18, color: FONT),),
                        ),
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: <Widget>[
                          Expanded(
                            flex: 5,
                            child: Container(
                              padding: EdgeInsets.all(10),
                              child: AdjustRaisedButton(
                                width: MediaQuery.of(context).size.width,
                                height: 60,
                                fontSize: 16,
                                textDirection: TextDirection.rtl,
                                primaryColor: GREEN,
                                secondaryColor: GREEN,
                                fontColor: WHITE,
                                text: BACK,
                                onPressed: () {
                                  Navigator.of(context).pop();
                                },
                              ),
                            ),
                          ),
                          Expanded(
                            flex: 5,
                            child: this.isInfo ? Container() :
                            Container(
                              padding: EdgeInsets.all(10),
                              child: AdjustRaisedButton(
                                width: MediaQuery.of(context).size.width,
                                height: 60,
                                fontSize: 16,
                                textDirection: TextDirection.rtl,
                                primaryColor: GREEN,
                                secondaryColor: GREEN,
                                fontColor: WHITE,

                                text: buttonText,
                                onPressed: () {
                                  this.onButtonPressed();
                                },
                              ),
                            ),
                          )
                        ],
                      )
                    ],
                  ),
                )
              ),
            )
          ],
        ),
      ),
    );
  }
}

class BackgroundClip extends CustomClipper<Path> {
  double r;
  BackgroundClip(this.r);

  @override
  Path getClip(Size size) {
    double height = size.height;
    double width = size.width;
    Path path = Path();
    path.lineTo(0, height - r);
    path.lineTo(0 + r, height);
    path.quadraticBezierTo(0 + r + 20, height + r, 0 + 40 + r, height);
    path.lineTo(width, height - 2 * r);
    path.lineTo(width, 0);
    path.lineTo(0, 0);
    return path;
  }

  @override
  bool shouldReclip(CustomClipper<Path> oldClipper) {
    return true;
  }

}