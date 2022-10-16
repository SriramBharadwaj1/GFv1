import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAddress } from 'app/shared/model/address.model';
import { searchEntities, getEntities } from './address.reducer';

export const Address = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const addressList = useAppSelector(state => state.address.entities);
  const loading = useAppSelector(state => state.address.loading);
  const totalItems = useAppSelector(state => state.address.totalItems);

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
      <h2 id="address-heading" data-cy="AddressHeading">
        <Translate contentKey="groupfaceApp.address.home.title">Addresses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.address.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/address/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.address.home.createLabel">Create new Address</Translate>
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
                  placeholder={translate('groupfaceApp.address.home.search')}
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
        {addressList && addressList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.address.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="groupfaceApp.address.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addressType')}>
                  <Translate contentKey="groupfaceApp.address.addressType">Address Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addressLn1')}>
                  <Translate contentKey="groupfaceApp.address.addressLn1">Address Ln 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addressLn2')}>
                  <Translate contentKey="groupfaceApp.address.addressLn2">Address Ln 2</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addressLn3')}>
                  <Translate contentKey="groupfaceApp.address.addressLn3">Address Ln 3</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addressLn4')}>
                  <Translate contentKey="groupfaceApp.address.addressLn4">Address Ln 4</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('landmark')}>
                  <Translate contentKey="groupfaceApp.address.landmark">Landmark</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('village')}>
                  <Translate contentKey="groupfaceApp.address.village">Village</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="groupfaceApp.address.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('district')}>
                  <Translate contentKey="groupfaceApp.address.district">District</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="groupfaceApp.address.state">State</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parentTableId')}>
                  <Translate contentKey="groupfaceApp.address.parentTableId">Parent Table Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parentModuleKy')}>
                  <Translate contentKey="groupfaceApp.address.parentModuleKy">Parent Module Ky</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('parentTableKy')}>
                  <Translate contentKey="groupfaceApp.address.parentTableKy">Parent Table Ky</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pin')}>
                  <Translate contentKey="groupfaceApp.address.pin">Pin</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="groupfaceApp.address.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.address.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnable')}>
                  <Translate contentKey="groupfaceApp.address.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.address.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.address.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.address.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.address.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.address.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.address.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="groupfaceApp.address.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remarks')}>
                  <Translate contentKey="groupfaceApp.address.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('extraFields')}>
                  <Translate contentKey="groupfaceApp.address.extraFields">Extra Fields</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zone')}>
                  <Translate contentKey="groupfaceApp.address.zone">Zone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgId')}>
                  <Translate contentKey="groupfaceApp.address.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hist')}>
                  <Translate contentKey="groupfaceApp.address.hist">Hist</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('column1')}>
                  <Translate contentKey="groupfaceApp.address.column1">Column 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.approvedByObj">Approved By Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.addedByUser">Added By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.updatedByUser">Updated By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.villageObj">Village Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.cityObj">City Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.districtObj">District Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.stateObj">State Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.address.countryObj">Country Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {addressList.map((address, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/address/${address.id}`} color="link" size="sm">
                      {address.id}
                    </Button>
                  </td>
                  <td>{address.name}</td>
                  <td>{address.addressType}</td>
                  <td>{address.addressLn1}</td>
                  <td>{address.addressLn2}</td>
                  <td>{address.addressLn3}</td>
                  <td>{address.addressLn4}</td>
                  <td>{address.landmark}</td>
                  <td>{address.village}</td>
                  <td>{address.city}</td>
                  <td>{address.district}</td>
                  <td>{address.state}</td>
                  <td>{address.parentTableId}</td>
                  <td>{address.parentModuleKy}</td>
                  <td>{address.parentTableKy}</td>
                  <td>{address.pin}</td>
                  <td>{address.country}</td>
                  <td>{address.isActive}</td>
                  <td>{address.isEnable}</td>
                  <td>{address.addedBy}</td>
                  <td>{address.addedOn ? <TextFormat type="date" value={address.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{address.updatedBy}</td>
                  <td>{address.updatedOn ? <TextFormat type="date" value={address.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{address.approvedBy}</td>
                  <td>
                    {address.approvedOn ? <TextFormat type="date" value={address.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{address.comments}</td>
                  <td>{address.remarks}</td>
                  <td>{address.extraFields}</td>
                  <td>{address.zone}</td>
                  <td>{address.orgId}</td>
                  <td>{address.hist}</td>
                  <td>{address.column1}</td>
                  <td>{address.orgIdObj ? <Link to={`/organisation/${address.orgIdObj.id}`}>{address.orgIdObj.name}</Link> : ''}</td>
                  <td>
                    {address.approvedByObj ? <Link to={`/user-mast/${address.approvedByObj.id}`}>{address.approvedByObj.name}</Link> : ''}
                  </td>
                  <td>{address.addedByUser ? <Link to={`/user-mast/${address.addedByUser.id}`}>{address.addedByUser.name}</Link> : ''}</td>
                  <td>
                    {address.updatedByUser ? <Link to={`/user-mast/${address.updatedByUser.id}`}>{address.updatedByUser.name}</Link> : ''}
                  </td>
                  <td>{address.villageObj ? <Link to={`/location/${address.villageObj.id}`}>{address.villageObj.name}</Link> : ''}</td>
                  <td>{address.cityObj ? <Link to={`/location/${address.cityObj.id}`}>{address.cityObj.name}</Link> : ''}</td>
                  <td>{address.districtObj ? <Link to={`/location/${address.districtObj.id}`}>{address.districtObj.name}</Link> : ''}</td>
                  <td>{address.stateObj ? <Link to={`/location/${address.stateObj.id}`}>{address.stateObj.name}</Link> : ''}</td>
                  <td>{address.countryObj ? <Link to={`/location/${address.countryObj.id}`}>{address.countryObj.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/address/${address.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/address/${address.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/address/${address.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="groupfaceApp.address.home.notFound">No Addresses found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={addressList && addressList.length > 0 ? '' : 'd-none'}>
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

export default Address;
