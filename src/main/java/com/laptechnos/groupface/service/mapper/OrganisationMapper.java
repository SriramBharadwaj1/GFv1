package com.laptechnos.groupface.service.mapper;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organisation} and its DTO {@link OrganisationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganisationMapper extends EntityMapper<OrganisationDTO, Organisation> {}
