package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Vestiario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vestiario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VestiarioRepository extends JpaRepository<Vestiario, Long> {

}
