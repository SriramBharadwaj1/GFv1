package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Events;
import com.laptechnos.groupface.domain.Masters;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.service.dto.EventsDTO;
import com.laptechnos.groupface.service.dto.MastersDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Events} and its DTO {@link EventsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventsMapper extends EntityMapper<EventsDTO, Events> {
    @Mapping(target = "addedByUsr", source = "addedByUsr", qualifiedByName = "userMastName")
    @Mapping(target = "updatedByUsr", source = "updatedByUsr", qualifiedByName = "userMastName")
    @Mapping(target = "approvedByUsr", source = "approvedByUsr", qualifiedByName = "userMastName")
    @Mapping(target = "userIdUsr", source = "userIdUsr", qualifiedByName = "userMastName")
    @Mapping(target = "eventTypeObj", source = "eventTypeObj", qualifiedByName = "mastersCodeval")
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    EventsDTO toDto(Events s);

    @Named("userMastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserMastDTO toDtoUserMastName(UserMast userMast);

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
