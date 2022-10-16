package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.GroupUser;
import com.laptechnos.groupface.domain.Groups;
import com.laptechnos.groupface.domain.Masters;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.GroupUserDTO;
import com.laptechnos.groupface.service.dto.GroupsDTO;
import com.laptechnos.groupface.service.dto.MastersDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupUser} and its DTO {@link GroupUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface GroupUserMapper extends EntityMapper<GroupUserDTO, GroupUser> {
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByUser", source = "approvedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "groupIdObj", source = "groupIdObj", qualifiedByName = "groupsName")
    @Mapping(target = "grpUserObj", source = "grpUserObj", qualifiedByName = "userMastName")
    @Mapping(target = "userTypeObj", source = "userTypeObj", qualifiedByName = "mastersCodeval")
    @Mapping(target = "orgIdOb", source = "orgIdOb", qualifiedByName = "organisationName")
    GroupUserDTO toDto(GroupUser s);

    @Named("userMastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserMastDTO toDtoUserMastName(UserMast userMast);

    @Named("groupsName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GroupsDTO toDtoGroupsName(Groups groups);

    @Named("mastersCodeval")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codeval", source = "codeval")
    MastersDTO toDtoMastersCodeval(Masters masters);

    @Named("organisationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganisationDTO toDtoOrganisationName(Organisation organisation);
}
