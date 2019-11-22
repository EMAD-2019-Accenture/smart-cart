package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Alimentare;
import it.unisa.scanapp.repository.AlimentareRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Alimentare}.
 */
@RestController
@RequestMapping("/api")
public class AlimentareResource {

    private final Logger log = LoggerFactory.getLogger(AlimentareResource.class);

    private static final String ENTITY_NAME = "alimentare";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlimentareRepository alimentareRepository;

    public AlimentareResource(AlimentareRepository alimentareRepository) {
        this.alimentareRepository = alimentareRepository;
    }

    /**
     * {@code POST  /alimentares} : Create a new alimentare.
     *
     * @param alimentare the alimentare to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alimentare, or with status {@code 400 (Bad Request)} if the alimentare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alimentares")
    public ResponseEntity<Alimentare> createAlimentare(@Valid @RequestBody Alimentare alimentare) throws URISyntaxException {
        log.debug("REST request to save Alimentare : {}", alimentare);
        if (alimentare.getId() != null) {
            throw new BadRequestAlertException("A new alimentare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alimentare result = alimentareRepository.save(alimentare);
        return ResponseEntity.created(new URI("/api/alimentares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alimentares} : Updates an existing alimentare.
     *
     * @param alimentare the alimentare to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alimentare,
     * or with status {@code 400 (Bad Request)} if the alimentare is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alimentare couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alimentares")
    public ResponseEntity<Alimentare> updateAlimentare(@Valid @RequestBody Alimentare alimentare) throws URISyntaxException {
        log.debug("REST request to update Alimentare : {}", alimentare);
        if (alimentare.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Alimentare result = alimentareRepository.save(alimentare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alimentare.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alimentares} : get all the alimentares.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alimentares in body.
     */
    @GetMapping("/alimentares")
    public List<Alimentare> getAllAlimentares(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("articolo-is-null".equals(filter)) {
            log.debug("REST request to get all Alimentares where articolo is null");
            return StreamSupport
                .stream(alimentareRepository.findAll().spliterator(), false)
                .filter(alimentare -> alimentare.getArticolo() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Alimentares");
        return alimentareRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /alimentares/:id} : get the "id" alimentare.
     *
     * @param id the id of the alimentare to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alimentare, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alimentares/{id}")
    public ResponseEntity<Alimentare> getAlimentare(@PathVariable Long id) {
        log.debug("REST request to get Alimentare : {}", id);
        Optional<Alimentare> alimentare = alimentareRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(alimentare);
    }

    /**
     * {@code DELETE  /alimentares/:id} : delete the "id" alimentare.
     *
     * @param id the id of the alimentare to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alimentares/{id}")
    public ResponseEntity<Void> deleteAlimentare(@PathVariable Long id) {
        log.debug("REST request to delete Alimentare : {}", id);
        alimentareRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
