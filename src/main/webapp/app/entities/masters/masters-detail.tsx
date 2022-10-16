import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './masters.reducer';

export const MastersDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mastersEntity = useAppSelector(state => state.masters.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mastersDetailsHeading">
          <Translate contentKey="groupfaceApp.masters.detail.title">Masters</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.masters.id">Id</Translate>
            </span>
          </dt>
          <dd>{mastersEntity.id}</dd>
          <dt>
            <span id="key">
              <Translate contentKey="groupfaceApp.masters.key">Key</Translate>
            </span>
          </dt>
          <dd>{mastersEntity.key}</dd>
          <dt>
            <span id="val">
              <Translate contentKey="groupfaceApp.masters.val">Val</Translate>
            </span>
          </dt>
          <dd>{mastersEntity.val}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="groupfaceApp.masters.code">Code</Translate>
            </span>
          </dt>
          <dd>{mastersEntity.code}</dd>
          <dt>
            <span id="codeval">
              <Translate contentKey="groupfaceApp.masters.codeval">Codeval</Translate>
            </span>
          </dt>
          <dd>{mastersEntity.codeval}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="groupfaceApp.masters.status">Status</Translate>
            </span>
          </dt>
          <dd>{mastersEntity.status}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.masters.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{mastersEntity.orgId}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.masters.addedByUser">Added By User</Translate>
          </dt>
          <dd>{mastersEntity.addedByUser ? mastersEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.masters.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{mastersEntity.updatedByUser ? mastersEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.masters.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{mastersEntity.approvedByUser ? mastersEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.masters.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{mastersEntity.orgIdObj ? mastersEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/masters" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/masters/${mastersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MastersDetail;
