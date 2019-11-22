package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Sconto;
import it.unisa.scanapp.repository.ScontoRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static it.unisa.scanapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScontoResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class ScontoResourceIT {

    private static final LocalDate DEFAULT_INIZIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INIZIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INIZIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FINE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_AMMONTARE = 0D;
    private static final Double UPDATED_AMMONTARE = 1D;
    private static final Double SMALLER_AMMONTARE = 0D - 1D;

    @Autowired
    private ScontoRepository scontoRepository;

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

    private MockMvc restScontoMockMvc;

    private Sconto sconto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScontoResource scontoResource = new ScontoResource(scontoRepository);
        this.restScontoMockMvc = MockMvcBuilders.standaloneSetup(scontoResource)
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
    public static Sconto createEntity(EntityManager em) {
        Sconto sconto = new Sconto()
            .inizio(DEFAULT_INIZIO)
            .fine(DEFAULT_FINE)
            .ammontare(DEFAULT_AMMONTARE);
        return sconto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sconto createUpdatedEntity(EntityManager em) {
        Sconto sconto = new Sconto()
            .inizio(UPDATED_INIZIO)
            .fine(UPDATED_FINE)
            .ammontare(UPDATED_AMMONTARE);
        return sconto;
    }

    @BeforeEach
    public void initTest() {
        sconto = createEntity(em);
    }

    @Test
    @Transactional
    public void createSconto() throws Exception {
        int databaseSizeBeforeCreate = scontoRepository.findAll().size();

        // Create the Sconto
        restScontoMockMvc.perform(post("/api/scontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isCreated());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeCreate + 1);
        Sconto testSconto = scontoList.get(scontoList.size() - 1);
        assertThat(testSconto.getInizio()).isEqualTo(DEFAULT_INIZIO);
        assertThat(testSconto.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testSconto.getAmmontare()).isEqualTo(DEFAULT_AMMONTARE);
    }

    @Test
    @Transactional
    public void createScontoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scontoRepository.findAll().size();

        // Create the Sconto with an existing ID
        sconto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScontoMockMvc.perform(post("/api/scontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isBadRequest());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = scontoRepository.findAll().size();
        // set the field null
        sconto.setInizio(null);

        // Create the Sconto, which fails.

        restScontoMockMvc.perform(post("/api/scontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isBadRequest());

        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFineIsRequired() throws Exception {
        int databaseSizeBeforeTest = scontoRepository.findAll().size();
        // set the field null
        sconto.setFine(null);

        // Create the Sconto, which fails.

        restScontoMockMvc.perform(post("/api/scontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isBadRequest());

        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmmontareIsRequired() throws Exception {
        int databaseSizeBeforeTest = scontoRepository.findAll().size();
        // set the field null
        sconto.setAmmontare(null);

        // Create the Sconto, which fails.

        restScontoMockMvc.perform(post("/api/scontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isBadRequest());

        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScontos() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        // Get all the scontoList
        restScontoMockMvc.perform(get("/api/scontos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sconto.getId().intValue())))
            .andExpect(jsonPath("$.[*].inizio").value(hasItem(DEFAULT_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.toString())))
            .andExpect(jsonPath("$.[*].ammontare").value(hasItem(DEFAULT_AMMONTARE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSconto() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        // Get the sconto
        restScontoMockMvc.perform(get("/api/scontos/{id}", sconto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sconto.getId().intValue()))
            .andExpect(jsonPath("$.inizio").value(DEFAULT_INIZIO.toString()))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE.toString()))
            .andExpect(jsonPath("$.ammontare").value(DEFAULT_AMMONTARE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSconto() throws Exception {
        // Get the sconto
        restScontoMockMvc.perform(get("/api/scontos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSconto() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();

        // Update the sconto
        Sconto updatedSconto = scontoRepository.findById(sconto.getId()).get();
        // Disconnect from session so that the updates on updatedSconto are not directly saved in db
        em.detach(updatedSconto);
        updatedSconto
            .inizio(UPDATED_INIZIO)
            .fine(UPDATED_FINE)
            .ammontare(UPDATED_AMMONTARE);

        restScontoMockMvc.perform(put("/api/scontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSconto)))
            .andExpect(status().isOk());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
        Sconto testSconto = scontoList.get(scontoList.size() - 1);
        assertThat(testSconto.getInizio()).isEqualTo(UPDATED_INIZIO);
        assertThat(testSconto.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testSconto.getAmmontare()).isEqualTo(UPDATED_AMMONTARE);
    }

    @Test
    @Transactional
    public void updateNonExistingSconto() throws Exception {
        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();

        // Create the Sconto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScontoMockMvc.perform(put("/api/scontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isBadRequest());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSconto() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        int databaseSizeBeforeDelete = scontoRepository.findAll().size();

        // Delete the sconto
        restScontoMockMvc.perform(delete("/api/scontos/{id}", sconto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sconto.class);
        Sconto sconto1 = new Sconto();
        sconto1.setId(1L);
        Sconto sconto2 = new Sconto();
        sconto2.setId(sconto1.getId());
        assertThat(sconto1).isEqualTo(sconto2);
        sconto2.setId(2L);
        assertThat(sconto1).isNotEqualTo(sconto2);
        sconto1.setId(null);
        assertThat(sconto1).isNotEqualTo(sconto2);
    }
}
