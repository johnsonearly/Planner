import appUser from 'app/entities/app-user/app-user.reducer';
import timetable from 'app/entities/timetable/timetable.reducer';
import course from 'app/entities/course/course.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  appUser,
  timetable,
  course,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
