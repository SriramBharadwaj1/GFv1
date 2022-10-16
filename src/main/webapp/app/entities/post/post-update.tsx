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
import { IPost } from 'app/shared/model/post.model';
import { getEntity, updateEntity, createEntity, reset } from './post.reducer';

export const PostUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const postEntity = useAppSelector(state => state.post.entity);
  const loading = useAppSelector(state => state.post.loading);
  const updating = useAppSelector(state => state.post.updating);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);

  const handleClose = () => {
    navigate('/post');
  };

  useEffect(() => {
    if (!isNew) {
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
      ...postEntity,
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
          ...postEntity,
          addedByUser: postEntity?.addedByUser?.id,
          updatedByUser: postEntity?.updatedByUser?.id,
          approvedByUser: postEntity?.approvedByUser?.id,
          orgIdObj: postEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.post.home.createOrEditLabel" data-cy="PostCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.post.home.createOrEditLabel">Create or edit a Post</Translate>
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
                  id="post-id"
                  label={translate('groupfaceApp.post.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.post.message')}
                id="post-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.category')}
                id="post-category"
                name="category"
                data-cy="category"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.categoryName')}
                id="post-categoryName"
                name="categoryName"
                data-cy="categoryName"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.post.meta')} id="post-meta" name="meta" data-cy="meta" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.post.tableKy')}
                id="post-tableKy"
                name="tableKy"
                data-cy="tableKy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.isActive')}
                id="post-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.actRejReason')}
                id="post-actRejReason"
                name="actRejReason"
                data-cy="actRejReason"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.isEnable')}
                id="post-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.addedBy')}
                id="post-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.addedOn')}
                id="post-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.updatedBy')}
                id="post-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.updatedOn')}
                id="post-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.approvedBy')}
                id="post-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.approvedOn')}
                id="post-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.comments')}
                id="post-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.remarks')}
                id="post-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.post.type')} id="post-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.post.isSalesPost')}
                id="post-isSalesPost"
                name="isSalesPost"
                data-cy="isSalesPost"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.salesCategory')}
                id="post-salesCategory"
                name="salesCategory"
                data-cy="salesCategory"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.post.price')} id="post-price" name="price" data-cy="price" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.post.validFrom')}
                id="post-validFrom"
                name="validFrom"
                data-cy="validFrom"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.validTill')}
                id="post-validTill"
                name="validTill"
                data-cy="validTill"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.post.phoneArea')}
                id="post-phoneArea"
                name="phoneArea"
                data-cy="phoneArea"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.post.phno')} id="post-phno" name="phno" data-cy="phno" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.post.videoGrp')}
                id="post-videoGrp"
                name="videoGrp"
                data-cy="videoGrp"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.post.orgId')} id="post-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.post.otherInfo')}
                id="post-otherInfo"
                name="otherInfo"
                data-cy="otherInfo"
                type="text"
              />
              <ValidatedField
                id="post-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.post.addedByUser')}
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
                id="post-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.post.updatedByUser')}
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
                id="post-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.post.approvedByUser')}
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
                id="post-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.post.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post" replace color="info">
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

export default PostUpdate;
