package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.GroupUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GroupUserDTO implements Serializable {

    private Long id;

    private Long groupId;

    private Long grpUser;

    private Integer isActive;

    private Integer isEnable;

    private Long addedBy;

    private LocalDate addedOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private LocalDate approvedOn;

    private String comments;

    private String remarks;

    private Long orgId;

    private Long userType;

    private UserMastDTO addedByUser;

    private UserMastDTO updatedByUser;

    private UserMastDTO approvedByUser;

    private GroupsDTO groupIdObj;

    private UserMastDTO grpUserObj;

    private MastersDTO userTypeObj;

    private OrganisationDTO orgIdOb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGrpUser() {
        return grpUser;
    }

    public void setGrpUser(Long grpUser) {
        this.grpUser = grpUser;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
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

    public GroupsDTO getGroupIdObj() {
        return groupIdObj;
    }

    public void setGroupIdObj(GroupsDTO groupIdObj) {
        this.groupIdObj = groupIdObj;
    }

    public UserMastDTO getGrpUserObj() {
        return grpUserObj;
    }

    public void setGrpUserObj(UserMastDTO grpUserObj) {
        this.grpUserObj = grpUserObj;
    }

    public MastersDTO getUserTypeObj() {
        return userTypeObj;
    }

    public void setUserTypeObj(MastersDTO userTypeObj) {
        this.userTypeObj = userTypeObj;
    }

    public OrganisationDTO getOrgIdOb() {
        return orgIdOb;
    }

    public void setOrgIdOb(OrganisationDTO orgIdOb) {
        this.orgIdOb = orgIdOb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupUserDTO)) {
            return false;
        }

        GroupUserDTO groupUserDTO = (GroupUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, groupUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupUserDTO{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", grpUser=" + getGrpUser() +
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
            ", orgId=" + getOrgId() +
            ", userType=" + getUserType() +
            ", addedByUser=" + getAddedByUser() +
            ", updatedByUser=" + getUpdatedByUser() +
            ", approvedByUser=" + getApprovedByUser() +
            ", groupIdObj=" + getGroupIdObj() +
            ", grpUserObj=" + getGrpUserObj() +
            ", userTypeObj=" + getUserTypeObj() +
            ", orgIdOb=" + getOrgIdOb() +
            "}";
    }
}
