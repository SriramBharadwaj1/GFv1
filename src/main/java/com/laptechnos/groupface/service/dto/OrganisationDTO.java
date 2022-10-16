package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Organisation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganisationDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Integer type;

    private Long parentOrgId;

    private Long primaryContactId;

    private Long orgHead;

    private Long locationId;

    private String website;

    private String layout;

    private Integer tableKy;

    private Integer isActive;

    private Integer isEnable;

    private Long addedBy;

    private LocalDate addedOn;

    private Integer updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private LocalDate approvedOn;

    private String comments;

    private String remarks;

    private String extraFields;

    private String buildFilePath;

    private String shortName;

    private String vatNo;

    private Integer moduleKy;

    private String hashCode;

    private String hashCertificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public Long getPrimaryContactId() {
        return primaryContactId;
    }

    public void setPrimaryContactId(Long primaryContactId) {
        this.primaryContactId = primaryContactId;
    }

    public Long getOrgHead() {
        return orgHead;
    }

    public void setOrgHead(Long orgHead) {
        this.orgHead = orgHead;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Integer getTableKy() {
        return tableKy;
    }

    public void setTableKy(Integer tableKy) {
        this.tableKy = tableKy;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public String getBuildFilePath() {
        return buildFilePath;
    }

    public void setBuildFilePath(String buildFilePath) {
        this.buildFilePath = buildFilePath;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public Integer getModuleKy() {
        return moduleKy;
    }

    public void setModuleKy(Integer moduleKy) {
        this.moduleKy = moduleKy;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getHashCertificate() {
        return hashCertificate;
    }

    public void setHashCertificate(String hashCertificate) {
        this.hashCertificate = hashCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationDTO)) {
            return false;
        }

        OrganisationDTO organisationDTO = (OrganisationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organisationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganisationDTO{" +
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
