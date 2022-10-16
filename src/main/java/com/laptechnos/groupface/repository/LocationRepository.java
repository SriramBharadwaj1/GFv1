package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Location entity.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    default Optional<Location> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Location> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Location> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct location from Location location left join fetch location.addedByUser left join fetch location.updatedByUser left join fetch location.approvedByUser left join fetch location.moderator1IdUser left join fetch location.moderator2IdUser left join fetch location.parentIdObj left join fetch location.orgIdObj",
        countQuery = "select count(distinct location) from Location location"
    )
    Page<Location> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct location from Location location left join fetch location.addedByUser left join fetch location.updatedByUser left join fetch location.approvedByUser left join fetch location.moderator1IdUser left join fetch location.moderator2IdUser left join fetch location.parentIdObj left join fetch location.orgIdObj"
    )
    List<Location> findAllWithToOneRelationships();

    @Query(
        "select location from Location location left join fetch location.addedByUser left join fetch location.updatedByUser left join fetch location.approvedByUser left join fetch location.moderator1IdUser left join fetch location.moderator2IdUser left join fetch location.parentIdObj left join fetch location.orgIdObj where location.id =:id"
    )
    Optional<Location> findOneWithToOneRelationships(@Param("id") Long id);
}
