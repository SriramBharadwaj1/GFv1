package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.AutoPostAprvlUsrs;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.AutoPostAprvlUsrsDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutoPostAprvlUsrs} and its DTO {@link AutoPostAprvlUsrsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AutoPostAprvlUsrsMapper extends EntityMapper<AutoPostAprvlUsrsDTO, AutoPostAprvlUsrs> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userMastName")
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByUser", source = "approvedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    AutoPostAprvlUsrsDTO toDto(AutoPostAprvlUsrs s);

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
