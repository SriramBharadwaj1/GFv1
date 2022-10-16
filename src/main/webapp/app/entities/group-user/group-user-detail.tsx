import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './group-user.reducer';

export const GroupUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const groupUserEntity = useAppSelector(state => state.groupUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groupUserDetailsHeading">
          <Translate contentKey="groupfaceApp.groupUser.detail.title">GroupUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.groupUser.id">Id</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.id}</dd>
          <dt>
            <span id="groupId">
              <Translate contentKey="groupfaceApp.groupUser.groupId">Group Id</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.groupId}</dd>
          <dt>
            <span id="grpUser">
              <Translate contentKey="groupfaceApp.groupUser.grpUser">Grp User</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.grpUser}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.groupUser.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.groupUser.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.groupUser.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.groupUser.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {groupUserEntity.addedOn ? <TextFormat value={groupUserEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.groupUser.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.groupUser.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {groupUserEntity.updatedOn ? <TextFormat value={groupUserEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.groupUser.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.groupUser.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {groupUserEntity.approvedOn ? (
              <TextFormat value={groupUserEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.groupUser.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.groupUser.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.remarks}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.groupUser.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.orgId}</dd>
          <dt>
            <span id="userType">
              <Translate contentKey="groupfaceApp.groupUser.userType">User Type</Translate>
            </span>
          </dt>
          <dd>{groupUserEntity.userType}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groupUser.addedByUser">Added By User</Translate>
          </dt>
          <dd>{groupUserEntity.addedByUser ? groupUserEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groupUser.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{groupUserEntity.updatedByUser ? groupUserEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groupUser.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{groupUserEntity.approvedByUser ? groupUserEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groupUser.groupIdObj">Group Id Obj</Translate>
          </dt>
          <dd>{groupUserEntity.groupIdObj ? groupUserEntity.groupIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groupUser.grpUserObj">Grp User Obj</Translate>
          </dt>
          <dd>{groupUserEntity.grpUserObj ? groupUserEntity.grpUserObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groupUser.userTypeObj">User Type Obj</Translate>
          </dt>
          <dd>{groupUserEntity.userTypeObj ? groupUserEntity.userTypeObj.codeval : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groupUser.orgIdOb">Org Id Ob</Translate>
          </dt>
          <dd>{groupUserEntity.orgIdOb ? groupUserEntity.orgIdOb.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/group-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/group-user/${groupUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroupUserDetail;
