import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserMast from './user-mast';
import UserMastDetail from './user-mast-detail';
import UserMastUpdate from './user-mast-update';
import UserMastDeleteDialog from './user-mast-delete-dialog';

const UserMastRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserMast />} />
    <Route path="new" element={<UserMastUpdate />} />
    <Route path=":id">
      <Route index element={<UserMastDetail />} />
      <Route path="edit" element={<UserMastUpdate />} />
      <Route path="delete" element={<UserMastDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserMastRoutes;
