package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Organisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {}
