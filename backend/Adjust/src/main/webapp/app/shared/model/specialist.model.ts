import { Moment } from 'moment';
import { IAdjustProgram } from 'app/shared/model/adjust-program.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ISpecialist {
  id?: number;
  username?: string;
  firstName?: string;
  lastName?: string;
  birth?: string;
  gender?: Gender;
  stars?: number;
  imageContentType?: string;
  image?: any;
  busy?: boolean;
  programs?: IAdjustProgram[];
  conversationId?: number;
}

export const defaultValue: Readonly<ISpecialist> = {
  busy: false,
};
