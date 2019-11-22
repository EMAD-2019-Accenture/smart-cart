package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Percentuale;
import it.unisa.scanapp.repository.PercentualeRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link it.unisa.scanapp.domain.Percentuale}.
 */
@RestController
@RequestMapping("/api")
public class PercentualeResource {

    private final Logger log = LoggerFactory.getLogger(PercentualeResource.class);

    private static final String ENTITY_NAME = "percentuale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PercentualeRepository percentualeRepository;

    public PercentualeResource(PercentualeRepository percentualeRepository) {
        this.percentualeRepository = percentualeRepository;
    }

    /**
     * {@code POST  /percentuales} : Create a new percentuale.
     *
     * @param percentuale the percentuale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new percentuale, or with status {@code 400 (Bad Request)} if the percentuale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/percentuales")
    public ResponseEntity<Percentuale> createPercentuale(@Valid @RequestBody Percentuale percentuale) throws URISyntaxException {
        log.debug("REST request to save Percentuale : {}", percentuale);
        if (percentuale.getId() != null) {
            throw new BadRequestAlertException("A new percentuale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Percentuale result = percentualeRepository.save(percentuale);
        return ResponseEntity.created(new URI("/api/percentuales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /percentuales} : Updates an existing percentuale.
     *
     * @param percentuale the percentuale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated percentuale,
     * or with status {@code 400 (Bad Request)} if the percentuale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the percentuale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/percentuales")
    public ResponseEntity<Percentuale> updatePercentuale(@Valid @RequestBody Percentuale percentuale) throws URISyntaxException {
        log.debug("REST request to update Percentuale : {}", percentuale);
        if (percentuale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Percentuale result = percentualeRepository.save(percentuale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, percentuale.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /percentuales} : get all the percentuales.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of percentuales in body.
     */
    @GetMapping("/percentuales")
    public List<Percentuale> getAllPercentuales(@RequestParam(required = false) String filter) {
        if ("articolo-is-null".equals(filter)) {
            log.debug("REST request to get all Percentuales where articolo is null");
            return StreamSupport
                .stream(percentualeRepository.findAll().spliterator(), false)
                .filter(percentuale -> percentuale.getArticolo() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Percentuales");
        return percentualeRepository.findAll();
    }

    /**
     * {@code GET  /percentuales/:id} : get the "id" percentuale.
     *
     * @param id the id of the percentuale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the percentuale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/percentuales/{id}")
    public ResponseEntity<Percentuale> getPercentuale(@PathVariable Long id) {
        log.debug("REST request to get Percentuale : {}", id);
        Optional<Percentuale> percentuale = percentualeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(percentuale);
    }

    /**
     * {@code DELETE  /percentuales/:id} : delete the "id" percentuale.
     *
     * @param id the id of the percentuale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/percentuales/{id}")
    public ResponseEntity<Void> deletePercentuale(@PathVariable Long id) {
        log.debug("REST request to delete Percentuale : {}", id);
        percentualeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
