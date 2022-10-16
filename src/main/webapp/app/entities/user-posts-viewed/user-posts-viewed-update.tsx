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
import { IUserPostsViewed } from 'app/shared/model/user-posts-viewed.model';
import { getEntity, updateEntity, createEntity, reset } from './user-posts-viewed.reducer';

export const UserPostsViewedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const userPostsViewedEntity = useAppSelector(state => state.userPostsViewed.entity);
  const loading = useAppSelector(state => state.userPostsViewed.loading);
  const updating = useAppSelector(state => state.userPostsViewed.updating);
  const updateSuccess = useAppSelector(state => state.userPostsViewed.updateSuccess);

  const handleClose = () => {
    navigate('/user-posts-viewed' + location.search);
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
      ...userPostsViewedEntity,
      ...values,
      userIdObj: userMasts.find(it => it.id.toString() === values.userIdObj.toString()),
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
          ...userPostsViewedEntity,
          userIdObj: userPostsViewedEntity?.userIdObj?.id,
          orgIdObj: userPostsViewedEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.userPostsViewed.home.createOrEditLabel" data-cy="UserPostsViewedCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.userPostsViewed.home.createOrEditLabel">Create or edit a UserPostsViewed</Translate>
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
                  id="user-posts-viewed-id"
                  label={translate('groupfaceApp.userPostsViewed.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('groupfaceApp.userPostsViewed.userId')}
                id="user-posts-viewed-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userPostsViewed.postid')}
                id="user-posts-viewed-postid"
                name="postid"
                data-cy="postid"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userPostsViewed.viewdate')}
                id="user-posts-viewed-viewdate"
                name="viewdate"
                data-cy="viewdate"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.userPostsViewed.status')}
                id="user-posts-viewed-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.userPostsViewed.orgId')}
                id="user-posts-viewed-orgId"
                name="orgId"
                data-cy="orgId"
                type="text"
              />
              <ValidatedField
                id="user-posts-viewed-userIdObj"
                name="userIdObj"
                data-cy="userIdObj"
                label={translate('groupfaceApp.userPostsViewed.userIdObj')}
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
                id="user-posts-viewed-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.userPostsViewed.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-posts-viewed" replace color="info">
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

export default UserPostsViewedUpdate;
