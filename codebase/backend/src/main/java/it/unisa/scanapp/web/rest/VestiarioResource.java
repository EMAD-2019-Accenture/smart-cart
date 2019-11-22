package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Vestiario;
import it.unisa.scanapp.repository.VestiarioRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Vestiario}.
 */
@RestController
@RequestMapping("/api")
public class VestiarioResource {

    private final Logger log = LoggerFactory.getLogger(VestiarioResource.class);

    private static final String ENTITY_NAME = "vestiario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VestiarioRepository vestiarioRepository;

    public VestiarioResource(VestiarioRepository vestiarioRepository) {
        this.vestiarioRepository = vestiarioRepository;
    }

    /**
     * {@code POST  /vestiarios} : Create a new vestiario.
     *
     * @param vestiario the vestiario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vestiario, or with status {@code 400 (Bad Request)} if the vestiario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vestiarios")
    public ResponseEntity<Vestiario> createVestiario(@Valid @RequestBody Vestiario vestiario) throws URISyntaxException {
        log.debug("REST request to save Vestiario : {}", vestiario);
        if (vestiario.getId() != null) {
            throw new BadRequestAlertException("A new vestiario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vestiario result = vestiarioRepository.save(vestiario);
        return ResponseEntity.created(new URI("/api/vestiarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vestiarios} : Updates an existing vestiario.
     *
     * @param vestiario the vestiario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vestiario,
     * or with status {@code 400 (Bad Request)} if the vestiario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vestiario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vestiarios")
    public ResponseEntity<Vestiario> updateVestiario(@Valid @RequestBody Vestiario vestiario) throws URISyntaxException {
        log.debug("REST request to update Vestiario : {}", vestiario);
        if (vestiario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vestiario result = vestiarioRepository.save(vestiario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vestiario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vestiarios} : get all the vestiarios.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vestiarios in body.
     */
    @GetMapping("/vestiarios")
    public List<Vestiario> getAllVestiarios(@RequestParam(required = false) String filter) {
        if ("articolo-is-null".equals(filter)) {
            log.debug("REST request to get all Vestiarios where articolo is null");
            return StreamSupport
                .stream(vestiarioRepository.findAll().spliterator(), false)
                .filter(vestiario -> vestiario.getArticolo() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Vestiarios");
        return vestiarioRepository.findAll();
    }

    /**
     * {@code GET  /vestiarios/:id} : get the "id" vestiario.
     *
     * @param id the id of the vestiario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vestiario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vestiarios/{id}")
    public ResponseEntity<Vestiario> getVestiario(@PathVariable Long id) {
        log.debug("REST request to get Vestiario : {}", id);
        Optional<Vestiario> vestiario = vestiarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vestiario);
    }

    /**
     * {@code DELETE  /vestiarios/:id} : delete the "id" vestiario.
     *
     * @param id the id of the vestiario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vestiarios/{id}")
    public ResponseEntity<Void> deleteVestiario(@PathVariable Long id) {
        log.debug("REST request to delete Vestiario : {}", id);
        vestiarioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
