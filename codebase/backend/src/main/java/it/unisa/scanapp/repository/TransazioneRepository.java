package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Transazione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transazione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Long> {

}
