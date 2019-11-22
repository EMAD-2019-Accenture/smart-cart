package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Percentuale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Percentuale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PercentualeRepository extends JpaRepository<Percentuale, Long> {

}
