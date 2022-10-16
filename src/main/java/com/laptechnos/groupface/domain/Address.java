package com.laptechnos.groupface.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "address")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address_type")
    private Integer addressType;

    @Column(name = "address_ln_1")
    private String addressLn1;

    @Column(name = "address_ln_2")
    private String addressLn2;

    @Column(name = "address_ln_3")
    private String addressLn3;

    @Column(name = "address_ln_4")
    private String addressLn4;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "village")
    private Long village;

    @Column(name = "city")
    private Long city;

    @Column(name = "district")
    private Long district;

    @Column(name = "state")
    private Long state;

    @Column(name = "parent_table_id")
    private Integer parentTableId;

    @Column(name = "parent_module_ky")
    private Integer parentModuleKy;

    @Column(name = "parent_table_ky")
    private Integer parentTableKy;

    @Column(name = "pin")
    private String pin;

    @Column(name = "country")
    private Long country;

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

    @Column(name = "extra_fields")
    private String extraFields;

    @Column(name = "zone")
    private Long zone;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "hist")
    private Integer hist;

    @Column(name = "column_1")
    private String column1;

    @ManyToOne
    private Organisation orgIdObj;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast approvedByObj;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast addedByUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orgIdObj" }, allowSetters = true)
    private UserMast updatedByUser;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByUser", "moderator1IdUser", "moderator2IdUser", "parentIdObj", "orgIdObj" },
        allowSetters = true
    )
    private Location villageObj;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByUser", "moderator1IdUser", "moderator2IdUser", "parentIdObj", "orgIdObj" },
        allowSetters = true
    )
    private Location cityObj;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByUser", "moderator1IdUser", "moderator2IdUser", "parentIdObj", "orgIdObj" },
        allowSetters = true
    )
    private Location districtObj;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByUser", "moderator1IdUser", "moderator2IdUser", "parentIdObj", "orgIdObj" },
        allowSetters = true
    )
    private Location stateObj;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "addedByUser", "updatedByUser", "approvedByUser", "moderator1IdUser", "moderator2IdUser", "parentIdObj", "orgIdObj" },
        allowSetters = true
    )
    private Location countryObj;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Address name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAddressType() {
        return this.addressType;
    }

    public Address addressType(Integer addressType) {
        this.setAddressType(addressType);
        return this;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public String getAddressLn1() {
        return this.addressLn1;
    }

    public Address addressLn1(String addressLn1) {
        this.setAddressLn1(addressLn1);
        return this;
    }

    public void setAddressLn1(String addressLn1) {
        this.addressLn1 = addressLn1;
    }

    public String getAddressLn2() {
        return this.addressLn2;
    }

    public Address addressLn2(String addressLn2) {
        this.setAddressLn2(addressLn2);
        return this;
    }

    public void setAddressLn2(String addressLn2) {
        this.addressLn2 = addressLn2;
    }

    public String getAddressLn3() {
        return this.addressLn3;
    }

    public Address addressLn3(String addressLn3) {
        this.setAddressLn3(addressLn3);
        return this;
    }

    public void setAddressLn3(String addressLn3) {
        this.addressLn3 = addressLn3;
    }

    public String getAddressLn4() {
        return this.addressLn4;
    }

    public Address addressLn4(String addressLn4) {
        this.setAddressLn4(addressLn4);
        return this;
    }

    public void setAddressLn4(String addressLn4) {
        this.addressLn4 = addressLn4;
    }

    public String getLandmark() {
        return this.landmark;
    }

    public Address landmark(String landmark) {
        this.setLandmark(landmark);
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Long getVillage() {
        return this.village;
    }

    public Address village(Long village) {
        this.setVillage(village);
        return this;
    }

    public void setVillage(Long village) {
        this.village = village;
    }

    public Long getCity() {
        return this.city;
    }

    public Address city(Long city) {
        this.setCity(city);
        return this;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDistrict() {
        return this.district;
    }

    public Address district(Long district) {
        this.setDistrict(district);
        return this;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public Long getState() {
        return this.state;
    }

    public Address state(Long state) {
        this.setState(state);
        return this;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Integer getParentTableId() {
        return this.parentTableId;
    }

    public Address parentTableId(Integer parentTableId) {
        this.setParentTableId(parentTableId);
        return this;
    }

    public void setParentTableId(Integer parentTableId) {
        this.parentTableId = parentTableId;
    }

    public Integer getParentModuleKy() {
        return this.parentModuleKy;
    }

    public Address parentModuleKy(Integer parentModuleKy) {
        this.setParentModuleKy(parentModuleKy);
        return this;
    }

    public void setParentModuleKy(Integer parentModuleKy) {
        this.parentModuleKy = parentModuleKy;
    }

    public Integer getParentTableKy() {
        return this.parentTableKy;
    }

    public Address parentTableKy(Integer parentTableKy) {
        this.setParentTableKy(parentTableKy);
        return this;
    }

    public void setParentTableKy(Integer parentTableKy) {
        this.parentTableKy = parentTableKy;
    }

    public String getPin() {
        return this.pin;
    }

    public Address pin(String pin) {
        this.setPin(pin);
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Long getCountry() {
        return this.country;
    }

    public Address country(Long country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public Address isActive(Integer isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public Address isEnable(Integer isEnable) {
        this.setIsEnable(isEnable);
        return this;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddedBy() {
        return this.addedBy;
    }

    public Address addedBy(Long addedBy) {
        this.setAddedBy(addedBy);
        return this;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getAddedOn() {
        return this.addedOn;
    }

    public Address addedOn(LocalDate addedOn) {
        this.setAddedOn(addedOn);
        return this;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Address updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Address updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getApprovedBy() {
        return this.approvedBy;
    }

    public Address approvedBy(Long approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedOn() {
        return this.approvedOn;
    }

    public Address approvedOn(LocalDate approvedOn) {
        this.setApprovedOn(approvedOn);
        return this;
    }

    public void setApprovedOn(LocalDate approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getComments() {
        return this.comments;
    }

    public Address comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Address remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExtraFields() {
        return this.extraFields;
    }

    public Address extraFields(String extraFields) {
        this.setExtraFields(extraFields);
        return this;
    }

    public void setExtraFields(String extraFields) {
        this.extraFields = extraFields;
    }

    public Long getZone() {
        return this.zone;
    }

    public Address zone(Long zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public Address orgId(Long orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getHist() {
        return this.hist;
    }

    public Address hist(Integer hist) {
        this.setHist(hist);
        return this;
    }

    public void setHist(Integer hist) {
        this.hist = hist;
    }

    public String getColumn1() {
        return this.column1;
    }

    public Address column1(String column1) {
        this.setColumn1(column1);
        return this;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public Organisation getOrgIdObj() {
        return this.orgIdObj;
    }

    public void setOrgIdObj(Organisation organisation) {
        this.orgIdObj = organisation;
    }

    public Address orgIdObj(Organisation organisation) {
        this.setOrgIdObj(organisation);
        return this;
    }

    public UserMast getApprovedByObj() {
        return this.approvedByObj;
    }

    public void setApprovedByObj(UserMast userMast) {
        this.approvedByObj = userMast;
    }

    public Address approvedByObj(UserMast userMast) {
        this.setApprovedByObj(userMast);
        return this;
    }

    public UserMast getAddedByUser() {
        return this.addedByUser;
    }

    public void setAddedByUser(UserMast userMast) {
        this.addedByUser = userMast;
    }

    public Address addedByUser(UserMast userMast) {
        this.setAddedByUser(userMast);
        return this;
    }

    public UserMast getUpdatedByUser() {
        return this.updatedByUser;
    }

    public void setUpdatedByUser(UserMast userMast) {
        this.updatedByUser = userMast;
    }

    public Address updatedByUser(UserMast userMast) {
        this.setUpdatedByUser(userMast);
        return this;
    }

    public Location getVillageObj() {
        return this.villageObj;
    }

    public void setVillageObj(Location location) {
        this.villageObj = location;
    }

    public Address villageObj(Location location) {
        this.setVillageObj(location);
        return this;
    }

    public Location getCityObj() {
        return this.cityObj;
    }

    public void setCityObj(Location location) {
        this.cityObj = location;
    }

    public Address cityObj(Location location) {
        this.setCityObj(location);
        return this;
    }

    public Location getDistrictObj() {
        return this.districtObj;
    }

    public void setDistrictObj(Location location) {
        this.districtObj = location;
    }

    public Address districtObj(Location location) {
        this.setDistrictObj(location);
        return this;
    }

    public Location getStateObj() {
        return this.stateObj;
    }

    public void setStateObj(Location location) {
        this.stateObj = location;
    }

    public Address stateObj(Location location) {
        this.setStateObj(location);
        return this;
    }

    public Location getCountryObj() {
        return this.countryObj;
    }

    public void setCountryObj(Location location) {
        this.countryObj = location;
    }

    public Address countryObj(Location location) {
        this.setCountryObj(location);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
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
            "}";
    }
}
