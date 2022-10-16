import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-activity.reducer';

export const UserActivityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userActivityEntity = useAppSelector(state => state.userActivity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userActivityDetailsHeading">
          <Translate contentKey="groupfaceApp.userActivity.detail.title">UserActivity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.userActivity.id">Id</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="groupfaceApp.userActivity.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.userId}</dd>
          <dt>
            <span id="loggedon">
              <Translate contentKey="groupfaceApp.userActivity.loggedon">Loggedon</Translate>
            </span>
          </dt>
          <dd>
            {userActivityEntity.loggedon ? (
              <TextFormat value={userActivityEntity.loggedon} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="activeduration">
              <Translate contentKey="groupfaceApp.userActivity.activeduration">Activeduration</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.activeduration}</dd>
          <dt>
            <span id="ipAdr">
              <Translate contentKey="groupfaceApp.userActivity.ipAdr">Ip Adr</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.ipAdr}</dd>
          <dt>
            <span id="usrLocation">
              <Translate contentKey="groupfaceApp.userActivity.usrLocation">Usr Location</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.usrLocation}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="groupfaceApp.userActivity.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.lat}</dd>
          <dt>
            <span id="longi">
              <Translate contentKey="groupfaceApp.userActivity.longi">Longi</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.longi}</dd>
          <dt>
            <span id="pin">
              <Translate contentKey="groupfaceApp.userActivity.pin">Pin</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.pin}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.userActivity.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.addedBy}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.userActivity.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{userActivityEntity.orgId}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.userActivity.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {userActivityEntity.addedOn ? (
              <TextFormat value={userActivityEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="groupfaceApp.userActivity.addedByUser">Added By User</Translate>
          </dt>
          <dd>{userActivityEntity.addedByUser ? userActivityEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.userActivity.userObj">User Obj</Translate>
          </dt>
          <dd>{userActivityEntity.userObj ? userActivityEntity.userObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.userActivity.addedByUseract">Added By Useract</Translate>
          </dt>
          <dd>{userActivityEntity.addedByUseract ? userActivityEntity.addedByUseract.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.userActivity.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{userActivityEntity.orgIdObj ? userActivityEntity.orgIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.userActivity.useractObj">Useract Obj</Translate>
          </dt>
          <dd>{userActivityEntity.useractObj ? userActivityEntity.useractObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-activity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-activity/${userActivityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserActivityDetail;
