package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Location} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String desc;

    private Long parentId;

    private Long moderator1Id;

    private Long moderator2Id;

    private String moderator1Code;

    private String moderator2Code;

    private Long parentTableKy;

    private Integer type;

    private Integer status;

    private Long zone;

    private Long orgId;

    private Integer hist;

    private Long addedBy;

    private LocalDate addedOn;

    private Integer updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private LocalDate approvedOn;

    private String otherinfo;

    private String comments;

    private String remarks;

    private UserMastDTO addedByUser;

    private UserMastDTO updatedByUser;

    private UserMastDTO approvedByUser;

    private UserMastDTO moderator1IdUser;

    private UserMastDTO moderator2IdUser;

    private LocationDTO parentIdObj;

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

    public Long getModerator1Id() {
        return moderator1Id;
    }

    public void setModerator1Id(Long moderator1Id) {
        this.moderator1Id = moderator1Id;
    }

    public Long getModerator2Id() {
        return moderator2Id;
    }

    public void setModerator2Id(Long moderator2Id) {
        this.moderator2Id = moderator2Id;
    }

    public String getModerator1Code() {
        return moderator1Code;
    }

    public void setModerator1Code(String moderator1Code) {
        this.moderator1Code = moderator1Code;
    }

    public String getModerator2Code() {
        return moderator2Code;
    }

    public void setModerator2Code(String moderator2Code) {
        this.moderator2Code = moderator2Code;
    }

    public Long getParentTableKy() {
        return parentTableKy;
    }

    public void setParentTableKy(Long parentTableKy) {
        this.parentTableKy = parentTableKy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public UserMastDTO getApprovedByUser() {
        return approvedByUser;
    }

    public void setApprovedByUser(UserMastDTO approvedByUser) {
        this.approvedByUser = approvedByUser;
    }

    public UserMastDTO getModerator1IdUser() {
        return moderator1IdUser;
    }

    public void setModerator1IdUser(UserMastDTO moderator1IdUser) {
        this.moderator1IdUser = moderator1IdUser;
    }

    public UserMastDTO getModerator2IdUser() {
        return moderator2IdUser;
    }

    public void setModerator2IdUser(UserMastDTO moderator2IdUser) {
        this.moderator2IdUser = moderator2IdUser;
    }

    public LocationDTO getParentIdObj() {
        return parentIdObj;
    }

    public void setParentIdObj(LocationDTO parentIdObj) {
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
        if (!(o instanceof LocationDTO)) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", parentId=" + getParentId() +
            ", moderator1Id=" + getModerator1Id() +
            ", moderator2Id=" + getModerator2Id() +
            ", moderator1Code='" + getModerator1Code() + "'" +
            ", moderator2Code='" + getModerator2Code() + "'" +
            ", parentTableKy=" + getParentTableKy() +
            ", type=" + getType() +
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
            ", approvedByUser=" + getApprovedByUser() +
            ", moderator1IdUser=" + getModerator1IdUser() +
            ", moderator2IdUser=" + getModerator2IdUser() +
            ", parentIdObj=" + getParentIdObj() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
