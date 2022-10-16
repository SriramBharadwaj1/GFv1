package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Caste} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CasteDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String desc;

    private Long parentId;

    private Integer moderatorId;

    private Integer parentTableKy;

    private Integer status;

    private Long zone;

    private Long orgId;

    private Integer hist;

    private Long addedBy;

    private LocalDate addedOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private LocalDate approvedOn;

    private String otherinfo;

    private String comments;

    private String remarks;

    private UserMastDTO addedByUser;

    private UserMastDTO updatedByUser;

    private UserMastDTO approvedByObj;

    private CasteDTO parentIdObj;

    private OrganisationDTO orgIdObj;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    public Integer getParentTableKy() {
        return parentTableKy;
    }

    public void setParentTableKy(Integer parentTableKy) {
        this.parentTableKy = parentTableKy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getHist() {
        return hist;
    }

    public void setHist(Integer hist) {
        this.hist = hist;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
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

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
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

    public UserMastDTO getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(UserMastDTO addedByUser) {
        this.addedByUser = addedByUser;
    }

    public UserMastDTO getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(UserMastDTO updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public UserMastDTO getApprovedByObj() {
        return approvedByObj;
    }

    public void setApprovedByObj(UserMastDTO approvedByObj) {
        this.approvedByObj = approvedByObj;
    }

    public CasteDTO getParentIdObj() {
        return parentIdObj;
    }

    public void setParentIdObj(CasteDTO parentIdObj) {
        this.parentIdObj = parentIdObj;
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
        if (!(o instanceof CasteDTO)) {
            return false;
        }

        CasteDTO casteDTO = (CasteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, casteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CasteDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", parentId=" + getParentId() +
            ", moderatorId=" + getModeratorId() +
            ", parentTableKy=" + getParentTableKy() +
            ", status=" + getStatus() +
            ", zone=" + getZone() +
            ", orgId=" + getOrgId() +
            ", hist=" + getHist() +
            ", addedBy=" + getAddedBy() +
            ", addedOn='" + getAddedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvedBy=" + getApprovedBy() +
            ", approvedOn='" + getApprovedOn() + "'" +
            ", otherinfo='" + getOtherinfo() + "'" +
            ", comments='" + getComments() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", addedByUser=" + getAddedByUser() +
            ", updatedByUser=" + getUpdatedByUser() +
            ", approvedByObj=" + getApprovedByObj() +
            ", parentIdObj=" + getParentIdObj() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
