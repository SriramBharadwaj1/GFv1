package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.AutoPostAprvlUsrs;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AutoPostAprvlUsrs entity.
 */
@Repository
public interface AutoPostAprvlUsrsRepository extends JpaRepository<AutoPostAprvlUsrs, Long> {
    default Optional<AutoPostAprvlUsrs> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AutoPostAprvlUsrs> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AutoPostAprvlUsrs> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct autoPostAprvlUsrs from AutoPostAprvlUsrs autoPostAprvlUsrs left join fetch autoPostAprvlUsrs.user left join fetch autoPostAprvlUsrs.addedByUser left join fetch autoPostAprvlUsrs.updatedByUser left join fetch autoPostAprvlUsrs.approvedByUser left join fetch autoPostAprvlUsrs.orgIdObj",
        countQuery = "select count(distinct autoPostAprvlUsrs) from AutoPostAprvlUsrs autoPostAprvlUsrs"
    )
    Page<AutoPostAprvlUsrs> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct autoPostAprvlUsrs from AutoPostAprvlUsrs autoPostAprvlUsrs left join fetch autoPostAprvlUsrs.user left join fetch autoPostAprvlUsrs.addedByUser left join fetch autoPostAprvlUsrs.updatedByUser left join fetch autoPostAprvlUsrs.approvedByUser left join fetch autoPostAprvlUsrs.orgIdObj"
    )
    List<AutoPostAprvlUsrs> findAllWithToOneRelationships();

    @Query(
        "select autoPostAprvlUsrs from AutoPostAprvlUsrs autoPostAprvlUsrs left join fetch autoPostAprvlUsrs.user left join fetch autoPostAprvlUsrs.addedByUser left join fetch autoPostAprvlUsrs.updatedByUser left join fetch autoPostAprvlUsrs.approvedByUser left join fetch autoPostAprvlUsrs.orgIdObj where autoPostAprvlUsrs.id =:id"
    )
    Optional<AutoPostAprvlUsrs> findOneWithToOneRelationships(@Param("id") Long id);
}
