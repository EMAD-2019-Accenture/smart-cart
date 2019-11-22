package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Sconto;
import it.unisa.scanapp.repository.ScontoRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Sconto}.
 */
@RestController
@RequestMapping("/api")
public class ScontoResource {

    private final Logger log = LoggerFactory.getLogger(ScontoResource.class);

    private static final String ENTITY_NAME = "sconto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScontoRepository scontoRepository;

    public ScontoResource(ScontoRepository scontoRepository) {
        this.scontoRepository = scontoRepository;
    }

    /**
     * {@code POST  /scontos} : Create a new sconto.
     *
     * @param sconto the sconto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sconto, or with status {@code 400 (Bad Request)} if the sconto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scontos")
    public ResponseEntity<Sconto> createSconto(@Valid @RequestBody Sconto sconto) throws URISyntaxException {
        log.debug("REST request to save Sconto : {}", sconto);
        if (sconto.getId() != null) {
            throw new BadRequestAlertException("A new sconto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sconto result = scontoRepository.save(sconto);
        return ResponseEntity.created(new URI("/api/scontos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scontos} : Updates an existing sconto.
     *
     * @param sconto the sconto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sconto,
     * or with status {@code 400 (Bad Request)} if the sconto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sconto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scontos")
    public ResponseEntity<Sconto> updateSconto(@Valid @RequestBody Sconto sconto) throws URISyntaxException {
        log.debug("REST request to update Sconto : {}", sconto);
        if (sconto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sconto result = scontoRepository.save(sconto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sconto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scontos} : get all the scontos.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scontos in body.
     */
    @GetMapping("/scontos")
    public List<Sconto> getAllScontos(@RequestParam(required = false) String filter) {
        if ("articolo-is-null".equals(filter)) {
            log.debug("REST request to get all Scontos where articolo is null");
            return StreamSupport
                .stream(scontoRepository.findAll().spliterator(), false)
                .filter(sconto -> sconto.getArticolo() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Scontos");
        return scontoRepository.findAll();
    }

    /**
     * {@code GET  /scontos/:id} : get the "id" sconto.
     *
     * @param id the id of the sconto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sconto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scontos/{id}")
    public ResponseEntity<Sconto> getSconto(@PathVariable Long id) {
        log.debug("REST request to get Sconto : {}", id);
        Optional<Sconto> sconto = scontoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sconto);
    }

    /**
     * {@code DELETE  /scontos/:id} : delete the "id" sconto.
     *
     * @param id the id of the sconto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scontos/{id}")
    public ResponseEntity<Void> deleteSconto(@PathVariable Long id) {
        log.debug("REST request to delete Sconto : {}", id);
        scontoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
