package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.PostPics;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PostPics entity.
 */
@Repository
public interface PostPicsRepository extends JpaRepository<PostPics, Long> {
    default Optional<PostPics> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PostPics> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PostPics> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct postPics from PostPics postPics left join fetch postPics.addedByUser left join fetch postPics.updatedByUser left join fetch postPics.approvedByUser left join fetch postPics.postObj left join fetch postPics.orgIdObj",
        countQuery = "select count(distinct postPics) from PostPics postPics"
    )
    Page<PostPics> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct postPics from PostPics postPics left join fetch postPics.addedByUser left join fetch postPics.updatedByUser left join fetch postPics.approvedByUser left join fetch postPics.postObj left join fetch postPics.orgIdObj"
    )
    List<PostPics> findAllWithToOneRelationships();

    @Query(
        "select postPics from PostPics postPics left join fetch postPics.addedByUser left join fetch postPics.updatedByUser left join fetch postPics.approvedByUser left join fetch postPics.postObj left join fetch postPics.orgIdObj where postPics.id =:id"
    )
    Optional<PostPics> findOneWithToOneRelationships(@Param("id") Long id);
}
