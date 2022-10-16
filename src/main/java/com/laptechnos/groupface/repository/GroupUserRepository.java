package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.GroupUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GroupUser entity.
 */
@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    default Optional<GroupUser> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GroupUser> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GroupUser> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct groupUser from GroupUser groupUser left join fetch groupUser.addedByUser left join fetch groupUser.updatedByUser left join fetch groupUser.approvedByUser left join fetch groupUser.groupIdObj left join fetch groupUser.grpUserObj left join fetch groupUser.userTypeObj left join fetch groupUser.orgIdOb",
        countQuery = "select count(distinct groupUser) from GroupUser groupUser"
    )
    Page<GroupUser> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct groupUser from GroupUser groupUser left join fetch groupUser.addedByUser left join fetch groupUser.updatedByUser left join fetch groupUser.approvedByUser left join fetch groupUser.groupIdObj left join fetch groupUser.grpUserObj left join fetch groupUser.userTypeObj left join fetch groupUser.orgIdOb"
    )
    List<GroupUser> findAllWithToOneRelationships();

    @Query(
        "select groupUser from GroupUser groupUser left join fetch groupUser.addedByUser left join fetch groupUser.updatedByUser left join fetch groupUser.approvedByUser left join fetch groupUser.groupIdObj left join fetch groupUser.grpUserObj left join fetch groupUser.userTypeObj left join fetch groupUser.orgIdOb where groupUser.id =:id"
    )
    Optional<GroupUser> findOneWithToOneRelationships(@Param("id") Long id);
}
