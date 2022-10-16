import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { IOrgDetails } from 'app/shared/model/org-details.model';
import { getEntity, updateEntity, createEntity, reset } from './org-details.reducer';

export const OrgDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const organisations = useAppSelector(state => state.organisation.entities);
  const orgDetailsEntity = useAppSelector(state => state.orgDetails.entity);
  const loading = useAppSelector(state => state.orgDetails.loading);
  const updating = useAppSelector(state => state.orgDetails.updating);
  const updateSuccess = useAppSelector(state => state.orgDetails.updateSuccess);

  const handleClose = () => {
    navigate('/org-details' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOrganisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orgDetailsEntity,
      ...values,
      orgIdObj: organisations.find(it => it.id.toString() === values.orgIdObj.toString()),
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
          ...orgDetailsEntity,
          orgIdObj: orgDetailsEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.orgDetails.home.createOrEditLabel" data-cy="OrgDetailsCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.orgDetails.home.createOrEditLabel">Create or edit a OrgDetails</Translate>
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
                  id="org-details-id"
                  label={translate('groupfaceApp.orgDetails.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.orgId')}
                id="org-details-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.name')}
                id="org-details-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.type')}
                id="org-details-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.keyName')}
                id="org-details-keyName"
                name="keyName"
                data-cy="keyName"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.keyValue')}
                id="org-details-keyValue"
                name="keyValue"
                data-cy="keyValue"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.isActive')}
                id="org-details-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.isEnable')}
                id="org-details-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.addedBy')}
                id="org-details-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.addedOn')}
                id="org-details-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.updatedBy')}
                id="org-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.updatedOn')}
                id="org-details-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.approvedBy')}
                id="org-details-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.approvedOn')}
                id="org-details-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.orgDetails.extraFields')}
                id="org-details-extraFields"
                name="extraFields"
                data-cy="extraFields"
                type="text"
              />
              <ValidatedField
                id="org-details-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.orgDetails.orgIdObj')}
                type="select"
              >
                <option value="" key="0" />
                {organisations
                  ? organisations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/org-details" replace color="info">
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

export default OrgDetailsUpdate;
