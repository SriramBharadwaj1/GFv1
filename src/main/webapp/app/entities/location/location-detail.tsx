import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location.reducer';

export const LocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">
          <Translate contentKey="groupfaceApp.location.detail.title">Location</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.location.id">Id</Translate>
            </span>
          </dt>
          <dd>{locationEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="groupfaceApp.location.code">Code</Translate>
            </span>
          </dt>
          <dd>{locationEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.location.name">Name</Translate>
            </span>
          </dt>
          <dd>{locationEntity.name}</dd>
          <dt>
            <span id="desc">
              <Translate contentKey="groupfaceApp.location.desc">Desc</Translate>
            </span>
          </dt>
          <dd>{locationEntity.desc}</dd>
          <dt>
            <span id="parentId">
              <Translate contentKey="groupfaceApp.location.parentId">Parent Id</Translate>
            </span>
          </dt>
          <dd>{locationEntity.parentId}</dd>
          <dt>
            <span id="moderator1Id">
              <Translate contentKey="groupfaceApp.location.moderator1Id">Moderator 1 Id</Translate>
            </span>
          </dt>
          <dd>{locationEntity.moderator1Id}</dd>
          <dt>
            <span id="moderator2Id">
              <Translate contentKey="groupfaceApp.location.moderator2Id">Moderator 2 Id</Translate>
            </span>
          </dt>
          <dd>{locationEntity.moderator2Id}</dd>
          <dt>
            <span id="moderator1Code">
              <Translate contentKey="groupfaceApp.location.moderator1Code">Moderator 1 Code</Translate>
            </span>
          </dt>
          <dd>{locationEntity.moderator1Code}</dd>
          <dt>
            <span id="moderator2Code">
              <Translate contentKey="groupfaceApp.location.moderator2Code">Moderator 2 Code</Translate>
            </span>
          </dt>
          <dd>{locationEntity.moderator2Code}</dd>
          <dt>
            <span id="parentTableKy">
              <Translate contentKey="groupfaceApp.location.parentTableKy">Parent Table Ky</Translate>
            </span>
          </dt>
          <dd>{locationEntity.parentTableKy}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="groupfaceApp.location.type">Type</Translate>
            </span>
          </dt>
          <dd>{locationEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="groupfaceApp.location.status">Status</Translate>
            </span>
          </dt>
          <dd>{locationEntity.status}</dd>
          <dt>
            <span id="zone">
              <Translate contentKey="groupfaceApp.location.zone">Zone</Translate>
            </span>
          </dt>
          <dd>{locationEntity.zone}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.location.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{locationEntity.orgId}</dd>
          <dt>
            <span id="hist">
              <Translate contentKey="groupfaceApp.location.hist">Hist</Translate>
            </span>
          </dt>
          <dd>{locationEntity.hist}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.location.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{locationEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.location.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {locationEntity.addedOn ? <TextFormat value={locationEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.location.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{locationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.location.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {locationEntity.updatedOn ? <TextFormat value={locationEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.location.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{locationEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.location.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {locationEntity.approvedOn ? <TextFormat value={locationEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="otherinfo">
              <Translate contentKey="groupfaceApp.location.otherinfo">Otherinfo</Translate>
            </span>
          </dt>
          <dd>{locationEntity.otherinfo}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.location.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{locationEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.location.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{locationEntity.remarks}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.location.addedByUser">Added By User</Translate>
          </dt>
          <dd>{locationEntity.addedByUser ? locationEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.location.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{locationEntity.updatedByUser ? locationEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.location.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{locationEntity.approvedByUser ? locationEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.location.moderator1IdUser">Moderator 1 Id User</Translate>
          </dt>
          <dd>{locationEntity.moderator1IdUser ? locationEntity.moderator1IdUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.location.moderator2IdUser">Moderator 2 Id User</Translate>
          </dt>
          <dd>{locationEntity.moderator2IdUser ? locationEntity.moderator2IdUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.location.parentIdObj">Parent Id Obj</Translate>
          </dt>
          <dd>{locationEntity.parentIdObj ? locationEntity.parentIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.location.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{locationEntity.orgIdObj ? locationEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
