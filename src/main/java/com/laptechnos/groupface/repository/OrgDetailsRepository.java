package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.OrgDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrgDetails entity.
 */
@Repository
public interface OrgDetailsRepository extends JpaRepository<OrgDetails, Long> {
    default Optional<OrgDetails> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OrgDetails> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OrgDetails> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct orgDetails from OrgDetails orgDetails left join fetch orgDetails.orgIdObj",
        countQuery = "select count(distinct orgDetails) from OrgDetails orgDetails"
    )
    Page<OrgDetails> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct orgDetails from OrgDetails orgDetails left join fetch orgDetails.orgIdObj")
    List<OrgDetails> findAllWithToOneRelationships();

    @Query("select orgDetails from OrgDetails orgDetails left join fetch orgDetails.orgIdObj where orgDetails.id =:id")
    Optional<OrgDetails> findOneWithToOneRelationships(@Param("id") Long id);
}
