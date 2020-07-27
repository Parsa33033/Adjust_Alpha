import 'package:adjust_client/model/meal.dart';
import 'package:adjust_client/model/nutrition_program.dart';
import 'package:adjust_client/states/meal_state.dart';

class NutritionProgramState extends NutritionProgram {
  List<Meal> meals;
  NutritionProgramState(int id, double dailyCalories, int proteinPercentage,
      int carbsPercentage, int fatPercentage, this.meals)
      : super(id, dailyCalories, proteinPercentage, carbsPercentage,
            fatPercentage);
}
