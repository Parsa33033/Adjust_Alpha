// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'meal_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

MealDTO _$MealDTOFromJson(Map<String, dynamic> json) {
  return MealDTO(
    json['id'] as int,
    json['name'] as String,
    json['number'] as int,
    json['lowFatDairyUnit'] as int,
    json['mediumFatDairyUnit'] as int,
    json['highFatDairyUnit'] as int,
    json['lowFatMeatUnit'] as int,
    json['mediumFatMeatUnit'] as int,
    json['highFatMeatUnit'] as int,
    json['breadUnit'] as int,
    json['cerealUnit'] as int,
    json['riceUnit'] as int,
    json['pastaUnit'] as int,
    json['fruitUnit'] as int,
    json['vegetableUnit'] as int,
    json['fatUnit'] as int,
    json['nutritionProgramId'] as int,
  );
}

Map<String, dynamic> _$MealDTOToJson(MealDTO instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'number': instance.number,
      'lowFatDairyUnit': instance.lowFatDairyUnit,
      'mediumFatDairyUnit': instance.mediumFatDairyUnit,
      'highFatDairyUnit': instance.highFatDairyUnit,
      'lowFatMeatUnit': instance.lowFatMeatUnit,
      'mediumFatMeatUnit': instance.mediumFatMeatUnit,
      'highFatMeatUnit': instance.highFatMeatUnit,
      'breadUnit': instance.breadUnit,
      'cerealUnit': instance.cerealUnit,
      'riceUnit': instance.riceUnit,
      'pastaUnit': instance.pastaUnit,
      'fruitUnit': instance.fruitUnit,
      'vegetableUnit': instance.vegetableUnit,
      'fatUnit': instance.fatUnit,
      'nutritionProgramId': instance.nutritionProgramId,
    };
