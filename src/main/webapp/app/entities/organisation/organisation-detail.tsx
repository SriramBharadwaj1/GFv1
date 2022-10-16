import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './organisation.reducer';

export const OrganisationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const organisationEntity = useAppSelector(state => state.organisation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="organisationDetailsHeading">
          <Translate contentKey="groupfaceApp.organisation.detail.title">Organisation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.organisation.id">Id</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="groupfaceApp.organisation.code">Code</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.organisation.name">Name</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="groupfaceApp.organisation.type">Type</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.type}</dd>
          <dt>
            <span id="parentOrgId">
              <Translate contentKey="groupfaceApp.organisation.parentOrgId">Parent Org Id</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.parentOrgId}</dd>
          <dt>
            <span id="primaryContactId">
              <Translate contentKey="groupfaceApp.organisation.primaryContactId">Primary Contact Id</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.primaryContactId}</dd>
          <dt>
            <span id="orgHead">
              <Translate contentKey="groupfaceApp.organisation.orgHead">Org Head</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.orgHead}</dd>
          <dt>
            <span id="locationId">
              <Translate contentKey="groupfaceApp.organisation.locationId">Location Id</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.locationId}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="groupfaceApp.organisation.website">Website</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.website}</dd>
          <dt>
            <span id="layout">
              <Translate contentKey="groupfaceApp.organisation.layout">Layout</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.layout}</dd>
          <dt>
            <span id="tableKy">
              <Translate contentKey="groupfaceApp.organisation.tableKy">Table Ky</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.tableKy}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.organisation.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.organisation.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.organisation.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.organisation.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {organisationEntity.addedOn ? (
              <TextFormat value={organisationEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.organisation.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.organisation.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {organisationEntity.updatedOn ? (
              <TextFormat value={organisationEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.organisation.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.organisation.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {organisationEntity.approvedOn ? (
              <TextFormat value={organisationEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.organisation.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.organisation.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.remarks}</dd>
          <dt>
            <span id="extraFields">
              <Translate contentKey="groupfaceApp.organisation.extraFields">Extra Fields</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.extraFields}</dd>
          <dt>
            <span id="buildFilePath">
              <Translate contentKey="groupfaceApp.organisation.buildFilePath">Build File Path</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.buildFilePath}</dd>
          <dt>
            <span id="shortName">
              <Translate contentKey="groupfaceApp.organisation.shortName">Short Name</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.shortName}</dd>
          <dt>
            <span id="vatNo">
              <Translate contentKey="groupfaceApp.organisation.vatNo">Vat No</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.vatNo}</dd>
          <dt>
            <span id="moduleKy">
              <Translate contentKey="groupfaceApp.organisation.moduleKy">Module Ky</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.moduleKy}</dd>
          <dt>
            <span id="hashCode">
              <Translate contentKey="groupfaceApp.organisation.hashCode">Hash Code</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.hashCode}</dd>
          <dt>
            <span id="hashCertificate">
              <Translate contentKey="groupfaceApp.organisation.hashCertificate">Hash Certificate</Translate>
            </span>
          </dt>
          <dd>{organisationEntity.hashCertificate}</dd>
        </dl>
        <Button tag={Link} to="/organisation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organisation/${organisationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrganisationDetail;
