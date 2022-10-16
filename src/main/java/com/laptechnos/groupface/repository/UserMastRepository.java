package com.laptechnos.groupface.repository;

import com.laptechnos.groupface.domain.UserMast;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserMast entity.
 */
@Repository
public interface UserMastRepository extends JpaRepository<UserMast, Long> {
    default Optional<UserMast> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UserMast> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UserMast> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct userMast from UserMast userMast left join fetch userMast.orgIdObj",
        countQuery = "select count(distinct userMast) from UserMast userMast"
    )
    Page<UserMast> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct userMast from UserMast userMast left join fetch userMast.orgIdObj")
    List<UserMast> findAllWithToOneRelationships();

    @Query("select userMast from UserMast userMast left join fetch userMast.orgIdObj where userMast.id =:id")
    Optional<UserMast> findOneWithToOneRelationships(@Param("id") Long id);
}
