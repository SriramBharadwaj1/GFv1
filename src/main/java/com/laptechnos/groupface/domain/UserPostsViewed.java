package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserPostsViewed.
 */
@Entity
@Table(name = "user_posts_viewed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "userpostsviewed")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserPostsViewed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "postid")
    private Long postid;

    @Column(name = "viewdate")
    private LocalDate viewdate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "org_id")
    private Long orgId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast userIdObj;

    @ManyToOne
    private Organisation orgIdObj;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserPostsViewed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public UserPostsViewed userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostid() {
        return this.postid;
    }

    public UserPostsViewed postid(Long postid) {
        this.setPostid(postid);
        return this;
    }

    public void setPostid(Long postid) {
        this.postid = postid;
    }

    public LocalDate getViewdate() {
        return this.viewdate;
    }

    public UserPostsViewed viewdate(LocalDate viewdate) {
        this.setViewdate(viewdate);
        return this;
    }

    public void setViewdate(LocalDate viewdate) {
        this.viewdate = viewdate;
    }

    public Integer getStatus() {
        return this.status;
    }

    public UserPostsViewed status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public UserPostsViewed orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public UserMast getUserIdObj() {
        return this.userIdObj;
    }

    public void setUserIdObj(UserMast userMast) {
        this.userIdObj = userMast;
    }

    public UserPostsViewed userIdObj(UserMast userMast) {
        this.setUserIdObj(userMast);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public UserPostsViewed orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPostsViewed)) {
            return false;
        }
        return id != null && id.equals(((UserPostsViewed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserPostsViewed{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", postid=" + getPostid() +
            ", viewdate='" + getViewdate() + "'" +
            ", status=" + getStatus() +
            ", orgId=" + getOrgId() +
            "}";
    }
}
