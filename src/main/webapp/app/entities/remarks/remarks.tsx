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

import { IRemarks } from 'app/shared/model/remarks.model';
import { searchEntities, getEntities, reset } from './remarks.reducer';

export const Remarks = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const remarksList = useAppSelector(state => state.remarks.entities);
  const loading = useAppSelector(state => state.remarks.loading);
  const totalItems = useAppSelector(state => state.remarks.totalItems);
  const links = useAppSelector(state => state.remarks.links);
  const entity = useAppSelector(state => state.remarks.entity);
  const updateSuccess = useAppSelector(state => state.remarks.updateSuccess);

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
      <h2 id="remarks-heading" data-cy="RemarksHeading">
        <Translate contentKey="groupfaceApp.remarks.home.title">Remarks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.remarks.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/remarks/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.remarks.home.createLabel">Create new Remarks</Translate>
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
                  placeholder={translate('groupfaceApp.remarks.home.search')}
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
          dataLength={remarksList ? remarksList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {remarksList && remarksList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="groupfaceApp.remarks.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('message')}>
                    <Translate contentKey="groupfaceApp.remarks.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('category')}>
                    <Translate contentKey="groupfaceApp.remarks.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('categoryName')}>
                    <Translate contentKey="groupfaceApp.remarks.categoryName">Category Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('meta')}>
                    <Translate contentKey="groupfaceApp.remarks.meta">Meta</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('postId')}>
                    <Translate contentKey="groupfaceApp.remarks.postId">Post Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="groupfaceApp.remarks.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('apprRejReason')}>
                    <Translate contentKey="groupfaceApp.remarks.apprRejReason">Appr Rej Reason</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isEnable')}>
                    <Translate contentKey="groupfaceApp.remarks.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedBy')}>
                    <Translate contentKey="groupfaceApp.remarks.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('addedOn')}>
                    <Translate contentKey="groupfaceApp.remarks.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="groupfaceApp.remarks.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="groupfaceApp.remarks.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('approvedBy')}>
                    <Translate contentKey="groupfaceApp.remarks.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('approvedOn')}>
                    <Translate contentKey="groupfaceApp.remarks.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('comments')}>
                    <Translate contentKey="groupfaceApp.remarks.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remarks')}>
                    <Translate contentKey="groupfaceApp.remarks.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('orgId')}>
                    <Translate contentKey="groupfaceApp.remarks.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('otherInfo')}>
                    <Translate contentKey="groupfaceApp.remarks.otherInfo">Other Info</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.remarks.repostIdObj">Repost Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.remarks.addedByUser">Added By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.remarks.updatedByUser">Updated By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.remarks.approvedByUser">Approved By User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="groupfaceApp.remarks.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {remarksList.map((remarks, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/remarks/${remarks.id}`} color="link" size="sm">
                        {remarks.id}
                      </Button>
                    </td>
                    <td>{remarks.message}</td>
                    <td>{remarks.category}</td>
                    <td>{remarks.categoryName}</td>
                    <td>{remarks.meta}</td>
                    <td>{remarks.postId}</td>
                    <td>{remarks.isActive}</td>
                    <td>{remarks.apprRejReason}</td>
                    <td>{remarks.isEnable}</td>
                    <td>{remarks.addedBy}</td>
                    <td>{remarks.addedOn ? <TextFormat type="date" value={remarks.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{remarks.updatedBy}</td>
                    <td>
                      {remarks.updatedOn ? <TextFormat type="date" value={remarks.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{remarks.approvedBy}</td>
                    <td>
                      {remarks.approvedOn ? <TextFormat type="date" value={remarks.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{remarks.comments}</td>
                    <td>{remarks.remarks}</td>
                    <td>{remarks.orgId}</td>
                    <td>{remarks.otherInfo}</td>
                    <td>{remarks.repostIdObj ? <Link to={`/post/${remarks.repostIdObj.id}`}>{remarks.repostIdObj.message}</Link> : ''}</td>
                    <td>
                      {remarks.addedByUser ? <Link to={`/user-mast/${remarks.addedByUser.id}`}>{remarks.addedByUser.name}</Link> : ''}
                    </td>
                    <td>
                      {remarks.updatedByUser ? <Link to={`/user-mast/${remarks.updatedByUser.id}`}>{remarks.updatedByUser.name}</Link> : ''}
                    </td>
                    <td>
                      {remarks.approvedByUser ? (
                        <Link to={`/user-mast/${remarks.approvedByUser.id}`}>{remarks.approvedByUser.name}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{remarks.orgIdObj ? <Link to={`/organisation/${remarks.orgIdObj.id}`}>{remarks.orgIdObj.name}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/remarks/${remarks.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/remarks/${remarks.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/remarks/${remarks.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="groupfaceApp.remarks.home.notFound">No Remarks found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Remarks;
