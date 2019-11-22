package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Farmacologico;
import it.unisa.scanapp.repository.FarmacologicoRepository;
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
 * Integration tests for the {@link FarmacologicoResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class FarmacologicoResourceIT {

    private static final String DEFAULT_ASSUNZIONE = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNZIONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_GIORNI_ALLA_SCADENZA = 0;
    private static final Integer UPDATED_GIORNI_ALLA_SCADENZA = 1;
    private static final Integer SMALLER_GIORNI_ALLA_SCADENZA = 0 - 1;

    private static final String DEFAULT_AVVERTENZE = "AAAAAAAAAA";
    private static final String UPDATED_AVVERTENZE = "BBBBBBBBBB";

    @Autowired
    private FarmacologicoRepository farmacologicoRepository;

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

    private MockMvc restFarmacologicoMockMvc;

    private Farmacologico farmacologico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FarmacologicoResource farmacologicoResource = new FarmacologicoResource(farmacologicoRepository);
        this.restFarmacologicoMockMvc = MockMvcBuilders.standaloneSetup(farmacologicoResource)
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
    public static Farmacologico createEntity(EntityManager em) {
        Farmacologico farmacologico = new Farmacologico()
            .assunzione(DEFAULT_ASSUNZIONE)
            .giorniAllaScadenza(DEFAULT_GIORNI_ALLA_SCADENZA)
            .avvertenze(DEFAULT_AVVERTENZE);
        return farmacologico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farmacologico createUpdatedEntity(EntityManager em) {
        Farmacologico farmacologico = new Farmacologico()
            .assunzione(UPDATED_ASSUNZIONE)
            .giorniAllaScadenza(UPDATED_GIORNI_ALLA_SCADENZA)
            .avvertenze(UPDATED_AVVERTENZE);
        return farmacologico;
    }

    @BeforeEach
    public void initTest() {
        farmacologico = createEntity(em);
    }

    @Test
    @Transactional
    public void createFarmacologico() throws Exception {
        int databaseSizeBeforeCreate = farmacologicoRepository.findAll().size();

        // Create the Farmacologico
        restFarmacologicoMockMvc.perform(post("/api/farmacologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacologico)))
            .andExpect(status().isCreated());

        // Validate the Farmacologico in the database
        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeCreate + 1);
        Farmacologico testFarmacologico = farmacologicoList.get(farmacologicoList.size() - 1);
        assertThat(testFarmacologico.getAssunzione()).isEqualTo(DEFAULT_ASSUNZIONE);
        assertThat(testFarmacologico.getGiorniAllaScadenza()).isEqualTo(DEFAULT_GIORNI_ALLA_SCADENZA);
        assertThat(testFarmacologico.getAvvertenze()).isEqualTo(DEFAULT_AVVERTENZE);
    }

    @Test
    @Transactional
    public void createFarmacologicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = farmacologicoRepository.findAll().size();

        // Create the Farmacologico with an existing ID
        farmacologico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmacologicoMockMvc.perform(post("/api/farmacologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacologico)))
            .andExpect(status().isBadRequest());

        // Validate the Farmacologico in the database
        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAssunzioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmacologicoRepository.findAll().size();
        // set the field null
        farmacologico.setAssunzione(null);

        // Create the Farmacologico, which fails.

        restFarmacologicoMockMvc.perform(post("/api/farmacologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacologico)))
            .andExpect(status().isBadRequest());

        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGiorniAllaScadenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmacologicoRepository.findAll().size();
        // set the field null
        farmacologico.setGiorniAllaScadenza(null);

        // Create the Farmacologico, which fails.

        restFarmacologicoMockMvc.perform(post("/api/farmacologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacologico)))
            .andExpect(status().isBadRequest());

        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvvertenzeIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmacologicoRepository.findAll().size();
        // set the field null
        farmacologico.setAvvertenze(null);

        // Create the Farmacologico, which fails.

        restFarmacologicoMockMvc.perform(post("/api/farmacologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacologico)))
            .andExpect(status().isBadRequest());

        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFarmacologicos() throws Exception {
        // Initialize the database
        farmacologicoRepository.saveAndFlush(farmacologico);

        // Get all the farmacologicoList
        restFarmacologicoMockMvc.perform(get("/api/farmacologicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmacologico.getId().intValue())))
            .andExpect(jsonPath("$.[*].assunzione").value(hasItem(DEFAULT_ASSUNZIONE.toString())))
            .andExpect(jsonPath("$.[*].giorniAllaScadenza").value(hasItem(DEFAULT_GIORNI_ALLA_SCADENZA)))
            .andExpect(jsonPath("$.[*].avvertenze").value(hasItem(DEFAULT_AVVERTENZE.toString())));
    }
    
    @Test
    @Transactional
    public void getFarmacologico() throws Exception {
        // Initialize the database
        farmacologicoRepository.saveAndFlush(farmacologico);

        // Get the farmacologico
        restFarmacologicoMockMvc.perform(get("/api/farmacologicos/{id}", farmacologico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(farmacologico.getId().intValue()))
            .andExpect(jsonPath("$.assunzione").value(DEFAULT_ASSUNZIONE.toString()))
            .andExpect(jsonPath("$.giorniAllaScadenza").value(DEFAULT_GIORNI_ALLA_SCADENZA))
            .andExpect(jsonPath("$.avvertenze").value(DEFAULT_AVVERTENZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFarmacologico() throws Exception {
        // Get the farmacologico
        restFarmacologicoMockMvc.perform(get("/api/farmacologicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFarmacologico() throws Exception {
        // Initialize the database
        farmacologicoRepository.saveAndFlush(farmacologico);

        int databaseSizeBeforeUpdate = farmacologicoRepository.findAll().size();

        // Update the farmacologico
        Farmacologico updatedFarmacologico = farmacologicoRepository.findById(farmacologico.getId()).get();
        // Disconnect from session so that the updates on updatedFarmacologico are not directly saved in db
        em.detach(updatedFarmacologico);
        updatedFarmacologico
            .assunzione(UPDATED_ASSUNZIONE)
            .giorniAllaScadenza(UPDATED_GIORNI_ALLA_SCADENZA)
            .avvertenze(UPDATED_AVVERTENZE);

        restFarmacologicoMockMvc.perform(put("/api/farmacologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFarmacologico)))
            .andExpect(status().isOk());

        // Validate the Farmacologico in the database
        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeUpdate);
        Farmacologico testFarmacologico = farmacologicoList.get(farmacologicoList.size() - 1);
        assertThat(testFarmacologico.getAssunzione()).isEqualTo(UPDATED_ASSUNZIONE);
        assertThat(testFarmacologico.getGiorniAllaScadenza()).isEqualTo(UPDATED_GIORNI_ALLA_SCADENZA);
        assertThat(testFarmacologico.getAvvertenze()).isEqualTo(UPDATED_AVVERTENZE);
    }

    @Test
    @Transactional
    public void updateNonExistingFarmacologico() throws Exception {
        int databaseSizeBeforeUpdate = farmacologicoRepository.findAll().size();

        // Create the Farmacologico

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmacologicoMockMvc.perform(put("/api/farmacologicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacologico)))
            .andExpect(status().isBadRequest());

        // Validate the Farmacologico in the database
        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFarmacologico() throws Exception {
        // Initialize the database
        farmacologicoRepository.saveAndFlush(farmacologico);

        int databaseSizeBeforeDelete = farmacologicoRepository.findAll().size();

        // Delete the farmacologico
        restFarmacologicoMockMvc.perform(delete("/api/farmacologicos/{id}", farmacologico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Farmacologico> farmacologicoList = farmacologicoRepository.findAll();
        assertThat(farmacologicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Farmacologico.class);
        Farmacologico farmacologico1 = new Farmacologico();
        farmacologico1.setId(1L);
        Farmacologico farmacologico2 = new Farmacologico();
        farmacologico2.setId(farmacologico1.getId());
        assertThat(farmacologico1).isEqualTo(farmacologico2);
        farmacologico2.setId(2L);
        assertThat(farmacologico1).isNotEqualTo(farmacologico2);
        farmacologico1.setId(null);
        assertThat(farmacologico1).isNotEqualTo(farmacologico2);
    }
}
