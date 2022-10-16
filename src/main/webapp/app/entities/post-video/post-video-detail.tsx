import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post-video.reducer';

export const PostVideoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postVideoEntity = useAppSelector(state => state.postVideo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postVideoDetailsHeading">
          <Translate contentKey="groupfaceApp.postVideo.detail.title">PostVideo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.postVideo.id">Id</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.id}</dd>
          <dt>
            <span id="postid">
              <Translate contentKey="groupfaceApp.postVideo.postid">Postid</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.postid}</dd>
          <dt>
            <span id="video">
              <Translate contentKey="groupfaceApp.postVideo.video">Video</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.video}</dd>
          <dt>
            <span id="videopath">
              <Translate contentKey="groupfaceApp.postVideo.videopath">Videopath</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.videopath}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.postVideo.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.postVideo.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.postVideo.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.postVideo.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {postVideoEntity.addedOn ? <TextFormat value={postVideoEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.postVideo.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.postVideo.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {postVideoEntity.updatedOn ? <TextFormat value={postVideoEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.postVideo.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.postVideo.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {postVideoEntity.approvedOn ? (
              <TextFormat value={postVideoEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.postVideo.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.postVideo.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.remarks}</dd>
          <dt>
            <span id="primVideo">
              <Translate contentKey="groupfaceApp.postVideo.primVideo">Prim Video</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.primVideo}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.postVideo.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.orgId}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="groupfaceApp.postVideo.status">Status</Translate>
            </span>
          </dt>
          <dd>{postVideoEntity.status}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postVideo.addedByUser">Added By User</Translate>
          </dt>
          <dd>{postVideoEntity.addedByUser ? postVideoEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postVideo.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{postVideoEntity.updatedByUser ? postVideoEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postVideo.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{postVideoEntity.approvedByUser ? postVideoEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postVideo.postidObj">Postid Obj</Translate>
          </dt>
          <dd>{postVideoEntity.postidObj ? postVideoEntity.postidObj.message : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.postVideo.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{postVideoEntity.orgIdObj ? postVideoEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/post-video" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post-video/${postVideoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostVideoDetail;
