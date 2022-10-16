import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrganisation } from 'app/shared/model/organisation.model';
import { searchEntities, getEntities } from './organisation.reducer';

export const Organisation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const organisationList = useAppSelector(state => state.organisation.entities);
  const loading = useAppSelector(state => state.organisation.loading);
  const totalItems = useAppSelector(state => state.organisation.totalItems);

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
      <h2 id="organisation-heading" data-cy="OrganisationHeading">
        <Translate contentKey="groupfaceApp.organisation.home.title">Organisations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.organisation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/organisation/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.organisation.home.createLabel">Create new Organisation</Translate>
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
                  placeholder={translate('groupfaceApp.organisation.home.search')}
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
        {organisationList && organisationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.organisation.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="groupfaceApp.organisation.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="groupfaceApp.organisation.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="groupfaceApp.organisation.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parentOrgId')}>
                  <Translate contentKey="groupfaceApp.organisation.parentOrgId">Parent Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('primaryContactId')}>
                  <Translate contentKey="groupfaceApp.organisation.primaryContactId">Primary Contact Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgHead')}>
                  <Translate contentKey="groupfaceApp.organisation.orgHead">Org Head</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('locationId')}>
                  <Translate contentKey="groupfaceApp.organisation.locationId">Location Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('website')}>
                  <Translate contentKey="groupfaceApp.organisation.website">Website</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('layout')}>
                  <Translate contentKey="groupfaceApp.organisation.layout">Layout</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tableKy')}>
                  <Translate contentKey="groupfaceApp.organisation.tableKy">Table Ky</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.organisation.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnable')}>
                  <Translate contentKey="groupfaceApp.organisation.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.organisation.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.organisation.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.organisation.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.organisation.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.organisation.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.organisation.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="groupfaceApp.organisation.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remarks')}>
                  <Translate contentKey="groupfaceApp.organisation.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('extraFields')}>
                  <Translate contentKey="groupfaceApp.organisation.extraFields">Extra Fields</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('buildFilePath')}>
                  <Translate contentKey="groupfaceApp.organisation.buildFilePath">Build File Path</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('shortName')}>
                  <Translate contentKey="groupfaceApp.organisation.shortName">Short Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vatNo')}>
                  <Translate contentKey="groupfaceApp.organisation.vatNo">Vat No</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('moduleKy')}>
                  <Translate contentKey="groupfaceApp.organisation.moduleKy">Module Ky</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hashCode')}>
                  <Translate contentKey="groupfaceApp.organisation.hashCode">Hash Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hashCertificate')}>
                  <Translate contentKey="groupfaceApp.organisation.hashCertificate">Hash Certificate</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {organisationList.map((organisation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/organisation/${organisation.id}`} color="link" size="sm">
                      {organisation.id}
                    </Button>
                  </td>
                  <td>{organisation.code}</td>
                  <td>{organisation.name}</td>
                  <td>{organisation.type}</td>
                  <td>{organisation.parentOrgId}</td>
                  <td>{organisation.primaryContactId}</td>
                  <td>{organisation.orgHead}</td>
                  <td>{organisation.locationId}</td>
                  <td>{organisation.website}</td>
                  <td>{organisation.layout}</td>
                  <td>{organisation.tableKy}</td>
                  <td>{organisation.isActive}</td>
                  <td>{organisation.isEnable}</td>
                  <td>{organisation.addedBy}</td>
                  <td>
                    {organisation.addedOn ? <TextFormat type="date" value={organisation.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{organisation.updatedBy}</td>
                  <td>
                    {organisation.updatedOn ? (
                      <TextFormat type="date" value={organisation.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{organisation.approvedBy}</td>
                  <td>
                    {organisation.approvedOn ? (
                      <TextFormat type="date" value={organisation.approvedOn} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{organisation.comments}</td>
                  <td>{organisation.remarks}</td>
                  <td>{organisation.extraFields}</td>
                  <td>{organisation.buildFilePath}</td>
                  <td>{organisation.shortName}</td>
                  <td>{organisation.vatNo}</td>
                  <td>{organisation.moduleKy}</td>
                  <td>{organisation.hashCode}</td>
                  <td>{organisation.hashCertificate}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/organisation/${organisation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/organisation/${organisation.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/organisation/${organisation.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="groupfaceApp.organisation.home.notFound">No Organisations found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={organisationList && organisationList.length > 0 ? '' : 'd-none'}>
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

export default Organisation;
