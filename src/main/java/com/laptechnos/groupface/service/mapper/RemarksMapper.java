package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.Post;
import com.laptechnos.groupface.domain.Remarks;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.PostDTO;
import com.laptechnos.groupface.service.dto.RemarksDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Remarks} and its DTO {@link RemarksDTO}.
 */
@Mapper(componentModel = "spring")
public interface RemarksMapper extends EntityMapper<RemarksDTO, Remarks> {
    @Mapping(target = "repostIdObj", source = "repostIdObj", qualifiedByName = "postMessage")
    @Mapping(target = "addedByUser", source = "addedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUser", source = "updatedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByUser", source = "approvedByUser", qualifiedByName = "userMastName")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    RemarksDTO toDto(Remarks s);

    @Named("postMessage")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "message", source = "message")
    PostDTO toDtoPostMessage(Post post);

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
