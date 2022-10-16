import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEvents } from 'app/shared/model/events.model';
import { searchEntities, getEntities } from './events.reducer';

export const Events = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const eventsList = useAppSelector(state => state.events.entities);
  const loading = useAppSelector(state => state.events.loading);
  const totalItems = useAppSelector(state => state.events.totalItems);

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
      <h2 id="events-heading" data-cy="EventsHeading">
        <Translate contentKey="groupfaceApp.events.home.title">Events</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.events.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/events/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.events.home.createLabel">Create new Events</Translate>
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
                  placeholder={translate('groupfaceApp.events.home.search')}
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
        {eventsList && eventsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.events.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="groupfaceApp.events.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userId')}>
                  <Translate contentKey="groupfaceApp.events.userId">User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.events.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('apprRejReason')}>
                  <Translate contentKey="groupfaceApp.events.apprRejReason">Appr Rej Reason</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eventType')}>
                  <Translate contentKey="groupfaceApp.events.eventType">Event Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnable')}>
                  <Translate contentKey="groupfaceApp.events.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.events.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.events.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.events.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.events.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.events.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgId')}>
                  <Translate contentKey="groupfaceApp.events.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.events.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.events.addedByUsr">Added By Usr</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.events.updatedByUsr">Updated By Usr</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.events.approvedByUsr">Approved By Usr</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.events.userIdUsr">User Id Usr</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.events.eventTypeObj">Event Type Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.events.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eventsList.map((events, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/events/${events.id}`} color="link" size="sm">
                      {events.id}
                    </Button>
                  </td>
                  <td>{events.name}</td>
                  <td>{events.userId}</td>
                  <td>{events.isActive}</td>
                  <td>{events.apprRejReason}</td>
                  <td>{events.eventType}</td>
                  <td>{events.isEnable}</td>
                  <td>{events.addedBy}</td>
                  <td>{events.addedOn ? <TextFormat type="date" value={events.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{events.updatedBy}</td>
                  <td>{events.updatedOn ? <TextFormat type="date" value={events.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{events.approvedBy}</td>
                  <td>{events.orgId}</td>
                  <td>{events.approvedOn ? <TextFormat type="date" value={events.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{events.addedByUsr ? <Link to={`/user-mast/${events.addedByUsr.id}`}>{events.addedByUsr.name}</Link> : ''}</td>
                  <td>{events.updatedByUsr ? <Link to={`/user-mast/${events.updatedByUsr.id}`}>{events.updatedByUsr.name}</Link> : ''}</td>
                  <td>
                    {events.approvedByUsr ? <Link to={`/user-mast/${events.approvedByUsr.id}`}>{events.approvedByUsr.name}</Link> : ''}
                  </td>
                  <td>{events.userIdUsr ? <Link to={`/user-mast/${events.userIdUsr.id}`}>{events.userIdUsr.name}</Link> : ''}</td>
                  <td>{events.eventTypeObj ? <Link to={`/masters/${events.eventTypeObj.id}`}>{events.eventTypeObj.codeval}</Link> : ''}</td>
                  <td>{events.orgIdObj ? <Link to={`/organisation/${events.orgIdObj.id}`}>{events.orgIdObj.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/events/${events.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/events/${events.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/events/${events.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="groupfaceApp.events.home.notFound">No Events found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={eventsList && eventsList.length > 0 ? '' : 'd-none'}>
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

export default Events;
