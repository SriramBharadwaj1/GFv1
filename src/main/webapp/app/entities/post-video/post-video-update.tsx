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
import { IPostVideo } from 'app/shared/model/post-video.model';
import { getEntity, updateEntity, createEntity, reset } from './post-video.reducer';

export const PostVideoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const posts = useAppSelector(state => state.post.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const postVideoEntity = useAppSelector(state => state.postVideo.entity);
  const loading = useAppSelector(state => state.postVideo.loading);
  const updating = useAppSelector(state => state.postVideo.updating);
  const updateSuccess = useAppSelector(state => state.postVideo.updateSuccess);

  const handleClose = () => {
    navigate('/post-video' + location.search);
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
      ...postVideoEntity,
      ...values,
      addedByUser: userMasts.find(it => it.id.toString() === values.addedByUser.toString()),
      updatedByUser: userMasts.find(it => it.id.toString() === values.updatedByUser.toString()),
      approvedByUser: userMasts.find(it => it.id.toString() === values.approvedByUser.toString()),
      postidObj: posts.find(it => it.id.toString() === values.postidObj.toString()),
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
          ...postVideoEntity,
          addedByUser: postVideoEntity?.addedByUser?.id,
          updatedByUser: postVideoEntity?.updatedByUser?.id,
          approvedByUser: postVideoEntity?.approvedByUser?.id,
          postidObj: postVideoEntity?.postidObj?.id,
          orgIdObj: postVideoEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.postVideo.home.createOrEditLabel" data-cy="PostVideoCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.postVideo.home.createOrEditLabel">Create or edit a PostVideo</Translate>
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
                  id="post-video-id"
                  label={translate('groupfaceApp.postVideo.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.postVideo.postid')}
                id="post-video-postid"
                name="postid"
                data-cy="postid"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.video')}
                id="post-video-video"
                name="video"
                data-cy="video"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.videopath')}
                id="post-video-videopath"
                name="videopath"
                data-cy="videopath"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.isActive')}
                id="post-video-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.isEnable')}
                id="post-video-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.addedBy')}
                id="post-video-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.addedOn')}
                id="post-video-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.updatedBy')}
                id="post-video-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.updatedOn')}
                id="post-video-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.approvedBy')}
                id="post-video-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.approvedOn')}
                id="post-video-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.comments')}
                id="post-video-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.remarks')}
                id="post-video-remarks"
                name="remarks"
                data-cy="remarks"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.primVideo')}
                id="post-video-primVideo"
                name="primVideo"
                data-cy="primVideo"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.orgId')}
                id="post-video-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.postVideo.status')}
                id="post-video-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                id="post-video-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.postVideo.addedByUser')}
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
                id="post-video-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.postVideo.updatedByUser')}
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
                id="post-video-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.postVideo.approvedByUser')}
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
                id="post-video-postidObj"
                name="postidObj"
                data-cy="postidObj"
                label={translate('groupfaceApp.postVideo.postidObj')}
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
                id="post-video-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.postVideo.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post-video" replace color="info">
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

export default PostVideoUpdate;
