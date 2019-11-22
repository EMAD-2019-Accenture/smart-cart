package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Acquirente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Acquirente entity.
 */
@Repository
public interface AcquirenteRepository extends JpaRepository<Acquirente, Long> {

    @Query(value = "select distinct acquirente from Acquirente acquirente left join fetch acquirente.allergenes",
        countQuery = "select count(distinct acquirente) from Acquirente acquirente")
    Page<Acquirente> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct acquirente from Acquirente acquirente left join fetch acquirente.allergenes")
    List<Acquirente> findAllWithEagerRelationships();

    @Query("select acquirente from Acquirente acquirente left join fetch acquirente.allergenes where acquirente.id =:id")
    Optional<Acquirente> findOneWithEagerRelationships(@Param("id") Long id);

}
