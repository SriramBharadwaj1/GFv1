package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.Events;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Events entity.
 */
@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
    default Optional<Events> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Events> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Events> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct events from Events events left join fetch events.addedByUsr left join fetch events.updatedByUsr left join fetch events.approvedByUsr left join fetch events.userIdUsr left join fetch events.eventTypeObj left join fetch events.orgIdObj",
        countQuery = "select count(distinct events) from Events events"
    )
    Page<Events> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct events from Events events left join fetch events.addedByUsr left join fetch events.updatedByUsr left join fetch events.approvedByUsr left join fetch events.userIdUsr left join fetch events.eventTypeObj left join fetch events.orgIdObj"
    )
    List<Events> findAllWithToOneRelationships();

    @Query(
        "select events from Events events left join fetch events.addedByUsr left join fetch events.updatedByUsr left join fetch events.approvedByUsr left join fetch events.userIdUsr left join fetch events.eventTypeObj left join fetch events.orgIdObj where events.id =:id"
    )
    Optional<Events> findOneWithToOneRelationships(@Param("id") Long id);
}
