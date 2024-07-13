import dayjs from 'dayjs';
import { Day } from 'app/shared/model/enumerations/day.model';

export interface ITimetable {
  id?: number;
  appUserId?: number | null;
  dayOfWeek?: keyof typeof Day | null;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  activity?: string | null;
}

export const defaultValue: Readonly<ITimetable> = {};
