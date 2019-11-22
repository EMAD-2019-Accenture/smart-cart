package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Farmacologico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Farmacologico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmacologicoRepository extends JpaRepository<Farmacologico, Long> {

}
