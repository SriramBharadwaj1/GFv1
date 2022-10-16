package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.UserPostsViewed} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserPostsViewedDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long postid;

    private LocalDate viewdate;

    private Integer status;

    private Long orgId;

    private UserMastDTO userIdObj;

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

    public Long getPostid() {
        return postid;
    }

    public void setPostid(Long postid) {
        this.postid = postid;
    }

    public LocalDate getViewdate() {
        return viewdate;
    }

    public void setViewdate(LocalDate viewdate) {
        this.viewdate = viewdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public UserMastDTO getUserIdObj() {
        return userIdObj;
    }

    public void setUserIdObj(UserMastDTO userIdObj) {
        this.userIdObj = userIdObj;
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
        if (!(o instanceof UserPostsViewedDTO)) {
            return false;
        }

        UserPostsViewedDTO userPostsViewedDTO = (UserPostsViewedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userPostsViewedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserPostsViewedDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", postid=" + getPostid() +
            ", viewdate='" + getViewdate() + "'" +
            ", status=" + getStatus() +
            ", orgId=" + getOrgId() +
            ", userIdObj=" + getUserIdObj() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
