package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Transazione;
import it.unisa.scanapp.repository.TransazioneRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Transazione}.
 */
@RestController
@RequestMapping("/api")
public class TransazioneResource {

    private final Logger log = LoggerFactory.getLogger(TransazioneResource.class);

    private static final String ENTITY_NAME = "transazione";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransazioneRepository transazioneRepository;

    public TransazioneResource(TransazioneRepository transazioneRepository) {
        this.transazioneRepository = transazioneRepository;
    }

    /**
     * {@code POST  /transaziones} : Create a new transazione.
     *
     * @param transazione the transazione to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transazione, or with status {@code 400 (Bad Request)} if the transazione has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaziones")
    public ResponseEntity<Transazione> createTransazione(@Valid @RequestBody Transazione transazione) throws URISyntaxException {
        log.debug("REST request to save Transazione : {}", transazione);
        if (transazione.getId() != null) {
            throw new BadRequestAlertException("A new transazione cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transazione result = transazioneRepository.save(transazione);
        return ResponseEntity.created(new URI("/api/transaziones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaziones} : Updates an existing transazione.
     *
     * @param transazione the transazione to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transazione,
     * or with status {@code 400 (Bad Request)} if the transazione is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transazione couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaziones")
    public ResponseEntity<Transazione> updateTransazione(@Valid @RequestBody Transazione transazione) throws URISyntaxException {
        log.debug("REST request to update Transazione : {}", transazione);
        if (transazione.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transazione result = transazioneRepository.save(transazione);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transazione.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaziones} : get all the transaziones.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transaziones in body.
     */
    @GetMapping("/transaziones")
    public List<Transazione> getAllTransaziones() {
        log.debug("REST request to get all Transaziones");
        return transazioneRepository.findAll();
    }

    /**
     * {@code GET  /transaziones/:id} : get the "id" transazione.
     *
     * @param id the id of the transazione to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transazione, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaziones/{id}")
    public ResponseEntity<Transazione> getTransazione(@PathVariable Long id) {
        log.debug("REST request to get Transazione : {}", id);
        Optional<Transazione> transazione = transazioneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transazione);
    }

    /**
     * {@code DELETE  /transaziones/:id} : delete the "id" transazione.
     *
     * @param id the id of the transazione to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaziones/{id}")
    public ResponseEntity<Void> deleteTransazione(@PathVariable Long id) {
        log.debug("REST request to delete Transazione : {}", id);
        transazioneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
