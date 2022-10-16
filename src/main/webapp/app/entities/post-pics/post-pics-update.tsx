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
import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { IPostPics } from 'app/shared/model/post-pics.model';
import { getEntity, updateEntity, createEntity, reset } from './post-pics.reducer';

export const PostPicsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const posts = useAppSelector(state => state.post.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const postPicsEntity = useAppSelector(state => state.postPics.entity);
  const loading = useAppSelector(state => state.postPics.loading);
  const updating = useAppSelector(state => state.postPics.updating);
  const updateSuccess = useAppSelector(state => state.postPics.updateSuccess);

  const handleClose = () => {
    navigate('/post-pics' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserMasts({}));
    dispatch(getPosts({}));
    dispatch(getOrganisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...postPicsEntity,
      ...values,
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      updatedByUser: userMasts.find(it => it.id.toString() === values.updatedByUser.toString()),
      approvedByUser: userMasts.find(it => it.id.toString() === values.approvedByUser.toString()),
      postObj: posts.find(it => it.id.toString() === values.postObj.toString()),
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
          ...postPicsEntity,
          addedByUser: postPicsEntity?.addedByUser?.id,
          updatedByUser: postPicsEntity?.updatedByUser?.id,
          approvedByUser: postPicsEntity?.approvedByUser?.id,
          postObj: postPicsEntity?.postObj?.id,
          orgIdObj: postPicsEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.postPics.home.createOrEditLabel" data-cy="PostPicsCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.postPics.home.createOrEditLabel">Create or edit a PostPics</Translate>
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
                  id="post-pics-id"
                  label={translate('groupfaceApp.postPics.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.postPics.postid')}
                id="post-pics-postid"
                name="postid"
                data-cy="postid"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.postPics.pic')} id="post-pics-pic" name="pic" data-cy="pic" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.postPics.picpath')}
                id="post-pics-picpath"
                name="picpath"
                data-cy="picpath"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.isActive')}
                id="post-pics-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.isEnable')}
                id="post-pics-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.addedBy')}
                id="post-pics-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.addedOn')}
                id="post-pics-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.updatedBy')}
                id="post-pics-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.updatedOn')}
                id="post-pics-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.approvedBy')}
                id="post-pics-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.approvedOn')}
                id="post-pics-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.comments')}
                id="post-pics-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.remarks')}
                id="post-pics-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.orgId')}
                id="post-pics-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postPics.status')}
                id="post-pics-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                id="post-pics-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.postPics.addedByUser')}
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
                id="post-pics-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.postPics.updatedByUser')}
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
                id="post-pics-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.postPics.approvedByUser')}
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
                id="post-pics-postObj"
                name="postObj"
                data-cy="postObj"
                label={translate('groupfaceApp.postPics.postObj')}
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
                id="post-pics-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.postPics.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post-pics" replace color="info">
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

export default PostPicsUpdate;
