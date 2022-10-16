import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './remarks.reducer';

export const RemarksDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const remarksEntity = useAppSelector(state => state.remarks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="remarksDetailsHeading">
          <Translate contentKey="groupfaceApp.remarks.detail.title">Remarks</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.remarks.id">Id</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="groupfaceApp.remarks.message">Message</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.message}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="groupfaceApp.remarks.category">Category</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.category}</dd>
          <dt>
            <span id="categoryName">
              <Translate contentKey="groupfaceApp.remarks.categoryName">Category Name</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.categoryName}</dd>
          <dt>
            <span id="meta">
              <Translate contentKey="groupfaceApp.remarks.meta">Meta</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.meta}</dd>
          <dt>
            <span id="postId">
              <Translate contentKey="groupfaceApp.remarks.postId">Post Id</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.postId}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.remarks.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.isActive}</dd>
          <dt>
            <span id="apprRejReason">
              <Translate contentKey="groupfaceApp.remarks.apprRejReason">Appr Rej Reason</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.apprRejReason}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.remarks.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.remarks.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.remarks.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.addedOn ? <TextFormat value={remarksEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.remarks.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.remarks.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {remarksEntity.updatedOn ? <TextFormat value={remarksEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.remarks.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.remarks.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {remarksEntity.approvedOn ? <TextFormat value={remarksEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.remarks.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.remarks.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.remarks}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.remarks.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.orgId}</dd>
          <dt>
            <span id="otherInfo">
              <Translate contentKey="groupfaceApp.remarks.otherInfo">Other Info</Translate>
            </span>
          </dt>
          <dd>{remarksEntity.otherInfo}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.remarks.repostIdObj">Repost Id Obj</Translate>
          </dt>
          <dd>{remarksEntity.repostIdObj ? remarksEntity.repostIdObj.message : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.remarks.addedByUser">Added By User</Translate>
          </dt>
          <dd>{remarksEntity.addedByUser ? remarksEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.remarks.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{remarksEntity.updatedByUser ? remarksEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.remarks.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{remarksEntity.approvedByUser ? remarksEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.remarks.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{remarksEntity.orgIdObj ? remarksEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/remarks" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/remarks/${remarksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RemarksDetail;
