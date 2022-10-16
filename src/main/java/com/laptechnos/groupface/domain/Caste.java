package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Caste.
 */
@Entity
@Table(name = "caste")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "caste")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Caste implements Serializable {

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

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @Column(name = "parent_table_ky")
    private Integer parentTableKy;

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
    private Long updatedBy;

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
    private UserMast approvedByObj;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByObj", "parentIdObj", "orgIdObj", "ids" },
        allowSetters = true
    )
    private Caste parentIdObj;

    @ManyToOne
    private Organisation orgIdObj;

    @OneToMany(mappedBy = "parentIdObj")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByObj", "parentIdObj", "orgIdObj", "ids" },
        allowSetters = true
    )
    private Set<Caste> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Caste id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Caste code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Caste name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public Caste desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Caste parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getModeratorId() {
        return this.moderatorId;
    }

    public Caste moderatorId(Integer moderatorId) {
        this.setModeratorId(moderatorId);
        return this;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    public Integer getParentTableKy() {
        return this.parentTableKy;
    }

    public Caste parentTableKy(Integer parentTableKy) {
        this.setParentTableKy(parentTableKy);
        return this;
    }

    public void setParentTableKy(Integer parentTableKy) {
        this.parentTableKy = parentTableKy;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Caste status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getZone() {
        return this.zone;
    }

    public Caste zone(Long zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public Caste orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getHist() {
        return this.hist;
    }

    public Caste hist(Integer hist) {
        this.setHist(hist);
        return this;
    }

    public void setHist(Integer hist) {
        this.hist = hist;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public Caste addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public Caste addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Caste updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Caste updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public Caste approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public Caste approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getOtherinfo() {
        return this.otherinfo;
    }

    public Caste otherinfo(String otherinfo) {
        this.setOtherinfo(otherinfo);
        return this;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }

    public String getComments() {
        return this.comments;
    }

    public Caste comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Caste remarks(String remarks) {
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

    public Caste addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(UserMast userMast) {
        this.updatedByUser = userMast;
    }

    public Caste updatedByUser(UserMast userMast) {
        this.setUpdatedByUser(userMast);
        return this;
    }

    public UserMast getApprovedByObj() {
        return this.approvedByObj;
    }

    public void setApprovedByObj(UserMast userMast) {
        this.approvedByObj = userMast;
    }

    public Caste approvedByObj(UserMast userMast) {
        this.setApprovedByObj(userMast);
        return this;
    }

    public Caste getParentIdObj() {
        return this.parentIdObj;
    }

    public void setParentIdObj(Caste caste) {
        this.parentIdObj = caste;
    }

    public Caste parentIdObj(Caste caste) {
        this.setParentIdObj(caste);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public Caste orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    public Set<Caste> getIds() {
        return this.ids;
    }

    public void setIds(Set<Caste> castes) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setParentIdObj(null));
        }
        if (castes != null) {
            castes.forEach(i -> i.setParentIdObj(this));
        }
        this.ids = castes;
    }

    public Caste ids(Set<Caste> castes) {
        this.setIds(castes);
        return this;
    }

    public Caste addId(Caste caste) {
        this.ids.add(caste);
        caste.setParentIdObj(this);
        return this;
    }

    public Caste removeId(Caste caste) {
        this.ids.remove(caste);
        caste.setParentIdObj(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Caste)) {
            return false;
        }
        return id != null && id.equals(((Caste) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Caste{" +
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
            "}";
    }
}
