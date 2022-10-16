import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntity, updateEntity, createEntity, reset } from './organisation.reducer';

export const OrganisationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const organisationEntity = useAppSelector(state => state.organisation.entity);
  const loading = useAppSelector(state => state.organisation.loading);
  const updating = useAppSelector(state => state.organisation.updating);
  const updateSuccess = useAppSelector(state => state.organisation.updateSuccess);

  const handleClose = () => {
    navigate('/organisation' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...organisationEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...organisationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.organisation.home.createOrEditLabel" data-cy="OrganisationCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.organisation.home.createOrEditLabel">Create or edit a Organisation</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="organisation-id"
                  label={translate('groupfaceApp.organisation.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.organisation.code')}
                id="organisation-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.name')}
                id="organisation-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.type')}
                id="organisation-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.parentOrgId')}
                id="organisation-parentOrgId"
                name="parentOrgId"
                data-cy="parentOrgId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.primaryContactId')}
                id="organisation-primaryContactId"
                name="primaryContactId"
                data-cy="primaryContactId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.orgHead')}
                id="organisation-orgHead"
                name="orgHead"
                data-cy="orgHead"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.locationId')}
                id="organisation-locationId"
                name="locationId"
                data-cy="locationId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.website')}
                id="organisation-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.layout')}
                id="organisation-layout"
                name="layout"
                data-cy="layout"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.tableKy')}
                id="organisation-tableKy"
                name="tableKy"
                data-cy="tableKy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.isActive')}
                id="organisation-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.isEnable')}
                id="organisation-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.addedBy')}
                id="organisation-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.addedOn')}
                id="organisation-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.updatedBy')}
                id="organisation-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.updatedOn')}
                id="organisation-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.approvedBy')}
                id="organisation-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.approvedOn')}
                id="organisation-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.comments')}
                id="organisation-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.remarks')}
                id="organisation-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.extraFields')}
                id="organisation-extraFields"
                name="extraFields"
                data-cy="extraFields"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.buildFilePath')}
                id="organisation-buildFilePath"
                name="buildFilePath"
                data-cy="buildFilePath"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.shortName')}
                id="organisation-shortName"
                name="shortName"
                data-cy="shortName"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.vatNo')}
                id="organisation-vatNo"
                name="vatNo"
                data-cy="vatNo"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.moduleKy')}
                id="organisation-moduleKy"
                name="moduleKy"
                data-cy="moduleKy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.hashCode')}
                id="organisation-hashCode"
                name="hashCode"
                data-cy="hashCode"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.organisation.hashCertificate')}
                id="organisation-hashCertificate"
                name="hashCertificate"
                data-cy="hashCertificate"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/organisation" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrganisationUpdate;
