import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rating.reducer';

export const RatingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ratingEntity = useAppSelector(state => state.rating.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ratingDetailsHeading">
          <Translate contentKey="groupfaceApp.rating.detail.title">Rating</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.rating.id">Id</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.id}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="groupfaceApp.rating.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.rating}</dd>
          <dt>
            <span id="postId">
              <Translate contentKey="groupfaceApp.rating.postId">Post Id</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.postId}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.rating.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.isActive}</dd>
          <dt>
            <span id="apprRejReason">
              <Translate contentKey="groupfaceApp.rating.apprRejReason">Appr Rej Reason</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.apprRejReason}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.rating.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.rating.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.rating.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.addedOn ? <TextFormat value={ratingEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.rating.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.rating.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {ratingEntity.updatedOn ? <TextFormat value={ratingEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.rating.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.approvedBy}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.rating.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{ratingEntity.orgId}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.rating.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {ratingEntity.approvedOn ? <TextFormat value={ratingEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="groupfaceApp.rating.rapostIdObj">Rapost Id Obj</Translate>
          </dt>
          <dd>{ratingEntity.rapostIdObj ? ratingEntity.rapostIdObj.message : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.rating.addedByUser">Added By User</Translate>
          </dt>
          <dd>{ratingEntity.addedByUser ? ratingEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.rating.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{ratingEntity.updatedByUser ? ratingEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.rating.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{ratingEntity.approvedByUser ? ratingEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.rating.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{ratingEntity.orgIdObj ? ratingEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/rating" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rating/${ratingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RatingDetail;
