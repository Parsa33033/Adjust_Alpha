import { IWorkout } from 'app/shared/model/workout.model';

export interface IFitnessProgram {
  id?: number;
  type?: string;
  description?: string;
  workouts?: IWorkout[];
  programId?: number;
}

export const defaultValue: Readonly<IFitnessProgram> = {};
