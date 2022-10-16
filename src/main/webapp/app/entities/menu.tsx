import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/organisation">
        <Translate contentKey="global.menu.entities.organisation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/org-details">
        <Translate contentKey="global.menu.entities.orgDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-mast">
        <Translate contentKey="global.menu.entities.userMast" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/groups">
        <Translate contentKey="global.menu.entities.groups" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/group-user">
        <Translate contentKey="global.menu.entities.groupUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/friends">
        <Translate contentKey="global.menu.entities.friends" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/post-pics">
        <Translate contentKey="global.menu.entities.postPics" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/post-video">
        <Translate contentKey="global.menu.entities.postVideo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/auto-post-aprvl-usrs">
        <Translate contentKey="global.menu.entities.autoPostAprvlUsrs" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/masters">
        <Translate contentKey="global.menu.entities.masters" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/caste">
        <Translate contentKey="global.menu.entities.caste" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        <Translate contentKey="global.menu.entities.location" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/post">
        <Translate contentKey="global.menu.entities.post" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-posts-viewed">
        <Translate contentKey="global.menu.entities.userPostsViewed" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        <Translate contentKey="global.menu.entities.comment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/remarks">
        <Translate contentKey="global.menu.entities.remarks" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/rating">
        <Translate contentKey="global.menu.entities.rating" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/events">
        <Translate contentKey="global.menu.entities.events" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-activity">
        <Translate contentKey="global.menu.entities.userActivity" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
