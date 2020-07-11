
import 'package:adjust_client/components/adjust_raised_button.dart';
import 'package:adjust_client/config/adjust_colors.dart';
import 'package:adjust_client/constants/words.dart';
import 'package:flutter/material.dart';

void showAdjustDialog(BuildContext context, String text, bool isChoice, Function func) {
  showDialog(
    context: context,
    child: Dialog(
     child: Container(
       padding: EdgeInsets.all(20),
       height: 400,
       child: Column(
         mainAxisAlignment: MainAxisAlignment.spaceEvenly,
         children: <Widget>[
           Directionality(
             textDirection: TextDirection.rtl,
             child: Text(text, style: TextStyle(fontFamily: "Iransans", fontSize: 18, color: FONT),),
           ),
           isChoice
           ?
               Row(
                 mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                 children: <Widget>[
                   Container(
                       child: AdjustRaisedButton(
                         textDirection: TextDirection.rtl,
                         height: 40,
                         width: 80,
                         primaryColor: GREEN,
                         secondaryColor: GREEN,
                         text: CANCEL,
                         onPressed: () {
                           Navigator.of(context, rootNavigator: true).pop("dialog");
                         },
                       )
                   ),
                   Container(
                       child: AdjustRaisedButton(
                         textDirection: TextDirection.rtl,
                         height: 40,
                         width: 80,
                         primaryColor: GREEN,
                         secondaryColor: GREEN,
                         text: OK,
                         onPressed: () {
                           Navigator.of(context, rootNavigator: true).pop("dialog");
                           func();
                         },
                       )
                   )
                 ],
               )
           :
               Container(
                 child: AdjustRaisedButton(
                   textDirection: TextDirection.rtl,
                   height: 40,
                   width: 80,
                   primaryColor: GREEN,
                   secondaryColor: GREEN,
                   text: OK,
                   onPressed: () {
                     Navigator.of(context, rootNavigator: true).pop("dialog");
                   },
                 )
               )
         ],
       ),
     ),
    )
  );
}