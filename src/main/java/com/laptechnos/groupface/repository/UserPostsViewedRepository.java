package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.UserPostsViewed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserPostsViewed entity.
 */
@Repository
public interface UserPostsViewedRepository extends JpaRepository<UserPostsViewed, Long> {
    default Optional<UserPostsViewed> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UserPostsViewed> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UserPostsViewed> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct userPostsViewed from UserPostsViewed userPostsViewed left join fetch userPostsViewed.userIdObj left join fetch userPostsViewed.orgIdObj",
        countQuery = "select count(distinct userPostsViewed) from UserPostsViewed userPostsViewed"
    )
    Page<UserPostsViewed> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct userPostsViewed from UserPostsViewed userPostsViewed left join fetch userPostsViewed.userIdObj left join fetch userPostsViewed.orgIdObj"
    )
    List<UserPostsViewed> findAllWithToOneRelationships();

    @Query(
        "select userPostsViewed from UserPostsViewed userPostsViewed left join fetch userPostsViewed.userIdObj left join fetch userPostsViewed.orgIdObj where userPostsViewed.id =:id"
    )
    Optional<UserPostsViewed> findOneWithToOneRelationships(@Param("id") Long id);
}
