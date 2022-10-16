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
import { getEntities as getCastes } from 'app/entities/caste/caste.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { ICaste } from 'app/shared/model/caste.model';
import { getEntity, updateEntity, createEntity, reset } from './caste.reducer';

export const CasteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const castes = useAppSelector(state => state.caste.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const casteEntity = useAppSelector(state => state.caste.entity);
  const loading = useAppSelector(state => state.caste.loading);
  const updating = useAppSelector(state => state.caste.updating);
  const updateSuccess = useAppSelector(state => state.caste.updateSuccess);

  const handleClose = () => {
    navigate('/caste' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserMasts({}));
    dispatch(getCastes({}));
    dispatch(getOrganisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...casteEntity,
      ...values,
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      updatedByUser: userMasts.find(it => it.id.toString() === values.updatedByUser.toString()),
      approvedByObj: userMasts.find(it => it.id.toString() === values.approvedByObj.toString()),
      parentIdObj: castes.find(it => it.id.toString() === values.parentIdObj.toString()),
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
          ...casteEntity,
          addedByUser: casteEntity?.addedByUser?.id,
          updatedByUser: casteEntity?.updatedByUser?.id,
          approvedByObj: casteEntity?.approvedByObj?.id,
          parentIdObj: casteEntity?.parentIdObj?.id,
          orgIdObj: casteEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.caste.home.createOrEditLabel" data-cy="CasteCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.caste.home.createOrEditLabel">Create or edit a Caste</Translate>
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
                  id="caste-id"
                  label={translate('groupfaceApp.caste.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('groupfaceApp.caste.code')} id="caste-code" name="code" data-cy="code" type="text" />
              <ValidatedField label={translate('groupfaceApp.caste.name')} id="caste-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('groupfaceApp.caste.desc')} id="caste-desc" name="desc" data-cy="desc" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.caste.parentId')}
                id="caste-parentId"
                name="parentId"
                data-cy="parentId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.moderatorId')}
                id="caste-moderatorId"
                name="moderatorId"
                data-cy="moderatorId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.parentTableKy')}
                id="caste-parentTableKy"
                name="parentTableKy"
                data-cy="parentTableKy"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.caste.status')} id="caste-status" name="status" data-cy="status" type="text" />
              <ValidatedField label={translate('groupfaceApp.caste.zone')} id="caste-zone" name="zone" data-cy="zone" type="text" />
              <ValidatedField label={translate('groupfaceApp.caste.orgId')} id="caste-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField label={translate('groupfaceApp.caste.hist')} id="caste-hist" name="hist" data-cy="hist" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.caste.addedBy')}
                id="caste-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.addedOn')}
                id="caste-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.updatedBy')}
                id="caste-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.updatedOn')}
                id="caste-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.approvedBy')}
                id="caste-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.approvedOn')}
                id="caste-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.otherinfo')}
                id="caste-otherinfo"
                name="otherinfo"
                data-cy="otherinfo"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.comments')}
                id="caste-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.caste.remarks')}
                id="caste-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                id="caste-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.caste.addedByUser')}
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
                id="caste-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.caste.updatedByUser')}
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
                id="caste-approvedByObj"
                name="approvedByObj"
                data-cy="approvedByObj"
                label={translate('groupfaceApp.caste.approvedByObj')}
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
                id="caste-parentIdObj"
                name="parentIdObj"
                data-cy="parentIdObj"
                label={translate('groupfaceApp.caste.parentIdObj')}
                type="select"
              >
                <option value="" key="0" />
                {castes
                  ? castes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="caste-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.caste.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/caste" replace color="info">
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

export default CasteUpdate;
