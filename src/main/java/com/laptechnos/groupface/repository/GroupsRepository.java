package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Groups;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Groups entity.
 */
@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {
    default Optional<Groups> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Groups> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Groups> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct groups from Groups groups left join fetch groups.addedByUser left join fetch groups.updatedByUser left join fetch groups.approvedByser left join fetch groups.orgIdObj",
        countQuery = "select count(distinct groups) from Groups groups"
    )
    Page<Groups> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct groups from Groups groups left join fetch groups.addedByUser left join fetch groups.updatedByUser left join fetch groups.approvedByser left join fetch groups.orgIdObj"
    )
    List<Groups> findAllWithToOneRelationships();

    @Query(
        "select groups from Groups groups left join fetch groups.addedByUser left join fetch groups.updatedByUser left join fetch groups.approvedByser left join fetch groups.orgIdObj where groups.id =:id"
    )
    Optional<Groups> findOneWithToOneRelationships(@Param("id") Long id);
}
