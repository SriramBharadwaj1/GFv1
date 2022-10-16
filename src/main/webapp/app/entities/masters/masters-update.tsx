import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserMast } from 'app/shared/model/user-mast.model';
import { getEntities as getUserMasts } from 'app/entities/user-mast/user-mast.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { IMasters } from 'app/shared/model/masters.model';
import { getEntity, updateEntity, createEntity, reset } from './masters.reducer';

export const MastersUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const mastersEntity = useAppSelector(state => state.masters.entity);
  const loading = useAppSelector(state => state.masters.loading);
  const updating = useAppSelector(state => state.masters.updating);
  const updateSuccess = useAppSelector(state => state.masters.updateSuccess);

  const handleClose = () => {
    navigate('/masters' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserMasts({}));
    dispatch(getOrganisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...mastersEntity,
      ...values,
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      updatedByUser: userMasts.find(it => it.id.toString() === values.updatedByUser.toString()),
      approvedByUser: userMasts.find(it => it.id.toString() === values.approvedByUser.toString()),
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
          ...mastersEntity,
          addedByUser: mastersEntity?.addedByUser?.id,
          updatedByUser: mastersEntity?.updatedByUser?.id,
          approvedByUser: mastersEntity?.approvedByUser?.id,
          orgIdObj: mastersEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.masters.home.createOrEditLabel" data-cy="MastersCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.masters.home.createOrEditLabel">Create or edit a Masters</Translate>
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
                  id="masters-id"
                  label={translate('groupfaceApp.masters.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('groupfaceApp.masters.key')} id="masters-key" name="key" data-cy="key" type="text" />
              <ValidatedField label={translate('groupfaceApp.masters.val')} id="masters-val" name="val" data-cy="val" type="text" />
              <ValidatedField label={translate('groupfaceApp.masters.code')} id="masters-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.masters.codeval')}
                id="masters-codeval"
                name="codeval"
                data-cy="codeval"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.masters.status')}
                id="masters-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.masters.orgId')} id="masters-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField
                id="masters-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.masters.addedByUser')}
                type="select"
              >
                <option value="" key="0" />
                {userMasts
                  ? userMasts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="masters-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.masters.updatedByUser')}
                type="select"
              >
                <option value="" key="0" />
                {userMasts
                  ? userMasts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="masters-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.masters.approvedByUser')}
                type="select"
              >
                <option value="" key="0" />
                {userMasts
                  ? userMasts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="masters-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.masters.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/masters" replace color="info">
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

export default MastersUpdate;
