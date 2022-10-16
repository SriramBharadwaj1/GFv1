package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Caste;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.CasteDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Caste} and its DTO {@link CasteDTO}.
 */
@Mapper(componentModel = "spring")
public interface CasteMapper extends EntityMapper<CasteDTO, Caste> {
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByObj", source = "approvedByObj", qualifiedByName = "userMastName")
    @Mapping(target = "parentIdObj", source = "parentIdObj", qualifiedByName = "casteName")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    CasteDTO toDto(Caste s);

    @Named("userMastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserMastDTO toDtoUserMastName(UserMast userMast);

    @Named("casteName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CasteDTO toDtoCasteName(Caste caste);

    @Named("organisationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganisationDTO toDtoOrganisationName(Organisation organisation);
}
