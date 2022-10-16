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
import { IAutoPostAprvlUsrs } from 'app/shared/model/auto-post-aprvl-usrs.model';
import { getEntity, updateEntity, createEntity, reset } from './auto-post-aprvl-usrs.reducer';

export const AutoPostAprvlUsrsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const autoPostAprvlUsrsEntity = useAppSelector(state => state.autoPostAprvlUsrs.entity);
  const loading = useAppSelector(state => state.autoPostAprvlUsrs.loading);
  const updating = useAppSelector(state => state.autoPostAprvlUsrs.updating);
  const updateSuccess = useAppSelector(state => state.autoPostAprvlUsrs.updateSuccess);

  const handleClose = () => {
    navigate('/auto-post-aprvl-usrs' + location.search);
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
      ...autoPostAprvlUsrsEntity,
      ...values,
      user: userMasts.find(it => it.id.toString() === values.user.toString()),
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
          ...autoPostAprvlUsrsEntity,
          user: autoPostAprvlUsrsEntity?.user?.id,
          addedByUser: autoPostAprvlUsrsEntity?.addedByUser?.id,
          updatedByUser: autoPostAprvlUsrsEntity?.updatedByUser?.id,
          approvedByUser: autoPostAprvlUsrsEntity?.approvedByUser?.id,
          orgIdObj: autoPostAprvlUsrsEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.autoPostAprvlUsrs.home.createOrEditLabel" data-cy="AutoPostAprvlUsrsCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.home.createOrEditLabel">Create or edit a AutoPostAprvlUsrs</Translate>
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
                  id="auto-post-aprvl-usrs-id"
                  label={translate('groupfaceApp.autoPostAprvlUsrs.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.apUserId')}
                id="auto-post-aprvl-usrs-apUserId"
                name="apUserId"
                data-cy="apUserId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.tableKy')}
                id="auto-post-aprvl-usrs-tableKy"
                name="tableKy"
                data-cy="tableKy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.isActive')}
                id="auto-post-aprvl-usrs-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.isEnable')}
                id="auto-post-aprvl-usrs-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.addedBy')}
                id="auto-post-aprvl-usrs-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.addedOn')}
                id="auto-post-aprvl-usrs-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.updatedBy')}
                id="auto-post-aprvl-usrs-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.updatedOn')}
                id="auto-post-aprvl-usrs-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.approvedBy')}
                id="auto-post-aprvl-usrs-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.approvedOn')}
                id="auto-post-aprvl-usrs-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.comments')}
                id="auto-post-aprvl-usrs-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.remarks')}
                id="auto-post-aprvl-usrs-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.orgId')}
                id="auto-post-aprvl-usrs-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.autoPostAprvlUsrs.extraFields')}
                id="auto-post-aprvl-usrs-extraFields"
                name="extraFields"
                data-cy="extraFields"
                type="text"
              />
              <ValidatedField
                id="auto-post-aprvl-usrs-user"
                name="user"
                data-cy="user"
                label={translate('groupfaceApp.autoPostAprvlUsrs.user')}
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
                id="auto-post-aprvl-usrs-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.autoPostAprvlUsrs.addedByUser')}
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
                id="auto-post-aprvl-usrs-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.autoPostAprvlUsrs.updatedByUser')}
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
                id="auto-post-aprvl-usrs-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.autoPostAprvlUsrs.approvedByUser')}
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
                id="auto-post-aprvl-usrs-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.autoPostAprvlUsrs.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/auto-post-aprvl-usrs" replace color="info">
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

export default AutoPostAprvlUsrsUpdate;
