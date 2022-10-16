import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './events.reducer';

export const EventsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eventsEntity = useAppSelector(state => state.events.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventsDetailsHeading">
          <Translate contentKey="groupfaceApp.events.detail.title">Events</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.events.id">Id</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.events.name">Name</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.name}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="groupfaceApp.events.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.userId}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.events.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.isActive}</dd>
          <dt>
            <span id="apprRejReason">
              <Translate contentKey="groupfaceApp.events.apprRejReason">Appr Rej Reason</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.apprRejReason}</dd>
          <dt>
            <span id="eventType">
              <Translate contentKey="groupfaceApp.events.eventType">Event Type</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.eventType}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.events.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.events.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.events.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.addedOn ? <TextFormat value={eventsEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.events.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.events.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {eventsEntity.updatedOn ? <TextFormat value={eventsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.events.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.approvedBy}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.events.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.orgId}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.events.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {eventsEntity.approvedOn ? <TextFormat value={eventsEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="groupfaceApp.events.addedByUsr">Added By Usr</Translate>
          </dt>
          <dd>{eventsEntity.addedByUsr ? eventsEntity.addedByUsr.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.events.updatedByUsr">Updated By Usr</Translate>
          </dt>
          <dd>{eventsEntity.updatedByUsr ? eventsEntity.updatedByUsr.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.events.approvedByUsr">Approved By Usr</Translate>
          </dt>
          <dd>{eventsEntity.approvedByUsr ? eventsEntity.approvedByUsr.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.events.userIdUsr">User Id Usr</Translate>
          </dt>
          <dd>{eventsEntity.userIdUsr ? eventsEntity.userIdUsr.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.events.eventTypeObj">Event Type Obj</Translate>
          </dt>
          <dd>{eventsEntity.eventTypeObj ? eventsEntity.eventTypeObj.codeval : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.events.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{eventsEntity.orgIdObj ? eventsEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/events" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/events/${eventsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventsDetail;
