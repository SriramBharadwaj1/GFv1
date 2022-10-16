package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.Post;
import com.laptechnos.groupface.domain.PostPics;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.PostDTO;
import com.laptechnos.groupface.service.dto.PostPicsDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostPics} and its DTO {@link PostPicsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostPicsMapper extends EntityMapper<PostPicsDTO, PostPics> {
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByUser", source = "approvedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "postObj", source = "postObj", qualifiedByName = "postMessage")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    PostPicsDTO toDto(PostPics s);

    @Named("userMastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserMastDTO toDtoUserMastName(UserMast userMast);

    @Named("postMessage")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "message", source = "message")
    PostDTO toDtoPostMessage(Post post);

    @Named("organisationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganisationDTO toDtoOrganisationName(Organisation organisation);
}
