package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.Post;
import com.laptechnos.groupface.domain.PostVideo;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.PostDTO;
import com.laptechnos.groupface.service.dto.PostVideoDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostVideo} and its DTO {@link PostVideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostVideoMapper extends EntityMapper<PostVideoDTO, PostVideo> {
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByUser", source = "approvedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "postidObj", source = "postidObj", qualifiedByName = "postMessage")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    PostVideoDTO toDto(PostVideo s);

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
