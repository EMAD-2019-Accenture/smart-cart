package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Allergene;
import it.unisa.scanapp.repository.AllergeneRepository;
import it.unisa.scanapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static it.unisa.scanapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AllergeneResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class AllergeneResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private AllergeneRepository allergeneRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAllergeneMockMvc;

    private Allergene allergene;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllergeneResource allergeneResource = new AllergeneResource(allergeneRepository);
        this.restAllergeneMockMvc = MockMvcBuilders.standaloneSetup(allergeneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergene createEntity(EntityManager em) {
        Allergene allergene = new Allergene()
            .nome(DEFAULT_NOME);
        return allergene;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergene createUpdatedEntity(EntityManager em) {
        Allergene allergene = new Allergene()
            .nome(UPDATED_NOME);
        return allergene;
    }

    @BeforeEach
    public void initTest() {
        allergene = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergene() throws Exception {
        int databaseSizeBeforeCreate = allergeneRepository.findAll().size();

        // Create the Allergene
        restAllergeneMockMvc.perform(post("/api/allergenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergene)))
            .andExpect(status().isCreated());

        // Validate the Allergene in the database
        List<Allergene> allergeneList = allergeneRepository.findAll();
        assertThat(allergeneList).hasSize(databaseSizeBeforeCreate + 1);
        Allergene testAllergene = allergeneList.get(allergeneList.size() - 1);
        assertThat(testAllergene.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createAllergeneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergeneRepository.findAll().size();

        // Create the Allergene with an existing ID
        allergene.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergeneMockMvc.perform(post("/api/allergenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergene)))
            .andExpect(status().isBadRequest());

        // Validate the Allergene in the database
        List<Allergene> allergeneList = allergeneRepository.findAll();
        assertThat(allergeneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = allergeneRepository.findAll().size();
        // set the field null
        allergene.setNome(null);

        // Create the Allergene, which fails.

        restAllergeneMockMvc.perform(post("/api/allergenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergene)))
            .andExpect(status().isBadRequest());

        List<Allergene> allergeneList = allergeneRepository.findAll();
        assertThat(allergeneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAllergenes() throws Exception {
        // Initialize the database
        allergeneRepository.saveAndFlush(allergene);

        // Get all the allergeneList
        restAllergeneMockMvc.perform(get("/api/allergenes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergene.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getAllergene() throws Exception {
        // Initialize the database
        allergeneRepository.saveAndFlush(allergene);

        // Get the allergene
        restAllergeneMockMvc.perform(get("/api/allergenes/{id}", allergene.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allergene.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllergene() throws Exception {
        // Get the allergene
        restAllergeneMockMvc.perform(get("/api/allergenes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergene() throws Exception {
        // Initialize the database
        allergeneRepository.saveAndFlush(allergene);

        int databaseSizeBeforeUpdate = allergeneRepository.findAll().size();

        // Update the allergene
        Allergene updatedAllergene = allergeneRepository.findById(allergene.getId()).get();
        // Disconnect from session so that the updates on updatedAllergene are not directly saved in db
        em.detach(updatedAllergene);
        updatedAllergene
            .nome(UPDATED_NOME);

        restAllergeneMockMvc.perform(put("/api/allergenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllergene)))
            .andExpect(status().isOk());

        // Validate the Allergene in the database
        List<Allergene> allergeneList = allergeneRepository.findAll();
        assertThat(allergeneList).hasSize(databaseSizeBeforeUpdate);
        Allergene testAllergene = allergeneList.get(allergeneList.size() - 1);
        assertThat(testAllergene.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergene() throws Exception {
        int databaseSizeBeforeUpdate = allergeneRepository.findAll().size();

        // Create the Allergene

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergeneMockMvc.perform(put("/api/allergenes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergene)))
            .andExpect(status().isBadRequest());

        // Validate the Allergene in the database
        List<Allergene> allergeneList = allergeneRepository.findAll();
        assertThat(allergeneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllergene() throws Exception {
        // Initialize the database
        allergeneRepository.saveAndFlush(allergene);

        int databaseSizeBeforeDelete = allergeneRepository.findAll().size();

        // Delete the allergene
        restAllergeneMockMvc.perform(delete("/api/allergenes/{id}", allergene.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Allergene> allergeneList = allergeneRepository.findAll();
        assertThat(allergeneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allergene.class);
        Allergene allergene1 = new Allergene();
        allergene1.setId(1L);
        Allergene allergene2 = new Allergene();
        allergene2.setId(allergene1.getId());
        assertThat(allergene1).isEqualTo(allergene2);
        allergene2.setId(2L);
        assertThat(allergene1).isNotEqualTo(allergene2);
        allergene1.setId(null);
        assertThat(allergene1).isNotEqualTo(allergene2);
    }
}
