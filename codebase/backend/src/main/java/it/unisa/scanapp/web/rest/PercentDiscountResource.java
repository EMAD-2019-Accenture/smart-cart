package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.PercentDiscount;
import it.unisa.scanapp.repository.PercentDiscountRepository;
import it.unisa.scanapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.unisa.scanapp.domain.PercentDiscount}.
 */
@RestController
@RequestMapping("/api")
public class PercentDiscountResource {

    private final Logger log = LoggerFactory.getLogger(PercentDiscountResource.class);

    private static final String ENTITY_NAME = "percentDiscount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PercentDiscountRepository percentDiscountRepository;

    public PercentDiscountResource(PercentDiscountRepository percentDiscountRepository) {
        this.percentDiscountRepository = percentDiscountRepository;
    }

    /**
     * {@code POST  /percent-discounts} : Create a new percentDiscount.
     *
     * @param percentDiscount the percentDiscount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new percentDiscount, or with status {@code 400 (Bad Request)} if the percentDiscount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/percent-discounts")
    public ResponseEntity<PercentDiscount> createPercentDiscount(@Valid @RequestBody PercentDiscount percentDiscount) throws URISyntaxException {
        log.debug("REST request to save PercentDiscount : {}", percentDiscount);
        if (percentDiscount.getId() != null) {
            throw new BadRequestAlertException("A new percentDiscount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PercentDiscount result = percentDiscountRepository.save(percentDiscount);
        return ResponseEntity.created(new URI("/api/percent-discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /percent-discounts} : Updates an existing percentDiscount.
     *
     * @param percentDiscount the percentDiscount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated percentDiscount,
     * or with status {@code 400 (Bad Request)} if the percentDiscount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the percentDiscount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/percent-discounts")
    public ResponseEntity<PercentDiscount> updatePercentDiscount(@Valid @RequestBody PercentDiscount percentDiscount) throws URISyntaxException {
        log.debug("REST request to update PercentDiscount : {}", percentDiscount);
        if (percentDiscount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PercentDiscount result = percentDiscountRepository.save(percentDiscount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, percentDiscount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /percent-discounts} : get all the percentDiscounts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of percentDiscounts in body.
     */
    @GetMapping("/percent-discounts")
    public List<PercentDiscount> getAllPercentDiscounts() {
        log.debug("REST request to get all PercentDiscounts");
        return percentDiscountRepository.findAll();
    }

    /**
     * {@code GET  /percent-discounts/:id} : get the "id" percentDiscount.
     *
     * @param id the id of the percentDiscount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the percentDiscount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/percent-discounts/{id}")
    public ResponseEntity<PercentDiscount> getPercentDiscount(@PathVariable Long id) {
        log.debug("REST request to get PercentDiscount : {}", id);
        Optional<PercentDiscount> percentDiscount = percentDiscountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(percentDiscount);
    }

    /**
     * {@code DELETE  /percent-discounts/:id} : delete the "id" percentDiscount.
     *
     * @param id the id of the percentDiscount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/percent-discounts/{id}")
    public ResponseEntity<Void> deletePercentDiscount(@PathVariable Long id) {
        log.debug("REST request to delete PercentDiscount : {}", id);
        percentDiscountRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
