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
import { IGroups } from 'app/shared/model/groups.model';
import { getEntities as getGroups } from 'app/entities/groups/groups.reducer';
import { IMasters } from 'app/shared/model/masters.model';
import { getEntities as getMasters } from 'app/entities/masters/masters.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { IGroupUser } from 'app/shared/model/group-user.model';
import { getEntity, updateEntity, createEntity, reset } from './group-user.reducer';

export const GroupUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const groups = useAppSelector(state => state.groups.entities);
  const masters = useAppSelector(state => state.masters.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const groupUserEntity = useAppSelector(state => state.groupUser.entity);
  const loading = useAppSelector(state => state.groupUser.loading);
  const updating = useAppSelector(state => state.groupUser.updating);
  const updateSuccess = useAppSelector(state => state.groupUser.updateSuccess);

  const handleClose = () => {
    navigate('/group-user' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserMasts({}));
    dispatch(getGroups({}));
    dispatch(getMasters({}));
    dispatch(getOrganisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...groupUserEntity,
      ...values,
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      updatedByUser: userMasts.find(it => it.id.toString() === values.updatedByUser.toString()),
      approvedByUser: userMasts.find(it => it.id.toString() === values.approvedByUser.toString()),
      grpUserObj: userMasts.find(it => it.id.toString() === values.grpUserObj.toString()),
      groupIdObj: groups.find(it => it.id.toString() === values.groupIdObj.toString()),
      userTypeObj: masters.find(it => it.id.toString() === values.userTypeObj.toString()),
      orgIdOb: organisations.find(it => it.id.toString() === values.orgIdOb.toString()),
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
          ...groupUserEntity,
          addedByUser: groupUserEntity?.addedByUser?.id,
          updatedByUser: groupUserEntity?.updatedByUser?.id,
          approvedByUser: groupUserEntity?.approvedByUser?.id,
          groupIdObj: groupUserEntity?.groupIdObj?.id,
          grpUserObj: groupUserEntity?.grpUserObj?.id,
          userTypeObj: groupUserEntity?.userTypeObj?.id,
          orgIdOb: groupUserEntity?.orgIdOb?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.groupUser.home.createOrEditLabel" data-cy="GroupUserCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.groupUser.home.createOrEditLabel">Create or edit a GroupUser</Translate>
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
                  id="group-user-id"
                  label={translate('groupfaceApp.groupUser.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.groupUser.groupId')}
                id="group-user-groupId"
                name="groupId"
                data-cy="groupId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.grpUser')}
                id="group-user-grpUser"
                name="grpUser"
                data-cy="grpUser"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.isActive')}
                id="group-user-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.isEnable')}
                id="group-user-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.addedBy')}
                id="group-user-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.addedOn')}
                id="group-user-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.updatedBy')}
                id="group-user-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.updatedOn')}
                id="group-user-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.approvedBy')}
                id="group-user-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.approvedOn')}
                id="group-user-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.comments')}
                id="group-user-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.remarks')}
                id="group-user-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.orgId')}
                id="group-user-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.groupUser.userType')}
                id="group-user-userType"
                name="userType"
                data-cy="userType"
                type="text"
              />
              <ValidatedField
                id="group-user-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.groupUser.addedByUser')}
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
                id="group-user-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.groupUser.updatedByUser')}
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
                id="group-user-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.groupUser.approvedByUser')}
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
                id="group-user-groupIdObj"
                name="groupIdObj"
                data-cy="groupIdObj"
                label={translate('groupfaceApp.groupUser.groupIdObj')}
                type="select"
              >
                <option value="" key="0" />
                {groups
                  ? groups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="group-user-grpUserObj"
                name="grpUserObj"
                data-cy="grpUserObj"
                label={translate('groupfaceApp.groupUser.grpUserObj')}
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
                id="group-user-userTypeObj"
                name="userTypeObj"
                data-cy="userTypeObj"
                label={translate('groupfaceApp.groupUser.userTypeObj')}
                type="select"
              >
                <option value="" key="0" />
                {masters
                  ? masters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.codeval}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="group-user-orgIdOb"
                name="orgIdOb"
                data-cy="orgIdOb"
                label={translate('groupfaceApp.groupUser.orgIdOb')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/group-user" replace color="info">
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

export default GroupUserUpdate;
