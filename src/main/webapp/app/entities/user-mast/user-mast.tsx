import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserMast } from 'app/shared/model/user-mast.model';
import { searchEntities, getEntities } from './user-mast.reducer';

export const UserMast = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const userMastList = useAppSelector(state => state.userMast.entities);
  const loading = useAppSelector(state => state.userMast.loading);
  const totalItems = useAppSelector(state => state.userMast.totalItems);

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
      <h2 id="user-mast-heading" data-cy="UserMastHeading">
        <Translate contentKey="groupfaceApp.userMast.home.title">User Masts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.userMast.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/user-mast/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.userMast.home.createLabel">Create new User Mast</Translate>
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
                  placeholder={translate('groupfaceApp.userMast.home.search')}
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
        {userMastList && userMastList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.userMast.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userId')}>
                  <Translate contentKey="groupfaceApp.userMast.userId">User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userType')}>
                  <Translate contentKey="groupfaceApp.userMast.userType">User Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="groupfaceApp.userMast.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastname')}>
                  <Translate contentKey="groupfaceApp.userMast.lastname">Lastname</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.userMast.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activatedBy')}>
                  <Translate contentKey="groupfaceApp.userMast.activatedBy">Activated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activatedOn')}>
                  <Translate contentKey="groupfaceApp.userMast.activatedOn">Activated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dob')}>
                  <Translate contentKey="groupfaceApp.userMast.dob">Dob</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="groupfaceApp.userMast.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phoneArea')}>
                  <Translate contentKey="groupfaceApp.userMast.phoneArea">Phone Area</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phno')}>
                  <Translate contentKey="groupfaceApp.userMast.phno">Phno</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email1')}>
                  <Translate contentKey="groupfaceApp.userMast.email1">Email 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email2')}>
                  <Translate contentKey="groupfaceApp.userMast.email2">Email 2</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('requestDt')}>
                  <Translate contentKey="groupfaceApp.userMast.requestDt">Request Dt</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('caste')}>
                  <Translate contentKey="groupfaceApp.userMast.caste">Caste</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('subCaste')}>
                  <Translate contentKey="groupfaceApp.userMast.subCaste">Sub Caste</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('validID')}>
                  <Translate contentKey="groupfaceApp.userMast.validID">Valid ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('validIDType')}>
                  <Translate contentKey="groupfaceApp.userMast.validIDType">Valid ID Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('validIDNo')}>
                  <Translate contentKey="groupfaceApp.userMast.validIDNo">Valid ID No</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('validValidTill')}>
                  <Translate contentKey="groupfaceApp.userMast.validValidTill">Valid Valid Till</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('refferedBy')}>
                  <Translate contentKey="groupfaceApp.userMast.refferedBy">Reffered By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('relationwith')}>
                  <Translate contentKey="groupfaceApp.userMast.relationwith">Relationwith</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('relationType')}>
                  <Translate contentKey="groupfaceApp.userMast.relationType">Relation Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('issuingCountry')}>
                  <Translate contentKey="groupfaceApp.userMast.issuingCountry">Issuing Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="groupfaceApp.userMast.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  <Translate contentKey="groupfaceApp.userMast.comment">Comment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherInfo')}>
                  <Translate contentKey="groupfaceApp.userMast.otherInfo">Other Info</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allergicDrug1')}>
                  <Translate contentKey="groupfaceApp.userMast.allergicDrug1">Allergic Drug 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('moduleKy')}>
                  <Translate contentKey="groupfaceApp.userMast.moduleKy">Module Ky</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tableKy')}>
                  <Translate contentKey="groupfaceApp.userMast.tableKy">Table Ky</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('allergicDrug2')}>
                  <Translate contentKey="groupfaceApp.userMast.allergicDrug2">Allergic Drug 2</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="groupfaceApp.userMast.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remarks')}>
                  <Translate contentKey="groupfaceApp.userMast.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('extraFields')}>
                  <Translate contentKey="groupfaceApp.userMast.extraFields">Extra Fields</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('column1')}>
                  <Translate contentKey="groupfaceApp.userMast.column1">Column 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('column2')}>
                  <Translate contentKey="groupfaceApp.userMast.column2">Column 2</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('column3')}>
                  <Translate contentKey="groupfaceApp.userMast.column3">Column 3</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hobbies')}>
                  <Translate contentKey="groupfaceApp.userMast.hobbies">Hobbies</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('otherActivities')}>
                  <Translate contentKey="groupfaceApp.userMast.otherActivities">Other Activities</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('password')}>
                  <Translate contentKey="groupfaceApp.userMast.password">Password</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('role')}>
                  <Translate contentKey="groupfaceApp.userMast.role">Role</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('path')}>
                  <Translate contentKey="groupfaceApp.userMast.path">Path</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('roleType')}>
                  <Translate contentKey="groupfaceApp.userMast.roleType">Role Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.userMast.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.userMast.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.userMast.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.userMast.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.userMast.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.userMast.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('language')}>
                  <Translate contentKey="groupfaceApp.userMast.language">Language</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hist')}>
                  <Translate contentKey="groupfaceApp.userMast.hist">Hist</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('layout')}>
                  <Translate contentKey="groupfaceApp.userMast.layout">Layout</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('securityKeyStatus')}>
                  <Translate contentKey="groupfaceApp.userMast.securityKeyStatus">Security Key Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hashCode')}>
                  <Translate contentKey="groupfaceApp.userMast.hashCode">Hash Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('encryptionStatus')}>
                  <Translate contentKey="groupfaceApp.userMast.encryptionStatus">Encryption Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('encryptedPassword')}>
                  <Translate contentKey="groupfaceApp.userMast.encryptedPassword">Encrypted Password</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zone')}>
                  <Translate contentKey="groupfaceApp.userMast.zone">Zone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgId')}>
                  <Translate contentKey="groupfaceApp.userMast.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('job')}>
                  <Translate contentKey="groupfaceApp.userMast.job">Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('presentAddress')}>
                  <Translate contentKey="groupfaceApp.userMast.presentAddress">Present Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('permanentAddress')}>
                  <Translate contentKey="groupfaceApp.userMast.permanentAddress">Permanent Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workcompany')}>
                  <Translate contentKey="groupfaceApp.userMast.workcompany">Workcompany</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workAddress')}>
                  <Translate contentKey="groupfaceApp.userMast.workAddress">Work Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.userMast.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userMastList.map((userMast, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-mast/${userMast.id}`} color="link" size="sm">
                      {userMast.id}
                    </Button>
                  </td>
                  <td>{userMast.userId}</td>
                  <td>{userMast.userType}</td>
                  <td>{userMast.name}</td>
                  <td>{userMast.lastname}</td>
                  <td>{userMast.isActive}</td>
                  <td>{userMast.activatedBy}</td>
                  <td>
                    {userMast.activatedOn ? <TextFormat type="date" value={userMast.activatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{userMast.dob ? <TextFormat type="date" value={userMast.dob} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{userMast.gender}</td>
                  <td>{userMast.phoneArea}</td>
                  <td>{userMast.phno}</td>
                  <td>{userMast.email1}</td>
                  <td>{userMast.email2}</td>
                  <td>
                    {userMast.requestDt ? <TextFormat type="date" value={userMast.requestDt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{userMast.caste}</td>
                  <td>{userMast.subCaste}</td>
                  <td>{userMast.validID}</td>
                  <td>{userMast.validIDType}</td>
                  <td>{userMast.validIDNo}</td>
                  <td>
                    {userMast.validValidTill ? (
                      <TextFormat type="date" value={userMast.validValidTill} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{userMast.refferedBy}</td>
                  <td>{userMast.relationwith}</td>
                  <td>{userMast.relationType}</td>
                  <td>{userMast.issuingCountry}</td>
                  <td>{userMast.status}</td>
                  <td>{userMast.comment}</td>
                  <td>{userMast.otherInfo}</td>
                  <td>{userMast.allergicDrug1}</td>
                  <td>{userMast.moduleKy}</td>
                  <td>{userMast.tableKy}</td>
                  <td>{userMast.allergicDrug2}</td>
                  <td>{userMast.comments}</td>
                  <td>{userMast.remarks}</td>
                  <td>{userMast.extraFields}</td>
                  <td>{userMast.column1}</td>
                  <td>{userMast.column2}</td>
                  <td>{userMast.column3}</td>
                  <td>{userMast.hobbies}</td>
                  <td>{userMast.otherActivities}</td>
                  <td>{userMast.password}</td>
                  <td>{userMast.role}</td>
                  <td>{userMast.path}</td>
                  <td>{userMast.roleType}</td>
                  <td>{userMast.addedBy}</td>
                  <td>{userMast.addedOn ? <TextFormat type="date" value={userMast.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{userMast.updatedBy}</td>
                  <td>
                    {userMast.updatedOn ? <TextFormat type="date" value={userMast.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{userMast.approvedBy}</td>
                  <td>
                    {userMast.approvedOn ? <TextFormat type="date" value={userMast.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{userMast.language}</td>
                  <td>{userMast.hist}</td>
                  <td>{userMast.layout}</td>
                  <td>{userMast.securityKeyStatus}</td>
                  <td>{userMast.hashCode}</td>
                  <td>{userMast.encryptionStatus}</td>
                  <td>{userMast.encryptedPassword}</td>
                  <td>{userMast.zone}</td>
                  <td>{userMast.orgId}</td>
                  <td>{userMast.job}</td>
                  <td>{userMast.presentAddress}</td>
                  <td>{userMast.permanentAddress}</td>
                  <td>{userMast.workcompany}</td>
                  <td>{userMast.workAddress}</td>
                  <td>{userMast.orgIdObj ? <Link to={`/organisation/${userMast.orgIdObj.id}`}>{userMast.orgIdObj.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/user-mast/${userMast.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-mast/${userMast.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/user-mast/${userMast.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="groupfaceApp.userMast.home.notFound">No User Masts found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={userMastList && userMastList.length > 0 ? '' : 'd-none'}>
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

export default UserMast;
