import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './org-details.reducer';

export const OrgDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orgDetailsEntity = useAppSelector(state => state.orgDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orgDetailsDetailsHeading">
          <Translate contentKey="groupfaceApp.orgDetails.detail.title">OrgDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.orgDetails.id">Id</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.id}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.orgDetails.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.orgId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.orgDetails.name">Name</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="groupfaceApp.orgDetails.type">Type</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.type}</dd>
          <dt>
            <span id="keyName">
              <Translate contentKey="groupfaceApp.orgDetails.keyName">Key Name</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.keyName}</dd>
          <dt>
            <span id="keyValue">
              <Translate contentKey="groupfaceApp.orgDetails.keyValue">Key Value</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.keyValue}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.orgDetails.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.orgDetails.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.orgDetails.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.orgDetails.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {orgDetailsEntity.addedOn ? <TextFormat value={orgDetailsEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.orgDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.orgDetails.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {orgDetailsEntity.updatedOn ? (
              <TextFormat value={orgDetailsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.orgDetails.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.orgDetails.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {orgDetailsEntity.approvedOn ? (
              <TextFormat value={orgDetailsEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="extraFields">
              <Translate contentKey="groupfaceApp.orgDetails.extraFields">Extra Fields</Translate>
            </span>
          </dt>
          <dd>{orgDetailsEntity.extraFields}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.orgDetails.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{orgDetailsEntity.orgIdObj ? orgDetailsEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/org-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/org-details/${orgDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrgDetailsDetail;
