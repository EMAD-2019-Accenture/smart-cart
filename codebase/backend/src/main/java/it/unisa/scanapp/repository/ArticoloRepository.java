package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Articolo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Articolo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticoloRepository extends JpaRepository<Articolo, Long> {

}
