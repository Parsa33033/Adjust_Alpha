import 'package:adjust_client/config/adjust_colors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AdjustInfoButton extends StatelessWidget {
  Color primaryColor;
  Color primaryColorLight;
  Color secondaryColor;
  bool isVertical;
  double height;
  double width;
  String name;
  String title;
  String description;
  double fontSize;
  Image image;
  Function func;

  AdjustInfoButton(
      {this.primaryColor,
      this.primaryColorLight,
      this.secondaryColor,
      this.isVertical,
      this.height,
      this.width,
      this.name,
      this.title,
      this.description,
      this.fontSize,
      this.image,
      this.func});

  @override
  Widget build(BuildContext context) {
    return InkWell(
      child: Container(
        height: this.height,
          width: this.width,
          margin: EdgeInsets.all(10),
          decoration: BoxDecoration(
              color: primaryColor,
              borderRadius: BorderRadius.all(Radius.circular(20)),
              boxShadow: [
                BoxShadow(
                  color: SHADOW,
                  blurRadius: 5,
                  spreadRadius: 5,
                  offset: Offset(2, 2),
                )
              ],
              gradient: LinearGradient(
                colors: [primaryColor, primaryColorLight, primaryColorLight],
              )),
          child: Stack(
            children: <Widget>[
              Positioned(
                right: 0,
                bottom: 0,
                height: this.height * 2 / 4,
                width: this.width * 2 / 4,
                child: ClipPath(
                  clipper: Decoration(20),
                  child: Container(
                    decoration: BoxDecoration(color: primaryColor
//                  gradient: LinearGradient(colors: [primaryColorLight, primaryColor, ], )
                        ),
                  ),
                ),
              ),
              isVertical
                  ? Positioned(
                      child: Container(
                        child: Stack(
                          children: <Widget>[
                            Positioned(
                              bottom: 5,
                              right: 10,
                              height: this.height * 3 / 5,
                              width: this.width * 3 / 5,
                              child: getImage(),
                            ),
                            Positioned(
                              top: 10,
                              right: 5,
                              height: this.height,
                              width: this.width,
                              child: Column(
                                children: <Widget>[
                                  Container(
                                    padding: EdgeInsets.all(4),
                                    width: this.width,
                                    child: Directionality(
                                      textDirection: TextDirection.rtl,
                                      child: Text(
                                        name,
                                        style: TextStyle(
                                            fontFamily: "Iransans",
                                            fontSize: this.fontSize,
                                            color: WHITE),
                                      ),
                                    ),
                                  ),
                                  Container(
                                    padding: EdgeInsets.all(4),
                                    width: this.width,
                                    child: Directionality(
                                      textDirection: TextDirection.rtl,
                                      child: Text(
                                        title,
                                        style: TextStyle(
                                            fontFamily: "Iransans",
                                            fontSize: this.fontSize - 1,
                                            color: WHITE),
                                      ),
                                    ),
                                  ),
                                  Container(
                                    padding: EdgeInsets.all(4),
                                    width: this.width,
                                    child: Directionality(
                                      textDirection: TextDirection.rtl,
                                      child: Text(
                                        description,
                                        style: TextStyle(
                                            fontFamily: "Iransans",
                                            fontSize: this.fontSize - 2,
                                            color: WHITE),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            )
                          ],
                        ),
                      ),
                    )
                  : Container(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Expanded(
                      flex: 7,
                      child: Container(
                        padding: EdgeInsets.all(20),
                        alignment: Alignment.centerRight,
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: <Widget>[
                            Expanded(
                              flex: 34,
                              child: Directionality(
                                textDirection: TextDirection.rtl,
                                child: Text(this.name, style: TextStyle(fontFamily: "Iransans", fontSize: fontSize, color: secondaryColor)),
                              ),
                            ),
                            Expanded(
                              flex: 33,
                              child: Directionality(
                                textDirection: TextDirection.rtl,
                                child: Text(this.title, style: TextStyle(fontFamily: "Iransans", fontSize: fontSize, color: secondaryColor)),
                              ),
                            ),
                            Expanded(
                              flex: 33,
                              child: Directionality(
                                textDirection: TextDirection.rtl,
                                child: Text(this.description, style: TextStyle(fontFamily: "Iransans", fontSize: fontSize, color: secondaryColor)),
                              ),
                            )
                          ],
                        ),
                      ),
                    ),
                    Expanded(
                      flex: 3,
                      child: Container(
                        width: this.width * 3/4,
                        height: this.height * 3/4,
                        padding: EdgeInsets.all(10),
                        child: image,
                      ),
                    )
                  ],
                ),
              )
            ],
          )),
      onTap: () {
        func();
      },
    );
  }

  Widget getImage() {
    return Container(
      child: Hero(
        tag: this.name,
        child: image,
      ),
    );
  }
}

class Decoration extends CustomClipper<Path> {
  double r;

  Decoration(this.r);

  @override
  Path getClip(Size size) {
    double height = size.height;
    double width = size.width;
    Path path = Path();
    path.moveTo(0, height);
    path.lineTo(width - r, height);
    path.quadraticBezierTo(width, height, width, height - r);
    path.lineTo(width, r);
//    path.quadraticBezierTo(width, 0, width - r, 0);
    path.lineTo(width, 0);
    path.quadraticBezierTo(0, 0, 0, height - r);
    path.close();
    return path;
  }

  @override
  bool shouldReclip(CustomClipper<Path> oldClipper) {
    return true;
  }
}
