

import 'package:adjust_client/constants/adjust_colors.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:adjust_client/states/nutrition_program_state.dart';
import 'package:adjust_client/states/program_state.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:spincircle_bottom_bar/modals.dart';
import 'package:spincircle_bottom_bar/spincircle_bottom_bar.dart';

class NutritionProgramPage extends StatefulWidget {
  int programIndex;
  NutritionProgramPage(this.programIndex);
  @override
  _NutritionProgramPageState createState() => _NutritionProgramPageState();
}

class _NutritionProgramPageState extends State<NutritionProgramPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StoreConnector<AppState, AppState>(
        converter: (Store store) => store.state,
        builder: (BuildContext context, AppState state) {
          ProgramState programState = state.programListState.programs[this.widget.programIndex];
          return SpinCircleBottomBarHolder(
            bottomNavigationBar: SCBottomBarDetails(
                circleColors: [Colors.white, Colors.orange, Colors.redAccent],
                iconTheme: IconThemeData(color: Colors.black45),
                activeIconTheme: IconThemeData(color: Colors.orange),
                backgroundColor: Colors.white,
                titleStyle: TextStyle(color: Colors.black45,fontSize: 12),
                activeTitleStyle: TextStyle(color: Colors.black,fontSize: 12,fontWeight: FontWeight.bold),
                actionButtonDetails: SCActionButtonDetails(
                    color: Colors.redAccent,
                    icon: Icon(
                      Icons.expand_less,
                      color: Colors.white,
                    ),
                    elevation: 2),
                elevation: 2.0,
                items: [
                  // Suggested count : 4
                  SCBottomBarItem(icon: Icons.verified_user, title: "User", onPressed: () {}),
                  SCBottomBarItem(icon: Icons.supervised_user_circle, title: "Details", onPressed: () {}),
                  SCBottomBarItem(icon: Icons.notifications, title: "Notifications", onPressed: () {}),
                  SCBottomBarItem(icon: Icons.details, title: "New Data", onPressed: () {}),
                ],
                circleItems: [
                  //Suggested Count: 3
                  SCItem(icon: Icon(Icons.add), onPressed: () {print("add");}),
                  SCItem(icon: Icon(Icons.print), onPressed: () {}),
                  SCItem(icon: Icon(Icons.map), onPressed: () {}),
                ],
                bnbHeight: 80 // Suggested Height 80
            ),
            // Put your Screen Content in Child
            child: Container(
              padding: EdgeInsets.all(20),
              alignment: Alignment.topCenter,
              color: Colors.orangeAccent.withAlpha(55),
              child: DataTable(
                columns: <DataColumn>[
                  DataColumn(
                    label: Directionality(
                      textDirection: TextDirection.rtl,
                      child: Text("تعداد واحد", style: TextStyle(fontFamily: "Iransans", fontSize: 16, color: FONT_COLOR),),
                    ),
                    numeric: false,
                  ),
                  DataColumn(
                    label: Align(
                      alignment: Alignment.centerRight,
                      child: Directionality(
                        textDirection: TextDirection.rtl,
                        child: Text("ماده غذایی", style: TextStyle(fontFamily: "Iransans", fontSize: 16, color: FONT_COLOR),),
                      ),
                    ),
                    numeric: false,
                  )
                ],
                rows: programState.nutritionProgramState.meals.map((e) => DataRow(
                    cells: <DataCell>[
                      DataCell(Align(
                        alignment: Alignment.centerRight,
                        child: Directionality(
                          textDirection: TextDirection.rtl,
                          child: Text(e., style: TextStyle(fontFamily: "Iransans", fontSize: 16, color: FONT_COLOR),),
                        ),
                      ),),
                      DataCell(Align(
                        alignment: Alignment.centerRight,
                        child: Directionality(
                          textDirection: TextDirection.rtl,
                          child: Text("ماده", style: TextStyle(fontFamily: "Iransans", fontSize: 16, color: FONT_COLOR),),
                        ),
                      ),),

                    ]
                ))

              )
            ),
          );
        },
      )
    );
  }
}