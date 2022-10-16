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
import { IMasters } from 'app/shared/model/masters.model';
import { getEntities as getMasters } from 'app/entities/masters/masters.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { IEvents } from 'app/shared/model/events.model';
import { getEntity, updateEntity, createEntity, reset } from './events.reducer';

export const EventsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMasts = useAppSelector(state => state.userMast.entities);
  const masters = useAppSelector(state => state.masters.entities);
  const organisations = useAppSelector(state => state.organisation.entities);
  const eventsEntity = useAppSelector(state => state.events.entity);
  const loading = useAppSelector(state => state.events.loading);
  const updating = useAppSelector(state => state.events.updating);
  const updateSuccess = useAppSelector(state => state.events.updateSuccess);

  const handleClose = () => {
    navigate('/events' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserMasts({}));
    dispatch(getMasters({}));
    dispatch(getOrganisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...eventsEntity,
      ...values,
      addedByUsr: userMasts.find(it => it.id.toString() === values.addedByUsr.toString()),
      updatedByUsr: userMasts.find(it => it.id.toString() === values.updatedByUsr.toString()),
      approvedByUsr: userMasts.find(it => it.id.toString() === values.approvedByUsr.toString()),
      userIdUsr: userMasts.find(it => it.id.toString() === values.userIdUsr.toString()),
      eventTypeObj: masters.find(it => it.id.toString() === values.eventTypeObj.toString()),
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
          ...eventsEntity,
          addedByUsr: eventsEntity?.addedByUsr?.id,
          updatedByUsr: eventsEntity?.updatedByUsr?.id,
          approvedByUsr: eventsEntity?.approvedByUsr?.id,
          userIdUsr: eventsEntity?.userIdUsr?.id,
          eventTypeObj: eventsEntity?.eventTypeObj?.id,
          orgIdObj: eventsEntity?.orgIdObj?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="groupfaceApp.events.home.createOrEditLabel" data-cy="EventsCreateUpdateHeading">
            <Translate contentKey="groupfaceApp.events.home.createOrEditLabel">Create or edit a Events</Translate>
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
                  id="events-id"
                  label={translate('groupfaceApp.events.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('groupfaceApp.events.name')} id="events-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.events.userId')}
                id="events-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.isActive')}
                id="events-isActive"
                name="isActive"
                data-cy="isActive"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.apprRejReason')}
                id="events-apprRejReason"
                name="apprRejReason"
                data-cy="apprRejReason"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.eventType')}
                id="events-eventType"
                name="eventType"
                data-cy="eventType"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.isEnable')}
                id="events-isEnable"
                name="isEnable"
                data-cy="isEnable"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.addedBy')}
                id="events-addedBy"
                name="addedBy"
                data-cy="addedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.addedOn')}
                id="events-addedOn"
                name="addedOn"
                data-cy="addedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.updatedBy')}
                id="events-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.updatedOn')}
                id="events-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('groupfaceApp.events.approvedBy')}
                id="events-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                type="text"
              />
              <ValidatedField label={translate('groupfaceApp.events.orgId')} id="events-orgId" name="orgId" data-cy="orgId" type="text" />
              <ValidatedField
                label={translate('groupfaceApp.events.approvedOn')}
                id="events-approvedOn"
                name="approvedOn"
                data-cy="approvedOn"
                type="date"
              />
              <ValidatedField
                id="events-addedByUsr"
                name="addedByUsr"
                data-cy="addedByUsr"
                label={translate('groupfaceApp.events.addedByUsr')}
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
                id="events-updatedByUsr"
                name="updatedByUsr"
                data-cy="updatedByUsr"
                label={translate('groupfaceApp.events.updatedByUsr')}
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
                id="events-approvedByUsr"
                name="approvedByUsr"
                data-cy="approvedByUsr"
                label={translate('groupfaceApp.events.approvedByUsr')}
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
                id="events-userIdUsr"
                name="userIdUsr"
                data-cy="userIdUsr"
                label={translate('groupfaceApp.events.userIdUsr')}
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
                id="events-eventTypeObj"
                name="eventTypeObj"
                data-cy="eventTypeObj"
                label={translate('groupfaceApp.events.eventTypeObj')}
                type="select"
              >
                <option value="" key="0" />
                {masters
                  ? masters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.codeval}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="events-orgIdObj"
                name="orgIdObj"
                data-cy="orgIdObj"
                label={translate('groupfaceApp.events.orgIdObj')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/events" replace color="info">
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

export default EventsUpdate;
