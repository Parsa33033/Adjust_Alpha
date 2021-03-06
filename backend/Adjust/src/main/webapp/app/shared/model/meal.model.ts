export interface IMeal {
  id?: number;
  name?: string;
  number?: number;
  lowFatDairyUnit?: number;
  mediumFatDairyUnit?: number;
  highFatDairyUnit?: number;
  lowFatMeatUnit?: number;
  mediumFatMeatUnit?: number;
  highFatMeatUnit?: number;
  breadUnit?: number;
  cerealUnit?: number;
  riceUnit?: number;
  pastaUnit?: number;
  fruitUnit?: number;
  vegetableUnit?: number;
  fatUnit?: number;
  nutritionProgramId?: number;
}

export const defaultValue: Readonly<IMeal> = {};
