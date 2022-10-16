package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PostPics.
 */
@Entity
@Table(name = "post_pics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "postpics")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostPics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "postid")
    private Long postid;

    @Column(name = "pic")
    private String pic;

    @Column(name = "picpath")
    private String picpath;

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

    @Column(name = "status")
    private Integer status;

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
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByUser", "orgIdObj", "ratposts", "remposts", "composts", "ids" },
        allowSetters = true
    )
    private Post postObj;

    @ManyToOne
    private Organisation orgIdObj;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PostPics id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostid() {
        return this.postid;
    }

    public PostPics postid(Long postid) {
        this.setPostid(postid);
        return this;
    }

    public void setPostid(Long postid) {
        this.postid = postid;
    }

    public String getPic() {
        return this.pic;
    }

    public PostPics pic(String pic) {
        this.setPic(pic);
        return this;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicpath() {
        return this.picpath;
    }

    public PostPics picpath(String picpath) {
        this.setPicpath(picpath);
        return this;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public PostPics isActive(Integer isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public PostPics isEnable(Integer isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public PostPics addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public PostPics addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public PostPics updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public PostPics updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public PostPics approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public PostPics approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getComments() {
        return this.comments;
    }

    public PostPics comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public PostPics remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public PostPics orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public PostPics status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserMast getAddedByUser() {
        return this.addedByUser;
    }

    public void setAddedByUser(UserMast userMast) {
        this.addedByUser = userMast;
    }

    public PostPics addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(UserMast userMast) {
        this.updatedByUser = userMast;
    }

    public PostPics updatedByUser(UserMast userMast) {
        this.setUpdatedByUser(userMast);
        return this;
    }

    public UserMast getApprovedByUser() {
        return this.approvedByUser;
    }

    public void setApprovedByUser(UserMast userMast) {
        this.approvedByUser = userMast;
    }

    public PostPics approvedByUser(UserMast userMast) {
        this.setApprovedByUser(userMast);
        return this;
    }

    public Post getPostObj() {
        return this.postObj;
    }

    public void setPostObj(Post post) {
        this.postObj = post;
    }

    public PostPics postObj(Post post) {
        this.setPostObj(post);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public PostPics orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostPics)) {
            return false;
        }
        return id != null && id.equals(((PostPics) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostPics{" +
            "id=" + getId() +
            ", postid=" + getPostid() +
            ", pic='" + getPic() + "'" +
            ", picpath='" + getPicpath() + "'" +
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
            ", status=" + getStatus() +
            "}";
    }
}
