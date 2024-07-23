import dayjs from 'dayjs';

export interface IFreeTime {
  id?: number;
  start?: dayjs.Dayjs | null;
  end?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IFreeTime> = {};
