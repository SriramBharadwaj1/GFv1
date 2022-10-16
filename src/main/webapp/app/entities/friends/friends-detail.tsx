import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './friends.reducer';

export const FriendsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const friendsEntity = useAppSelector(state => state.friends.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="friendsDetailsHeading">
          <Translate contentKey="groupfaceApp.friends.detail.title">Friends</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.friends.id">Id</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="groupfaceApp.friends.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.userId}</dd>
          <dt>
            <span id="friendId">
              <Translate contentKey="groupfaceApp.friends.friendId">Friend Id</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.friendId}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.friends.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.friends.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.friends.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.friends.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.addedOn ? <TextFormat value={friendsEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.friends.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.friends.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {friendsEntity.updatedOn ? <TextFormat value={friendsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.friends.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.friends.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {friendsEntity.approvedOn ? <TextFormat value={friendsEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.friends.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.friends.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.remarks}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.friends.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.orgId}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="groupfaceApp.friends.type">Type</Translate>
            </span>
          </dt>
          <dd>{friendsEntity.type}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.friends.addedByUser">Added By User</Translate>
          </dt>
          <dd>{friendsEntity.addedByUser ? friendsEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.friends.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{friendsEntity.updatedByUser ? friendsEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.friends.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{friendsEntity.approvedByUser ? friendsEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.friends.userIdObj">User Id Obj</Translate>
          </dt>
          <dd>{friendsEntity.userIdObj ? friendsEntity.userIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.friends.friendIdObj">Friend Id Obj</Translate>
          </dt>
          <dd>{friendsEntity.friendIdObj ? friendsEntity.friendIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.friends.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{friendsEntity.orgIdObj ? friendsEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/friends" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/friends/${friendsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FriendsDetail;
