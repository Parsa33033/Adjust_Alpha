export interface IAdjustMeal {
  id?: number;
  name?: string;
  number?: number;
  lowFatDairyUnit?: number;
  mediumFatDairyUnit?: number;
  highFatDairyUnit?: number;
  lowFatMeatUnit?: number;
  mediumFatMeatUnit?: number;
  highFatMeatUnti?: number;
  breadUnit?: number;
  cerealUnit?: number;
  riceUnit?: number;
  pastaUnit?: number;
  fruitUnit?: number;
  vegetableUnit?: number;
  fatUnit?: number;
}

export const defaultValue: Readonly<IAdjustMeal> = {};
