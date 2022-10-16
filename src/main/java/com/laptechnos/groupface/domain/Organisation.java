package com.laptechnos.groupface.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organisation.
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "organisation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "parent_org_id")
    private Long parentOrgId;

    @Column(name = "primary_contact_id")
    private Long primaryContactId;

    @Column(name = "org_head")
    private Long orgHead;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "website")
    private String website;

    @Column(name = "layout")
    private String layout;

    @Column(name = "table_ky")
    private Integer tableKy;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "is_enable")
    private Integer isEnable;

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

    @Column(name = "comments")
    private String comments;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "extra_fields")
    private String extraFields;

    @Column(name = "build_file_path")
    private String buildFilePath;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "vat_no")
    private String vatNo;

    @Column(name = "module_ky")
    private Integer moduleKy;

    @Column(name = "hash_code")
    private String hashCode;

    @Column(name = "hash_certificate")
    private String hashCertificate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organisation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Organisation code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Organisation name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return this.type;
    }

    public Organisation type(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentOrgId() {
        return this.parentOrgId;
    }

    public Organisation parentOrgId(Long parentOrgId) {
        this.setParentOrgId(parentOrgId);
        return this;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public Long getPrimaryContactId() {
        return this.primaryContactId;
    }

    public Organisation primaryContactId(Long primaryContactId) {
        this.setPrimaryContactId(primaryContactId);
        return this;
    }

    public void setPrimaryContactId(Long primaryContactId) {
        this.primaryContactId = primaryContactId;
    }

    public Long getOrgHead() {
        return this.orgHead;
    }

    public Organisation orgHead(Long orgHead) {
        this.setOrgHead(orgHead);
        return this;
    }

    public void setOrgHead(Long orgHead) {
        this.orgHead = orgHead;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public Organisation locationId(Long locationId) {
        this.setLocationId(locationId);
        return this;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getWebsite() {
        return this.website;
    }

    public Organisation website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLayout() {
        return this.layout;
    }

    public Organisation layout(String layout) {
        this.setLayout(layout);
        return this;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Integer getTableKy() {
        return this.tableKy;
    }

    public Organisation tableKy(Integer tableKy) {
        this.setTableKy(tableKy);
        return this;
    }

    public void setTableKy(Integer tableKy) {
        this.tableKy = tableKy;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public Organisation isActive(Integer isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public Organisation isEnable(Integer isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public Organisation addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public Organisation addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public Organisation updatedBy(Integer updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Organisation updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public Organisation approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public Organisation approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getComments() {
        return this.comments;
    }

    public Organisation comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Organisation remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExtraFields() {
        return this.extraFields;
    }

    public Organisation extraFields(String extraFields) {
        this.setExtraFields(extraFields);
        return this;
    }

    public void setExtraFields(String extraFields) {
        this.extraFields = extraFields;
    }

    public String getBuildFilePath() {
        return this.buildFilePath;
    }

    public Organisation buildFilePath(String buildFilePath) {
        this.setBuildFilePath(buildFilePath);
        return this;
    }

    public void setBuildFilePath(String buildFilePath) {
        this.buildFilePath = buildFilePath;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Organisation shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getVatNo() {
        return this.vatNo;
    }

    public Organisation vatNo(String vatNo) {
        this.setVatNo(vatNo);
        return this;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public Integer getModuleKy() {
        return this.moduleKy;
    }

    public Organisation moduleKy(Integer moduleKy) {
        this.setModuleKy(moduleKy);
        return this;
    }

    public void setModuleKy(Integer moduleKy) {
        this.moduleKy = moduleKy;
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public Organisation hashCode(String hashCode) {
        this.setHashCode(hashCode);
        return this;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getHashCertificate() {
        return this.hashCertificate;
    }

    public Organisation hashCertificate(String hashCertificate) {
        this.setHashCertificate(hashCertificate);
        return this;
    }

    public void setHashCertificate(String hashCertificate) {
        this.hashCertificate = hashCertificate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisation)) {
            return false;
        }
        return id != null && id.equals(((Organisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", type=" + getType() +
            ", parentOrgId=" + getParentOrgId() +
            ", primaryContactId=" + getPrimaryContactId() +
            ", orgHead=" + getOrgHead() +
            ", locationId=" + getLocationId() +
            ", website='" + getWebsite() + "'" +
            ", layout='" + getLayout() + "'" +
            ", tableKy=" + getTableKy() +
            ", isActive=" + getIsActive() +
            ", isEnable=" + getIsEnable() +
            ", addedBy=" + getAddedBy() +
            ", addedOn='" + getAddedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvedBy=" + getApprovedBy() +
            ", approvedOn='" + getApprovedOn() + "'" +
            ", comments='" + getComments() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", extraFields='" + getExtraFields() + "'" +
            ", buildFilePath='" + getBuildFilePath() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", vatNo='" + getVatNo() + "'" +
            ", moduleKy=" + getModuleKy() +
            ", hashCode='" + getHashCode() + "'" +
            ", hashCertificate='" + getHashCertificate() + "'" +
            "}";
    }
}
