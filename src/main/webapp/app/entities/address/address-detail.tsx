import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="groupfaceApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.address.id">Id</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.address.name">Name</Translate>
            </span>
          </dt>
          <dd>{addressEntity.name}</dd>
          <dt>
            <span id="addressType">
              <Translate contentKey="groupfaceApp.address.addressType">Address Type</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addressType}</dd>
          <dt>
            <span id="addressLn1">
              <Translate contentKey="groupfaceApp.address.addressLn1">Address Ln 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addressLn1}</dd>
          <dt>
            <span id="addressLn2">
              <Translate contentKey="groupfaceApp.address.addressLn2">Address Ln 2</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addressLn2}</dd>
          <dt>
            <span id="addressLn3">
              <Translate contentKey="groupfaceApp.address.addressLn3">Address Ln 3</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addressLn3}</dd>
          <dt>
            <span id="addressLn4">
              <Translate contentKey="groupfaceApp.address.addressLn4">Address Ln 4</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addressLn4}</dd>
          <dt>
            <span id="landmark">
              <Translate contentKey="groupfaceApp.address.landmark">Landmark</Translate>
            </span>
          </dt>
          <dd>{addressEntity.landmark}</dd>
          <dt>
            <span id="village">
              <Translate contentKey="groupfaceApp.address.village">Village</Translate>
            </span>
          </dt>
          <dd>{addressEntity.village}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="groupfaceApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="district">
              <Translate contentKey="groupfaceApp.address.district">District</Translate>
            </span>
          </dt>
          <dd>{addressEntity.district}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="groupfaceApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="parentTableId">
              <Translate contentKey="groupfaceApp.address.parentTableId">Parent Table Id</Translate>
            </span>
          </dt>
          <dd>{addressEntity.parentTableId}</dd>
          <dt>
            <span id="parentModuleKy">
              <Translate contentKey="groupfaceApp.address.parentModuleKy">Parent Module Ky</Translate>
            </span>
          </dt>
          <dd>{addressEntity.parentModuleKy}</dd>
          <dt>
            <span id="parentTableKy">
              <Translate contentKey="groupfaceApp.address.parentTableKy">Parent Table Ky</Translate>
            </span>
          </dt>
          <dd>{addressEntity.parentTableKy}</dd>
          <dt>
            <span id="pin">
              <Translate contentKey="groupfaceApp.address.pin">Pin</Translate>
            </span>
          </dt>
          <dd>{addressEntity.pin}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="groupfaceApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.address.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{addressEntity.isActive}</dd>
          <dt>
            <span id="isEnable">
              <Translate contentKey="groupfaceApp.address.isEnable">Is Enable</Translate>
            </span>
          </dt>
          <dd>{addressEntity.isEnable}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.address.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.address.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addedOn ? <TextFormat value={addressEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.address.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.address.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.updatedOn ? <TextFormat value={addressEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.address.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.address.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.approvedOn ? <TextFormat value={addressEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.address.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{addressEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.address.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{addressEntity.remarks}</dd>
          <dt>
            <span id="extraFields">
              <Translate contentKey="groupfaceApp.address.extraFields">Extra Fields</Translate>
            </span>
          </dt>
          <dd>{addressEntity.extraFields}</dd>
          <dt>
            <span id="zone">
              <Translate contentKey="groupfaceApp.address.zone">Zone</Translate>
            </span>
          </dt>
          <dd>{addressEntity.zone}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.address.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{addressEntity.orgId}</dd>
          <dt>
            <span id="hist">
              <Translate contentKey="groupfaceApp.address.hist">Hist</Translate>
            </span>
          </dt>
          <dd>{addressEntity.hist}</dd>
          <dt>
            <span id="column1">
              <Translate contentKey="groupfaceApp.address.column1">Column 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.column1}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{addressEntity.orgIdObj ? addressEntity.orgIdObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.approvedByObj">Approved By Obj</Translate>
          </dt>
          <dd>{addressEntity.approvedByObj ? addressEntity.approvedByObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.addedByUser">Added By User</Translate>
          </dt>
          <dd>{addressEntity.addedByUser ? addressEntity.addedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.updatedByUser">Updated By User</Translate>
          </dt>
          <dd>{addressEntity.updatedByUser ? addressEntity.updatedByUser.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.villageObj">Village Obj</Translate>
          </dt>
          <dd>{addressEntity.villageObj ? addressEntity.villageObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.cityObj">City Obj</Translate>
          </dt>
          <dd>{addressEntity.cityObj ? addressEntity.cityObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.districtObj">District Obj</Translate>
          </dt>
          <dd>{addressEntity.districtObj ? addressEntity.districtObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.stateObj">State Obj</Translate>
          </dt>
          <dd>{addressEntity.stateObj ? addressEntity.stateObj.name : ''}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.address.countryObj">Country Obj</Translate>
          </dt>
          <dd>{addressEntity.countryObj ? addressEntity.countryObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
