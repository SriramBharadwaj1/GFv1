import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Remarks from './remarks';
import RemarksDetail from './remarks-detail';
import RemarksUpdate from './remarks-update';
import RemarksDeleteDialog from './remarks-delete-dialog';

const RemarksRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Remarks />} />
    <Route path="new" element={<RemarksUpdate />} />
    <Route path=":id">
      <Route index element={<RemarksDetail />} />
      <Route path="edit" element={<RemarksUpdate />} />
      <Route path="delete" element={<RemarksDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RemarksRoutes;
