import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AutoPostAprvlUsrs from './auto-post-aprvl-usrs';
import AutoPostAprvlUsrsDetail from './auto-post-aprvl-usrs-detail';
import AutoPostAprvlUsrsUpdate from './auto-post-aprvl-usrs-update';
import AutoPostAprvlUsrsDeleteDialog from './auto-post-aprvl-usrs-delete-dialog';

const AutoPostAprvlUsrsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AutoPostAprvlUsrs />} />
    <Route path="new" element={<AutoPostAprvlUsrsUpdate />} />
    <Route path=":id">
      <Route index element={<AutoPostAprvlUsrsDetail />} />
      <Route path="edit" element={<AutoPostAprvlUsrsUpdate />} />
      <Route path="delete" element={<AutoPostAprvlUsrsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AutoPostAprvlUsrsRoutes;
