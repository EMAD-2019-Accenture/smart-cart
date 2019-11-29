package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.domain.Allergen;
import it.unisa.scanapp.repository.AllergenRepository;
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
 * REST controller for managing {@link it.unisa.scanapp.domain.Allergen}.
 */
@RestController
@RequestMapping("/api")
public class AllergenResource {

    private final Logger log = LoggerFactory.getLogger(AllergenResource.class);

    private static final String ENTITY_NAME = "allergen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllergenRepository allergenRepository;

    public AllergenResource(AllergenRepository allergenRepository) {
        this.allergenRepository = allergenRepository;
    }

    /**
     * {@code POST  /allergens} : Create a new allergen.
     *
     * @param allergen the allergen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allergen, or with status {@code 400 (Bad Request)} if the allergen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allergens")
    public ResponseEntity<Allergen> createAllergen(@Valid @RequestBody Allergen allergen) throws URISyntaxException {
        log.debug("REST request to save Allergen : {}", allergen);
        if (allergen.getId() != null) {
            throw new BadRequestAlertException("A new allergen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Allergen result = allergenRepository.save(allergen);
        return ResponseEntity.created(new URI("/api/allergens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allergens} : Updates an existing allergen.
     *
     * @param allergen the allergen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergen,
     * or with status {@code 400 (Bad Request)} if the allergen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allergen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allergens")
    public ResponseEntity<Allergen> updateAllergen(@Valid @RequestBody Allergen allergen) throws URISyntaxException {
        log.debug("REST request to update Allergen : {}", allergen);
        if (allergen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Allergen result = allergenRepository.save(allergen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, allergen.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allergens} : get all the allergens.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allergens in body.
     */
    @GetMapping("/allergens")
    public List<Allergen> getAllAllergens() {
        log.debug("REST request to get all Allergens");
        return allergenRepository.findAll();
    }

    /**
     * {@code GET  /allergens/:id} : get the "id" allergen.
     *
     * @param id the id of the allergen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allergen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allergens/{id}")
    public ResponseEntity<Allergen> getAllergen(@PathVariable Long id) {
        log.debug("REST request to get Allergen : {}", id);
        Optional<Allergen> allergen = allergenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allergen);
    }

    /**
     * {@code DELETE  /allergens/:id} : delete the "id" allergen.
     *
     * @param id the id of the allergen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allergens/{id}")
    public ResponseEntity<Void> deleteAllergen(@PathVariable Long id) {
        log.debug("REST request to delete Allergen : {}", id);
        allergenRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
