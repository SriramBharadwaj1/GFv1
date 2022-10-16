import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, InputGroup, FormGroup, Form, Row, Col, Table } from 'reactstrap';
import { Translate, translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRating } from 'app/shared/model/rating.model';
import { searchEntities, getEntities } from './rating.reducer';

export const Rating = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const ratingList = useAppSelector(state => state.rating.entities);
  const loading = useAppSelector(state => state.rating.loading);
  const totalItems = useAppSelector(state => state.rating.totalItems);

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
      <h2 id="rating-heading" data-cy="RatingHeading">
        <Translate contentKey="groupfaceApp.rating.home.title">Ratings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupfaceApp.rating.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/rating/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupfaceApp.rating.home.createLabel">Create new Rating</Translate>
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
                  placeholder={translate('groupfaceApp.rating.home.search')}
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
        {ratingList && ratingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="groupfaceApp.rating.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rating')}>
                  <Translate contentKey="groupfaceApp.rating.rating">Rating</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('postId')}>
                  <Translate contentKey="groupfaceApp.rating.postId">Post Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="groupfaceApp.rating.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('apprRejReason')}>
                  <Translate contentKey="groupfaceApp.rating.apprRejReason">Appr Rej Reason</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isEnable')}>
                  <Translate contentKey="groupfaceApp.rating.isEnable">Is Enable</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedBy')}>
                  <Translate contentKey="groupfaceApp.rating.addedBy">Added By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('addedOn')}>
                  <Translate contentKey="groupfaceApp.rating.addedOn">Added On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedBy')}>
                  <Translate contentKey="groupfaceApp.rating.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedOn')}>
                  <Translate contentKey="groupfaceApp.rating.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedBy')}>
                  <Translate contentKey="groupfaceApp.rating.approvedBy">Approved By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orgId')}>
                  <Translate contentKey="groupfaceApp.rating.orgId">Org Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('approvedOn')}>
                  <Translate contentKey="groupfaceApp.rating.approvedOn">Approved On</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.rating.rapostIdObj">Rapost Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.rating.addedByUser">Added By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.rating.updatedByUser">Updated By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.rating.approvedByUser">Approved By User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="groupfaceApp.rating.orgIdObj">Org Id Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ratingList.map((rating, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/rating/${rating.id}`} color="link" size="sm">
                      {rating.id}
                    </Button>
                  </td>
                  <td>{rating.rating}</td>
                  <td>{rating.postId}</td>
                  <td>{rating.isActive}</td>
                  <td>{rating.apprRejReason}</td>
                  <td>{rating.isEnable}</td>
                  <td>{rating.addedBy}</td>
                  <td>{rating.addedOn ? <TextFormat type="date" value={rating.addedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{rating.updatedBy}</td>
                  <td>{rating.updatedOn ? <TextFormat type="date" value={rating.updatedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{rating.approvedBy}</td>
                  <td>{rating.orgId}</td>
                  <td>{rating.approvedOn ? <TextFormat type="date" value={rating.approvedOn} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{rating.rapostIdObj ? <Link to={`/post/${rating.rapostIdObj.id}`}>{rating.rapostIdObj.message}</Link> : ''}</td>
                  <td>{rating.addedByUser ? <Link to={`/user-mast/${rating.addedByUser.id}`}>{rating.addedByUser.name}</Link> : ''}</td>
                  <td>
                    {rating.updatedByUser ? <Link to={`/user-mast/${rating.updatedByUser.id}`}>{rating.updatedByUser.name}</Link> : ''}
                  </td>
                  <td>
                    {rating.approvedByUser ? <Link to={`/user-mast/${rating.approvedByUser.id}`}>{rating.approvedByUser.name}</Link> : ''}
                  </td>
                  <td>{rating.orgIdObj ? <Link to={`/organisation/${rating.orgIdObj.id}`}>{rating.orgIdObj.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/rating/${rating.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rating/${rating.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/rating/${rating.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="groupfaceApp.rating.home.notFound">No Ratings found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={ratingList && ratingList.length > 0 ? '' : 'd-none'}>
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

export default Rating;
