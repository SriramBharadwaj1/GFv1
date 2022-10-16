package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.UserMast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserMastDTO implements Serializable {

    private Long id;

    private String userId;

    private Integer userType;

    private String name;

    private String lastname;

    private String isActive;

    private String activatedBy;

    private LocalDate activatedOn;

    private LocalDate dob;

    private String gender;

    private String phoneArea;

    private String phno;

    private String email1;

    private String email2;

    private LocalDate requestDt;

    private Long caste;

    private Long subCaste;

    private String validID;

    private Integer validIDType;

    private String validIDNo;

    private LocalDate validValidTill;

    private Integer refferedBy;

    private Integer relationwith;

    private Integer relationType;

    private Integer issuingCountry;

    private Integer status;

    private String comment;

    private String otherInfo;

    private String allergicDrug1;

    private Integer moduleKy;

    private Integer tableKy;

    private String allergicDrug2;

    private String comments;

    private String remarks;

    private String extraFields;

    private String column1;

    private Integer column2;

    private String column3;

    private String hobbies;

    private String otherActivities;

    private String password;

    private String role;

    private Integer path;

    private String roleType;

    private Long addedBy;

    private LocalDate addedOn;

    private Integer updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private LocalDate approvedOn;

    private String language;

    private String hist;

    private String layout;

    private String securityKeyStatus;

    private String hashCode;

    private Integer encryptionStatus;

    private String encryptedPassword;

    private String zone;

    private Long orgId;

    private String job;

    private Long presentAddress;

    private Long permanentAddress;

    private String workcompany;

    private Long workAddress;

    private OrganisationDTO orgIdObj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getActivatedBy() {
        return activatedBy;
    }

    public void setActivatedBy(String activatedBy) {
        this.activatedBy = activatedBy;
    }

    public LocalDate getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(LocalDate activatedOn) {
        this.activatedOn = activatedOn;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public LocalDate getRequestDt() {
        return requestDt;
    }

    public void setRequestDt(LocalDate requestDt) {
        this.requestDt = requestDt;
    }

    public Long getCaste() {
        return caste;
    }

    public void setCaste(Long caste) {
        this.caste = caste;
    }

    public Long getSubCaste() {
        return subCaste;
    }

    public void setSubCaste(Long subCaste) {
        this.subCaste = subCaste;
    }

    public String getValidID() {
        return validID;
    }

    public void setValidID(String validID) {
        this.validID = validID;
    }

    public Integer getValidIDType() {
        return validIDType;
    }

    public void setValidIDType(Integer validIDType) {
        this.validIDType = validIDType;
    }

    public String getValidIDNo() {
        return validIDNo;
    }

    public void setValidIDNo(String validIDNo) {
        this.validIDNo = validIDNo;
    }

    public LocalDate getValidValidTill() {
        return validValidTill;
    }

    public void setValidValidTill(LocalDate validValidTill) {
        this.validValidTill = validValidTill;
    }

    public Integer getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(Integer refferedBy) {
        this.refferedBy = refferedBy;
    }

    public Integer getRelationwith() {
        return relationwith;
    }

    public void setRelationwith(Integer relationwith) {
        this.relationwith = relationwith;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public Integer getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(Integer issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getAllergicDrug1() {
        return allergicDrug1;
    }

    public void setAllergicDrug1(String allergicDrug1) {
        this.allergicDrug1 = allergicDrug1;
    }

    public Integer getModuleKy() {
        return moduleKy;
    }

    public void setModuleKy(Integer moduleKy) {
        this.moduleKy = moduleKy;
    }

    public Integer getTableKy() {
        return tableKy;
    }

    public void setTableKy(Integer tableKy) {
        this.tableKy = tableKy;
    }

    public String getAllergicDrug2() {
        return allergicDrug2;
    }

    public void setAllergicDrug2(String allergicDrug2) {
        this.allergicDrug2 = allergicDrug2;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(String extraFields) {
        this.extraFields = extraFields;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public Integer getColumn2() {
        return column2;
    }

    public void setColumn2(Integer column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getOtherActivities() {
        return otherActivities;
    }

    public void setOtherActivities(String otherActivities) {
        this.otherActivities = otherActivities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getPath() {
        return path;
    }

    public void setPath(Integer path) {
        this.path = path;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHist() {
        return hist;
    }

    public void setHist(String hist) {
        this.hist = hist;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getSecurityKeyStatus() {
        return securityKeyStatus;
    }

    public void setSecurityKeyStatus(String securityKeyStatus) {
        this.securityKeyStatus = securityKeyStatus;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Integer getEncryptionStatus() {
        return encryptionStatus;
    }

    public void setEncryptionStatus(Integer encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(Long presentAddress) {
        this.presentAddress = presentAddress;
    }

    public Long getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(Long permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getWorkcompany() {
        return workcompany;
    }

    public void setWorkcompany(String workcompany) {
        this.workcompany = workcompany;
    }

    public Long getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Long workAddress) {
        this.workAddress = workAddress;
    }

    public OrganisationDTO getOrgIdObj() {
        return orgIdObj;
    }

    public void setOrgIdObj(OrganisationDTO orgIdObj) {
        this.orgIdObj = orgIdObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserMastDTO)) {
            return false;
        }

        UserMastDTO userMastDTO = (UserMastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userMastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserMastDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userType=" + getUserType() +
            ", name='" + getName() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", activatedBy='" + getActivatedBy() + "'" +
            ", activatedOn='" + getActivatedOn() + "'" +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", phoneArea='" + getPhoneArea() + "'" +
            ", phno='" + getPhno() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", requestDt='" + getRequestDt() + "'" +
            ", caste=" + getCaste() +
            ", subCaste=" + getSubCaste() +
            ", validID='" + getValidID() + "'" +
            ", validIDType=" + getValidIDType() +
            ", validIDNo='" + getValidIDNo() + "'" +
            ", validValidTill='" + getValidValidTill() + "'" +
            ", refferedBy=" + getRefferedBy() +
            ", relationwith=" + getRelationwith() +
            ", relationType=" + getRelationType() +
            ", issuingCountry=" + getIssuingCountry() +
            ", status=" + getStatus() +
            ", comment='" + getComment() + "'" +
            ", otherInfo='" + getOtherInfo() + "'" +
            ", allergicDrug1='" + getAllergicDrug1() + "'" +
            ", moduleKy=" + getModuleKy() +
            ", tableKy=" + getTableKy() +
            ", allergicDrug2='" + getAllergicDrug2() + "'" +
            ", comments='" + getComments() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", extraFields='" + getExtraFields() + "'" +
            ", column1='" + getColumn1() + "'" +
            ", column2=" + getColumn2() +
            ", column3='" + getColumn3() + "'" +
            ", hobbies='" + getHobbies() + "'" +
            ", otherActivities='" + getOtherActivities() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            ", path=" + getPath() +
            ", roleType='" + getRoleType() + "'" +
            ", addedBy=" + getAddedBy() +
            ", addedOn='" + getAddedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvedBy=" + getApprovedBy() +
            ", approvedOn='" + getApprovedOn() + "'" +
            ", language='" + getLanguage() + "'" +
            ", hist='" + getHist() + "'" +
            ", layout='" + getLayout() + "'" +
            ", securityKeyStatus='" + getSecurityKeyStatus() + "'" +
            ", hashCode='" + getHashCode() + "'" +
            ", encryptionStatus=" + getEncryptionStatus() +
            ", encryptedPassword='" + getEncryptedPassword() + "'" +
            ", zone='" + getZone() + "'" +
            ", orgId=" + getOrgId() +
            ", job='" + getJob() + "'" +
            ", presentAddress=" + getPresentAddress() +
            ", permanentAddress=" + getPermanentAddress() +
            ", workcompany='" + getWorkcompany() + "'" +
            ", workAddress=" + getWorkAddress() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
