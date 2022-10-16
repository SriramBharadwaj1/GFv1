package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Groups;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.GroupsDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Groups} and its DTO {@link GroupsDTO}.
 */
@Mapper(componentModel = "spring")
public interface GroupsMapper extends EntityMapper<GroupsDTO, Groups> {
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByser", source = "approvedByser", qualifiedByName = "userMastName")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    GroupsDTO toDto(Groups s);

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
