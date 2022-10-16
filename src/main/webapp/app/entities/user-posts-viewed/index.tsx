import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserPostsViewed from './user-posts-viewed';
import UserPostsViewedDetail from './user-posts-viewed-detail';
import UserPostsViewedUpdate from './user-posts-viewed-update';
import UserPostsViewedDeleteDialog from './user-posts-viewed-delete-dialog';

const UserPostsViewedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserPostsViewed />} />
    <Route path="new" element={<UserPostsViewedUpdate />} />
    <Route path=":id">
      <Route index element={<UserPostsViewedDetail />} />
      <Route path="edit" element={<UserPostsViewedUpdate />} />
      <Route path="delete" element={<UserPostsViewedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserPostsViewedRoutes;
