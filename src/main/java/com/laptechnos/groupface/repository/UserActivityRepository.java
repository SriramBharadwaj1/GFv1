package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.UserActivity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserActivity entity.
 */
@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    default Optional<UserActivity> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UserActivity> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UserActivity> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct userActivity from UserActivity userActivity left join fetch userActivity.addedByUser left join fetch userActivity.userObj left join fetch userActivity.addedByUseract left join fetch userActivity.orgIdObj left join fetch userActivity.useractObj",
        countQuery = "select count(distinct userActivity) from UserActivity userActivity"
    )
    Page<UserActivity> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct userActivity from UserActivity userActivity left join fetch userActivity.addedByUser left join fetch userActivity.userObj left join fetch userActivity.addedByUseract left join fetch userActivity.orgIdObj left join fetch userActivity.useractObj"
    )
    List<UserActivity> findAllWithToOneRelationships();

    @Query(
        "select userActivity from UserActivity userActivity left join fetch userActivity.addedByUser left join fetch userActivity.userObj left join fetch userActivity.addedByUseract left join fetch userActivity.orgIdObj left join fetch userActivity.useractObj where userActivity.id =:id"
    )
    Optional<UserActivity> findOneWithToOneRelationships(@Param("id") Long id);
}
