package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Events} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventsDTO implements Serializable {

    private Long id;

    private String name;

    private Long userId;

    private Integer isActive;

    private Integer apprRejReason;

    private Integer eventType;

    private Integer isEnable;

    private Long addedBy;

    private LocalDate addedOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private Long orgId;

    private LocalDate approvedOn;

    private UserMastDTO addedByUsr;

    private UserMastDTO updatedByUsr;

    private UserMastDTO approvedByUsr;

    private UserMastDTO userIdUsr;

    private MastersDTO eventTypeObj;

    private OrganisationDTO orgIdObj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
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

    public UserMastDTO getAddedByUsr() {
        return addedByUsr;
    }

    public void setAddedByUsr(UserMastDTO addedByUsr) {
        this.addedByUsr = addedByUsr;
    }

    public UserMastDTO getUpdatedByUsr() {
        return updatedByUsr;
    }

    public void setUpdatedByUsr(UserMastDTO updatedByUsr) {
        this.updatedByUsr = updatedByUsr;
    }

    public UserMastDTO getApprovedByUsr() {
        return approvedByUsr;
    }

    public void setApprovedByUsr(UserMastDTO approvedByUsr) {
        this.approvedByUsr = approvedByUsr;
    }

    public UserMastDTO getUserIdUsr() {
        return userIdUsr;
    }

    public void setUserIdUsr(UserMastDTO userIdUsr) {
        this.userIdUsr = userIdUsr;
    }

    public MastersDTO getEventTypeObj() {
        return eventTypeObj;
    }

    public void setEventTypeObj(MastersDTO eventTypeObj) {
        this.eventTypeObj = eventTypeObj;
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
        if (!(o instanceof EventsDTO)) {
            return false;
        }

        EventsDTO eventsDTO = (EventsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", userId=" + getUserId() +
            ", isActive=" + getIsActive() +
            ", apprRejReason=" + getApprRejReason() +
            ", eventType=" + getEventType() +
            ", isEnable=" + getIsEnable() +
            ", addedBy=" + getAddedBy() +
            ", addedOn='" + getAddedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvedBy=" + getApprovedBy() +
            ", orgId=" + getOrgId() +
            ", approvedOn='" + getApprovedOn() + "'" +
            ", addedByUsr=" + getAddedByUsr() +
            ", updatedByUsr=" + getUpdatedByUsr() +
            ", approvedByUsr=" + getApprovedByUsr() +
            ", userIdUsr=" + getUserIdUsr() +
            ", eventTypeObj=" + getEventTypeObj() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
