package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Allergene;
import it.unisa.scanapp.repository.AllergeneRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Allergene}.
 */
@RestController
@RequestMapping("/api")
public class AllergeneResource {

    private final Logger log = LoggerFactory.getLogger(AllergeneResource.class);

    private static final String ENTITY_NAME = "allergene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllergeneRepository allergeneRepository;

    public AllergeneResource(AllergeneRepository allergeneRepository) {
        this.allergeneRepository = allergeneRepository;
    }

    /**
     * {@code POST  /allergenes} : Create a new allergene.
     *
     * @param allergene the allergene to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allergene, or with status {@code 400 (Bad Request)} if the allergene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allergenes")
    public ResponseEntity<Allergene> createAllergene(@Valid @RequestBody Allergene allergene) throws URISyntaxException {
        log.debug("REST request to save Allergene : {}", allergene);
        if (allergene.getId() != null) {
            throw new BadRequestAlertException("A new allergene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Allergene result = allergeneRepository.save(allergene);
        return ResponseEntity.created(new URI("/api/allergenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allergenes} : Updates an existing allergene.
     *
     * @param allergene the allergene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergene,
     * or with status {@code 400 (Bad Request)} if the allergene is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allergene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allergenes")
    public ResponseEntity<Allergene> updateAllergene(@Valid @RequestBody Allergene allergene) throws URISyntaxException {
        log.debug("REST request to update Allergene : {}", allergene);
        if (allergene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Allergene result = allergeneRepository.save(allergene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, allergene.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allergenes} : get all the allergenes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allergenes in body.
     */
    @GetMapping("/allergenes")
    public List<Allergene> getAllAllergenes() {
        log.debug("REST request to get all Allergenes");
        return allergeneRepository.findAll();
    }

    /**
     * {@code GET  /allergenes/:id} : get the "id" allergene.
     *
     * @param id the id of the allergene to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allergene, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allergenes/{id}")
    public ResponseEntity<Allergene> getAllergene(@PathVariable Long id) {
        log.debug("REST request to get Allergene : {}", id);
        Optional<Allergene> allergene = allergeneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allergene);
    }

    /**
     * {@code DELETE  /allergenes/:id} : delete the "id" allergene.
     *
     * @param id the id of the allergene to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allergenes/{id}")
    public ResponseEntity<Void> deleteAllergene(@PathVariable Long id) {
        log.debug("REST request to delete Allergene : {}", id);
        allergeneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
