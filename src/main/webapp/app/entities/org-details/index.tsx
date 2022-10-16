import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OrgDetails from './org-details';
import OrgDetailsDetail from './org-details-detail';
import OrgDetailsUpdate from './org-details-update';
import OrgDetailsDeleteDialog from './org-details-delete-dialog';

const OrgDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OrgDetails />} />
    <Route path="new" element={<OrgDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<OrgDetailsDetail />} />
      <Route path="edit" element={<OrgDetailsUpdate />} />
      <Route path="delete" element={<OrgDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrgDetailsRoutes;
