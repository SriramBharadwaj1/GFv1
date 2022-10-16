package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    default Optional<Post> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Post> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Post> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct post from Post post left join fetch post.addedByUser left join fetch post.updatedByUser left join fetch post.approvedByUser left join fetch post.orgIdObj",
        countQuery = "select count(distinct post) from Post post"
    )
    Page<Post> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct post from Post post left join fetch post.addedByUser left join fetch post.updatedByUser left join fetch post.approvedByUser left join fetch post.orgIdObj"
    )
    List<Post> findAllWithToOneRelationships();

    @Query(
        "select post from Post post left join fetch post.addedByUser left join fetch post.updatedByUser left join fetch post.approvedByUser left join fetch post.orgIdObj where post.id =:id"
    )
    Optional<Post> findOneWithToOneRelationships(@Param("id") Long id);
}
