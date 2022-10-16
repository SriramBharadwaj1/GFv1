import organisation from 'app/entities/organisation/organisation.reducer';
import orgDetails from 'app/entities/org-details/org-details.reducer';
import userMast from 'app/entities/user-mast/user-mast.reducer';
import address from 'app/entities/address/address.reducer';
import groups from 'app/entities/groups/groups.reducer';
import groupUser from 'app/entities/group-user/group-user.reducer';
import friends from 'app/entities/friends/friends.reducer';
import postPics from 'app/entities/post-pics/post-pics.reducer';
import postVideo from 'app/entities/post-video/post-video.reducer';
import autoPostAprvlUsrs from 'app/entities/auto-post-aprvl-usrs/auto-post-aprvl-usrs.reducer';
import masters from 'app/entities/masters/masters.reducer';
import caste from 'app/entities/caste/caste.reducer';
import location from 'app/entities/location/location.reducer';
import post from 'app/entities/post/post.reducer';
import userPostsViewed from 'app/entities/user-posts-viewed/user-posts-viewed.reducer';
import comment from 'app/entities/comment/comment.reducer';
import remarks from 'app/entities/remarks/remarks.reducer';
import rating from 'app/entities/rating/rating.reducer';
import events from 'app/entities/events/events.reducer';
import userActivity from 'app/entities/user-activity/user-activity.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  organisation,
  orgDetails,
  userMast,
  address,
  groups,
  groupUser,
  friends,
  postPics,
  postVideo,
  autoPostAprvlUsrs,
  masters,
  caste,
  location,
  post,
  userPostsViewed,
  comment,
  remarks,
  rating,
  events,
  userActivity,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
