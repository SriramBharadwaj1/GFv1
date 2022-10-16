package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Friends;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Friends entity.
 */
@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    default Optional<Friends> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Friends> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Friends> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct friends from Friends friends left join fetch friends.addedByUser left join fetch friends.updatedByUser left join fetch friends.approvedByUser left join fetch friends.userIdObj left join fetch friends.friendIdObj left join fetch friends.orgIdObj",
        countQuery = "select count(distinct friends) from Friends friends"
    )
    Page<Friends> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct friends from Friends friends left join fetch friends.addedByUser left join fetch friends.updatedByUser left join fetch friends.approvedByUser left join fetch friends.userIdObj left join fetch friends.friendIdObj left join fetch friends.orgIdObj"
    )
    List<Friends> findAllWithToOneRelationships();

    @Query(
        "select friends from Friends friends left join fetch friends.addedByUser left join fetch friends.updatedByUser left join fetch friends.approvedByUser left join fetch friends.userIdObj left join fetch friends.friendIdObj left join fetch friends.orgIdObj where friends.id =:id"
    )
    Optional<Friends> findOneWithToOneRelationships(@Param("id") Long id);
}
