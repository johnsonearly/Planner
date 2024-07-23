import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface ICourse {
  id?: number;
  appUserId?: number | null;
  courseName?: string | null;
  difficulty?: keyof typeof Difficulty | null;
}

export const defaultValue: Readonly<ICourse> = {};
