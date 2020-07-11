import { IMeal } from 'app/shared/model/meal.model';

export interface INutritionProgram {
  id?: number;
  dailyCalories?: number;
  proteinPercentage?: number;
  carbsPercentage?: number;
  fatPercentage?: number;
  meals?: IMeal[];
  programId?: number;
}

export const defaultValue: Readonly<INutritionProgram> = {};
