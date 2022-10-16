import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAutoPostAprvlUsrs } from 'app/shared/model/auto-post-aprvl-usrs.model';
import { searchEntities, getEntities } from './auto-post-aprvl-usrs.reducer';

export const AutoPostAprvlUsrs = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const autoPostAprvlUsrsList = useAppSelector(state => state.autoPostAprvlUsrs.entities);
  const loading = useAppSelector(state => state.autoPostAprvlUsrs.loading);
  const totalItems = useAppSelector(state => state.autoPostAprvlUsrs.totalItems);

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
      <h2 id="auto-post-aprvl-usrs-heading" data-cy="AutoPostAprvlUsrsHeading">
        <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.home.title">Auto Post Aprvl Usrs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/auto-post-aprvl-usrs/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.home.createLabel">Create new Auto Post Aprvl Usrs</Translate>
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
                  placeholder={translate('groupfaceApp.autoPostAprvlUsrs.home.search')}
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
        {autoPostAprvlUsrsList && autoPostAprvlUsrsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('apUserId')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.apUserId">Ap User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tableKy')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.tableKy">Table Ky</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnable')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remarks')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgId')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('extraFields')}>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.extraFields">Extra Fields</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.addedByUser">Added By User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.updatedByUser">Updated By User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.approvedByUser">Approved By User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {autoPostAprvlUsrsList.map((autoPostAprvlUsrs, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/auto-post-aprvl-usrs/${autoPostAprvlUsrs.id}`} color="link" size="sm">
                      {autoPostAprvlUsrs.id}
                    </Button>
                  </td>
                  <td>{autoPostAprvlUsrs.apUserId}</td>
                  <td>{autoPostAprvlUsrs.tableKy}</td>
                  <td>{autoPostAprvlUsrs.isActive}</td>
                  <td>{autoPostAprvlUsrs.isEnable}</td>
                  <td>{autoPostAprvlUsrs.addedBy}</td>
                  <td>
                    {autoPostAprvlUsrs.addedOn ? (
                      <TextFormat type="date" value={autoPostAprvlUsrs.addedOn} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{autoPostAprvlUsrs.updatedBy}</td>
                  <td>
                    {autoPostAprvlUsrs.updatedOn ? (
                      <TextFormat type="date" value={autoPostAprvlUsrs.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{autoPostAprvlUsrs.approvedBy}</td>
                  <td>
                    {autoPostAprvlUsrs.approvedOn ? (
                      <TextFormat type="date" value={autoPostAprvlUsrs.approvedOn} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{autoPostAprvlUsrs.comments}</td>
                  <td>{autoPostAprvlUsrs.remarks}</td>
                  <td>{autoPostAprvlUsrs.orgId}</td>
                  <td>{autoPostAprvlUsrs.extraFields}</td>
                  <td>
                    {autoPostAprvlUsrs.user ? (
                      <Link to={`/user-mast/${autoPostAprvlUsrs.user.id}`}>{autoPostAprvlUsrs.user.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {autoPostAprvlUsrs.addedByUser ? (
                      <Link to={`/user-mast/${autoPostAprvlUsrs.addedByUser.id}`}>{autoPostAprvlUsrs.addedByUser.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {autoPostAprvlUsrs.updatedByUser ? (
                      <Link to={`/user-mast/${autoPostAprvlUsrs.updatedByUser.id}`}>{autoPostAprvlUsrs.updatedByUser.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {autoPostAprvlUsrs.approvedByUser ? (
                      <Link to={`/user-mast/${autoPostAprvlUsrs.approvedByUser.id}`}>{autoPostAprvlUsrs.approvedByUser.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {autoPostAprvlUsrs.orgIdObj ? (
                      <Link to={`/organisation/${autoPostAprvlUsrs.orgIdObj.id}`}>{autoPostAprvlUsrs.orgIdObj.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/auto-post-aprvl-usrs/${autoPostAprvlUsrs.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/auto-post-aprvl-usrs/${autoPostAprvlUsrs.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/auto-post-aprvl-usrs/${autoPostAprvlUsrs.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="groupfaceApp.autoPostAprvlUsrs.home.notFound">No Auto Post Aprvl Usrs found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={autoPostAprvlUsrsList && autoPostAprvlUsrsList.length > 0 ? '' : 'd-none'}>
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

export default AutoPostAprvlUsrs;
