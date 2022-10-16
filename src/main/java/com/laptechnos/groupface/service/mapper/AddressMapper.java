package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Address;
import com.laptechnos.groupface.domain.Location;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.AddressDTO;
import com.laptechnos.groupface.service.dto.LocationDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    @Mapping(target = "approvedByObj", source = "approvedByObj", qualifiedByName = "userMastName")
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "villageObj", source = "villageObj", qualifiedByName = "locationName")
    @Mapping(target = "cityObj", source = "cityObj", qualifiedByName = "locationName")
    @Mapping(target = "districtObj", source = "districtObj", qualifiedByName = "locationName")
    @Mapping(target = "stateObj", source = "stateObj", qualifiedByName = "locationName")
    @Mapping(target = "countryObj", source = "countryObj", qualifiedByName = "locationName")
    AddressDTO toDto(Address s);

    @Named("organisationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganisationDTO toDtoOrganisationName(Organisation organisation);

    @Named("userMastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserMastDTO toDtoUserMastName(UserMast userMast);

    @Named("locationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    LocationDTO toDtoLocationName(Location location);
}
