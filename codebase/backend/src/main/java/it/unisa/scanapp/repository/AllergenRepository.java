package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Allergen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Allergen entity.
 */
@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {

    @Query(value = "select distinct allergen from Allergen allergen left join fetch allergen.users",
        countQuery = "select count(distinct allergen) from Allergen allergen")
    Page<Allergen> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct allergen from Allergen allergen left join fetch allergen.users")
    List<Allergen> findAllWithEagerRelationships();

    @Query("select allergen from Allergen allergen left join fetch allergen.users where allergen.id =:id")
    Optional<Allergen> findOneWithEagerRelationships(@Param("id") Long id);

}
