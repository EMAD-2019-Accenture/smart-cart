package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Sconto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sconto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScontoRepository extends JpaRepository<Sconto, Long> {

}
