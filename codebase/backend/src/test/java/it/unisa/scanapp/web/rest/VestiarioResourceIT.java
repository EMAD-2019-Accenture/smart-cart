package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Vestiario;
import it.unisa.scanapp.repository.VestiarioRepository;
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
 * Integration tests for the {@link VestiarioResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class VestiarioResourceIT {

    private static final String DEFAULT_TAGLIA = "AAAAAAAAAA";
    private static final String UPDATED_TAGLIA = "BBBBBBBBBB";

    private static final String DEFAULT_COLORE = "AAAAAAAAAA";
    private static final String UPDATED_COLORE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIALE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIALE = "BBBBBBBBBB";

    private static final String DEFAULT_STAGLIONE = "AAAAAAAAAA";
    private static final String UPDATED_STAGLIONE = "BBBBBBBBBB";

    @Autowired
    private VestiarioRepository vestiarioRepository;

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

    private MockMvc restVestiarioMockMvc;

    private Vestiario vestiario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VestiarioResource vestiarioResource = new VestiarioResource(vestiarioRepository);
        this.restVestiarioMockMvc = MockMvcBuilders.standaloneSetup(vestiarioResource)
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
    public static Vestiario createEntity(EntityManager em) {
        Vestiario vestiario = new Vestiario()
            .taglia(DEFAULT_TAGLIA)
            .colore(DEFAULT_COLORE)
            .materiale(DEFAULT_MATERIALE)
            .staglione(DEFAULT_STAGLIONE);
        return vestiario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vestiario createUpdatedEntity(EntityManager em) {
        Vestiario vestiario = new Vestiario()
            .taglia(UPDATED_TAGLIA)
            .colore(UPDATED_COLORE)
            .materiale(UPDATED_MATERIALE)
            .staglione(UPDATED_STAGLIONE);
        return vestiario;
    }

    @BeforeEach
    public void initTest() {
        vestiario = createEntity(em);
    }

    @Test
    @Transactional
    public void createVestiario() throws Exception {
        int databaseSizeBeforeCreate = vestiarioRepository.findAll().size();

        // Create the Vestiario
        restVestiarioMockMvc.perform(post("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vestiario)))
            .andExpect(status().isCreated());

        // Validate the Vestiario in the database
        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeCreate + 1);
        Vestiario testVestiario = vestiarioList.get(vestiarioList.size() - 1);
        assertThat(testVestiario.getTaglia()).isEqualTo(DEFAULT_TAGLIA);
        assertThat(testVestiario.getColore()).isEqualTo(DEFAULT_COLORE);
        assertThat(testVestiario.getMateriale()).isEqualTo(DEFAULT_MATERIALE);
        assertThat(testVestiario.getStaglione()).isEqualTo(DEFAULT_STAGLIONE);
    }

    @Test
    @Transactional
    public void createVestiarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vestiarioRepository.findAll().size();

        // Create the Vestiario with an existing ID
        vestiario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVestiarioMockMvc.perform(post("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vestiario)))
            .andExpect(status().isBadRequest());

        // Validate the Vestiario in the database
        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTagliaIsRequired() throws Exception {
        int databaseSizeBeforeTest = vestiarioRepository.findAll().size();
        // set the field null
        vestiario.setTaglia(null);

        // Create the Vestiario, which fails.

        restVestiarioMockMvc.perform(post("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vestiario)))
            .andExpect(status().isBadRequest());

        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = vestiarioRepository.findAll().size();
        // set the field null
        vestiario.setColore(null);

        // Create the Vestiario, which fails.

        restVestiarioMockMvc.perform(post("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vestiario)))
            .andExpect(status().isBadRequest());

        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaterialeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vestiarioRepository.findAll().size();
        // set the field null
        vestiario.setMateriale(null);

        // Create the Vestiario, which fails.

        restVestiarioMockMvc.perform(post("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vestiario)))
            .andExpect(status().isBadRequest());

        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStaglioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = vestiarioRepository.findAll().size();
        // set the field null
        vestiario.setStaglione(null);

        // Create the Vestiario, which fails.

        restVestiarioMockMvc.perform(post("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vestiario)))
            .andExpect(status().isBadRequest());

        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVestiarios() throws Exception {
        // Initialize the database
        vestiarioRepository.saveAndFlush(vestiario);

        // Get all the vestiarioList
        restVestiarioMockMvc.perform(get("/api/vestiarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vestiario.getId().intValue())))
            .andExpect(jsonPath("$.[*].taglia").value(hasItem(DEFAULT_TAGLIA.toString())))
            .andExpect(jsonPath("$.[*].colore").value(hasItem(DEFAULT_COLORE.toString())))
            .andExpect(jsonPath("$.[*].materiale").value(hasItem(DEFAULT_MATERIALE.toString())))
            .andExpect(jsonPath("$.[*].staglione").value(hasItem(DEFAULT_STAGLIONE.toString())));
    }
    
    @Test
    @Transactional
    public void getVestiario() throws Exception {
        // Initialize the database
        vestiarioRepository.saveAndFlush(vestiario);

        // Get the vestiario
        restVestiarioMockMvc.perform(get("/api/vestiarios/{id}", vestiario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vestiario.getId().intValue()))
            .andExpect(jsonPath("$.taglia").value(DEFAULT_TAGLIA.toString()))
            .andExpect(jsonPath("$.colore").value(DEFAULT_COLORE.toString()))
            .andExpect(jsonPath("$.materiale").value(DEFAULT_MATERIALE.toString()))
            .andExpect(jsonPath("$.staglione").value(DEFAULT_STAGLIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVestiario() throws Exception {
        // Get the vestiario
        restVestiarioMockMvc.perform(get("/api/vestiarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVestiario() throws Exception {
        // Initialize the database
        vestiarioRepository.saveAndFlush(vestiario);

        int databaseSizeBeforeUpdate = vestiarioRepository.findAll().size();

        // Update the vestiario
        Vestiario updatedVestiario = vestiarioRepository.findById(vestiario.getId()).get();
        // Disconnect from session so that the updates on updatedVestiario are not directly saved in db
        em.detach(updatedVestiario);
        updatedVestiario
            .taglia(UPDATED_TAGLIA)
            .colore(UPDATED_COLORE)
            .materiale(UPDATED_MATERIALE)
            .staglione(UPDATED_STAGLIONE);

        restVestiarioMockMvc.perform(put("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVestiario)))
            .andExpect(status().isOk());

        // Validate the Vestiario in the database
        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeUpdate);
        Vestiario testVestiario = vestiarioList.get(vestiarioList.size() - 1);
        assertThat(testVestiario.getTaglia()).isEqualTo(UPDATED_TAGLIA);
        assertThat(testVestiario.getColore()).isEqualTo(UPDATED_COLORE);
        assertThat(testVestiario.getMateriale()).isEqualTo(UPDATED_MATERIALE);
        assertThat(testVestiario.getStaglione()).isEqualTo(UPDATED_STAGLIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingVestiario() throws Exception {
        int databaseSizeBeforeUpdate = vestiarioRepository.findAll().size();

        // Create the Vestiario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVestiarioMockMvc.perform(put("/api/vestiarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vestiario)))
            .andExpect(status().isBadRequest());

        // Validate the Vestiario in the database
        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVestiario() throws Exception {
        // Initialize the database
        vestiarioRepository.saveAndFlush(vestiario);

        int databaseSizeBeforeDelete = vestiarioRepository.findAll().size();

        // Delete the vestiario
        restVestiarioMockMvc.perform(delete("/api/vestiarios/{id}", vestiario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vestiario> vestiarioList = vestiarioRepository.findAll();
        assertThat(vestiarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vestiario.class);
        Vestiario vestiario1 = new Vestiario();
        vestiario1.setId(1L);
        Vestiario vestiario2 = new Vestiario();
        vestiario2.setId(vestiario1.getId());
        assertThat(vestiario1).isEqualTo(vestiario2);
        vestiario2.setId(2L);
        assertThat(vestiario1).isNotEqualTo(vestiario2);
        vestiario1.setId(null);
        assertThat(vestiario1).isNotEqualTo(vestiario2);
    }
}
