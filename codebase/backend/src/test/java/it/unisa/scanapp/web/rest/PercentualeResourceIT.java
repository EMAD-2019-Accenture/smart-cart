package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Percentuale;
import it.unisa.scanapp.repository.PercentualeRepository;
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
 * Integration tests for the {@link PercentualeResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class PercentualeResourceIT {

    private static final LocalDate DEFAULT_INIZIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INIZIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INIZIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FINE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_VALORE = 0D;
    private static final Double UPDATED_VALORE = 1D;
    private static final Double SMALLER_VALORE = 0D - 1D;

    @Autowired
    private PercentualeRepository percentualeRepository;

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

    private MockMvc restPercentualeMockMvc;

    private Percentuale percentuale;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PercentualeResource percentualeResource = new PercentualeResource(percentualeRepository);
        this.restPercentualeMockMvc = MockMvcBuilders.standaloneSetup(percentualeResource)
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
    public static Percentuale createEntity(EntityManager em) {
        Percentuale percentuale = new Percentuale()
            .inizio(DEFAULT_INIZIO)
            .fine(DEFAULT_FINE)
            .valore(DEFAULT_VALORE);
        return percentuale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Percentuale createUpdatedEntity(EntityManager em) {
        Percentuale percentuale = new Percentuale()
            .inizio(UPDATED_INIZIO)
            .fine(UPDATED_FINE)
            .valore(UPDATED_VALORE);
        return percentuale;
    }

    @BeforeEach
    public void initTest() {
        percentuale = createEntity(em);
    }

    @Test
    @Transactional
    public void createPercentuale() throws Exception {
        int databaseSizeBeforeCreate = percentualeRepository.findAll().size();

        // Create the Percentuale
        restPercentualeMockMvc.perform(post("/api/percentuales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentuale)))
            .andExpect(status().isCreated());

        // Validate the Percentuale in the database
        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeCreate + 1);
        Percentuale testPercentuale = percentualeList.get(percentualeList.size() - 1);
        assertThat(testPercentuale.getInizio()).isEqualTo(DEFAULT_INIZIO);
        assertThat(testPercentuale.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testPercentuale.getValore()).isEqualTo(DEFAULT_VALORE);
    }

    @Test
    @Transactional
    public void createPercentualeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = percentualeRepository.findAll().size();

        // Create the Percentuale with an existing ID
        percentuale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPercentualeMockMvc.perform(post("/api/percentuales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentuale)))
            .andExpect(status().isBadRequest());

        // Validate the Percentuale in the database
        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = percentualeRepository.findAll().size();
        // set the field null
        percentuale.setInizio(null);

        // Create the Percentuale, which fails.

        restPercentualeMockMvc.perform(post("/api/percentuales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentuale)))
            .andExpect(status().isBadRequest());

        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFineIsRequired() throws Exception {
        int databaseSizeBeforeTest = percentualeRepository.findAll().size();
        // set the field null
        percentuale.setFine(null);

        // Create the Percentuale, which fails.

        restPercentualeMockMvc.perform(post("/api/percentuales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentuale)))
            .andExpect(status().isBadRequest());

        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = percentualeRepository.findAll().size();
        // set the field null
        percentuale.setValore(null);

        // Create the Percentuale, which fails.

        restPercentualeMockMvc.perform(post("/api/percentuales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentuale)))
            .andExpect(status().isBadRequest());

        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPercentuales() throws Exception {
        // Initialize the database
        percentualeRepository.saveAndFlush(percentuale);

        // Get all the percentualeList
        restPercentualeMockMvc.perform(get("/api/percentuales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(percentuale.getId().intValue())))
            .andExpect(jsonPath("$.[*].inizio").value(hasItem(DEFAULT_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.toString())))
            .andExpect(jsonPath("$.[*].valore").value(hasItem(DEFAULT_VALORE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPercentuale() throws Exception {
        // Initialize the database
        percentualeRepository.saveAndFlush(percentuale);

        // Get the percentuale
        restPercentualeMockMvc.perform(get("/api/percentuales/{id}", percentuale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(percentuale.getId().intValue()))
            .andExpect(jsonPath("$.inizio").value(DEFAULT_INIZIO.toString()))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE.toString()))
            .andExpect(jsonPath("$.valore").value(DEFAULT_VALORE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPercentuale() throws Exception {
        // Get the percentuale
        restPercentualeMockMvc.perform(get("/api/percentuales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePercentuale() throws Exception {
        // Initialize the database
        percentualeRepository.saveAndFlush(percentuale);

        int databaseSizeBeforeUpdate = percentualeRepository.findAll().size();

        // Update the percentuale
        Percentuale updatedPercentuale = percentualeRepository.findById(percentuale.getId()).get();
        // Disconnect from session so that the updates on updatedPercentuale are not directly saved in db
        em.detach(updatedPercentuale);
        updatedPercentuale
            .inizio(UPDATED_INIZIO)
            .fine(UPDATED_FINE)
            .valore(UPDATED_VALORE);

        restPercentualeMockMvc.perform(put("/api/percentuales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPercentuale)))
            .andExpect(status().isOk());

        // Validate the Percentuale in the database
        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeUpdate);
        Percentuale testPercentuale = percentualeList.get(percentualeList.size() - 1);
        assertThat(testPercentuale.getInizio()).isEqualTo(UPDATED_INIZIO);
        assertThat(testPercentuale.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testPercentuale.getValore()).isEqualTo(UPDATED_VALORE);
    }

    @Test
    @Transactional
    public void updateNonExistingPercentuale() throws Exception {
        int databaseSizeBeforeUpdate = percentualeRepository.findAll().size();

        // Create the Percentuale

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPercentualeMockMvc.perform(put("/api/percentuales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentuale)))
            .andExpect(status().isBadRequest());

        // Validate the Percentuale in the database
        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePercentuale() throws Exception {
        // Initialize the database
        percentualeRepository.saveAndFlush(percentuale);

        int databaseSizeBeforeDelete = percentualeRepository.findAll().size();

        // Delete the percentuale
        restPercentualeMockMvc.perform(delete("/api/percentuales/{id}", percentuale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Percentuale> percentualeList = percentualeRepository.findAll();
        assertThat(percentualeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Percentuale.class);
        Percentuale percentuale1 = new Percentuale();
        percentuale1.setId(1L);
        Percentuale percentuale2 = new Percentuale();
        percentuale2.setId(percentuale1.getId());
        assertThat(percentuale1).isEqualTo(percentuale2);
        percentuale2.setId(2L);
        assertThat(percentuale1).isNotEqualTo(percentuale2);
        percentuale1.setId(null);
        assertThat(percentuale1).isNotEqualTo(percentuale2);
    }
}
