import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './groups.reducer';

export const GroupsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const groupsEntity = useAppSelector(state => state.groups.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groupsDetailsHeading">
          <Translate contentKey="groupfaceApp.groups.detail.title">Groups</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.groups.id">Id</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.groups.name">Name</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="groupfaceApp.groups.description">Description</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.description}</dd>
          <dt>
            <span id="activitysId">
              <Translate contentKey="groupfaceApp.groups.activitysId">Activitys Id</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.activitysId}</dd>
          <dt>
            <span id="groupType">
              <Translate contentKey="groupfaceApp.groups.groupType">Group Type</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.groupType}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.groups.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.groups.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.groups.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.groups.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.addedOn ? <TextFormat value={groupsEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.groups.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.groups.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {groupsEntity.updatedOn ? <TextFormat value={groupsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.groups.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.groups.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {groupsEntity.approvedOn ? <TextFormat value={groupsEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.groups.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.groups.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.remarks}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.groups.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{groupsEntity.orgId}</dd>
          <dt>
            <span id="validFrom">
              <Translate contentKey="groupfaceApp.groups.validFrom">Valid From</Translate>
            </span>
          </dt>
          <dd>
            {groupsEntity.validFrom ? <TextFormat value={groupsEntity.validFrom} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="validTill">
              <Translate contentKey="groupfaceApp.groups.validTill">Valid Till</Translate>
            </span>
          </dt>
          <dd>
            {groupsEntity.validTill ? <TextFormat value={groupsEntity.validTill} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="groupfaceApp.groups.addedByUser">Added By User</Translate>
          </dt>
          <dd>{groupsEntity.addedByUser ? groupsEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groups.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{groupsEntity.updatedByUser ? groupsEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groups.approvedByser">Approved Byser</Translate>
          </dt>
          <dd>{groupsEntity.approvedByser ? groupsEntity.approvedByser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.groups.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{groupsEntity.orgIdObj ? groupsEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/groups" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/groups/${groupsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroupsDetail;
