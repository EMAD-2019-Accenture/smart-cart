package it.unisa.scanapp.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import it.unisa.scanapp.domain.Product;
import it.unisa.scanapp.service.RecommendationService;
import it.unisa.scanapp.service.dto.AssociationDTO;
import it.unisa.scanapp.service.dto.ProductsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * RecommendationResource controller
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationsResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Logger log = LoggerFactory.getLogger(RecommendationsResource.class);
    private final RecommendationService recommendationService;

    public RecommendationsResource(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }



    @PostMapping("")
    public ResponseEntity<Product> defaultAction(@RequestBody ProductsDTO productsList) {
        log.info("POST request to get recommendations from {}",productsList);
        Optional<Product> recommended = recommendationService.getRecommendedFrom(productsList);
        return ResponseUtil.wrapOrNotFound(recommended);
    }

    @PostMapping("/associate")
    public ResponseEntity<String> createAssociation(@RequestBody AssociationDTO associationDTO) throws URISyntaxException {
        log.info("POST request to get recommendations from {}",associationDTO);
        recommendationService.createAssociation(associationDTO);
        return ResponseEntity.ok().build();
    }

}
