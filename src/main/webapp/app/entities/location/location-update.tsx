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
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntity, updateEntity, createEntity, reset } from './location.reducer';

export const LocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const locations = useAppSelector(state => state.location.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const locationEntity = useAppSelector(state => state.location.entity);
  const loading = useAppSelector(state => state.location.loading);
  const updating = useAppSelector(state => state.location.updating);
  const updateSuccess = useAppSelector(state => state.location.updateSuccess);

  const handleClose = () => {
    navigate('/location' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserMasts({}));
    dispatch(getLocations({}));
    dispatch(getOrganisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...locationEntity,
      ...values,
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      updatedByUser: userMasts.find(it => it.id.toString() === values.updatedByUser.toString()),
      approvedByUser: userMasts.find(it => it.id.toString() === values.approvedByUser.toString()),
      moderator1IdUser: userMasts.find(it => it.id.toString() === values.moderator1IdUser.toString()),
      moderator2IdUser: userMasts.find(it => it.id.toString() === values.moderator2IdUser.toString()),
      parentIdObj: locations.find(it => it.id.toString() === values.parentIdObj.toString()),
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
          ...locationEntity,
          addedByUser: locationEntity?.addedByUser?.id,
          updatedByUser: locationEntity?.updatedByUser?.id,
          approvedByUser: locationEntity?.approvedByUser?.id,
          moderator1IdUser: locationEntity?.moderator1IdUser?.id,
          moderator2IdUser: locationEntity?.moderator2IdUser?.id,
          parentIdObj: locationEntity?.parentIdObj?.id,
          orgIdObj: locationEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.location.home.createOrEditLabel" data-cy="LocationCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.location.home.createOrEditLabel">Create or edit a Location</Translate>
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
                  id="location-id"
                  label={translate('groupfaceApp.location.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('groupfaceApp.location.code')} id="location-code" name="code" data-cy="code" type="text" />
              <ValidatedField label={translate('groupfaceApp.location.name')} id="location-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('groupfaceApp.location.desc')} id="location-desc" name="desc" data-cy="desc" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.location.parentId')}
                id="location-parentId"
                name="parentId"
                data-cy="parentId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.moderator1Id')}
                id="location-moderator1Id"
                name="moderator1Id"
                data-cy="moderator1Id"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.moderator2Id')}
                id="location-moderator2Id"
                name="moderator2Id"
                data-cy="moderator2Id"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.moderator1Code')}
                id="location-moderator1Code"
                name="moderator1Code"
                data-cy="moderator1Code"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.moderator2Code')}
                id="location-moderator2Code"
                name="moderator2Code"
                data-cy="moderator2Code"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.parentTableKy')}
                id="location-parentTableKy"
                name="parentTableKy"
                data-cy="parentTableKy"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.location.type')} id="location-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.location.status')}
                id="location-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.location.zone')} id="location-zone" name="zone" data-cy="zone" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.location.orgId')}
                id="location-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.location.hist')} id="location-hist" name="hist" data-cy="hist" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.location.addedBy')}
                id="location-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.addedOn')}
                id="location-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.updatedBy')}
                id="location-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.updatedOn')}
                id="location-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.approvedBy')}
                id="location-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.approvedOn')}
                id="location-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.otherinfo')}
                id="location-otherinfo"
                name="otherinfo"
                data-cy="otherinfo"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.comments')}
                id="location-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.location.remarks')}
                id="location-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                id="location-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.location.addedByUser')}
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
                id="location-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.location.updatedByUser')}
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
                id="location-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.location.approvedByUser')}
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
                id="location-moderator1IdUser"
                name="moderator1IdUser"
                data-cy="moderator1IdUser"
                label={translate('groupfaceApp.location.moderator1IdUser')}
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
                id="location-moderator2IdUser"
                name="moderator2IdUser"
                data-cy="moderator2IdUser"
                label={translate('groupfaceApp.location.moderator2IdUser')}
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
                id="location-parentIdObj"
                name="parentIdObj"
                data-cy="parentIdObj"
                label={translate('groupfaceApp.location.parentIdObj')}
                type="select"
              >
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="location-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.location.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location" replace color="info">
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

export default LocationUpdate;
