import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Organisation from './organisation';
import OrgDetails from './org-details';
import UserMast from './user-mast';
import Address from './address';
import Groups from './groups';
import GroupUser from './group-user';
import Friends from './friends';
import PostPics from './post-pics';
import PostVideo from './post-video';
import AutoPostAprvlUsrs from './auto-post-aprvl-usrs';
import Masters from './masters';
import Caste from './caste';
import Location from './location';
import Post from './post';
import UserPostsViewed from './user-posts-viewed';
import Comment from './comment';
import Remarks from './remarks';
import Rating from './rating';
import Events from './events';
import UserActivity from './user-activity';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="organisation/*" element={<Organisation />} />
        <Route path="org-details/*" element={<OrgDetails />} />
        <Route path="user-mast/*" element={<UserMast />} />
        <Route path="address/*" element={<Address />} />
        <Route path="groups/*" element={<Groups />} />
        <Route path="group-user/*" element={<GroupUser />} />
        <Route path="friends/*" element={<Friends />} />
        <Route path="post-pics/*" element={<PostPics />} />
        <Route path="post-video/*" element={<PostVideo />} />
        <Route path="auto-post-aprvl-usrs/*" element={<AutoPostAprvlUsrs />} />
        <Route path="masters/*" element={<Masters />} />
        <Route path="caste/*" element={<Caste />} />
        <Route path="location/*" element={<Location />} />
        <Route path="post/*" element={<Post />} />
        <Route path="user-posts-viewed/*" element={<UserPostsViewed />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="remarks/*" element={<Remarks />} />
        <Route path="rating/*" element={<Rating />} />
        <Route path="events/*" element={<Events />} />
        <Route path="user-activity/*" element={<UserActivity />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
