package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserActivity.
 */
@Entity
@Table(name = "user_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "useractivity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "loggedon")
    private LocalDate loggedon;

    @Column(name = "activeduration")
    private Double activeduration;

    @Column(name = "ip_adr")
    private String ipAdr;

    @Column(name = "usr_location")
    private String usrLocation;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "longi")
    private Float longi;

    @Column(name = "pin")
    private String pin;

    @Column(name = "added_by")
    private Long addedBy;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "added_on")
    private LocalDate addedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast addedByUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast userObj;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast addedByUseract;

    @ManyToOne
    private Organisation orgIdObj;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast useractObj;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserActivity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public UserActivity userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getLoggedon() {
        return this.loggedon;
    }

    public UserActivity loggedon(LocalDate loggedon) {
        this.setLoggedon(loggedon);
        return this;
    }

    public void setLoggedon(LocalDate loggedon) {
        this.loggedon = loggedon;
    }

    public Double getActiveduration() {
        return this.activeduration;
    }

    public UserActivity activeduration(Double activeduration) {
        this.setActiveduration(activeduration);
        return this;
    }

    public void setActiveduration(Double activeduration) {
        this.activeduration = activeduration;
    }

    public String getIpAdr() {
        return this.ipAdr;
    }

    public UserActivity ipAdr(String ipAdr) {
        this.setIpAdr(ipAdr);
        return this;
    }

    public void setIpAdr(String ipAdr) {
        this.ipAdr = ipAdr;
    }

    public String getUsrLocation() {
        return this.usrLocation;
    }

    public UserActivity usrLocation(String usrLocation) {
        this.setUsrLocation(usrLocation);
        return this;
    }

    public void setUsrLocation(String usrLocation) {
        this.usrLocation = usrLocation;
    }

    public Float getLat() {
        return this.lat;
    }

    public UserActivity lat(Float lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLongi() {
        return this.longi;
    }

    public UserActivity longi(Float longi) {
        this.setLongi(longi);
        return this;
    }

    public void setLongi(Float longi) {
        this.longi = longi;
    }

    public String getPin() {
        return this.pin;
    }

    public UserActivity pin(String pin) {
        this.setPin(pin);
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public UserActivity addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public UserActivity orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public UserActivity addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public UserMast getAddedByUser() {
        return this.addedByUser;
    }

    public void setAddedByUser(UserMast userMast) {
        this.addedByUser = userMast;
    }

    public UserActivity addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUserObj() {
        return this.userObj;
    }

    public void setUserObj(UserMast userMast) {
        this.userObj = userMast;
    }

    public UserActivity userObj(UserMast userMast) {
        this.setUserObj(userMast);
        return this;
    }

    public UserMast getAddedByUseract() {
        return this.addedByUseract;
    }

    public void setAddedByUseract(UserMast userMast) {
        this.addedByUseract = userMast;
    }

    public UserActivity addedByUseract(UserMast userMast) {
        this.setAddedByUseract(userMast);
        return this;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public UserActivity orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    public UserMast getUseractObj() {
        return this.useractObj;
    }

    public void setUseractObj(UserMast userMast) {
        this.useractObj = userMast;
    }

    public UserActivity useractObj(UserMast userMast) {
        this.setUseractObj(userMast);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivity)) {
            return false;
        }
        return id != null && id.equals(((UserActivity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivity{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", loggedon='" + getLoggedon() + "'" +
            ", activeduration=" + getActiveduration() +
            ", ipAdr='" + getIpAdr() + "'" +
            ", usrLocation='" + getUsrLocation() + "'" +
            ", lat=" + getLat() +
            ", longi=" + getLongi() +
            ", pin='" + getPin() + "'" +
            ", addedBy=" + getAddedBy() +
            ", orgId=" + getOrgId() +
            ", addedOn='" + getAddedOn() + "'" +
            "}";
    }
}
