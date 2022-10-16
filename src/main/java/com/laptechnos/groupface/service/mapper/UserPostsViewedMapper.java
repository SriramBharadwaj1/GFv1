package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.domain.UserPostsViewed;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import com.laptechnos.groupface.service.dto.UserPostsViewedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserPostsViewed} and its DTO {@link UserPostsViewedDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserPostsViewedMapper extends EntityMapper<UserPostsViewedDTO, UserPostsViewed> {
    @Mapping(target = "userIdObj", source = "userIdObj", qualifiedByName = "userMastName")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    UserPostsViewedDTO toDto(UserPostsViewed s);

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
