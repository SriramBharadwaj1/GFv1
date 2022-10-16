package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Masters;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Masters entity.
 */
@Repository
public interface MastersRepository extends JpaRepository<Masters, Long> {
    default Optional<Masters> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Masters> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Masters> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct masters from Masters masters left join fetch masters.addedByUser left join fetch masters.updatedByUser left join fetch masters.approvedByUser left join fetch masters.orgIdObj",
        countQuery = "select count(distinct masters) from Masters masters"
    )
    Page<Masters> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct masters from Masters masters left join fetch masters.addedByUser left join fetch masters.updatedByUser left join fetch masters.approvedByUser left join fetch masters.orgIdObj"
    )
    List<Masters> findAllWithToOneRelationships();

    @Query(
        "select masters from Masters masters left join fetch masters.addedByUser left join fetch masters.updatedByUser left join fetch masters.approvedByUser left join fetch masters.orgIdObj where masters.id =:id"
    )
    Optional<Masters> findOneWithToOneRelationships(@Param("id") Long id);
}
