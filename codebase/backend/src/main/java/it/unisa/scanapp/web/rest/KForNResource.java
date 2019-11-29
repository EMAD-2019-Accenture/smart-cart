package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.KForN;
import it.unisa.scanapp.repository.KForNRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.KForN}.
 */
@RestController
@RequestMapping("/api")
public class KForNResource {

    private final Logger log = LoggerFactory.getLogger(KForNResource.class);

    private static final String ENTITY_NAME = "kForN";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KForNRepository kForNRepository;

    public KForNResource(KForNRepository kForNRepository) {
        this.kForNRepository = kForNRepository;
    }

    /**
     * {@code POST  /k-for-ns} : Create a new kForN.
     *
     * @param kForN the kForN to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kForN, or with status {@code 400 (Bad Request)} if the kForN has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/k-for-ns")
    public ResponseEntity<KForN> createKForN(@Valid @RequestBody KForN kForN) throws URISyntaxException {
        log.debug("REST request to save KForN : {}", kForN);
        if (kForN.getId() != null) {
            throw new BadRequestAlertException("A new kForN cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KForN result = kForNRepository.save(kForN);
        return ResponseEntity.created(new URI("/api/k-for-ns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /k-for-ns} : Updates an existing kForN.
     *
     * @param kForN the kForN to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kForN,
     * or with status {@code 400 (Bad Request)} if the kForN is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kForN couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/k-for-ns")
    public ResponseEntity<KForN> updateKForN(@Valid @RequestBody KForN kForN) throws URISyntaxException {
        log.debug("REST request to update KForN : {}", kForN);
        if (kForN.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KForN result = kForNRepository.save(kForN);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kForN.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /k-for-ns} : get all the kForNS.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kForNS in body.
     */
    @GetMapping("/k-for-ns")
    public List<KForN> getAllKForNS() {
        log.debug("REST request to get all KForNS");
        return kForNRepository.findAll();
    }

    /**
     * {@code GET  /k-for-ns/:id} : get the "id" kForN.
     *
     * @param id the id of the kForN to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kForN, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/k-for-ns/{id}")
    public ResponseEntity<KForN> getKForN(@PathVariable Long id) {
        log.debug("REST request to get KForN : {}", id);
        Optional<KForN> kForN = kForNRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kForN);
    }

    /**
     * {@code DELETE  /k-for-ns/:id} : delete the "id" kForN.
     *
     * @param id the id of the kForN to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/k-for-ns/{id}")
    public ResponseEntity<Void> deleteKForN(@PathVariable Long id) {
        log.debug("REST request to delete KForN : {}", id);
        kForNRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
