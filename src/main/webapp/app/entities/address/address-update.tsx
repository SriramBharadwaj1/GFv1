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
import { IUserMast } from 'app/shared/model/user-mast.model';
import { getEntities as getUserMasts } from 'app/entities/user-mast/user-mast.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';

export const AddressUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const organisations = useAppSelector(state => state.organisation.entities);
  const userMasts = useAppSelector(state => state.userMast.entities);
  const locations = useAppSelector(state => state.location.entities);
  const addressEntity = useAppSelector(state => state.address.entity);
  const loading = useAppSelector(state => state.address.loading);
  const updating = useAppSelector(state => state.address.updating);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);

  const handleClose = () => {
    navigate('/address' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOrganisations({}));
    dispatch(getUserMasts({}));
    dispatch(getLocations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...addressEntity,
      ...values,
      orgIdObj: organisations.find(it => it.id.toString() === values.orgIdObj.toString()),
      approvedByObj: userMasts.find(it => it.id.toString() === values.approvedByObj.toString()),
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      updatedByUser: userMasts.find(it => it.id.toString() === values.updatedByUser.toString()),
      villageObj: locations.find(it => it.id.toString() === values.villageObj.toString()),
      cityObj: locations.find(it => it.id.toString() === values.cityObj.toString()),
      districtObj: locations.find(it => it.id.toString() === values.districtObj.toString()),
      stateObj: locations.find(it => it.id.toString() === values.stateObj.toString()),
      countryObj: locations.find(it => it.id.toString() === values.countryObj.toString()),
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
          ...addressEntity,
          orgIdObj: addressEntity?.orgIdObj?.id,
          approvedByObj: addressEntity?.approvedByObj?.id,
          addedByUser: addressEntity?.addedByUser?.id,
          updatedByUser: addressEntity?.updatedByUser?.id,
          villageObj: addressEntity?.villageObj?.id,
          cityObj: addressEntity?.cityObj?.id,
          districtObj: addressEntity?.districtObj?.id,
          stateObj: addressEntity?.stateObj?.id,
          countryObj: addressEntity?.countryObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.address.home.createOrEditLabel" data-cy="AddressCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.address.home.createOrEditLabel">Create or edit a Address</Translate>
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
                  id="address-id"
                  label={translate('groupfaceApp.address.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('groupfaceApp.address.name')} id="address-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.address.addressType')}
                id="address-addressType"
                name="addressType"
                data-cy="addressType"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.addressLn1')}
                id="address-addressLn1"
                name="addressLn1"
                data-cy="addressLn1"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.addressLn2')}
                id="address-addressLn2"
                name="addressLn2"
                data-cy="addressLn2"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.addressLn3')}
                id="address-addressLn3"
                name="addressLn3"
                data-cy="addressLn3"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.addressLn4')}
                id="address-addressLn4"
                name="addressLn4"
                data-cy="addressLn4"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.landmark')}
                id="address-landmark"
                name="landmark"
                data-cy="landmark"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.village')}
                id="address-village"
                name="village"
                data-cy="village"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.address.city')} id="address-city" name="city" data-cy="city" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.address.district')}
                id="address-district"
                name="district"
                data-cy="district"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.address.state')} id="address-state" name="state" data-cy="state" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.address.parentTableId')}
                id="address-parentTableId"
                name="parentTableId"
                data-cy="parentTableId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.parentModuleKy')}
                id="address-parentModuleKy"
                name="parentModuleKy"
                data-cy="parentModuleKy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.parentTableKy')}
                id="address-parentTableKy"
                name="parentTableKy"
                data-cy="parentTableKy"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.address.pin')} id="address-pin" name="pin" data-cy="pin" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.address.country')}
                id="address-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.isActive')}
                id="address-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.isEnable')}
                id="address-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.addedBy')}
                id="address-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.addedOn')}
                id="address-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.updatedBy')}
                id="address-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.updatedOn')}
                id="address-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.approvedBy')}
                id="address-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.approvedOn')}
                id="address-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.comments')}
                id="address-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.remarks')}
                id="address-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.address.extraFields')}
                id="address-extraFields"
                name="extraFields"
                data-cy="extraFields"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.address.zone')} id="address-zone" name="zone" data-cy="zone" type="text" />
              <ValidatedField label={translate('groupfaceApp.address.orgId')} id="address-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField label={translate('groupfaceApp.address.hist')} id="address-hist" name="hist" data-cy="hist" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.address.column1')}
                id="address-column1"
                name="column1"
                data-cy="column1"
                type="text"
              />
              <ValidatedField
                id="address-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.address.orgIdObj')}
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
              <ValidatedField
                id="address-approvedByObj"
                name="approvedByObj"
                data-cy="approvedByObj"
                label={translate('groupfaceApp.address.approvedByObj')}
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
                id="address-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.address.addedByUser')}
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
                id="address-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.address.updatedByUser')}
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
                id="address-villageObj"
                name="villageObj"
                data-cy="villageObj"
                label={translate('groupfaceApp.address.villageObj')}
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
                id="address-cityObj"
                name="cityObj"
                data-cy="cityObj"
                label={translate('groupfaceApp.address.cityObj')}
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
                id="address-districtObj"
                name="districtObj"
                data-cy="districtObj"
                label={translate('groupfaceApp.address.districtObj')}
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
                id="address-stateObj"
                name="stateObj"
                data-cy="stateObj"
                label={translate('groupfaceApp.address.stateObj')}
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
                id="address-countryObj"
                name="countryObj"
                data-cy="countryObj"
                label={translate('groupfaceApp.address.countryObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/address" replace color="info">
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

export default AddressUpdate;
