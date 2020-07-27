import 'dart:convert';
import 'dart:io';

import 'package:adjust_client/actions/jwt.dart';
import 'package:adjust_client/constants/urls.dart';
import 'package:adjust_client/dto/body_composition_dto.dart';
import 'package:adjust_client/dto/fitness_program_dto.dart';
import 'package:adjust_client/dto/nutrition_program_dto.dart';
import 'package:adjust_client/dto/program_dto.dart';
import 'package:adjust_client/main.dart';
import 'package:adjust_client/states/app_state.dart';
import 'package:adjust_client/states/body_composition_state.dart';
import 'package:adjust_client/states/exercise_state.dart';
import 'package:adjust_client/states/fitness_program_state.dart';
import 'package:adjust_client/states/nutrition_program_state.dart';
import 'package:adjust_client/states/program_state.dart';
import 'package:adjust_client/states/workout_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:http/http.dart' as http;

class GetProgramListAction {
  ProgramListState payload;

  GetProgramListAction({this.payload});
}

Future<int> requestForProgram(
    BuildContext context, ProgramDTO programDTO) async {
  String jwt = await getJwt(context);

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt)
    ..putIfAbsent("Content-Type", () => "application/json");

  String content = jsonEncode(programDTO.toJson());

  http.Response response = await http.post(REQUEST_PROGRAM_URL,
      headers: headers, body: content, encoding: Encoding.getByName("UTF-8"));
  if (response.statusCode == HttpStatus.ok) {
    // set a get all requests later
    return 1;
  }
  return 0;
}

Future<int> getClientPrograms(BuildContext context) async {
  String jwt = await getJwt(context);

  Map<String, String> headers = Map<String, String>()
    ..putIfAbsent("Authorization", () => "Bearer " + jwt);

  http.Response response = await http.get(PROGRAM_URL, headers: headers);
  if (response.statusCode == HttpStatus.ok) {
    List l = jsonDecode(utf8.decode(response.bodyBytes));
    List<ProgramState> programList = l.map((e) {
      ProgramDTO programDTO = ProgramDTO.fromJson(e);

      BodyCompositionDTO bodyCompositionDTO = programDTO.bodyComposition;
      BodyCompositionState bodyCompositionState = BodyCompositionState(
          bodyCompositionDTO.id,
          bodyCompositionDTO.createdAt,
          bodyCompositionDTO.height,
          bodyCompositionDTO.weight,
          bodyCompositionDTO.bmi,
          bodyCompositionDTO.bodyCompositionFile,
          bodyCompositionDTO.bodyCompositionFileContentType,
          bodyCompositionDTO.bloodTestFile,
          bodyCompositionDTO.bloodTestFileContentType);

      NutritionProgramDTO nutritionProgramDTO = programDTO.nutritionProgram;
      NutritionProgramState nutritionProgramState = NutritionProgramState(
          nutritionProgramDTO.id,
          nutritionProgramDTO.dailyCalories,
          nutritionProgramDTO.proteinPercentage,
          nutritionProgramDTO.carbsPercentage,
          nutritionProgramDTO.fatPercentage,
          nutritionProgramDTO.meals);

      FitnessProgramDTO fitnessProgramDTO = programDTO.fitnessProgram;
      List<WorkoutState> workoutStateList = fitnessProgramDTO.workouts.map((e) {
        List<ExerciseState> exerciseStateList = e.exercises.map((e) {
          return ExerciseState(e.id, e.number, e.sets, e.repsMin, e.repsMax,
              e.moveId, e.workoutId, e.move);
        }).toList();
        WorkoutState workoutState =
            WorkoutState(e.id, e.dayNumber, e.programId, exerciseStateList);
        return workoutState;
      }).toList();
      FitnessProgramState fitnessProgramState = FitnessProgramState(
          fitnessProgramDTO.id,
          fitnessProgramDTO.type,
          fitnessProgramDTO.description,
          workoutStateList);

      ProgramState programState = ProgramState(
          programDTO.id,
          programDTO.createdAt,
          programDTO.expirationDate,
          programDTO.designed,
          programDTO.done,
          programDTO.paid,
          programDTO.bodyCompostionId,
          programDTO.fitnessProgramId,
          programDTO.nutritionProgramId,
          programDTO.clientId,
          programDTO.specialistId,
          programDTO.client,
          programDTO.specialist,
          bodyCompositionState,
          nutritionProgramState,
          fitnessProgramState);
      return programState;
    }).toList();
    try {
      StoreProvider.of<AppState>(context).dispatch(
          GetProgramListAction(payload: ProgramListState(programList)));
      return 1;
    } catch (e) {
      store.dispatch(
          GetProgramListAction(payload: ProgramListState(programList)));
      return 1;
    }
  }
  return 0;
}
