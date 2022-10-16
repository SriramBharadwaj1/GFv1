package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.OrgDetails;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.service.dto.OrgDetailsDTO;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrgDetails} and its DTO {@link OrgDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrgDetailsMapper extends EntityMapper<OrgDetailsDTO, OrgDetails> {
    @Mapping(target = "orgIdObj", source = "orgIdObj", qualifiedByName = "organisationName")
    OrgDetailsDTO toDto(OrgDetails s);

    @Named("organisationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganisationDTO toDtoOrganisationName(Organisation organisation);
}
