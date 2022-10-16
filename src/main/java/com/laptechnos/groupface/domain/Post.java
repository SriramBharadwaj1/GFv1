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
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "post")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "category")
    private Integer category;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "meta")
    private String meta;

    @Column(name = "table_ky")
    private Integer tableKy;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "act_rej_reason")
    private Integer actRejReason;

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

    @Column(name = "type")
    private Integer type;

    @Column(name = "is_sales_post")
    private Integer isSalesPost;

    @Column(name = "sales_category")
    private Integer salesCategory;

    @Column(name = "price")
    private Double price;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_till")
    private LocalDate validTill;

    @Column(name = "phone_area")
    private String phoneArea;

    @Column(name = "phno")
    private String phno;

    @Column(name = "video_grp")
    private Long videoGrp;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "other_info")
    private String otherInfo;

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
    private Organisation orgIdObj;

    @OneToMany(mappedBy = "rapostIdObj")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "rapostIdObj", "addedByUser", "updatedByUser", "approvedByUser", "orgIdObj" }, allowSetters = true)
    private Set<Rating> ratposts = new HashSet<>();

    @OneToMany(mappedBy = "repostIdObj")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "repostIdObj", "addedByUser", "updatedByUser", "approvedByUser", "orgIdObj" }, allowSetters = true)
    private Set<Remarks> remposts = new HashSet<>();

    @OneToMany(mappedBy = "copostIdObj")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "copostIdObj", "addedByUser", "updatedByUser", "approvedByUser", "orgIdObj" }, allowSetters = true)
    private Set<Comment> composts = new HashSet<>();

    @OneToMany(mappedBy = "postObj")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "addedByUser", "updatedByUser", "approvedByUser", "postObj", "orgIdObj" }, allowSetters = true)
    private Set<PostPics> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public Post message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCategory() {
        return this.category;
    }

    public Post category(Integer category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public Post categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMeta() {
        return this.meta;
    }

    public Post meta(String meta) {
        this.setMeta(meta);
        return this;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Integer getTableKy() {
        return this.tableKy;
    }

    public Post tableKy(Integer tableKy) {
        this.setTableKy(tableKy);
        return this;
    }

    public void setTableKy(Integer tableKy) {
        this.tableKy = tableKy;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public Post isActive(Integer isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getActRejReason() {
        return this.actRejReason;
    }

    public Post actRejReason(Integer actRejReason) {
        this.setActRejReason(actRejReason);
        return this;
    }

    public void setActRejReason(Integer actRejReason) {
        this.actRejReason = actRejReason;
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public Post isEnable(Integer isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public Post addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public Post addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Post updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Post updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public Post approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public Post approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getComments() {
        return this.comments;
    }

    public Post comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Post remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getType() {
        return this.type;
    }

    public Post type(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsSalesPost() {
        return this.isSalesPost;
    }

    public Post isSalesPost(Integer isSalesPost) {
        this.setIsSalesPost(isSalesPost);
        return this;
    }

    public void setIsSalesPost(Integer isSalesPost) {
        this.isSalesPost = isSalesPost;
    }

    public Integer getSalesCategory() {
        return this.salesCategory;
    }

    public Post salesCategory(Integer salesCategory) {
        this.setSalesCategory(salesCategory);
        return this;
    }

    public void setSalesCategory(Integer salesCategory) {
        this.salesCategory = salesCategory;
    }

    public Double getPrice() {
        return this.price;
    }

    public Post price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getValidFrom() {
        return this.validFrom;
    }

    public Post validFrom(LocalDate validFrom) {
        this.setValidFrom(validFrom);
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTill() {
        return this.validTill;
    }

    public Post validTill(LocalDate validTill) {
        this.setValidTill(validTill);
        return this;
    }

    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public String getPhoneArea() {
        return this.phoneArea;
    }

    public Post phoneArea(String phoneArea) {
        this.setPhoneArea(phoneArea);
        return this;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getPhno() {
        return this.phno;
    }

    public Post phno(String phno) {
        this.setPhno(phno);
        return this;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public Long getVideoGrp() {
        return this.videoGrp;
    }

    public Post videoGrp(Long videoGrp) {
        this.setVideoGrp(videoGrp);
        return this;
    }

    public void setVideoGrp(Long videoGrp) {
        this.videoGrp = videoGrp;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public Post orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOtherInfo() {
        return this.otherInfo;
    }

    public Post otherInfo(String otherInfo) {
        this.setOtherInfo(otherInfo);
        return this;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public UserMast getAddedByUser() {
        return this.addedByUser;
    }

    public void setAddedByUser(UserMast userMast) {
        this.addedByUser = userMast;
    }

    public Post addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(UserMast userMast) {
        this.updatedByUser = userMast;
    }

    public Post updatedByUser(UserMast userMast) {
        this.setUpdatedByUser(userMast);
        return this;
    }

    public UserMast getApprovedByUser() {
        return this.approvedByUser;
    }

    public void setApprovedByUser(UserMast userMast) {
        this.approvedByUser = userMast;
    }

    public Post approvedByUser(UserMast userMast) {
        this.setApprovedByUser(userMast);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public Post orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    public Set<Rating> getRatposts() {
        return this.ratposts;
    }

    public void setRatposts(Set<Rating> ratings) {
        if (this.ratposts != null) {
            this.ratposts.forEach(i -> i.setRapostIdObj(null));
        }
        if (ratings != null) {
            ratings.forEach(i -> i.setRapostIdObj(this));
        }
        this.ratposts = ratings;
    }

    public Post ratposts(Set<Rating> ratings) {
        this.setRatposts(ratings);
        return this;
    }

    public Post addRatpost(Rating rating) {
        this.ratposts.add(rating);
        rating.setRapostIdObj(this);
        return this;
    }

    public Post removeRatpost(Rating rating) {
        this.ratposts.remove(rating);
        rating.setRapostIdObj(null);
        return this;
    }

    public Set<Remarks> getRemposts() {
        return this.remposts;
    }

    public void setRemposts(Set<Remarks> remarks) {
        if (this.remposts != null) {
            this.remposts.forEach(i -> i.setRepostIdObj(null));
        }
        if (remarks != null) {
            remarks.forEach(i -> i.setRepostIdObj(this));
        }
        this.remposts = remarks;
    }

    public Post remposts(Set<Remarks> remarks) {
        this.setRemposts(remarks);
        return this;
    }

    public Post addRempost(Remarks remarks) {
        this.remposts.add(remarks);
        remarks.setRepostIdObj(this);
        return this;
    }

    public Post removeRempost(Remarks remarks) {
        this.remposts.remove(remarks);
        remarks.setRepostIdObj(null);
        return this;
    }

    public Set<Comment> getComposts() {
        return this.composts;
    }

    public void setComposts(Set<Comment> comments) {
        if (this.composts != null) {
            this.composts.forEach(i -> i.setCopostIdObj(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setCopostIdObj(this));
        }
        this.composts = comments;
    }

    public Post composts(Set<Comment> comments) {
        this.setComposts(comments);
        return this;
    }

    public Post addCompost(Comment comment) {
        this.composts.add(comment);
        comment.setCopostIdObj(this);
        return this;
    }

    public Post removeCompost(Comment comment) {
        this.composts.remove(comment);
        comment.setCopostIdObj(null);
        return this;
    }

    public Set<PostPics> getIds() {
        return this.ids;
    }

    public void setIds(Set<PostPics> postPics) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setPostObj(null));
        }
        if (postPics != null) {
            postPics.forEach(i -> i.setPostObj(this));
        }
        this.ids = postPics;
    }

    public Post ids(Set<PostPics> postPics) {
        this.setIds(postPics);
        return this;
    }

    public Post addId(PostPics postPics) {
        this.ids.add(postPics);
        postPics.setPostObj(this);
        return this;
    }

    public Post removeId(PostPics postPics) {
        this.ids.remove(postPics);
        postPics.setPostObj(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", category=" + getCategory() +
            ", categoryName='" + getCategoryName() + "'" +
            ", meta='" + getMeta() + "'" +
            ", tableKy=" + getTableKy() +
            ", isActive=" + getIsActive() +
            ", actRejReason=" + getActRejReason() +
            ", isEnable=" + getIsEnable() +
            ", addedBy=" + getAddedBy() +
            ", addedOn='" + getAddedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvedBy=" + getApprovedBy() +
            ", approvedOn='" + getApprovedOn() + "'" +
            ", comments='" + getComments() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", type=" + getType() +
            ", isSalesPost=" + getIsSalesPost() +
            ", salesCategory=" + getSalesCategory() +
            ", price=" + getPrice() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTill='" + getValidTill() + "'" +
            ", phoneArea='" + getPhoneArea() + "'" +
            ", phno='" + getPhno() + "'" +
            ", videoGrp=" + getVideoGrp() +
            ", orgId=" + getOrgId() +
            ", otherInfo='" + getOtherInfo() + "'" +
            "}";
    }
}
