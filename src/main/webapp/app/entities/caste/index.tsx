import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Caste from './caste';
import CasteDetail from './caste-detail';
import CasteUpdate from './caste-update';
import CasteDeleteDialog from './caste-delete-dialog';

const CasteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Caste />} />
    <Route path="new" element={<CasteUpdate />} />
    <Route path=":id">
      <Route index element={<CasteDetail />} />
      <Route path="edit" element={<CasteUpdate />} />
      <Route path="delete" element={<CasteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CasteRoutes;
