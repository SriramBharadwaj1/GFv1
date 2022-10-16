package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.UserActivity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserActivityDTO implements Serializable {

    private Long id;

    private Long userId;

    private LocalDate loggedon;

    private Double activeduration;

    private String ipAdr;

    private String usrLocation;

    private Float lat;

    private Float longi;

    private String pin;

    private Long addedBy;

    private Long orgId;

    private LocalDate addedOn;

    private UserMastDTO addedByUser;

    private UserMastDTO userObj;

    private UserMastDTO addedByUseract;

    private OrganisationDTO orgIdObj;

    private UserMastDTO useractObj;

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

    public LocalDate getLoggedon() {
        return loggedon;
    }

    public void setLoggedon(LocalDate loggedon) {
        this.loggedon = loggedon;
    }

    public Double getActiveduration() {
        return activeduration;
    }

    public void setActiveduration(Double activeduration) {
        this.activeduration = activeduration;
    }

    public String getIpAdr() {
        return ipAdr;
    }

    public void setIpAdr(String ipAdr) {
        this.ipAdr = ipAdr;
    }

    public String getUsrLocation() {
        return usrLocation;
    }

    public void setUsrLocation(String usrLocation) {
        this.usrLocation = usrLocation;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLongi() {
        return longi;
    }

    public void setLongi(Float longi) {
        this.longi = longi;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public UserMastDTO getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(UserMastDTO addedByUser) {
        this.addedByUser = addedByUser;
    }

    public UserMastDTO getUserObj() {
        return userObj;
    }

    public void setUserObj(UserMastDTO userObj) {
        this.userObj = userObj;
    }

    public UserMastDTO getAddedByUseract() {
        return addedByUseract;
    }

    public void setAddedByUseract(UserMastDTO addedByUseract) {
        this.addedByUseract = addedByUseract;
    }

    public OrganisationDTO getOrgIdObj() {
        return orgIdObj;
    }

    public void setOrgIdObj(OrganisationDTO orgIdObj) {
        this.orgIdObj = orgIdObj;
    }

    public UserMastDTO getUseractObj() {
        return useractObj;
    }

    public void setUseractObj(UserMastDTO useractObj) {
        this.useractObj = useractObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivityDTO)) {
            return false;
        }

        UserActivityDTO userActivityDTO = (UserActivityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userActivityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivityDTO{" +
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
            ", addedByUser=" + getAddedByUser() +
            ", userObj=" + getUserObj() +
            ", addedByUseract=" + getAddedByUseract() +
            ", orgIdObj=" + getOrgIdObj() +
            ", useractObj=" + getUseractObj() +
            "}";
    }
}
