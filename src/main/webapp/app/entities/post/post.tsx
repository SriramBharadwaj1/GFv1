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

import { IPost } from 'app/shared/model/post.model';
import { searchEntities, getEntities, reset } from './post.reducer';

export const Post = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const postList = useAppSelector(state => state.post.entities);
  const loading = useAppSelector(state => state.post.loading);
  const totalItems = useAppSelector(state => state.post.totalItems);
  const links = useAppSelector(state => state.post.links);
  const entity = useAppSelector(state => state.post.entity);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);

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
      <h2 id="post-heading" data-cy="PostHeading">
        <Translate contentKey="groupfaceApp.post.home.title">Posts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.post.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/post/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.post.home.createLabel">Create new Post</Translate>
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
                  placeholder={translate('groupfaceApp.post.home.search')}
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
          dataLength={postList ? postList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {postList && postList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="groupfaceApp.post.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('message')}>
                    <Translate contentKey="groupfaceApp.post.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('category')}>
                    <Translate contentKey="groupfaceApp.post.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('categoryName')}>
                    <Translate contentKey="groupfaceApp.post.categoryName">Category Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('meta')}>
                    <Translate contentKey="groupfaceApp.post.meta">Meta</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tableKy')}>
                    <Translate contentKey="groupfaceApp.post.tableKy">Table Ky</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="groupfaceApp.post.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('actRejReason')}>
                    <Translate contentKey="groupfaceApp.post.actRejReason">Act Rej Reason</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isEnable')}>
                    <Translate contentKey="groupfaceApp.post.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedBy')}>
                    <Translate contentKey="groupfaceApp.post.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedOn')}>
                    <Translate contentKey="groupfaceApp.post.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="groupfaceApp.post.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="groupfaceApp.post.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('approvedBy')}>
                    <Translate contentKey="groupfaceApp.post.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('approvedOn')}>
                    <Translate contentKey="groupfaceApp.post.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('comments')}>
                    <Translate contentKey="groupfaceApp.post.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remarks')}>
                    <Translate contentKey="groupfaceApp.post.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="groupfaceApp.post.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isSalesPost')}>
                    <Translate contentKey="groupfaceApp.post.isSalesPost">Is Sales Post</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('salesCategory')}>
                    <Translate contentKey="groupfaceApp.post.salesCategory">Sales Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('price')}>
                    <Translate contentKey="groupfaceApp.post.price">Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('validFrom')}>
                    <Translate contentKey="groupfaceApp.post.validFrom">Valid From</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('validTill')}>
                    <Translate contentKey="groupfaceApp.post.validTill">Valid Till</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('phoneArea')}>
                    <Translate contentKey="groupfaceApp.post.phoneArea">Phone Area</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('phno')}>
                    <Translate contentKey="groupfaceApp.post.phno">Phno</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('videoGrp')}>
                    <Translate contentKey="groupfaceApp.post.videoGrp">Video Grp</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('orgId')}>
                    <Translate contentKey="groupfaceApp.post.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('otherInfo')}>
                    <Translate contentKey="groupfaceApp.post.otherInfo">Other Info</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.post.addedByUser">Added By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.post.updatedByUser">Updated By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.post.approvedByUser">Approved By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.post.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {postList.map((post, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/post/${post.id}`} color="link" size="sm">
                        {post.id}
                      </Button>
                    </td>
                    <td>{post.message}</td>
                    <td>{post.category}</td>
                    <td>{post.categoryName}</td>
                    <td>{post.meta}</td>
                    <td>{post.tableKy}</td>
                    <td>{post.isActive}</td>
                    <td>{post.actRejReason}</td>
                    <td>{post.isEnable}</td>
                    <td>{post.addedBy}</td>
                    <td>{post.addedOn ? <TextFormat type="date" value={post.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{post.updatedBy}</td>
                    <td>{post.updatedOn ? <TextFormat type="date" value={post.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{post.approvedBy}</td>
                    <td>{post.approvedOn ? <TextFormat type="date" value={post.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{post.comments}</td>
                    <td>{post.remarks}</td>
                    <td>{post.type}</td>
                    <td>{post.isSalesPost}</td>
                    <td>{post.salesCategory}</td>
                    <td>{post.price}</td>
                    <td>{post.validFrom ? <TextFormat type="date" value={post.validFrom} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{post.validTill ? <TextFormat type="date" value={post.validTill} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{post.phoneArea}</td>
                    <td>{post.phno}</td>
                    <td>{post.videoGrp}</td>
                    <td>{post.orgId}</td>
                    <td>{post.otherInfo}</td>
                    <td>{post.addedByUser ? <Link to={`/user-mast/${post.addedByUser.id}`}>{post.addedByUser.name}</Link> : ''}</td>
                    <td>{post.updatedByUser ? <Link to={`/user-mast/${post.updatedByUser.id}`}>{post.updatedByUser.name}</Link> : ''}</td>
                    <td>
                      {post.approvedByUser ? <Link to={`/user-mast/${post.approvedByUser.id}`}>{post.approvedByUser.name}</Link> : ''}
                    </td>
                    <td>{post.orgIdObj ? <Link to={`/organisation/${post.orgIdObj.id}`}>{post.orgIdObj.name}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/post/${post.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/post/${post.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/post/${post.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="groupfaceApp.post.home.notFound">No Posts found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Post;
