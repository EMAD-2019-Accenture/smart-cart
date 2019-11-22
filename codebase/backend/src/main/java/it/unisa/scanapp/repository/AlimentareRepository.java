package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Alimentare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Alimentare entity.
 */
@Repository
public interface AlimentareRepository extends JpaRepository<Alimentare, Long> {

    @Query(value = "select distinct alimentare from Alimentare alimentare left join fetch alimentare.allergenes",
        countQuery = "select count(distinct alimentare) from Alimentare alimentare")
    Page<Alimentare> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct alimentare from Alimentare alimentare left join fetch alimentare.allergenes")
    List<Alimentare> findAllWithEagerRelationships();

    @Query("select alimentare from Alimentare alimentare left join fetch alimentare.allergenes where alimentare.id =:id")
    Optional<Alimentare> findOneWithEagerRelationships(@Param("id") Long id);

}
