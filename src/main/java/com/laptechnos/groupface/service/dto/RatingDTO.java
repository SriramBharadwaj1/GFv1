package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Rating} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingDTO implements Serializable {

    private Long id;

    private Double rating;

    private Long postId;

    private Integer isActive;

    private Integer apprRejReason;

    private Integer isEnable;

    private Long addedBy;

    private LocalDate addedOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private Long orgId;

    private LocalDate approvedOn;

    private PostDTO rapostIdObj;

    private UserMastDTO addedByUser;

    private UserMastDTO updatedByUser;

    private UserMastDTO approvedByUser;

    private OrganisationDTO orgIdObj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getApprRejReason() {
        return apprRejReason;
    }

    public void setApprRejReason(Integer apprRejReason) {
        this.apprRejReason = apprRejReason;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public LocalDate getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public PostDTO getRapostIdObj() {
        return rapostIdObj;
    }

    public void setRapostIdObj(PostDTO rapostIdObj) {
        this.rapostIdObj = rapostIdObj;
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
        if (!(o instanceof RatingDTO)) {
            return false;
        }

        RatingDTO ratingDTO = (RatingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ratingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", postId=" + getPostId() +
            ", isActive=" + getIsActive() +
            ", apprRejReason=" + getApprRejReason() +
            ", isEnable=" + getIsEnable() +
            ", addedBy=" + getAddedBy() +
            ", addedOn='" + getAddedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvedBy=" + getApprovedBy() +
            ", orgId=" + getOrgId() +
            ", approvedOn='" + getApprovedOn() + "'" +
            ", rapostIdObj=" + getRapostIdObj() +
            ", addedByUser=" + getAddedByUser() +
            ", updatedByUser=" + getUpdatedByUser() +
            ", approvedByUser=" + getApprovedByUser() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
