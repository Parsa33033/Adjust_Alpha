import 'package:adjust_client/model/meal.dart';
import 'package:json_annotation/json_annotation.dart';

part 'meal_dto.g.dart';

@JsonSerializable()
class MealDTO extends Meal {
  MealDTO(
      int id,
      String name,
      int number,
      int lowFatDairyUnit,
      int mediumFatDairyUnit,
      int highFatDairyUnit,
      int lowFatMeatUnit,
      int mediumFatMeatUnit,
      int highFatMeatUnit,
      int breadUnit,
      int cerealUnit,
      int riceUnit,
      int pastaUnit,
      int fruitUnit,
      int vegetableUnit,
      int fatUnit,
      int nutritionProgramId)
      : super(
            id,
            name,
            number,
            lowFatDairyUnit,
            mediumFatDairyUnit,
            highFatDairyUnit,
            lowFatMeatUnit,
            mediumFatMeatUnit,
            highFatMeatUnit,
            breadUnit,
            cerealUnit,
            riceUnit,
            pastaUnit,
            fruitUnit,
            vegetableUnit,
            fatUnit,
            nutritionProgramId);

  factory MealDTO.fromJson(Map<String, dynamic> json) =>
      _$MealDTOFromJson(json);

  Map<String, dynamic> toJson() => _$MealDTOToJson(this);
}
