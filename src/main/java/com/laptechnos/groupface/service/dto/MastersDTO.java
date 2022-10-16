package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Masters} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MastersDTO implements Serializable {

    private Long id;

    private Integer key;

    private String val;

    private Integer code;

    private String codeval;

    private Integer status;

    private Long orgId;

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

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeval() {
        return codeval;
    }

    public void setCodeval(String codeval) {
        this.codeval = codeval;
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
        if (!(o instanceof MastersDTO)) {
            return false;
        }

        MastersDTO mastersDTO = (MastersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mastersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MastersDTO{" +
            "id=" + getId() +
            ", key=" + getKey() +
            ", val='" + getVal() + "'" +
            ", code=" + getCode() +
            ", codeval='" + getCodeval() + "'" +
            ", status=" + getStatus() +
            ", orgId=" + getOrgId() +
            ", addedByUser=" + getAddedByUser() +
            ", updatedByUser=" + getUpdatedByUser() +
            ", approvedByUser=" + getApprovedByUser() +
            ", orgIdObj=" + getOrgIdObj() +
            "}";
    }
}
