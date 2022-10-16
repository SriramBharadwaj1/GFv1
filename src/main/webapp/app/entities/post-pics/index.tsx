import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PostPics from './post-pics';
import PostPicsDetail from './post-pics-detail';
import PostPicsUpdate from './post-pics-update';
import PostPicsDeleteDialog from './post-pics-delete-dialog';

const PostPicsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PostPics />} />
    <Route path="new" element={<PostPicsUpdate />} />
    <Route path=":id">
      <Route index element={<PostPicsDetail />} />
      <Route path="edit" element={<PostPicsUpdate />} />
      <Route path="delete" element={<PostPicsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PostPicsRoutes;
