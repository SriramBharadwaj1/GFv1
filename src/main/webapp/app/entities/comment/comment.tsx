import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IComment } from 'app/shared/model/comment.model';
import { searchEntities, getEntities, reset } from './comment.reducer';

export const Comment = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const commentList = useAppSelector(state => state.comment.entities);
  const loading = useAppSelector(state => state.comment.loading);
  const totalItems = useAppSelector(state => state.comment.totalItems);
  const links = useAppSelector(state => state.comment.links);
  const entity = useAppSelector(state => state.comment.entity);
  const updateSuccess = useAppSelector(state => state.comment.updateSuccess);

  const getAllEntities = () => {
    if (search) {
      dispatch(
        searchEntities({
          query: search,
          page: paginationState.activePage - 1,
          size: paginationState.itemsPerPage,
          sort: `${paginationState.sort},${paginationState.order}`,
        })
      );
    } else {
      dispatch(
        getEntities({
          page: paginationState.activePage - 1,
          size: paginationState.itemsPerPage,
          sort: `${paginationState.sort},${paginationState.order}`,
        })
      );
    }
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  const startSearching = e => {
    if (search) {
      dispatch(reset());
      setPaginationState({
        ...paginationState,
        activePage: 1,
      });
      dispatch(
        searchEntities({
          query: search,
          page: paginationState.activePage - 1,
          size: paginationState.itemsPerPage,
          sort: `${paginationState.sort},${paginationState.order}`,
        })
      );
    }
    e.preventDefault();
  };

  const clear = () => {
    dispatch(reset());
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  const handleSearch = event => setSearch(event.target.value);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting, search]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="comment-heading" data-cy="CommentHeading">
        <Translate contentKey="groupfaceApp.comment.home.title">Comments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.comment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/comment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.comment.home.createLabel">Create new Comment</Translate>
          </Link>
        </div>
      </h2>
      <Row>
        <Col sm="12">
          <Form onSubmit={startSearching}>
            <FormGroup>
              <InputGroup>
                <Input
                  type="text"
                  name="search"
                  defaultValue={search}
                  onChange={handleSearch}
                  placeholder={translate('groupfaceApp.comment.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </FormGroup>
          </Form>
        </Col>
      </Row>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={commentList ? commentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {commentList && commentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="groupfaceApp.comment.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('message')}>
                    <Translate contentKey="groupfaceApp.comment.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('category')}>
                    <Translate contentKey="groupfaceApp.comment.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('categoryName')}>
                    <Translate contentKey="groupfaceApp.comment.categoryName">Category Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('meta')}>
                    <Translate contentKey="groupfaceApp.comment.meta">Meta</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('postId')}>
                    <Translate contentKey="groupfaceApp.comment.postId">Post Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="groupfaceApp.comment.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('apprRejReason')}>
                    <Translate contentKey="groupfaceApp.comment.apprRejReason">Appr Rej Reason</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isEnable')}>
                    <Translate contentKey="groupfaceApp.comment.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedBy')}>
                    <Translate contentKey="groupfaceApp.comment.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedOn')}>
                    <Translate contentKey="groupfaceApp.comment.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="groupfaceApp.comment.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="groupfaceApp.comment.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('approvedBy')}>
                    <Translate contentKey="groupfaceApp.comment.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('approvedOn')}>
                    <Translate contentKey="groupfaceApp.comment.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('comments')}>
                    <Translate contentKey="groupfaceApp.comment.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('orgId')}>
                    <Translate contentKey="groupfaceApp.comment.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('otherInfo')}>
                    <Translate contentKey="groupfaceApp.comment.otherInfo">Other Info</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.comment.copostIdObj">Copost Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.comment.addedByUser">Added By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.comment.updatedByUser">Updated By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.comment.approvedByUser">Approved By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.comment.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {commentList.map((comment, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/comment/${comment.id}`} color="link" size="sm">
                        {comment.id}
                      </Button>
                    </td>
                    <td>{comment.message}</td>
                    <td>{comment.category}</td>
                    <td>{comment.categoryName}</td>
                    <td>{comment.meta}</td>
                    <td>{comment.postId}</td>
                    <td>{comment.isActive}</td>
                    <td>{comment.apprRejReason}</td>
                    <td>{comment.isEnable}</td>
                    <td>{comment.addedBy}</td>
                    <td>{comment.addedOn ? <TextFormat type="date" value={comment.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{comment.updatedBy}</td>
                    <td>
                      {comment.updatedOn ? <TextFormat type="date" value={comment.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{comment.approvedBy}</td>
                    <td>
                      {comment.approvedOn ? <TextFormat type="date" value={comment.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{comment.comments}</td>
                    <td>{comment.orgId}</td>
                    <td>{comment.otherInfo}</td>
                    <td>{comment.copostIdObj ? <Link to={`/post/${comment.copostIdObj.id}`}>{comment.copostIdObj.message}</Link> : ''}</td>
                    <td>
                      {comment.addedByUser ? <Link to={`/user-mast/${comment.addedByUser.id}`}>{comment.addedByUser.name}</Link> : ''}
                    </td>
                    <td>
                      {comment.updatedByUser ? <Link to={`/user-mast/${comment.updatedByUser.id}`}>{comment.updatedByUser.name}</Link> : ''}
                    </td>
                    <td>
                      {comment.approvedByUser ? (
                        <Link to={`/user-mast/${comment.approvedByUser.id}`}>{comment.approvedByUser.name}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{comment.orgIdObj ? <Link to={`/organisation/${comment.orgIdObj.id}`}>{comment.orgIdObj.name}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/comment/${comment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/comment/${comment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/comment/${comment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="groupfaceApp.comment.home.notFound">No Comments found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Comment;
