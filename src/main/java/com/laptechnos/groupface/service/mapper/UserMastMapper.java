package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserMast} and its DTO {@link UserMastDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserMastMapper extends EntityMapper<UserMastDTO, UserMast> {
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    UserMastDTO toDto(UserMast s);

    @Named("organisationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganisationDTO toDtoOrganisationName(Organisation organisation);
}
