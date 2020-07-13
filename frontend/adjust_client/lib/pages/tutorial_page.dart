import 'dart:convert';
import 'dart:typed_data';
import 'dart:math';

import 'package:adjust_client/actions/shoping_action.dart';
import 'package:adjust_client/actions/client_action.dart';
import 'package:adjust_client/actions/tutorial_action.dart';
import 'package:adjust_client/components/adjust_dialog.dart';
import 'package:adjust_client/components/adjust_info_button.dart';
import 'package:adjust_client/components/adjust_raised_button.dart';
import 'package:adjust_client/components/preloader.dart';
import 'package:adjust_client/config/adjust_colors.dart';
import 'package:adjust_client/constants/words.dart';
import 'package:adjust_client/main.dart';
import 'package:adjust_client/model/shoping_item.dart';
import 'package:adjust_client/model/tutorial.dart';
import 'package:adjust_client/pages/order_page.dart';
import 'package:adjust_client/pages/tutorial_video_page.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:persian_number_utility/persian_number_utility.dart';
import 'package:redux/redux.dart';

class TutorialPage extends StatefulWidget {
  @override
  _TutorialPageState createState() => _TutorialPageState();
}

class _TutorialPageState extends State<TutorialPage> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    loadTutorials();
  }

  void loadTutorials() async {
    int j = await getClientTutorials(context);
    int i = await getTutorials(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Align(
          alignment: Alignment.centerRight,
          child: Directionality(
            textDirection: TextDirection.rtl,
            child: Text(
              "ویدیوهای آموزشی",
              style:
                  TextStyle(fontFamily: "Iransans", fontSize: 20, color: FONT),
            ),
          ),
        ),
        backgroundColor: LIGHT_YELLOW,
        elevation: 4,
        leading: InkWell(
          child: Icon(Icons.arrow_back),
          onTap: () {
            Navigator.of(context).pop();
          },
        ),
      ),
      body: StoreConnector<AppState, AppState>(
        converter: (Store store) => store.state,
        builder: (BuildContext context, AppState state) {
          double amount = 0;
          state.cartState.items.forEach((element) {
            amount += element.price;
          });
          return Container(
            color: LIGHT_GREY,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: <Widget>[
                Expanded(
                  child: ListView.builder(
                      itemCount: state.tutorialListState.items.length,
                      itemBuilder: (BuildContext context, int pos) {
                        Tutorial e = state.tutorialListState.items[pos];
                        Uint8List imageByte = Uint8List.fromList(e.thumbnail);
                        Image image = Image.memory(imageByte);
                        bool isClientTutorial = false;
                        state.clientTutorialsState.items.forEach((element) {
                          if (element.title == e.title) {
                            isClientTutorial = true;
                            return;
                          }
                        });
                        return AdjustInfoButton(
                          width: 150,
                          height: 150,
                          title: e == null ? "" : e.description,
                          description: e == null
                              ? ""
                              : isClientTutorial
                                  ? ""
                                  : NumberUtility.changeDigit(
                                      e.tokenPrice.round().toString() + " توکن",
                                      NumStrLanguage.Farsi),
                          name: e == null ? "" : e.title,
                          fontSize: 14,
                          isVertical: false,
                          primaryColor: isClientTutorial ? WHITE : YELLOW,
                          primaryColorLight:
                              isClientTutorial ? WHITE : LIGHT_YELLOW,
                          secondaryColor: isClientTutorial ? FONT : FONT,
                          image: image,
                          func: () async {
                            if (isClientTutorial) {
                              Navigator.of(context).push(MaterialPageRoute(
                                  builder: (context) => TutorialVideoPage(e)));
                            } else {
                              if (store.state.clientState.token <
                                  e.tokenPrice) {
                                showAdjustDialog(
                                    context,
                                    "مقدار توکن شما برای خرید درون برنامه این آموزش کافی نمیباشد!",
                                    false,
                                    null);
                              } else {
                                showAdjustDialog(
                                    context,
                                    "با تایید، " +
                                        NumberUtility.extractNumber(
                                            e.tokenPrice.round().toString(),
                                            NumStrLanguage.Farsi) +
                                        " توکن جهت تماشای ویدیو و محتوای آموزشی، از شما کسر خواهد شد.",
                                    true, () async {
                                  int i = await buyTutorial(context, e.id);
                                  if (i == 1) {
                                    i = await getClientToken(context);
                                    if (i == 1) {
                                      Navigator.of(context).push(
                                          MaterialPageRoute(
                                              builder: (context) =>
                                                  TutorialVideoPage(e)));
                                    } else if (i == 0){
                                      showAdjustDialog(context, FAILURE, false, null);
                                    }
                                  } else if (i == 0) {
                                    Navigator.of(context).pop();
                                  }
                                });
                              }

                            }
                          },
                        );
                      }),
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}
