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
import { getEntity, updateEntity, createEntity, reset } from './user-mast.reducer';

export const UserMastUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const organisations = useAppSelector(state => state.organisation.entities);
  const userMastEntity = useAppSelector(state => state.userMast.entity);
  const loading = useAppSelector(state => state.userMast.loading);
  const updating = useAppSelector(state => state.userMast.updating);
  const updateSuccess = useAppSelector(state => state.userMast.updateSuccess);

  const handleClose = () => {
    navigate('/user-mast' + location.search);
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
      ...userMastEntity,
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
          ...userMastEntity,
          orgIdObj: userMastEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.userMast.home.createOrEditLabel" data-cy="UserMastCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.userMast.home.createOrEditLabel">Create or edit a UserMast</Translate>
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
                  id="user-mast-id"
                  label={translate('groupfaceApp.userMast.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.userMast.userId')}
                id="user-mast-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.userType')}
                id="user-mast-userType"
                name="userType"
                data-cy="userType"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.userMast.name')} id="user-mast-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.userMast.lastname')}
                id="user-mast-lastname"
                name="lastname"
                data-cy="lastname"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.isActive')}
                id="user-mast-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.activatedBy')}
                id="user-mast-activatedBy"
                name="activatedBy"
                data-cy="activatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.activatedOn')}
                id="user-mast-activatedOn"
                name="activatedOn"
                data-cy="activatedOn"
                type="date"
              />
              <ValidatedField label={translate('groupfaceApp.userMast.dob')} id="user-mast-dob" name="dob" data-cy="dob" type="date" />
              <ValidatedField
                label={translate('groupfaceApp.userMast.gender')}
                id="user-mast-gender"
                name="gender"
                data-cy="gender"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.phoneArea')}
                id="user-mast-phoneArea"
                name="phoneArea"
                data-cy="phoneArea"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.userMast.phno')} id="user-mast-phno" name="phno" data-cy="phno" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.userMast.email1')}
                id="user-mast-email1"
                name="email1"
                data-cy="email1"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.email2')}
                id="user-mast-email2"
                name="email2"
                data-cy="email2"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.requestDt')}
                id="user-mast-requestDt"
                name="requestDt"
                data-cy="requestDt"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.caste')}
                id="user-mast-caste"
                name="caste"
                data-cy="caste"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.subCaste')}
                id="user-mast-subCaste"
                name="subCaste"
                data-cy="subCaste"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.validID')}
                id="user-mast-validID"
                name="validID"
                data-cy="validID"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.validIDType')}
                id="user-mast-validIDType"
                name="validIDType"
                data-cy="validIDType"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.validIDNo')}
                id="user-mast-validIDNo"
                name="validIDNo"
                data-cy="validIDNo"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.validValidTill')}
                id="user-mast-validValidTill"
                name="validValidTill"
                data-cy="validValidTill"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.refferedBy')}
                id="user-mast-refferedBy"
                name="refferedBy"
                data-cy="refferedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.relationwith')}
                id="user-mast-relationwith"
                name="relationwith"
                data-cy="relationwith"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.relationType')}
                id="user-mast-relationType"
                name="relationType"
                data-cy="relationType"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.issuingCountry')}
                id="user-mast-issuingCountry"
                name="issuingCountry"
                data-cy="issuingCountry"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.status')}
                id="user-mast-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.comment')}
                id="user-mast-comment"
                name="comment"
                data-cy="comment"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.otherInfo')}
                id="user-mast-otherInfo"
                name="otherInfo"
                data-cy="otherInfo"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.allergicDrug1')}
                id="user-mast-allergicDrug1"
                name="allergicDrug1"
                data-cy="allergicDrug1"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.moduleKy')}
                id="user-mast-moduleKy"
                name="moduleKy"
                data-cy="moduleKy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.tableKy')}
                id="user-mast-tableKy"
                name="tableKy"
                data-cy="tableKy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.allergicDrug2')}
                id="user-mast-allergicDrug2"
                name="allergicDrug2"
                data-cy="allergicDrug2"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.comments')}
                id="user-mast-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.remarks')}
                id="user-mast-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.extraFields')}
                id="user-mast-extraFields"
                name="extraFields"
                data-cy="extraFields"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.column1')}
                id="user-mast-column1"
                name="column1"
                data-cy="column1"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.column2')}
                id="user-mast-column2"
                name="column2"
                data-cy="column2"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.column3')}
                id="user-mast-column3"
                name="column3"
                data-cy="column3"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.hobbies')}
                id="user-mast-hobbies"
                name="hobbies"
                data-cy="hobbies"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.otherActivities')}
                id="user-mast-otherActivities"
                name="otherActivities"
                data-cy="otherActivities"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.password')}
                id="user-mast-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.userMast.role')} id="user-mast-role" name="role" data-cy="role" type="text" />
              <ValidatedField label={translate('groupfaceApp.userMast.path')} id="user-mast-path" name="path" data-cy="path" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.userMast.roleType')}
                id="user-mast-roleType"
                name="roleType"
                data-cy="roleType"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.addedBy')}
                id="user-mast-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.addedOn')}
                id="user-mast-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.updatedBy')}
                id="user-mast-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.updatedOn')}
                id="user-mast-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.approvedBy')}
                id="user-mast-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.approvedOn')}
                id="user-mast-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.language')}
                id="user-mast-language"
                name="language"
                data-cy="language"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.userMast.hist')} id="user-mast-hist" name="hist" data-cy="hist" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.userMast.layout')}
                id="user-mast-layout"
                name="layout"
                data-cy="layout"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.securityKeyStatus')}
                id="user-mast-securityKeyStatus"
                name="securityKeyStatus"
                data-cy="securityKeyStatus"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.hashCode')}
                id="user-mast-hashCode"
                name="hashCode"
                data-cy="hashCode"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.encryptionStatus')}
                id="user-mast-encryptionStatus"
                name="encryptionStatus"
                data-cy="encryptionStatus"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.encryptedPassword')}
                id="user-mast-encryptedPassword"
                name="encryptedPassword"
                data-cy="encryptedPassword"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.userMast.zone')} id="user-mast-zone" name="zone" data-cy="zone" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.userMast.orgId')}
                id="user-mast-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.userMast.job')} id="user-mast-job" name="job" data-cy="job" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.userMast.presentAddress')}
                id="user-mast-presentAddress"
                name="presentAddress"
                data-cy="presentAddress"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.permanentAddress')}
                id="user-mast-permanentAddress"
                name="permanentAddress"
                data-cy="permanentAddress"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.workcompany')}
                id="user-mast-workcompany"
                name="workcompany"
                data-cy="workcompany"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userMast.workAddress')}
                id="user-mast-workAddress"
                name="workAddress"
                data-cy="workAddress"
                type="text"
              />
              <ValidatedField
                id="user-mast-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.userMast.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-mast" replace color="info">
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

export default UserMastUpdate;
