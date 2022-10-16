package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Masters;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.MastersDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Masters} and its DTO {@link MastersDTO}.
 */
@Mapper(componentModel = "spring")
public interface MastersMapper extends EntityMapper<MastersDTO, Masters> {
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByUser", source = "approvedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    MastersDTO toDto(Masters s);

    @Named("userMastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserMastDTO toDtoUserMastName(UserMast userMast);

    @Named("organisationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganisationDTO toDtoOrganisationName(Organisation organisation);
}
