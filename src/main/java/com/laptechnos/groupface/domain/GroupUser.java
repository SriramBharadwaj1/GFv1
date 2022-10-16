package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GroupUser.
 */
@Entity
@Table(name = "group_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "groupuser")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "grp_user")
    private Long grpUser;

    @Column(name = "is_active")
    private Integer isActive;

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

    @Column(name = "approved_on")
    private LocalDate approvedOn;

    @Column(name = "comments")
    private String comments;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "user_type")
    private Long userType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast addedByUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast updatedByUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast approvedByUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "addedByUser", "updatedByUser", "approvedByser", "orgIdObj" }, allowSetters = true)
    private Groups groupIdObj;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast grpUserObj;

    @ManyToOne
    @JsonIgnoreProperties(value = { "addedByUser", "updatedByUser", "approvedByUser", "orgIdObj" }, allowSetters = true)
    private Masters userTypeObj;

    @ManyToOne
    private Organisation orgIdOb;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GroupUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public GroupUser groupId(Long groupId) {
        this.setGroupId(groupId);
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGrpUser() {
        return this.grpUser;
    }

    public GroupUser grpUser(Long grpUser) {
        this.setGrpUser(grpUser);
        return this;
    }

    public void setGrpUser(Long grpUser) {
        this.grpUser = grpUser;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public GroupUser isActive(Integer isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public GroupUser isEnable(Integer isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public GroupUser addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public GroupUser addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public GroupUser updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public GroupUser updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public GroupUser approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public GroupUser approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getComments() {
        return this.comments;
    }

    public GroupUser comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public GroupUser remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public GroupUser orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserType() {
        return this.userType;
    }

    public GroupUser userType(Long userType) {
        this.setUserType(userType);
        return this;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public UserMast getAddedByUser() {
        return this.addedByUser;
    }

    public void setAddedByUser(UserMast userMast) {
        this.addedByUser = userMast;
    }

    public GroupUser addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(UserMast userMast) {
        this.updatedByUser = userMast;
    }

    public GroupUser updatedByUser(UserMast userMast) {
        this.setUpdatedByUser(userMast);
        return this;
    }

    public UserMast getApprovedByUser() {
        return this.approvedByUser;
    }

    public void setApprovedByUser(UserMast userMast) {
        this.approvedByUser = userMast;
    }

    public GroupUser approvedByUser(UserMast userMast) {
        this.setApprovedByUser(userMast);
        return this;
    }

    public Groups getGroupIdObj() {
        return this.groupIdObj;
    }

    public void setGroupIdObj(Groups groups) {
        this.groupIdObj = groups;
    }

    public GroupUser groupIdObj(Groups groups) {
        this.setGroupIdObj(groups);
        return this;
    }

    public UserMast getGrpUserObj() {
        return this.grpUserObj;
    }

    public void setGrpUserObj(UserMast userMast) {
        this.grpUserObj = userMast;
    }

    public GroupUser grpUserObj(UserMast userMast) {
        this.setGrpUserObj(userMast);
        return this;
    }

    public Masters getUserTypeObj() {
        return this.userTypeObj;
    }

    public void setUserTypeObj(Masters masters) {
        this.userTypeObj = masters;
    }

    public GroupUser userTypeObj(Masters masters) {
        this.setUserTypeObj(masters);
        return this;
    }

    public Organisation getOrgIdOb() {
        return this.orgIdOb;
    }

    public void setOrgIdOb(Organisation organisation) {
        this.orgIdOb = organisation;
    }

    public GroupUser orgIdOb(Organisation organisation) {
        this.setOrgIdOb(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupUser)) {
            return false;
        }
        return id != null && id.equals(((GroupUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupUser{" +
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
            "}";
    }
}
