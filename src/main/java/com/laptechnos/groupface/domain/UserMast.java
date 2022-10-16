package com.laptechnos.groupface.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserMast.
 */
@Entity
@Table(name = "usermaster")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "usermast")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserMast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "is_active")
    private String isActive;

    @Column(name = "activated_by")
    private String activatedBy;

    @Column(name = "activated_on")
    private LocalDate activatedOn;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone_area")
    private String phoneArea;

    @Column(name = "phno")
    private String phno;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "request_dt")
    private LocalDate requestDt;

    @Column(name = "caste")
    private Long caste;

    @Column(name = "sub_caste")
    private Long subCaste;

    @Column(name = "valid_id")
    private String validID;

    @Column(name = "valid_id_type")
    private Integer validIDType;

    @Column(name = "valid_id_no")
    private String validIDNo;

    @Column(name = "valid_valid_till")
    private LocalDate validValidTill;

    @Column(name = "reffered_by")
    private Integer refferedBy;

    @Column(name = "relationwith")
    private Integer relationwith;

    @Column(name = "relation_type")
    private Integer relationType;

    @Column(name = "issuing_country")
    private Integer issuingCountry;

    @Column(name = "status")
    private Integer status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "other_info")
    private String otherInfo;

    @Column(name = "allergic_drug_1")
    private String allergicDrug1;

    @Column(name = "module_ky")
    private Integer moduleKy;

    @Column(name = "table_ky")
    private Integer tableKy;

    @Column(name = "allergic_drug_2")
    private String allergicDrug2;

    @Column(name = "comments")
    private String comments;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "extra_fields")
    private String extraFields;

    @Column(name = "column_1")
    private String column1;

    @Column(name = "column_2")
    private Integer column2;

    @Column(name = "column_3")
    private String column3;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "other_activities")
    private String otherActivities;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "path")
    private Integer path;

    @Column(name = "role_type")
    private String roleType;

    @Column(name = "added_by")
    private Long addedBy;

    @Column(name = "added_on")
    private LocalDate addedOn;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approved_on")
    private LocalDate approvedOn;

    @Column(name = "language")
    private String language;

    @Column(name = "hist")
    private String hist;

    @Column(name = "layout")
    private String layout;

    @Column(name = "security_key_status")
    private String securityKeyStatus;

    @Column(name = "hash_code")
    private String hashCode;

    @Column(name = "encryption_status")
    private Integer encryptionStatus;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Column(name = "zone")
    private String zone;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "job")
    private String job;

    @Column(name = "present_address")
    private Long presentAddress;

    @Column(name = "permanent_address")
    private Long permanentAddress;

    @Column(name = "workcompany")
    private String workcompany;

    @Column(name = "work_address")
    private Long workAddress;

    @ManyToOne
    private Organisation orgIdObj;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserMast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public UserMast userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public UserMast userType(Integer userType) {
        this.setUserType(userType);
        return this;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getName() {
        return this.name;
    }

    public UserMast name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return this.lastname;
    }

    public UserMast lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIsActive() {
        return this.isActive;
    }

    public UserMast isActive(String isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getActivatedBy() {
        return this.activatedBy;
    }

    public UserMast activatedBy(String activatedBy) {
        this.setActivatedBy(activatedBy);
        return this;
    }

    public void setActivatedBy(String activatedBy) {
        this.activatedBy = activatedBy;
    }

    public LocalDate getActivatedOn() {
        return this.activatedOn;
    }

    public UserMast activatedOn(LocalDate activatedOn) {
        this.setActivatedOn(activatedOn);
        return this;
    }

    public void setActivatedOn(LocalDate activatedOn) {
        this.activatedOn = activatedOn;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public UserMast dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return this.gender;
    }

    public UserMast gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneArea() {
        return this.phoneArea;
    }

    public UserMast phoneArea(String phoneArea) {
        this.setPhoneArea(phoneArea);
        return this;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getPhno() {
        return this.phno;
    }

    public UserMast phno(String phno) {
        this.setPhno(phno);
        return this;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getEmail1() {
        return this.email1;
    }

    public UserMast email1(String email1) {
        this.setEmail1(email1);
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return this.email2;
    }

    public UserMast email2(String email2) {
        this.setEmail2(email2);
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public LocalDate getRequestDt() {
        return this.requestDt;
    }

    public UserMast requestDt(LocalDate requestDt) {
        this.setRequestDt(requestDt);
        return this;
    }

    public void setRequestDt(LocalDate requestDt) {
        this.requestDt = requestDt;
    }

    public Long getCaste() {
        return this.caste;
    }

    public UserMast caste(Long caste) {
        this.setCaste(caste);
        return this;
    }

    public void setCaste(Long caste) {
        this.caste = caste;
    }

    public Long getSubCaste() {
        return this.subCaste;
    }

    public UserMast subCaste(Long subCaste) {
        this.setSubCaste(subCaste);
        return this;
    }

    public void setSubCaste(Long subCaste) {
        this.subCaste = subCaste;
    }

    public String getValidID() {
        return this.validID;
    }

    public UserMast validID(String validID) {
        this.setValidID(validID);
        return this;
    }

    public void setValidID(String validID) {
        this.validID = validID;
    }

    public Integer getValidIDType() {
        return this.validIDType;
    }

    public UserMast validIDType(Integer validIDType) {
        this.setValidIDType(validIDType);
        return this;
    }

    public void setValidIDType(Integer validIDType) {
        this.validIDType = validIDType;
    }

    public String getValidIDNo() {
        return this.validIDNo;
    }

    public UserMast validIDNo(String validIDNo) {
        this.setValidIDNo(validIDNo);
        return this;
    }

    public void setValidIDNo(String validIDNo) {
        this.validIDNo = validIDNo;
    }

    public LocalDate getValidValidTill() {
        return this.validValidTill;
    }

    public UserMast validValidTill(LocalDate validValidTill) {
        this.setValidValidTill(validValidTill);
        return this;
    }

    public void setValidValidTill(LocalDate validValidTill) {
        this.validValidTill = validValidTill;
    }

    public Integer getRefferedBy() {
        return this.refferedBy;
    }

    public UserMast refferedBy(Integer refferedBy) {
        this.setRefferedBy(refferedBy);
        return this;
    }

    public void setRefferedBy(Integer refferedBy) {
        this.refferedBy = refferedBy;
    }

    public Integer getRelationwith() {
        return this.relationwith;
    }

    public UserMast relationwith(Integer relationwith) {
        this.setRelationwith(relationwith);
        return this;
    }

    public void setRelationwith(Integer relationwith) {
        this.relationwith = relationwith;
    }

    public Integer getRelationType() {
        return this.relationType;
    }

    public UserMast relationType(Integer relationType) {
        this.setRelationType(relationType);
        return this;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public Integer getIssuingCountry() {
        return this.issuingCountry;
    }

    public UserMast issuingCountry(Integer issuingCountry) {
        this.setIssuingCountry(issuingCountry);
        return this;
    }

    public void setIssuingCountry(Integer issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public Integer getStatus() {
        return this.status;
    }

    public UserMast status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return this.comment;
    }

    public UserMast comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOtherInfo() {
        return this.otherInfo;
    }

    public UserMast otherInfo(String otherInfo) {
        this.setOtherInfo(otherInfo);
        return this;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getAllergicDrug1() {
        return this.allergicDrug1;
    }

    public UserMast allergicDrug1(String allergicDrug1) {
        this.setAllergicDrug1(allergicDrug1);
        return this;
    }

    public void setAllergicDrug1(String allergicDrug1) {
        this.allergicDrug1 = allergicDrug1;
    }

    public Integer getModuleKy() {
        return this.moduleKy;
    }

    public UserMast moduleKy(Integer moduleKy) {
        this.setModuleKy(moduleKy);
        return this;
    }

    public void setModuleKy(Integer moduleKy) {
        this.moduleKy = moduleKy;
    }

    public Integer getTableKy() {
        return this.tableKy;
    }

    public UserMast tableKy(Integer tableKy) {
        this.setTableKy(tableKy);
        return this;
    }

    public void setTableKy(Integer tableKy) {
        this.tableKy = tableKy;
    }

    public String getAllergicDrug2() {
        return this.allergicDrug2;
    }

    public UserMast allergicDrug2(String allergicDrug2) {
        this.setAllergicDrug2(allergicDrug2);
        return this;
    }

    public void setAllergicDrug2(String allergicDrug2) {
        this.allergicDrug2 = allergicDrug2;
    }

    public String getComments() {
        return this.comments;
    }

    public UserMast comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public UserMast remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExtraFields() {
        return this.extraFields;
    }

    public UserMast extraFields(String extraFields) {
        this.setExtraFields(extraFields);
        return this;
    }

    public void setExtraFields(String extraFields) {
        this.extraFields = extraFields;
    }

    public String getColumn1() {
        return this.column1;
    }

    public UserMast column1(String column1) {
        this.setColumn1(column1);
        return this;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public Integer getColumn2() {
        return this.column2;
    }

    public UserMast column2(Integer column2) {
        this.setColumn2(column2);
        return this;
    }

    public void setColumn2(Integer column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return this.column3;
    }

    public UserMast column3(String column3) {
        this.setColumn3(column3);
        return this;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getHobbies() {
        return this.hobbies;
    }

    public UserMast hobbies(String hobbies) {
        this.setHobbies(hobbies);
        return this;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getOtherActivities() {
        return this.otherActivities;
    }

    public UserMast otherActivities(String otherActivities) {
        this.setOtherActivities(otherActivities);
        return this;
    }

    public void setOtherActivities(String otherActivities) {
        this.otherActivities = otherActivities;
    }

    public String getPassword() {
        return this.password;
    }

    public UserMast password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public UserMast role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getPath() {
        return this.path;
    }

    public UserMast path(Integer path) {
        this.setPath(path);
        return this;
    }

    public void setPath(Integer path) {
        this.path = path;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public UserMast roleType(String roleType) {
        this.setRoleType(roleType);
        return this;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public UserMast addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public UserMast addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public UserMast updatedBy(Integer updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public UserMast updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public UserMast approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public UserMast approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getLanguage() {
        return this.language;
    }

    public UserMast language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHist() {
        return this.hist;
    }

    public UserMast hist(String hist) {
        this.setHist(hist);
        return this;
    }

    public void setHist(String hist) {
        this.hist = hist;
    }

    public String getLayout() {
        return this.layout;
    }

    public UserMast layout(String layout) {
        this.setLayout(layout);
        return this;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getSecurityKeyStatus() {
        return this.securityKeyStatus;
    }

    public UserMast securityKeyStatus(String securityKeyStatus) {
        this.setSecurityKeyStatus(securityKeyStatus);
        return this;
    }

    public void setSecurityKeyStatus(String securityKeyStatus) {
        this.securityKeyStatus = securityKeyStatus;
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public UserMast hashCode(String hashCode) {
        this.setHashCode(hashCode);
        return this;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Integer getEncryptionStatus() {
        return this.encryptionStatus;
    }

    public UserMast encryptionStatus(Integer encryptionStatus) {
        this.setEncryptionStatus(encryptionStatus);
        return this;
    }

    public void setEncryptionStatus(Integer encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public UserMast encryptedPassword(String encryptedPassword) {
        this.setEncryptedPassword(encryptedPassword);
        return this;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getZone() {
        return this.zone;
    }

    public UserMast zone(String zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public UserMast orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getJob() {
        return this.job;
    }

    public UserMast job(String job) {
        this.setJob(job);
        return this;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getPresentAddress() {
        return this.presentAddress;
    }

    public UserMast presentAddress(Long presentAddress) {
        this.setPresentAddress(presentAddress);
        return this;
    }

    public void setPresentAddress(Long presentAddress) {
        this.presentAddress = presentAddress;
    }

    public Long getPermanentAddress() {
        return this.permanentAddress;
    }

    public UserMast permanentAddress(Long permanentAddress) {
        this.setPermanentAddress(permanentAddress);
        return this;
    }

    public void setPermanentAddress(Long permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getWorkcompany() {
        return this.workcompany;
    }

    public UserMast workcompany(String workcompany) {
        this.setWorkcompany(workcompany);
        return this;
    }

    public void setWorkcompany(String workcompany) {
        this.workcompany = workcompany;
    }

    public Long getWorkAddress() {
        return this.workAddress;
    }

    public UserMast workAddress(Long workAddress) {
        this.setWorkAddress(workAddress);
        return this;
    }

    public void setWorkAddress(Long workAddress) {
        this.workAddress = workAddress;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public UserMast orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserMast)) {
            return false;
        }
        return id != null && id.equals(((UserMast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserMast{" +
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
            "}";
    }
}
