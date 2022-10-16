package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Caste;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Caste entity.
 */
@Repository
public interface CasteRepository extends JpaRepository<Caste, Long> {
    default Optional<Caste> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Caste> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Caste> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct caste from Caste caste left join fetch caste.addedByUser left join fetch caste.updatedByUser left join fetch caste.approvedByObj left join fetch caste.parentIdObj left join fetch caste.orgIdObj",
        countQuery = "select count(distinct caste) from Caste caste"
    )
    Page<Caste> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct caste from Caste caste left join fetch caste.addedByUser left join fetch caste.updatedByUser left join fetch caste.approvedByObj left join fetch caste.parentIdObj left join fetch caste.orgIdObj"
    )
    List<Caste> findAllWithToOneRelationships();

    @Query(
        "select caste from Caste caste left join fetch caste.addedByUser left join fetch caste.updatedByUser left join fetch caste.approvedByObj left join fetch caste.parentIdObj left join fetch caste.orgIdObj where caste.id =:id"
    )
    Optional<Caste> findOneWithToOneRelationships(@Param("id") Long id);
}
