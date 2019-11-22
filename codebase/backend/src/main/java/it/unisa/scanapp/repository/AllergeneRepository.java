package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.Allergene;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Allergene entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergeneRepository extends JpaRepository<Allergene, Long> {

}
