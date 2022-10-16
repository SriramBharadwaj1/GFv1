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
import { IRating } from 'app/shared/model/rating.model';
import { getEntity, updateEntity, createEntity, reset } from './rating.reducer';

export const RatingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const posts = useAppSelector(state => state.post.entities);
  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const ratingEntity = useAppSelector(state => state.rating.entity);
  const loading = useAppSelector(state => state.rating.loading);
  const updating = useAppSelector(state => state.rating.updating);
  const updateSuccess = useAppSelector(state => state.rating.updateSuccess);

  const handleClose = () => {
    navigate('/rating' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
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
      ...ratingEntity,
      ...values,
      rapostIdObj: posts.find(it => it.id.toString() === values.rapostIdObj.toString()),
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
          ...ratingEntity,
          rapostIdObj: ratingEntity?.rapostIdObj?.id,
          addedByUser: ratingEntity?.addedByUser?.id,
          updatedByUser: ratingEntity?.updatedByUser?.id,
          approvedByUser: ratingEntity?.approvedByUser?.id,
          orgIdObj: ratingEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.rating.home.createOrEditLabel" data-cy="RatingCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.rating.home.createOrEditLabel">Create or edit a Rating</Translate>
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
                  id="rating-id"
                  label={translate('groupfaceApp.rating.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.rating.rating')}
                id="rating-rating"
                name="rating"
                data-cy="rating"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.postId')}
                id="rating-postId"
                name="postId"
                data-cy="postId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.isActive')}
                id="rating-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.apprRejReason')}
                id="rating-apprRejReason"
                name="apprRejReason"
                data-cy="apprRejReason"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.isEnable')}
                id="rating-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.addedBy')}
                id="rating-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.addedOn')}
                id="rating-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.updatedBy')}
                id="rating-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.updatedOn')}
                id="rating-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.rating.approvedBy')}
                id="rating-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.rating.orgId')} id="rating-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.rating.approvedOn')}
                id="rating-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                id="rating-rapostIdObj"
                name="rapostIdObj"
                data-cy="rapostIdObj"
                label={translate('groupfaceApp.rating.rapostIdObj')}
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
                id="rating-addedByUser"
                name="addedByUser"
                data-cy="addedByUser"
                label={translate('groupfaceApp.rating.addedByUser')}
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
                id="rating-updatedByUser"
                name="updatedByUser"
                data-cy="updatedByUser"
                label={translate('groupfaceApp.rating.updatedByUser')}
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
                id="rating-approvedByUser"
                name="approvedByUser"
                data-cy="approvedByUser"
                label={translate('groupfaceApp.rating.approvedByUser')}
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
                id="rating-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.rating.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rating" replace color="info">
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

export default RatingUpdate;
