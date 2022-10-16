import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './auto-post-aprvl-usrs.reducer';

export const AutoPostAprvlUsrsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const autoPostAprvlUsrsEntity = useAppSelector(state => state.autoPostAprvlUsrs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="autoPostAprvlUsrsDetailsHeading">
          <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.detail.title">AutoPostAprvlUsrs</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.id">Id</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.id}</dd>
          <dt>
            <span id="apUserId">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.apUserId">Ap User Id</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.apUserId}</dd>
          <dt>
            <span id="tableKy">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.tableKy">Table Ky</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.tableKy}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {autoPostAprvlUsrsEntity.addedOn ? (
              <TextFormat value={autoPostAprvlUsrsEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {autoPostAprvlUsrsEntity.updatedOn ? (
              <TextFormat value={autoPostAprvlUsrsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {autoPostAprvlUsrsEntity.approvedOn ? (
              <TextFormat value={autoPostAprvlUsrsEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.remarks}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.orgId}</dd>
          <dt>
            <span id="extraFields">
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.extraFields">Extra Fields</Translate>
            </span>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.extraFields}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.user">User</Translate>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.user ? autoPostAprvlUsrsEntity.user.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.addedByUser">Added By User</Translate>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.addedByUser ? autoPostAprvlUsrsEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.updatedByUser ? autoPostAprvlUsrsEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.approvedByUser ? autoPostAprvlUsrsEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{autoPostAprvlUsrsEntity.orgIdObj ? autoPostAprvlUsrsEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/auto-post-aprvl-usrs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/auto-post-aprvl-usrs/${autoPostAprvlUsrsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AutoPostAprvlUsrsDetail;
