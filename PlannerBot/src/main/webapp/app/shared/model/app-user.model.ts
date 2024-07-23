import { Chronotype } from 'app/shared/model/enumerations/chronotype.model';
import { ReadingType } from 'app/shared/model/enumerations/reading-type.model';
import { AttentionSpan } from 'app/shared/model/enumerations/attention-span.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { ReadingStrategy } from 'app/shared/model/enumerations/reading-strategy.model';

export interface IAppUser {
  id?: number;
  name?: string | null;
  age?: number | null;
  appUserId?: number | null;
  chronotype?: keyof typeof Chronotype | null;
  readingType?: keyof typeof ReadingType | null;
  attentionSpan?: keyof typeof AttentionSpan | null;
  gender?: keyof typeof Gender | null;
  readingStrategy?: keyof typeof ReadingStrategy | null;
}

export const defaultValue: Readonly<IAppUser> = {};
