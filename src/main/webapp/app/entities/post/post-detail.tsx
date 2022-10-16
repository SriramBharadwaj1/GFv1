import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post.reducer';

export const PostDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postEntity = useAppSelector(state => state.post.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postDetailsHeading">
          <Translate contentKey="groupfaceApp.post.detail.title">Post</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.post.id">Id</Translate>
            </span>
          </dt>
          <dd>{postEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="groupfaceApp.post.message">Message</Translate>
            </span>
          </dt>
          <dd>{postEntity.message}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="groupfaceApp.post.category">Category</Translate>
            </span>
          </dt>
          <dd>{postEntity.category}</dd>
          <dt>
            <span id="categoryName">
              <Translate contentKey="groupfaceApp.post.categoryName">Category Name</Translate>
            </span>
          </dt>
          <dd>{postEntity.categoryName}</dd>
          <dt>
            <span id="meta">
              <Translate contentKey="groupfaceApp.post.meta">Meta</Translate>
            </span>
          </dt>
          <dd>{postEntity.meta}</dd>
          <dt>
            <span id="tableKy">
              <Translate contentKey="groupfaceApp.post.tableKy">Table Ky</Translate>
            </span>
          </dt>
          <dd>{postEntity.tableKy}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.post.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{postEntity.isActive}</dd>
          <dt>
            <span id="actRejReason">
              <Translate contentKey="groupfaceApp.post.actRejReason">Act Rej Reason</Translate>
            </span>
          </dt>
          <dd>{postEntity.actRejReason}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.post.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{postEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.post.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{postEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.post.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{postEntity.addedOn ? <TextFormat value={postEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.post.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{postEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.post.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>{postEntity.updatedOn ? <TextFormat value={postEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.post.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{postEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.post.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>{postEntity.approvedOn ? <TextFormat value={postEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.post.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{postEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.post.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{postEntity.remarks}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="groupfaceApp.post.type">Type</Translate>
            </span>
          </dt>
          <dd>{postEntity.type}</dd>
          <dt>
            <span id="isSalesPost">
              <Translate contentKey="groupfaceApp.post.isSalesPost">Is Sales Post</Translate>
            </span>
          </dt>
          <dd>{postEntity.isSalesPost}</dd>
          <dt>
            <span id="salesCategory">
              <Translate contentKey="groupfaceApp.post.salesCategory">Sales Category</Translate>
            </span>
          </dt>
          <dd>{postEntity.salesCategory}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="groupfaceApp.post.price">Price</Translate>
            </span>
          </dt>
          <dd>{postEntity.price}</dd>
          <dt>
            <span id="validFrom">
              <Translate contentKey="groupfaceApp.post.validFrom">Valid From</Translate>
            </span>
          </dt>
          <dd>{postEntity.validFrom ? <TextFormat value={postEntity.validFrom} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="validTill">
              <Translate contentKey="groupfaceApp.post.validTill">Valid Till</Translate>
            </span>
          </dt>
          <dd>{postEntity.validTill ? <TextFormat value={postEntity.validTill} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="phoneArea">
              <Translate contentKey="groupfaceApp.post.phoneArea">Phone Area</Translate>
            </span>
          </dt>
          <dd>{postEntity.phoneArea}</dd>
          <dt>
            <span id="phno">
              <Translate contentKey="groupfaceApp.post.phno">Phno</Translate>
            </span>
          </dt>
          <dd>{postEntity.phno}</dd>
          <dt>
            <span id="videoGrp">
              <Translate contentKey="groupfaceApp.post.videoGrp">Video Grp</Translate>
            </span>
          </dt>
          <dd>{postEntity.videoGrp}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.post.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{postEntity.orgId}</dd>
          <dt>
            <span id="otherInfo">
              <Translate contentKey="groupfaceApp.post.otherInfo">Other Info</Translate>
            </span>
          </dt>
          <dd>{postEntity.otherInfo}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.post.addedByUser">Added By User</Translate>
          </dt>
          <dd>{postEntity.addedByUser ? postEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.post.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{postEntity.updatedByUser ? postEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.post.approvedByUser">Approved By User</Translate>
          </dt>
          <dd>{postEntity.approvedByUser ? postEntity.approvedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.post.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{postEntity.orgIdObj ? postEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostDetail;
