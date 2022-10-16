package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Masters.
 */
@Entity
@Table(name = "masters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "masters")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Masters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private Integer key;

    @Column(name = "val")
    private String val;

    @Column(name = "code")
    private Integer code;

    @Column(name = "codeval")
    private String codeval;

    @Column(name = "status")
    private Integer status;

    @Column(name = "org_id")
    private Long orgId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Masters id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKey() {
        return this.key;
    }

    public Masters key(Integer key) {
        this.setKey(key);
        return this;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getVal() {
        return this.val;
    }

    public Masters val(String val) {
        this.setVal(val);
        return this;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Integer getCode() {
        return this.code;
    }

    public Masters code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeval() {
        return this.codeval;
    }

    public Masters codeval(String codeval) {
        this.setCodeval(codeval);
        return this;
    }

    public void setCodeval(String codeval) {
        this.codeval = codeval;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Masters status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public Masters orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public UserMast getAddedByUser() {
        return this.addedByUser;
    }

    public void setAddedByUser(UserMast userMast) {
        this.addedByUser = userMast;
    }

    public Masters addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(UserMast userMast) {
        this.updatedByUser = userMast;
    }

    public Masters updatedByUser(UserMast userMast) {
        this.setUpdatedByUser(userMast);
        return this;
    }

    public UserMast getApprovedByUser() {
        return this.approvedByUser;
    }

    public void setApprovedByUser(UserMast userMast) {
        this.approvedByUser = userMast;
    }

    public Masters approvedByUser(UserMast userMast) {
        this.setApprovedByUser(userMast);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public Masters orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Masters)) {
            return false;
        }
        return id != null && id.equals(((Masters) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Masters{" +
            "id=" + getId() +
            ", key=" + getKey() +
            ", val='" + getVal() + "'" +
            ", code=" + getCode() +
            ", codeval='" + getCodeval() + "'" +
            ", status=" + getStatus() +
            ", orgId=" + getOrgId() +
            "}";
    }
}
