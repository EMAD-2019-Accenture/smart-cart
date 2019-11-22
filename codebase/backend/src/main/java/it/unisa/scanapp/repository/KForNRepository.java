package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.KForN;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the KForN entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KForNRepository extends JpaRepository<KForN, Long> {

}
