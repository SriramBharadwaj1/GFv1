package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserActivity;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserActivityDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserActivity} and its DTO {@link UserActivityDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserActivityMapper extends EntityMapper<UserActivityDTO, UserActivity> {
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "userObj", source = "userObj", qualifiedByName = "userMastName")
    @Mapping(target = "addedByUseract", source = "addedByUseract", qualifiedByName = "userMastName")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    @Mapping(target = "useractObj", source = "useractObj", qualifiedByName = "userMastName")
    UserActivityDTO toDto(UserActivity s);

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
