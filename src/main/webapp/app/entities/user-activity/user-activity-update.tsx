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
import { IUserActivity } from 'app/shared/model/user-activity.model';
import { getEntity, updateEntity, createEntity, reset } from './user-activity.reducer';

export const UserActivityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const userActivityEntity = useAppSelector(state => state.userActivity.entity);
  const loading = useAppSelector(state => state.userActivity.loading);
  const updating = useAppSelector(state => state.userActivity.updating);
  const updateSuccess = useAppSelector(state => state.userActivity.updateSuccess);

  const handleClose = () => {
    navigate('/user-activity' + location.search);
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
      ...userActivityEntity,
      ...values,
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      userObj: userMasts.find(it => it.id.toString() === values.userObj.toString()),
      addedByUseract: userMasts.find(it => it.id.toString() === values.addedByUseract.toString()),
      useractObj: userMasts.find(it => it.id.toString() === values.useractObj.toString()),
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
          ...userActivityEntity,
          addedByUser: userActivityEntity?.addedByUser?.id,
          userObj: userActivityEntity?.userObj?.id,
          addedByUseract: userActivityEntity?.addedByUseract?.id,
          orgIdObj: userActivityEntity?.orgIdObj?.id,
          useractObj: userActivityEntity?.useractObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.userActivity.home.createOrEditLabel" data-cy="UserActivityCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.userActivity.home.createOrEditLabel">Create or edit a UserActivity</Translate>
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
                  id="user-activity-id"
                  label={translate('groupfaceApp.userActivity.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.userActivity.userId')}
                id="user-activity-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.loggedon')}
                id="user-activity-loggedon"
                name="loggedon"
                data-cy="loggedon"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.activeduration')}
                id="user-activity-activeduration"
                name="activeduration"
                data-cy="activeduration"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.ipAdr')}
                id="user-activity-ipAdr"
                name="ipAdr"
                data-cy="ipAdr"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.usrLocation')}
                id="user-activity-usrLocation"
                name="usrLocation"
                data-cy="usrLocation"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.lat')}
                id="user-activity-lat"
                name="lat"
                data-cy="lat"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.longi')}
                id="user-activity-longi"
                name="longi"
                data-cy="longi"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.pin')}
                id="user-activity-pin"
                name="pin"
                data-cy="pin"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.addedBy')}
                id="user-activity-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.orgId')}
                id="user-activity-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userActivity.addedOn')}
                id="user-activity-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                id="user-activity-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.userActivity.addedByUser')}
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
                id="user-activity-userObj"
                name="userObj"
                data-cy="userObj"
                label={translate('groupfaceApp.userActivity.userObj')}
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
                id="user-activity-addedByUseract"
                name="addedByUseract"
                data-cy="addedByUseract"
                label={translate('groupfaceApp.userActivity.addedByUseract')}
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
                id="user-activity-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.userActivity.orgIdObj')}
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
                id="user-activity-useractObj"
                name="useractObj"
                data-cy="useractObj"
                label={translate('groupfaceApp.userActivity.useractObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-activity" replace color="info">
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

export default UserActivityUpdate;
