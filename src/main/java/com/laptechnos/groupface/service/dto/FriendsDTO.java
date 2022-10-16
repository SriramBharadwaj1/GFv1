package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Friends} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FriendsDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long friendId;

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

    private Long orgId;

    private Integer type;

    private UserMastDTO addedByUser;

    private UserMastDTO updatedByUser;

    private UserMastDTO approvedByUser;

    private UserMastDTO userIdObj;

    private UserMastDTO friendIdObj;

    private OrganisationDTO orgIdObj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public UserMastDTO getUserIdObj() {
        return userIdObj;
    }

    public void setUserIdObj(UserMastDTO userIdObj) {
        this.userIdObj = userIdObj;
    }

    public UserMastDTO getFriendIdObj() {
        return friendIdObj;
    }

    public void setFriendIdObj(UserMastDTO friendIdObj) {
        this.friendIdObj = friendIdObj;
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
        if (!(o instanceof FriendsDTO)) {
            return false;
        }

        FriendsDTO friendsDTO = (FriendsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, friendsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendsDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", friendId=" + getFriendId() +
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
            ", type=" + getType() +
            ", addedByUser=" + getAddedByUser() +
            ", updatedByUser=" + getUpdatedByUser() +
            ", approvedByUser=" + getApprovedByUser() +
            ", userIdObj=" + getUserIdObj() +
            ", friendIdObj=" + getFriendIdObj() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
