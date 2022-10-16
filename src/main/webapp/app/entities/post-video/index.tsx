import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PostVideo from './post-video';
import PostVideoDetail from './post-video-detail';
import PostVideoUpdate from './post-video-update';
import PostVideoDeleteDialog from './post-video-delete-dialog';

const PostVideoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PostVideo />} />
    <Route path="new" element={<PostVideoUpdate />} />
    <Route path=":id">
      <Route index element={<PostVideoDetail />} />
      <Route path="edit" element={<PostVideoUpdate />} />
      <Route path="delete" element={<PostVideoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PostVideoRoutes;
