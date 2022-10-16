import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPostPics } from 'app/shared/model/post-pics.model';
import { searchEntities, getEntities } from './post-pics.reducer';

export const PostPics = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const postPicsList = useAppSelector(state => state.postPics.entities);
  const loading = useAppSelector(state => state.postPics.loading);
  const totalItems = useAppSelector(state => state.postPics.totalItems);

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

  const startSearching = e => {
    if (search) {
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
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  const handleSearch = event => setSearch(event.target.value);

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort, search]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="post-pics-heading" data-cy="PostPicsHeading">
        <Translate contentKey="groupfaceApp.postPics.home.title">Post Pics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.postPics.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/post-pics/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.postPics.home.createLabel">Create new Post Pics</Translate>
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
                  placeholder={translate('groupfaceApp.postPics.home.search')}
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
        {postPicsList && postPicsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.postPics.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('postid')}>
                  <Translate contentKey="groupfaceApp.postPics.postid">Postid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pic')}>
                  <Translate contentKey="groupfaceApp.postPics.pic">Pic</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('picpath')}>
                  <Translate contentKey="groupfaceApp.postPics.picpath">Picpath</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.postPics.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnable')}>
                  <Translate contentKey="groupfaceApp.postPics.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.postPics.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.postPics.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.postPics.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.postPics.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.postPics.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.postPics.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="groupfaceApp.postPics.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remarks')}>
                  <Translate contentKey="groupfaceApp.postPics.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgId')}>
                  <Translate contentKey="groupfaceApp.postPics.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="groupfaceApp.postPics.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.postPics.addedByUser">Added By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.postPics.updatedByUser">Updated By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.postPics.approvedByUser">Approved By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.postPics.postObj">Post Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.postPics.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {postPicsList.map((postPics, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/post-pics/${postPics.id}`} color="link" size="sm">
                      {postPics.id}
                    </Button>
                  </td>
                  <td>{postPics.postid}</td>
                  <td>{postPics.pic}</td>
                  <td>{postPics.picpath}</td>
                  <td>{postPics.isActive}</td>
                  <td>{postPics.isEnable}</td>
                  <td>{postPics.addedBy}</td>
                  <td>{postPics.addedOn ? <TextFormat type="date" value={postPics.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{postPics.updatedBy}</td>
                  <td>
                    {postPics.updatedOn ? <TextFormat type="date" value={postPics.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{postPics.approvedBy}</td>
                  <td>
                    {postPics.approvedOn ? <TextFormat type="date" value={postPics.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{postPics.comments}</td>
                  <td>{postPics.remarks}</td>
                  <td>{postPics.orgId}</td>
                  <td>{postPics.status}</td>
                  <td>
                    {postPics.addedByUser ? <Link to={`/user-mast/${postPics.addedByUser.id}`}>{postPics.addedByUser.name}</Link> : ''}
                  </td>
                  <td>
                    {postPics.updatedByUser ? (
                      <Link to={`/user-mast/${postPics.updatedByUser.id}`}>{postPics.updatedByUser.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {postPics.approvedByUser ? (
                      <Link to={`/user-mast/${postPics.approvedByUser.id}`}>{postPics.approvedByUser.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{postPics.postObj ? <Link to={`/post/${postPics.postObj.id}`}>{postPics.postObj.message}</Link> : ''}</td>
                  <td>{postPics.orgIdObj ? <Link to={`/organisation/${postPics.orgIdObj.id}`}>{postPics.orgIdObj.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/post-pics/${postPics.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/post-pics/${postPics.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/post-pics/${postPics.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="groupfaceApp.postPics.home.notFound">No Post Pics found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={postPicsList && postPicsList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default PostPics;
