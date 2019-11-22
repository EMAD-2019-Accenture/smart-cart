package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Articolo;
import it.unisa.scanapp.repository.ArticoloRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Articolo}.
 */
@RestController
@RequestMapping("/api")
public class ArticoloResource {

    private final Logger log = LoggerFactory.getLogger(ArticoloResource.class);

    private static final String ENTITY_NAME = "articolo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticoloRepository articoloRepository;

    public ArticoloResource(ArticoloRepository articoloRepository) {
        this.articoloRepository = articoloRepository;
    }

    /**
     * {@code POST  /articolos} : Create a new articolo.
     *
     * @param articolo the articolo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articolo, or with status {@code 400 (Bad Request)} if the articolo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articolos")
    public ResponseEntity<Articolo> createArticolo(@Valid @RequestBody Articolo articolo) throws URISyntaxException {
        log.debug("REST request to save Articolo : {}", articolo);
        if (articolo.getId() != null) {
            throw new BadRequestAlertException("A new articolo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Articolo result = articoloRepository.save(articolo);
        return ResponseEntity.created(new URI("/api/articolos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articolos} : Updates an existing articolo.
     *
     * @param articolo the articolo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articolo,
     * or with status {@code 400 (Bad Request)} if the articolo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articolo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articolos")
    public ResponseEntity<Articolo> updateArticolo(@Valid @RequestBody Articolo articolo) throws URISyntaxException {
        log.debug("REST request to update Articolo : {}", articolo);
        if (articolo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Articolo result = articoloRepository.save(articolo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articolo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articolos} : get all the articolos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articolos in body.
     */
    @GetMapping("/articolos")
    public List<Articolo> getAllArticolos() {
        log.debug("REST request to get all Articolos");
        return articoloRepository.findAll();
    }

    /**
     * {@code GET  /articolos/:id} : get the "id" articolo.
     *
     * @param id the id of the articolo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articolo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articolos/{id}")
    public ResponseEntity<Articolo> getArticolo(@PathVariable Long id) {
        log.debug("REST request to get Articolo : {}", id);
        Optional<Articolo> articolo = articoloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(articolo);
    }

    /**
     * {@code DELETE  /articolos/:id} : delete the "id" articolo.
     *
     * @param id the id of the articolo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articolos/{id}")
    public ResponseEntity<Void> deleteArticolo(@PathVariable Long id) {
        log.debug("REST request to delete Articolo : {}", id);
        articoloRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
