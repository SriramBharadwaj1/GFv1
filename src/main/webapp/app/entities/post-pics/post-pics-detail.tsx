import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post-pics.reducer';

export const PostPicsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postPicsEntity = useAppSelector(state => state.postPics.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postPicsDetailsHeading">
          <Translate contentKey="groupfaceApp.postPics.detail.title">PostPics</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.postPics.id">Id</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.id}</dd>
          <dt>
            <span id="postid">
              <Translate contentKey="groupfaceApp.postPics.postid">Postid</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.postid}</dd>
          <dt>
            <span id="pic">
              <Translate contentKey="groupfaceApp.postPics.pic">Pic</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.pic}</dd>
          <dt>
            <span id="picpath">
              <Translate contentKey="groupfaceApp.postPics.picpath">Picpath</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.picpath}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.postPics.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.postPics.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.postPics.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.postPics.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {postPicsEntity.addedOn ? <TextFormat value={postPicsEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.postPics.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.postPics.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {postPicsEntity.updatedOn ? <TextFormat value={postPicsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.postPics.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.postPics.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {postPicsEntity.approvedOn ? <TextFormat value={postPicsEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.postPics.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.postPics.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.remarks}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.postPics.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.orgId}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="groupfaceApp.postPics.status">Status</Translate>
            </span>
          </dt>
          <dd>{postPicsEntity.status}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postPics.addedByUser">Added By User</Translate>
          </dt>
          <dd>{postPicsEntity.addedByUser ? postPicsEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postPics.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{postPicsEntity.updatedByUser ? postPicsEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postPics.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{postPicsEntity.approvedByUser ? postPicsEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postPics.postObj">Post Obj</Translate>
          </dt>
          <dd>{postPicsEntity.postObj ? postPicsEntity.postObj.message : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postPics.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{postPicsEntity.orgIdObj ? postPicsEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/post-pics" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post-pics/${postPicsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostPicsDetail;
