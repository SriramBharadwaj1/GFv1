import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { IUserMast } from 'app/shared/model/user-mast.model';
import { getEntities as getUserMasts } from 'app/entities/user-mast/user-mast.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { IRemarks } from 'app/shared/model/remarks.model';
import { getEntity, updateEntity, createEntity, reset } from './remarks.reducer';

export const RemarksUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const posts = useAppSelector(state => state.post.entities);
  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const remarksEntity = useAppSelector(state => state.remarks.entity);
  const loading = useAppSelector(state => state.remarks.loading);
  const updating = useAppSelector(state => state.remarks.updating);
  const updateSuccess = useAppSelector(state => state.remarks.updateSuccess);

  const handleClose = () => {
    navigate('/remarks');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPosts({}));
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
      ...remarksEntity,
      ...values,
      repostIdObj: posts.find(it => it.id.toString() === values.repostIdObj.toString()),
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
          ...remarksEntity,
          repostIdObj: remarksEntity?.repostIdObj?.id,
          addedByUser: remarksEntity?.addedByUser?.id,
          updatedByUser: remarksEntity?.updatedByUser?.id,
          approvedByUser: remarksEntity?.approvedByUser?.id,
          orgIdObj: remarksEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.remarks.home.createOrEditLabel" data-cy="RemarksCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.remarks.home.createOrEditLabel">Create or edit a Remarks</Translate>
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
                  id="remarks-id"
                  label={translate('groupfaceApp.remarks.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.remarks.message')}
                id="remarks-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.category')}
                id="remarks-category"
                name="category"
                data-cy="category"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.categoryName')}
                id="remarks-categoryName"
                name="categoryName"
                data-cy="categoryName"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.remarks.meta')} id="remarks-meta" name="meta" data-cy="meta" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.remarks.postId')}
                id="remarks-postId"
                name="postId"
                data-cy="postId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.isActive')}
                id="remarks-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.apprRejReason')}
                id="remarks-apprRejReason"
                name="apprRejReason"
                data-cy="apprRejReason"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.isEnable')}
                id="remarks-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.addedBy')}
                id="remarks-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.addedOn')}
                id="remarks-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.updatedBy')}
                id="remarks-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.updatedOn')}
                id="remarks-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.approvedBy')}
                id="remarks-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.approvedOn')}
                id="remarks-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.comments')}
                id="remarks-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.remarks.remarks')}
                id="remarks-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.remarks.orgId')} id="remarks-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.remarks.otherInfo')}
                id="remarks-otherInfo"
                name="otherInfo"
                data-cy="otherInfo"
                type="text"
              />
              <ValidatedField
                id="remarks-repostIdObj"
                name="repostIdObj"
                data-cy="repostIdObj"
                label={translate('groupfaceApp.remarks.repostIdObj')}
                type="select"
              >
                <option value="" key="0" />
                {posts
                  ? posts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.message}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="remarks-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.remarks.addedByUser')}
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
                id="remarks-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.remarks.updatedByUser')}
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
                id="remarks-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.remarks.approvedByUser')}
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
                id="remarks-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.remarks.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/remarks" replace color="info">
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

export default RemarksUpdate;
