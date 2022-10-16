package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.PostVideo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PostVideo entity.
 */
@Repository
public interface PostVideoRepository extends JpaRepository<PostVideo, Long> {
    default Optional<PostVideo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PostVideo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PostVideo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct postVideo from PostVideo postVideo left join fetch postVideo.addedByUser left join fetch postVideo.updatedByUser left join fetch postVideo.approvedByUser left join fetch postVideo.postidObj left join fetch postVideo.orgIdObj",
        countQuery = "select count(distinct postVideo) from PostVideo postVideo"
    )
    Page<PostVideo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct postVideo from PostVideo postVideo left join fetch postVideo.addedByUser left join fetch postVideo.updatedByUser left join fetch postVideo.approvedByUser left join fetch postVideo.postidObj left join fetch postVideo.orgIdObj"
    )
    List<PostVideo> findAllWithToOneRelationships();

    @Query(
        "select postVideo from PostVideo postVideo left join fetch postVideo.addedByUser left join fetch postVideo.updatedByUser left join fetch postVideo.approvedByUser left join fetch postVideo.postidObj left join fetch postVideo.orgIdObj where postVideo.id =:id"
    )
    Optional<PostVideo> findOneWithToOneRelationships(@Param("id") Long id);
}
