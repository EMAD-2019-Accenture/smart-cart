package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Farmacologico;
import it.unisa.scanapp.repository.FarmacologicoRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Farmacologico}.
 */
@RestController
@RequestMapping("/api")
public class FarmacologicoResource {

    private final Logger log = LoggerFactory.getLogger(FarmacologicoResource.class);

    private static final String ENTITY_NAME = "farmacologico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FarmacologicoRepository farmacologicoRepository;

    public FarmacologicoResource(FarmacologicoRepository farmacologicoRepository) {
        this.farmacologicoRepository = farmacologicoRepository;
    }

    /**
     * {@code POST  /farmacologicos} : Create a new farmacologico.
     *
     * @param farmacologico the farmacologico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new farmacologico, or with status {@code 400 (Bad Request)} if the farmacologico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/farmacologicos")
    public ResponseEntity<Farmacologico> createFarmacologico(@Valid @RequestBody Farmacologico farmacologico) throws URISyntaxException {
        log.debug("REST request to save Farmacologico : {}", farmacologico);
        if (farmacologico.getId() != null) {
            throw new BadRequestAlertException("A new farmacologico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Farmacologico result = farmacologicoRepository.save(farmacologico);
        return ResponseEntity.created(new URI("/api/farmacologicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /farmacologicos} : Updates an existing farmacologico.
     *
     * @param farmacologico the farmacologico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farmacologico,
     * or with status {@code 400 (Bad Request)} if the farmacologico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the farmacologico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/farmacologicos")
    public ResponseEntity<Farmacologico> updateFarmacologico(@Valid @RequestBody Farmacologico farmacologico) throws URISyntaxException {
        log.debug("REST request to update Farmacologico : {}", farmacologico);
        if (farmacologico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Farmacologico result = farmacologicoRepository.save(farmacologico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, farmacologico.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /farmacologicos} : get all the farmacologicos.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of farmacologicos in body.
     */
    @GetMapping("/farmacologicos")
    public List<Farmacologico> getAllFarmacologicos(@RequestParam(required = false) String filter) {
        if ("articolo-is-null".equals(filter)) {
            log.debug("REST request to get all Farmacologicos where articolo is null");
            return StreamSupport
                .stream(farmacologicoRepository.findAll().spliterator(), false)
                .filter(farmacologico -> farmacologico.getArticolo() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Farmacologicos");
        return farmacologicoRepository.findAll();
    }

    /**
     * {@code GET  /farmacologicos/:id} : get the "id" farmacologico.
     *
     * @param id the id of the farmacologico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the farmacologico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/farmacologicos/{id}")
    public ResponseEntity<Farmacologico> getFarmacologico(@PathVariable Long id) {
        log.debug("REST request to get Farmacologico : {}", id);
        Optional<Farmacologico> farmacologico = farmacologicoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(farmacologico);
    }

    /**
     * {@code DELETE  /farmacologicos/:id} : delete the "id" farmacologico.
     *
     * @param id the id of the farmacologico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/farmacologicos/{id}")
    public ResponseEntity<Void> deleteFarmacologico(@PathVariable Long id) {
        log.debug("REST request to delete Farmacologico : {}", id);
        farmacologicoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
