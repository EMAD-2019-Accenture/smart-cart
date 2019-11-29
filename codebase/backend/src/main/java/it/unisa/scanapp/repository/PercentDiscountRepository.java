package it.unisa.scanapp.repository;
import it.unisa.scanapp.domain.PercentDiscount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PercentDiscount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PercentDiscountRepository extends JpaRepository<PercentDiscount, Long> {

}
