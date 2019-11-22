package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Acquirente;
import it.unisa.scanapp.repository.AcquirenteRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Acquirente}.
 */
@RestController
@RequestMapping("/api")
public class AcquirenteResource {

    private final Logger log = LoggerFactory.getLogger(AcquirenteResource.class);

    private static final String ENTITY_NAME = "acquirente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcquirenteRepository acquirenteRepository;

    public AcquirenteResource(AcquirenteRepository acquirenteRepository) {
        this.acquirenteRepository = acquirenteRepository;
    }

    /**
     * {@code POST  /acquirentes} : Create a new acquirente.
     *
     * @param acquirente the acquirente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acquirente, or with status {@code 400 (Bad Request)} if the acquirente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acquirentes")
    public ResponseEntity<Acquirente> createAcquirente(@Valid @RequestBody Acquirente acquirente) throws URISyntaxException {
        log.debug("REST request to save Acquirente : {}", acquirente);
        if (acquirente.getId() != null) {
            throw new BadRequestAlertException("A new acquirente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acquirente result = acquirenteRepository.save(acquirente);
        return ResponseEntity.created(new URI("/api/acquirentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acquirentes} : Updates an existing acquirente.
     *
     * @param acquirente the acquirente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acquirente,
     * or with status {@code 400 (Bad Request)} if the acquirente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acquirente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acquirentes")
    public ResponseEntity<Acquirente> updateAcquirente(@Valid @RequestBody Acquirente acquirente) throws URISyntaxException {
        log.debug("REST request to update Acquirente : {}", acquirente);
        if (acquirente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Acquirente result = acquirenteRepository.save(acquirente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acquirente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /acquirentes} : get all the acquirentes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acquirentes in body.
     */
    @GetMapping("/acquirentes")
    public List<Acquirente> getAllAcquirentes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Acquirentes");
        return acquirenteRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /acquirentes/:id} : get the "id" acquirente.
     *
     * @param id the id of the acquirente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acquirente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acquirentes/{id}")
    public ResponseEntity<Acquirente> getAcquirente(@PathVariable Long id) {
        log.debug("REST request to get Acquirente : {}", id);
        Optional<Acquirente> acquirente = acquirenteRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(acquirente);
    }

    /**
     * {@code DELETE  /acquirentes/:id} : delete the "id" acquirente.
     *
     * @param id the id of the acquirente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acquirentes/{id}")
    public ResponseEntity<Void> deleteAcquirente(@PathVariable Long id) {
        log.debug("REST request to delete Acquirente : {}", id);
        acquirenteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
