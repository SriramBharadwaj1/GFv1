import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGroupUser } from 'app/shared/model/group-user.model';
import { searchEntities, getEntities } from './group-user.reducer';

export const GroupUser = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const groupUserList = useAppSelector(state => state.groupUser.entities);
  const loading = useAppSelector(state => state.groupUser.loading);
  const totalItems = useAppSelector(state => state.groupUser.totalItems);

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
      <h2 id="group-user-heading" data-cy="GroupUserHeading">
        <Translate contentKey="groupfaceApp.groupUser.home.title">Group Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.groupUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/group-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.groupUser.home.createLabel">Create new Group User</Translate>
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
                  placeholder={translate('groupfaceApp.groupUser.home.search')}
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
        {groupUserList && groupUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.groupUser.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('groupId')}>
                  <Translate contentKey="groupfaceApp.groupUser.groupId">Group Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('grpUser')}>
                  <Translate contentKey="groupfaceApp.groupUser.grpUser">Grp User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.groupUser.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnable')}>
                  <Translate contentKey="groupfaceApp.groupUser.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.groupUser.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.groupUser.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.groupUser.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.groupUser.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.groupUser.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.groupUser.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="groupfaceApp.groupUser.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remarks')}>
                  <Translate contentKey="groupfaceApp.groupUser.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgId')}>
                  <Translate contentKey="groupfaceApp.groupUser.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userType')}>
                  <Translate contentKey="groupfaceApp.groupUser.userType">User Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.groupUser.addedByUser">Added By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.groupUser.updatedByUser">Updated By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.groupUser.approvedByUser">Approved By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.groupUser.groupIdObj">Group Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.groupUser.grpUserObj">Grp User Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.groupUser.userTypeObj">User Type Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.groupUser.orgIdOb">Org Id Ob</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {groupUserList.map((groupUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/group-user/${groupUser.id}`} color="link" size="sm">
                      {groupUser.id}
                    </Button>
                  </td>
                  <td>{groupUser.groupId}</td>
                  <td>{groupUser.grpUser}</td>
                  <td>{groupUser.isActive}</td>
                  <td>{groupUser.isEnable}</td>
                  <td>{groupUser.addedBy}</td>
                  <td>{groupUser.addedOn ? <TextFormat type="date" value={groupUser.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{groupUser.updatedBy}</td>
                  <td>
                    {groupUser.updatedOn ? <TextFormat type="date" value={groupUser.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{groupUser.approvedBy}</td>
                  <td>
                    {groupUser.approvedOn ? <TextFormat type="date" value={groupUser.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{groupUser.comments}</td>
                  <td>{groupUser.remarks}</td>
                  <td>{groupUser.orgId}</td>
                  <td>{groupUser.userType}</td>
                  <td>
                    {groupUser.addedByUser ? <Link to={`/user-mast/${groupUser.addedByUser.id}`}>{groupUser.addedByUser.name}</Link> : ''}
                  </td>
                  <td>
                    {groupUser.updatedByUser ? (
                      <Link to={`/user-mast/${groupUser.updatedByUser.id}`}>{groupUser.updatedByUser.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {groupUser.approvedByUser ? (
                      <Link to={`/user-mast/${groupUser.approvedByUser.id}`}>{groupUser.approvedByUser.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{groupUser.groupIdObj ? <Link to={`/groups/${groupUser.groupIdObj.id}`}>{groupUser.groupIdObj.name}</Link> : ''}</td>
                  <td>
                    {groupUser.grpUserObj ? <Link to={`/user-mast/${groupUser.grpUserObj.id}`}>{groupUser.grpUserObj.name}</Link> : ''}
                  </td>
                  <td>
                    {groupUser.userTypeObj ? <Link to={`/masters/${groupUser.userTypeObj.id}`}>{groupUser.userTypeObj.codeval}</Link> : ''}
                  </td>
                  <td>{groupUser.orgIdOb ? <Link to={`/organisation/${groupUser.orgIdOb.id}`}>{groupUser.orgIdOb.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/group-user/${groupUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/group-user/${groupUser.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/group-user/${groupUser.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="groupfaceApp.groupUser.home.notFound">No Group Users found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={groupUserList && groupUserList.length > 0 ? '' : 'd-none'}>
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

export default GroupUser;
