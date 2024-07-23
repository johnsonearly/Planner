import appUser from 'app/entities/app-user/app-user.reducer';
import timetable from 'app/entities/timetable/timetable.reducer';
import course from 'app/entities/course/course.reducer';
import freeTime from 'app/entities/free-time/free-time.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  appUser,
  timetable,
  course,
  freeTime,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
