import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './comment.reducer';

export const CommentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const commentEntity = useAppSelector(state => state.comment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commentDetailsHeading">
          <Translate contentKey="groupfaceApp.comment.detail.title">Comment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.comment.id">Id</Translate>
            </span>
          </dt>
          <dd>{commentEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="groupfaceApp.comment.message">Message</Translate>
            </span>
          </dt>
          <dd>{commentEntity.message}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="groupfaceApp.comment.category">Category</Translate>
            </span>
          </dt>
          <dd>{commentEntity.category}</dd>
          <dt>
            <span id="categoryName">
              <Translate contentKey="groupfaceApp.comment.categoryName">Category Name</Translate>
            </span>
          </dt>
          <dd>{commentEntity.categoryName}</dd>
          <dt>
            <span id="meta">
              <Translate contentKey="groupfaceApp.comment.meta">Meta</Translate>
            </span>
          </dt>
          <dd>{commentEntity.meta}</dd>
          <dt>
            <span id="postId">
              <Translate contentKey="groupfaceApp.comment.postId">Post Id</Translate>
            </span>
          </dt>
          <dd>{commentEntity.postId}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.comment.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{commentEntity.isActive}</dd>
          <dt>
            <span id="apprRejReason">
              <Translate contentKey="groupfaceApp.comment.apprRejReason">Appr Rej Reason</Translate>
            </span>
          </dt>
          <dd>{commentEntity.apprRejReason}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.comment.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{commentEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.comment.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{commentEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.comment.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{commentEntity.addedOn ? <TextFormat value={commentEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.comment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{commentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.comment.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {commentEntity.updatedOn ? <TextFormat value={commentEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.comment.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{commentEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.comment.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {commentEntity.approvedOn ? <TextFormat value={commentEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.comment.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{commentEntity.comments}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.comment.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{commentEntity.orgId}</dd>
          <dt>
            <span id="otherInfo">
              <Translate contentKey="groupfaceApp.comment.otherInfo">Other Info</Translate>
            </span>
          </dt>
          <dd>{commentEntity.otherInfo}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.comment.copostIdObj">Copost Id Obj</Translate>
          </dt>
          <dd>{commentEntity.copostIdObj ? commentEntity.copostIdObj.message : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.comment.addedByUser">Added By User</Translate>
          </dt>
          <dd>{commentEntity.addedByUser ? commentEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.comment.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{commentEntity.updatedByUser ? commentEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.comment.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{commentEntity.approvedByUser ? commentEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.comment.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{commentEntity.orgIdObj ? commentEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comment/${commentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommentDetail;
