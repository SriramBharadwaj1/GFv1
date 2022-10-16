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
import { IComment } from 'app/shared/model/comment.model';
import { getEntity, updateEntity, createEntity, reset } from './comment.reducer';

export const CommentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const posts = useAppSelector(state => state.post.entities);
  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const commentEntity = useAppSelector(state => state.comment.entity);
  const loading = useAppSelector(state => state.comment.loading);
  const updating = useAppSelector(state => state.comment.updating);
  const updateSuccess = useAppSelector(state => state.comment.updateSuccess);

  const handleClose = () => {
    navigate('/comment');
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
      ...commentEntity,
      ...values,
      copostIdObj: posts.find(it => it.id.toString() === values.copostIdObj.toString()),
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
          ...commentEntity,
          copostIdObj: commentEntity?.copostIdObj?.id,
          addedByUser: commentEntity?.addedByUser?.id,
          updatedByUser: commentEntity?.updatedByUser?.id,
          approvedByUser: commentEntity?.approvedByUser?.id,
          orgIdObj: commentEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.comment.home.createOrEditLabel" data-cy="CommentCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.comment.home.createOrEditLabel">Create or edit a Comment</Translate>
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
                  id="comment-id"
                  label={translate('groupfaceApp.comment.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.comment.message')}
                id="comment-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.category')}
                id="comment-category"
                name="category"
                data-cy="category"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.categoryName')}
                id="comment-categoryName"
                name="categoryName"
                data-cy="categoryName"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.comment.meta')} id="comment-meta" name="meta" data-cy="meta" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.comment.postId')}
                id="comment-postId"
                name="postId"
                data-cy="postId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.isActive')}
                id="comment-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.apprRejReason')}
                id="comment-apprRejReason"
                name="apprRejReason"
                data-cy="apprRejReason"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.isEnable')}
                id="comment-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.addedBy')}
                id="comment-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.addedOn')}
                id="comment-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.updatedBy')}
                id="comment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.updatedOn')}
                id="comment-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.approvedBy')}
                id="comment-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.approvedOn')}
                id="comment-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.comment.comments')}
                id="comment-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.comment.orgId')} id="comment-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.comment.otherInfo')}
                id="comment-otherInfo"
                name="otherInfo"
                data-cy="otherInfo"
                type="text"
              />
              <ValidatedField
                id="comment-copostIdObj"
                name="copostIdObj"
                data-cy="copostIdObj"
                label={translate('groupfaceApp.comment.copostIdObj')}
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
                id="comment-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.comment.addedByUser')}
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
                id="comment-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.comment.updatedByUser')}
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
                id="comment-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.comment.approvedByUser')}
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
                id="comment-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.comment.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/comment" replace color="info">
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

export default CommentUpdate;
