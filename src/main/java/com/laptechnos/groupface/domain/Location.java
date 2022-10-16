package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "location")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

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

    @Column(name = "lap_desc")
    private String desc;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "moderator_1_id")
    private Long moderator1Id;

    @Column(name = "moderator_2_id")
    private Long moderator2Id;

    @Column(name = "moderator_1_code")
    private String moderator1Code;

    @Column(name = "moderator_2_code")
    private String moderator2Code;

    @Column(name = "parent_table_ky")
    private Long parentTableKy;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "zone")
    private Long zone;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "hist")
    private Integer hist;

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

    @Column(name = "otherinfo")
    private String otherinfo;

    @Column(name = "comments")
    private String comments;

    @Column(name = "remarks")
    private String remarks;

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
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast moderator1IdUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast moderator2IdUser;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByUser", "moderator1IdUser", "moderator2IdUser", "parentIdObj", "orgIdObj" },
        allowSetters = true
    )
    private Location parentIdObj;

    @ManyToOne
    private Organisation orgIdObj;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Location code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Location name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public Location desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Location parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getModerator1Id() {
        return this.moderator1Id;
    }

    public Location moderator1Id(Long moderator1Id) {
        this.setModerator1Id(moderator1Id);
        return this;
    }

    public void setModerator1Id(Long moderator1Id) {
        this.moderator1Id = moderator1Id;
    }

    public Long getModerator2Id() {
        return this.moderator2Id;
    }

    public Location moderator2Id(Long moderator2Id) {
        this.setModerator2Id(moderator2Id);
        return this;
    }

    public void setModerator2Id(Long moderator2Id) {
        this.moderator2Id = moderator2Id;
    }

    public String getModerator1Code() {
        return this.moderator1Code;
    }

    public Location moderator1Code(String moderator1Code) {
        this.setModerator1Code(moderator1Code);
        return this;
    }

    public void setModerator1Code(String moderator1Code) {
        this.moderator1Code = moderator1Code;
    }

    public String getModerator2Code() {
        return this.moderator2Code;
    }

    public Location moderator2Code(String moderator2Code) {
        this.setModerator2Code(moderator2Code);
        return this;
    }

    public void setModerator2Code(String moderator2Code) {
        this.moderator2Code = moderator2Code;
    }

    public Long getParentTableKy() {
        return this.parentTableKy;
    }

    public Location parentTableKy(Long parentTableKy) {
        this.setParentTableKy(parentTableKy);
        return this;
    }

    public void setParentTableKy(Long parentTableKy) {
        this.parentTableKy = parentTableKy;
    }

    public Integer getType() {
        return this.type;
    }

    public Location type(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Location status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getZone() {
        return this.zone;
    }

    public Location zone(Long zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public Location orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getHist() {
        return this.hist;
    }

    public Location hist(Integer hist) {
        this.setHist(hist);
        return this;
    }

    public void setHist(Integer hist) {
        this.hist = hist;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public Location addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public Location addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public Location updatedBy(Integer updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Location updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public Location approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public Location approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getOtherinfo() {
        return this.otherinfo;
    }

    public Location otherinfo(String otherinfo) {
        this.setOtherinfo(otherinfo);
        return this;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }

    public String getComments() {
        return this.comments;
    }

    public Location comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Location remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public UserMast getAddedByUser() {
        return this.addedByUser;
    }

    public void setAddedByUser(UserMast userMast) {
        this.addedByUser = userMast;
    }

    public Location addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(UserMast userMast) {
        this.updatedByUser = userMast;
    }

    public Location updatedByUser(UserMast userMast) {
        this.setUpdatedByUser(userMast);
        return this;
    }

    public UserMast getApprovedByUser() {
        return this.approvedByUser;
    }

    public void setApprovedByUser(UserMast userMast) {
        this.approvedByUser = userMast;
    }

    public Location approvedByUser(UserMast userMast) {
        this.setApprovedByUser(userMast);
        return this;
    }

    public UserMast getModerator1IdUser() {
        return this.moderator1IdUser;
    }

    public void setModerator1IdUser(UserMast userMast) {
        this.moderator1IdUser = userMast;
    }

    public Location moderator1IdUser(UserMast userMast) {
        this.setModerator1IdUser(userMast);
        return this;
    }

    public UserMast getModerator2IdUser() {
        return this.moderator2IdUser;
    }

    public void setModerator2IdUser(UserMast userMast) {
        this.moderator2IdUser = userMast;
    }

    public Location moderator2IdUser(UserMast userMast) {
        this.setModerator2IdUser(userMast);
        return this;
    }

    public Location getParentIdObj() {
        return this.parentIdObj;
    }

    public void setParentIdObj(Location location) {
        this.parentIdObj = location;
    }

    public Location parentIdObj(Location location) {
        this.setParentIdObj(location);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public Location orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
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
            "}";
    }
}
