import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './caste.reducer';

export const CasteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const casteEntity = useAppSelector(state => state.caste.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="casteDetailsHeading">
          <Translate contentKey="groupfaceApp.caste.detail.title">Caste</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.caste.id">Id</Translate>
            </span>
          </dt>
          <dd>{casteEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="groupfaceApp.caste.code">Code</Translate>
            </span>
          </dt>
          <dd>{casteEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.caste.name">Name</Translate>
            </span>
          </dt>
          <dd>{casteEntity.name}</dd>
          <dt>
            <span id="desc">
              <Translate contentKey="groupfaceApp.caste.desc">Desc</Translate>
            </span>
          </dt>
          <dd>{casteEntity.desc}</dd>
          <dt>
            <span id="parentId">
              <Translate contentKey="groupfaceApp.caste.parentId">Parent Id</Translate>
            </span>
          </dt>
          <dd>{casteEntity.parentId}</dd>
          <dt>
            <span id="moderatorId">
              <Translate contentKey="groupfaceApp.caste.moderatorId">Moderator Id</Translate>
            </span>
          </dt>
          <dd>{casteEntity.moderatorId}</dd>
          <dt>
            <span id="parentTableKy">
              <Translate contentKey="groupfaceApp.caste.parentTableKy">Parent Table Ky</Translate>
            </span>
          </dt>
          <dd>{casteEntity.parentTableKy}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="groupfaceApp.caste.status">Status</Translate>
            </span>
          </dt>
          <dd>{casteEntity.status}</dd>
          <dt>
            <span id="zone">
              <Translate contentKey="groupfaceApp.caste.zone">Zone</Translate>
            </span>
          </dt>
          <dd>{casteEntity.zone}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.caste.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{casteEntity.orgId}</dd>
          <dt>
            <span id="hist">
              <Translate contentKey="groupfaceApp.caste.hist">Hist</Translate>
            </span>
          </dt>
          <dd>{casteEntity.hist}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.caste.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{casteEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.caste.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{casteEntity.addedOn ? <TextFormat value={casteEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.caste.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{casteEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.caste.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{casteEntity.updatedOn ? <TextFormat value={casteEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.caste.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{casteEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.caste.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {casteEntity.approvedOn ? <TextFormat value={casteEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="otherinfo">
              <Translate contentKey="groupfaceApp.caste.otherinfo">Otherinfo</Translate>
            </span>
          </dt>
          <dd>{casteEntity.otherinfo}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.caste.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{casteEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.caste.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{casteEntity.remarks}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.caste.addedByUser">Added By User</Translate>
          </dt>
          <dd>{casteEntity.addedByUser ? casteEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.caste.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{casteEntity.updatedByUser ? casteEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.caste.approvedByObj">Approved By Obj</Translate>
          </dt>
          <dd>{casteEntity.approvedByObj ? casteEntity.approvedByObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.caste.parentIdObj">Parent Id Obj</Translate>
          </dt>
          <dd>{casteEntity.parentIdObj ? casteEntity.parentIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.caste.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{casteEntity.orgIdObj ? casteEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/caste" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/caste/${casteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CasteDetail;
