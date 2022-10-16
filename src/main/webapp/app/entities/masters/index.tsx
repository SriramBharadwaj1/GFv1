import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Masters from './masters';
import MastersDetail from './masters-detail';
import MastersUpdate from './masters-update';
import MastersDeleteDialog from './masters-delete-dialog';

const MastersRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Masters />} />
    <Route path="new" element={<MastersUpdate />} />
    <Route path=":id">
      <Route index element={<MastersDetail />} />
      <Route path="edit" element={<MastersUpdate />} />
      <Route path="delete" element={<MastersDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MastersRoutes;
