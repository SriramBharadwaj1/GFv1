import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-mast.reducer';

export const UserMastDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userMastEntity = useAppSelector(state => state.userMast.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userMastDetailsHeading">
          <Translate contentKey="groupfaceApp.userMast.detail.title">UserMast</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="groupfaceApp.userMast.id">Id</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="groupfaceApp.userMast.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.userId}</dd>
          <dt>
            <span id="userType">
              <Translate contentKey="groupfaceApp.userMast.userType">User Type</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.userType}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="groupfaceApp.userMast.name">Name</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.name}</dd>
          <dt>
            <span id="lastname">
              <Translate contentKey="groupfaceApp.userMast.lastname">Lastname</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.lastname}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="groupfaceApp.userMast.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.isActive}</dd>
          <dt>
            <span id="activatedBy">
              <Translate contentKey="groupfaceApp.userMast.activatedBy">Activated By</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.activatedBy}</dd>
          <dt>
            <span id="activatedOn">
              <Translate contentKey="groupfaceApp.userMast.activatedOn">Activated On</Translate>
            </span>
          </dt>
          <dd>
            {userMastEntity.activatedOn ? (
              <TextFormat value={userMastEntity.activatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dob">
              <Translate contentKey="groupfaceApp.userMast.dob">Dob</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.dob ? <TextFormat value={userMastEntity.dob} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="groupfaceApp.userMast.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.gender}</dd>
          <dt>
            <span id="phoneArea">
              <Translate contentKey="groupfaceApp.userMast.phoneArea">Phone Area</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.phoneArea}</dd>
          <dt>
            <span id="phno">
              <Translate contentKey="groupfaceApp.userMast.phno">Phno</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.phno}</dd>
          <dt>
            <span id="email1">
              <Translate contentKey="groupfaceApp.userMast.email1">Email 1</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.email1}</dd>
          <dt>
            <span id="email2">
              <Translate contentKey="groupfaceApp.userMast.email2">Email 2</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.email2}</dd>
          <dt>
            <span id="requestDt">
              <Translate contentKey="groupfaceApp.userMast.requestDt">Request Dt</Translate>
            </span>
          </dt>
          <dd>
            {userMastEntity.requestDt ? <TextFormat value={userMastEntity.requestDt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="caste">
              <Translate contentKey="groupfaceApp.userMast.caste">Caste</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.caste}</dd>
          <dt>
            <span id="subCaste">
              <Translate contentKey="groupfaceApp.userMast.subCaste">Sub Caste</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.subCaste}</dd>
          <dt>
            <span id="validID">
              <Translate contentKey="groupfaceApp.userMast.validID">Valid ID</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.validID}</dd>
          <dt>
            <span id="validIDType">
              <Translate contentKey="groupfaceApp.userMast.validIDType">Valid ID Type</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.validIDType}</dd>
          <dt>
            <span id="validIDNo">
              <Translate contentKey="groupfaceApp.userMast.validIDNo">Valid ID No</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.validIDNo}</dd>
          <dt>
            <span id="validValidTill">
              <Translate contentKey="groupfaceApp.userMast.validValidTill">Valid Valid Till</Translate>
            </span>
          </dt>
          <dd>
            {userMastEntity.validValidTill ? (
              <TextFormat value={userMastEntity.validValidTill} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="refferedBy">
              <Translate contentKey="groupfaceApp.userMast.refferedBy">Reffered By</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.refferedBy}</dd>
          <dt>
            <span id="relationwith">
              <Translate contentKey="groupfaceApp.userMast.relationwith">Relationwith</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.relationwith}</dd>
          <dt>
            <span id="relationType">
              <Translate contentKey="groupfaceApp.userMast.relationType">Relation Type</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.relationType}</dd>
          <dt>
            <span id="issuingCountry">
              <Translate contentKey="groupfaceApp.userMast.issuingCountry">Issuing Country</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.issuingCountry}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="groupfaceApp.userMast.status">Status</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.status}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="groupfaceApp.userMast.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.comment}</dd>
          <dt>
            <span id="otherInfo">
              <Translate contentKey="groupfaceApp.userMast.otherInfo">Other Info</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.otherInfo}</dd>
          <dt>
            <span id="allergicDrug1">
              <Translate contentKey="groupfaceApp.userMast.allergicDrug1">Allergic Drug 1</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.allergicDrug1}</dd>
          <dt>
            <span id="moduleKy">
              <Translate contentKey="groupfaceApp.userMast.moduleKy">Module Ky</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.moduleKy}</dd>
          <dt>
            <span id="tableKy">
              <Translate contentKey="groupfaceApp.userMast.tableKy">Table Ky</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.tableKy}</dd>
          <dt>
            <span id="allergicDrug2">
              <Translate contentKey="groupfaceApp.userMast.allergicDrug2">Allergic Drug 2</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.allergicDrug2}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="groupfaceApp.userMast.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.comments}</dd>
          <dt>
            <span id="remarks">
              <Translate contentKey="groupfaceApp.userMast.remarks">Remarks</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.remarks}</dd>
          <dt>
            <span id="extraFields">
              <Translate contentKey="groupfaceApp.userMast.extraFields">Extra Fields</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.extraFields}</dd>
          <dt>
            <span id="column1">
              <Translate contentKey="groupfaceApp.userMast.column1">Column 1</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.column1}</dd>
          <dt>
            <span id="column2">
              <Translate contentKey="groupfaceApp.userMast.column2">Column 2</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.column2}</dd>
          <dt>
            <span id="column3">
              <Translate contentKey="groupfaceApp.userMast.column3">Column 3</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.column3}</dd>
          <dt>
            <span id="hobbies">
              <Translate contentKey="groupfaceApp.userMast.hobbies">Hobbies</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.hobbies}</dd>
          <dt>
            <span id="otherActivities">
              <Translate contentKey="groupfaceApp.userMast.otherActivities">Other Activities</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.otherActivities}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="groupfaceApp.userMast.password">Password</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.password}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="groupfaceApp.userMast.role">Role</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.role}</dd>
          <dt>
            <span id="path">
              <Translate contentKey="groupfaceApp.userMast.path">Path</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.path}</dd>
          <dt>
            <span id="roleType">
              <Translate contentKey="groupfaceApp.userMast.roleType">Role Type</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.roleType}</dd>
          <dt>
            <span id="addedBy">
              <Translate contentKey="groupfaceApp.userMast.addedBy">Added By</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.addedBy}</dd>
          <dt>
            <span id="addedOn">
              <Translate contentKey="groupfaceApp.userMast.addedOn">Added On</Translate>
            </span>
          </dt>
          <dd>
            {userMastEntity.addedOn ? <TextFormat value={userMastEntity.addedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="groupfaceApp.userMast.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="groupfaceApp.userMast.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {userMastEntity.updatedOn ? <TextFormat value={userMastEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="approvedBy">
              <Translate contentKey="groupfaceApp.userMast.approvedBy">Approved By</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.approvedBy}</dd>
          <dt>
            <span id="approvedOn">
              <Translate contentKey="groupfaceApp.userMast.approvedOn">Approved On</Translate>
            </span>
          </dt>
          <dd>
            {userMastEntity.approvedOn ? <TextFormat value={userMastEntity.approvedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="language">
              <Translate contentKey="groupfaceApp.userMast.language">Language</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.language}</dd>
          <dt>
            <span id="hist">
              <Translate contentKey="groupfaceApp.userMast.hist">Hist</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.hist}</dd>
          <dt>
            <span id="layout">
              <Translate contentKey="groupfaceApp.userMast.layout">Layout</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.layout}</dd>
          <dt>
            <span id="securityKeyStatus">
              <Translate contentKey="groupfaceApp.userMast.securityKeyStatus">Security Key Status</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.securityKeyStatus}</dd>
          <dt>
            <span id="hashCode">
              <Translate contentKey="groupfaceApp.userMast.hashCode">Hash Code</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.hashCode}</dd>
          <dt>
            <span id="encryptionStatus">
              <Translate contentKey="groupfaceApp.userMast.encryptionStatus">Encryption Status</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.encryptionStatus}</dd>
          <dt>
            <span id="encryptedPassword">
              <Translate contentKey="groupfaceApp.userMast.encryptedPassword">Encrypted Password</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.encryptedPassword}</dd>
          <dt>
            <span id="zone">
              <Translate contentKey="groupfaceApp.userMast.zone">Zone</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.zone}</dd>
          <dt>
            <span id="orgId">
              <Translate contentKey="groupfaceApp.userMast.orgId">Org Id</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.orgId}</dd>
          <dt>
            <span id="job">
              <Translate contentKey="groupfaceApp.userMast.job">Job</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.job}</dd>
          <dt>
            <span id="presentAddress">
              <Translate contentKey="groupfaceApp.userMast.presentAddress">Present Address</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.presentAddress}</dd>
          <dt>
            <span id="permanentAddress">
              <Translate contentKey="groupfaceApp.userMast.permanentAddress">Permanent Address</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.permanentAddress}</dd>
          <dt>
            <span id="workcompany">
              <Translate contentKey="groupfaceApp.userMast.workcompany">Workcompany</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.workcompany}</dd>
          <dt>
            <span id="workAddress">
              <Translate contentKey="groupfaceApp.userMast.workAddress">Work Address</Translate>
            </span>
          </dt>
          <dd>{userMastEntity.workAddress}</dd>
          <dt>
            <Translate contentKey="groupfaceApp.userMast.orgIdObj">Org Id Obj</Translate>
          </dt>
          <dd>{userMastEntity.orgIdObj ? userMastEntity.orgIdObj.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-mast" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-mast/${userMastEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserMastDetail;
