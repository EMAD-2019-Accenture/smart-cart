package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Allergen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Allergen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {

}
