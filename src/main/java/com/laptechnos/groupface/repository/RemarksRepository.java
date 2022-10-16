package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Remarks;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Remarks entity.
 */
@Repository
public interface RemarksRepository extends JpaRepository<Remarks, Long> {
    default Optional<Remarks> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Remarks> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Remarks> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct remarks from Remarks remarks left join fetch remarks.repostIdObj left join fetch remarks.addedByUser left join fetch remarks.updatedByUser left join fetch remarks.approvedByUser left join fetch remarks.orgIdObj",
        countQuery = "select count(distinct remarks) from Remarks remarks"
    )
    Page<Remarks> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct remarks from Remarks remarks left join fetch remarks.repostIdObj left join fetch remarks.addedByUser left join fetch remarks.updatedByUser left join fetch remarks.approvedByUser left join fetch remarks.orgIdObj"
    )
    List<Remarks> findAllWithToOneRelationships();

    @Query(
        "select remarks from Remarks remarks left join fetch remarks.repostIdObj left join fetch remarks.addedByUser left join fetch remarks.updatedByUser left join fetch remarks.approvedByUser left join fetch remarks.orgIdObj where remarks.id =:id"
    )
    Optional<Remarks> findOneWithToOneRelationships(@Param("id") Long id);
}
