import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/app-user">
        App User
      </MenuItem>
      <MenuItem icon="asterisk" to="/timetable">
        Timetable
      </MenuItem>
      <MenuItem icon="asterisk" to="/course">
        Course
      </MenuItem>
      <MenuItem icon="asterisk" to="/free-time">
        Free Time
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
