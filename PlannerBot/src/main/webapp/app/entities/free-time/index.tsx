import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FreeTime from './free-time';
import FreeTimeDetail from './free-time-detail';
import FreeTimeUpdate from './free-time-update';
import FreeTimeDeleteDialog from './free-time-delete-dialog';

const FreeTimeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FreeTime />} />
    <Route path="new" element={<FreeTimeUpdate />} />
    <Route path=":id">
      <Route index element={<FreeTimeDetail />} />
      <Route path="edit" element={<FreeTimeUpdate />} />
      <Route path="delete" element={<FreeTimeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FreeTimeRoutes;
