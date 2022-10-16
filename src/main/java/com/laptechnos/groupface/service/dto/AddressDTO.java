package com.laptechnos.groupface.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.laptechnos.groupface.domain.Address} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressDTO implements Serializable {

    private Long id;

    private String name;

    private Integer addressType;

    private String addressLn1;

    private String addressLn2;

    private String addressLn3;

    private String addressLn4;

    private String landmark;

    private Long village;

    private Long city;

    private Long district;

    private Long state;

    private Integer parentTableId;

    private Integer parentModuleKy;

    private Integer parentTableKy;

    private String pin;

    private Long country;

    private Integer isActive;

    private Integer isEnable;

    private Long addedBy;

    private LocalDate addedOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private Long approvedBy;

    private LocalDate approvedOn;

    private String comments;

    private String remarks;

    private String extraFields;

    private Long zone;

    private Long orgId;

    private Integer hist;

    private String column1;

    private OrganisationDTO orgIdObj;

    private UserMastDTO approvedByObj;

    private UserMastDTO addedByUser;

    private UserMastDTO updatedByUser;

    private LocationDTO villageObj;

    private LocationDTO cityObj;

    private LocationDTO districtObj;

    private LocationDTO stateObj;

    private LocationDTO countryObj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public String getAddressLn1() {
        return addressLn1;
    }

    public void setAddressLn1(String addressLn1) {
        this.addressLn1 = addressLn1;
    }

    public String getAddressLn2() {
        return addressLn2;
    }

    public void setAddressLn2(String addressLn2) {
        this.addressLn2 = addressLn2;
    }

    public String getAddressLn3() {
        return addressLn3;
    }

    public void setAddressLn3(String addressLn3) {
        this.addressLn3 = addressLn3;
    }

    public String getAddressLn4() {
        return addressLn4;
    }

    public void setAddressLn4(String addressLn4) {
        this.addressLn4 = addressLn4;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Long getVillage() {
        return village;
    }

    public void setVillage(Long village) {
        this.village = village;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Integer getParentTableId() {
        return parentTableId;
    }

    public void setParentTableId(Integer parentTableId) {
        this.parentTableId = parentTableId;
    }

    public Integer getParentModuleKy() {
        return parentModuleKy;
    }

    public void setParentModuleKy(Integer parentModuleKy) {
        this.parentModuleKy = parentModuleKy;
    }

    public Integer getParentTableKy() {
        return parentTableKy;
    }

    public void setParentTableKy(Integer parentTableKy) {
        this.parentTableKy = parentTableKy;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(String extraFields) {
        this.extraFields = extraFields;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getHist() {
        return hist;
    }

    public void setHist(Integer hist) {
        this.hist = hist;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public OrganisationDTO getOrgIdObj() {
        return orgIdObj;
    }

    public void setOrgIdObj(OrganisationDTO orgIdObj) {
        this.orgIdObj = orgIdObj;
    }

    public UserMastDTO getApprovedByObj() {
        return approvedByObj;
    }

    public void setApprovedByObj(UserMastDTO approvedByObj) {
        this.approvedByObj = approvedByObj;
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

    public LocationDTO getVillageObj() {
        return villageObj;
    }

    public void setVillageObj(LocationDTO villageObj) {
        this.villageObj = villageObj;
    }

    public LocationDTO getCityObj() {
        return cityObj;
    }

    public void setCityObj(LocationDTO cityObj) {
        this.cityObj = cityObj;
    }

    public LocationDTO getDistrictObj() {
        return districtObj;
    }

    public void setDistrictObj(LocationDTO districtObj) {
        this.districtObj = districtObj;
    }

    public LocationDTO getStateObj() {
        return stateObj;
    }

    public void setStateObj(LocationDTO stateObj) {
        this.stateObj = stateObj;
    }

    public LocationDTO getCountryObj() {
        return countryObj;
    }

    public void setCountryObj(LocationDTO countryObj) {
        this.countryObj = countryObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, addressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", addressType=" + getAddressType() +
            ", addressLn1='" + getAddressLn1() + "'" +
            ", addressLn2='" + getAddressLn2() + "'" +
            ", addressLn3='" + getAddressLn3() + "'" +
            ", addressLn4='" + getAddressLn4() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", village=" + getVillage() +
            ", city=" + getCity() +
            ", district=" + getDistrict() +
            ", state=" + getState() +
            ", parentTableId=" + getParentTableId() +
            ", parentModuleKy=" + getParentModuleKy() +
            ", parentTableKy=" + getParentTableKy() +
            ", pin='" + getPin() + "'" +
            ", country=" + getCountry() +
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
            ", extraFields='" + getExtraFields() + "'" +
            ", zone=" + getZone() +
            ", orgId=" + getOrgId() +
            ", hist=" + getHist() +
            ", column1='" + getColumn1() + "'" +
            ", orgIdObj=" + getOrgIdObj() +
            ", approvedByObj=" + getApprovedByObj() +
            ", addedByUser=" + getAddedByUser() +
            ", updatedByUser=" + getUpdatedByUser() +
            ", villageObj=" + getVillageObj() +
            ", cityObj=" + getCityObj() +
            ", districtObj=" + getDistrictObj() +
            ", stateObj=" + getStateObj() +
            ", countryObj=" + getCountryObj() +
            "}";
    }
}
