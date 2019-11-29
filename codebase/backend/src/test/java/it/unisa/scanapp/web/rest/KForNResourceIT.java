package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.SmartCartApp;
import it.unisa.scanapp.domain.KForN;
import it.unisa.scanapp.repository.KForNRepository;
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
 * Integration tests for the {@link KForNResource} REST controller.
 */
@SpringBootTest(classes = SmartCartApp.class)
public class KForNResourceIT {

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_CONDITION = 0;
    private static final Integer UPDATED_CONDITION = 1;
    private static final Integer SMALLER_CONDITION = 0 - 1;

    private static final Integer DEFAULT_FREE = 0;
    private static final Integer UPDATED_FREE = 1;
    private static final Integer SMALLER_FREE = 0 - 1;

    @Autowired
    private KForNRepository kForNRepository;

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

    private MockMvc restKForNMockMvc;

    private KForN kForN;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KForNResource kForNResource = new KForNResource(kForNRepository);
        this.restKForNMockMvc = MockMvcBuilders.standaloneSetup(kForNResource)
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
    public static KForN createEntity(EntityManager em) {
        KForN kForN = new KForN()
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .condition(DEFAULT_CONDITION)
            .free(DEFAULT_FREE);
        return kForN;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KForN createUpdatedEntity(EntityManager em) {
        KForN kForN = new KForN()
            .start(UPDATED_START)
            .end(UPDATED_END)
            .condition(UPDATED_CONDITION)
            .free(UPDATED_FREE);
        return kForN;
    }

    @BeforeEach
    public void initTest() {
        kForN = createEntity(em);
    }

    @Test
    @Transactional
    public void createKForN() throws Exception {
        int databaseSizeBeforeCreate = kForNRepository.findAll().size();

        // Create the KForN
        restKForNMockMvc.perform(post("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kForN)))
            .andExpect(status().isCreated());

        // Validate the KForN in the database
        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeCreate + 1);
        KForN testKForN = kForNList.get(kForNList.size() - 1);
        assertThat(testKForN.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testKForN.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testKForN.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testKForN.getFree()).isEqualTo(DEFAULT_FREE);
    }

    @Test
    @Transactional
    public void createKForNWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kForNRepository.findAll().size();

        // Create the KForN with an existing ID
        kForN.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKForNMockMvc.perform(post("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kForN)))
            .andExpect(status().isBadRequest());

        // Validate the KForN in the database
        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setStart(null);

        // Create the KForN, which fails.

        restKForNMockMvc.perform(post("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kForN)))
            .andExpect(status().isBadRequest());

        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setEnd(null);

        // Create the KForN, which fails.

        restKForNMockMvc.perform(post("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kForN)))
            .andExpect(status().isBadRequest());

        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setCondition(null);

        // Create the KForN, which fails.

        restKForNMockMvc.perform(post("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kForN)))
            .andExpect(status().isBadRequest());

        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setFree(null);

        // Create the KForN, which fails.

        restKForNMockMvc.perform(post("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kForN)))
            .andExpect(status().isBadRequest());

        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKForNS() throws Exception {
        // Initialize the database
        kForNRepository.saveAndFlush(kForN);

        // Get all the kForNList
        restKForNMockMvc.perform(get("/api/k-for-ns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kForN.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].free").value(hasItem(DEFAULT_FREE)));
    }
    
    @Test
    @Transactional
    public void getKForN() throws Exception {
        // Initialize the database
        kForNRepository.saveAndFlush(kForN);

        // Get the kForN
        restKForNMockMvc.perform(get("/api/k-for-ns/{id}", kForN.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kForN.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION))
            .andExpect(jsonPath("$.free").value(DEFAULT_FREE));
    }

    @Test
    @Transactional
    public void getNonExistingKForN() throws Exception {
        // Get the kForN
        restKForNMockMvc.perform(get("/api/k-for-ns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKForN() throws Exception {
        // Initialize the database
        kForNRepository.saveAndFlush(kForN);

        int databaseSizeBeforeUpdate = kForNRepository.findAll().size();

        // Update the kForN
        KForN updatedKForN = kForNRepository.findById(kForN.getId()).get();
        // Disconnect from session so that the updates on updatedKForN are not directly saved in db
        em.detach(updatedKForN);
        updatedKForN
            .start(UPDATED_START)
            .end(UPDATED_END)
            .condition(UPDATED_CONDITION)
            .free(UPDATED_FREE);

        restKForNMockMvc.perform(put("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKForN)))
            .andExpect(status().isOk());

        // Validate the KForN in the database
        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeUpdate);
        KForN testKForN = kForNList.get(kForNList.size() - 1);
        assertThat(testKForN.getStart()).isEqualTo(UPDATED_START);
        assertThat(testKForN.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testKForN.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testKForN.getFree()).isEqualTo(UPDATED_FREE);
    }

    @Test
    @Transactional
    public void updateNonExistingKForN() throws Exception {
        int databaseSizeBeforeUpdate = kForNRepository.findAll().size();

        // Create the KForN

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKForNMockMvc.perform(put("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kForN)))
            .andExpect(status().isBadRequest());

        // Validate the KForN in the database
        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKForN() throws Exception {
        // Initialize the database
        kForNRepository.saveAndFlush(kForN);

        int databaseSizeBeforeDelete = kForNRepository.findAll().size();

        // Delete the kForN
        restKForNMockMvc.perform(delete("/api/k-for-ns/{id}", kForN.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KForN.class);
        KForN kForN1 = new KForN();
        kForN1.setId(1L);
        KForN kForN2 = new KForN();
        kForN2.setId(kForN1.getId());
        assertThat(kForN1).isEqualTo(kForN2);
        kForN2.setId(2L);
        assertThat(kForN1).isNotEqualTo(kForN2);
        kForN1.setId(null);
        assertThat(kForN1).isNotEqualTo(kForN2);
    }
}
