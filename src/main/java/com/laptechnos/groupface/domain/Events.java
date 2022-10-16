package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Events.
 */
@Entity
@Table(name = "events")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "events")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "appr_rej_reason")
    private Integer apprRejReason;

    @Column(name = "event_type")
    private Integer eventType;

    @Column(name = "is_enable")
    private Integer isEnable;

    @Column(name = "added_by")
    private Long addedBy;

    @Column(name = "added_on")
    private LocalDate addedOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "approved_on")
    private LocalDate approvedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast addedByUsr;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast updatedByUsr;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast approvedByUsr;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast userIdUsr;

    @ManyToOne
    @JsonIgnoreProperties(value = { "addedByUser", "updatedByUser", "approvedByUser", "orgIdObj" }, allowSetters = true)
    private Masters eventTypeObj;

    @ManyToOne
    private Organisation orgIdObj;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Events id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Events name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Events userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public Events isActive(Integer isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getApprRejReason() {
        return this.apprRejReason;
    }

    public Events apprRejReason(Integer apprRejReason) {
        this.setApprRejReason(apprRejReason);
        return this;
    }

    public void setApprRejReason(Integer apprRejReason) {
        this.apprRejReason = apprRejReason;
    }

    public Integer getEventType() {
        return this.eventType;
    }

    public Events eventType(Integer eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public Events isEnable(Integer isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public Events addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public Events addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Events updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Events updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public Events approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public Events orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public Events approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public UserMast getAddedByUsr() {
        return this.addedByUsr;
    }

    public void setAddedByUsr(UserMast userMast) {
        this.addedByUsr = userMast;
    }

    public Events addedByUsr(UserMast userMast) {
        this.setAddedByUsr(userMast);
        return this;
    }

    public UserMast getUpdatedByUsr() {
        return this.updatedByUsr;
    }

    public void setUpdatedByUsr(UserMast userMast) {
        this.updatedByUsr = userMast;
    }

    public Events updatedByUsr(UserMast userMast) {
        this.setUpdatedByUsr(userMast);
        return this;
    }

    public UserMast getApprovedByUsr() {
        return this.approvedByUsr;
    }

    public void setApprovedByUsr(UserMast userMast) {
        this.approvedByUsr = userMast;
    }

    public Events approvedByUsr(UserMast userMast) {
        this.setApprovedByUsr(userMast);
        return this;
    }

    public UserMast getUserIdUsr() {
        return this.userIdUsr;
    }

    public void setUserIdUsr(UserMast userMast) {
        this.userIdUsr = userMast;
    }

    public Events userIdUsr(UserMast userMast) {
        this.setUserIdUsr(userMast);
        return this;
    }

    public Masters getEventTypeObj() {
        return this.eventTypeObj;
    }

    public void setEventTypeObj(Masters masters) {
        this.eventTypeObj = masters;
    }

    public Events eventTypeObj(Masters masters) {
        this.setEventTypeObj(masters);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public Events orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Events)) {
            return false;
        }
        return id != null && id.equals(((Events) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Events{" +
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
            "}";
    }
}
