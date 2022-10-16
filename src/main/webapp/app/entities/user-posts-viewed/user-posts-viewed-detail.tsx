import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-posts-viewed.reducer';

export const UserPostsViewedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userPostsViewedEntity = useAppSelector(state => state.userPostsViewed.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userPostsViewedDetailsHeading">
          <Translate contentKey="groupfaceApp.userPostsViewed.detail.title">UserPostsViewed</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.userPostsViewed.id">Id</Translate>
            </span>
          </dt>
          <dd>{userPostsViewedEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="groupfaceApp.userPostsViewed.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{userPostsViewedEntity.userId}</dd>
          <dt>
            <span id="postid">
              <Translate contentKey="groupfaceApp.userPostsViewed.postid">Postid</Translate>
            </span>
          </dt>
          <dd>{userPostsViewedEntity.postid}</dd>
          <dt>
            <span id="viewdate">
              <Translate contentKey="groupfaceApp.userPostsViewed.viewdate">Viewdate</Translate>
            </span>
          </dt>
          <dd>
            {userPostsViewedEntity.viewdate ? (
              <TextFormat value={userPostsViewedEntity.viewdate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="groupfaceApp.userPostsViewed.status">Status</Translate>
            </span>
          </dt>
          <dd>{userPostsViewedEntity.status}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.userPostsViewed.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{userPostsViewedEntity.orgId}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.userPostsViewed.userIdObj">User Id Obj</Translate>
          </dt>
          <dd>{userPostsViewedEntity.userIdObj ? userPostsViewedEntity.userIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.userPostsViewed.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{userPostsViewedEntity.orgIdObj ? userPostsViewedEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-posts-viewed" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-posts-viewed/${userPostsViewedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserPostsViewedDetail;
