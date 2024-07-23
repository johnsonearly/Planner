import dayjs from 'dayjs';
import { Importance } from 'app/shared/model/enumerations/importance.model';

export interface ITimetable {
  id?: number;
  appUserId?: number | null;
  dayOfWeek?: string | null;
  dateOfActivity?: dayjs.Dayjs | null;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  activity?: string | null;
  isDone?: boolean | null;
  levelOfImportance?: keyof typeof Importance | null;
}

export const defaultValue: Readonly<ITimetable> = {
  isDone: false,
};
