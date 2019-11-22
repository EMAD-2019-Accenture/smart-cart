package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
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
@SpringBootTest(classes = ScanAppBackendApp.class)
public class KForNResourceIT {

    private static final LocalDate DEFAULT_INIZIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INIZIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INIZIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FINE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_CONDIZIONE = 0;
    private static final Integer UPDATED_CONDIZIONE = 1;
    private static final Integer SMALLER_CONDIZIONE = 0 - 1;

    private static final Integer DEFAULT_OMAGGIO = 0;
    private static final Integer UPDATED_OMAGGIO = 1;
    private static final Integer SMALLER_OMAGGIO = 0 - 1;

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
            .inizio(DEFAULT_INIZIO)
            .fine(DEFAULT_FINE)
            .condizione(DEFAULT_CONDIZIONE)
            .omaggio(DEFAULT_OMAGGIO);
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
            .inizio(UPDATED_INIZIO)
            .fine(UPDATED_FINE)
            .condizione(UPDATED_CONDIZIONE)
            .omaggio(UPDATED_OMAGGIO);
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
        assertThat(testKForN.getInizio()).isEqualTo(DEFAULT_INIZIO);
        assertThat(testKForN.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testKForN.getCondizione()).isEqualTo(DEFAULT_CONDIZIONE);
        assertThat(testKForN.getOmaggio()).isEqualTo(DEFAULT_OMAGGIO);
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
    public void checkInizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setInizio(null);

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
    public void checkFineIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setFine(null);

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
    public void checkCondizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setCondizione(null);

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
    public void checkOmaggioIsRequired() throws Exception {
        int databaseSizeBeforeTest = kForNRepository.findAll().size();
        // set the field null
        kForN.setOmaggio(null);

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
            .andExpect(jsonPath("$.[*].inizio").value(hasItem(DEFAULT_INIZIO.toString())))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.toString())))
            .andExpect(jsonPath("$.[*].condizione").value(hasItem(DEFAULT_CONDIZIONE)))
            .andExpect(jsonPath("$.[*].omaggio").value(hasItem(DEFAULT_OMAGGIO)));
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
            .andExpect(jsonPath("$.inizio").value(DEFAULT_INIZIO.toString()))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE.toString()))
            .andExpect(jsonPath("$.condizione").value(DEFAULT_CONDIZIONE))
            .andExpect(jsonPath("$.omaggio").value(DEFAULT_OMAGGIO));
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
            .inizio(UPDATED_INIZIO)
            .fine(UPDATED_FINE)
            .condizione(UPDATED_CONDIZIONE)
            .omaggio(UPDATED_OMAGGIO);

        restKForNMockMvc.perform(put("/api/k-for-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKForN)))
            .andExpect(status().isOk());

        // Validate the KForN in the database
        List<KForN> kForNList = kForNRepository.findAll();
        assertThat(kForNList).hasSize(databaseSizeBeforeUpdate);
        KForN testKForN = kForNList.get(kForNList.size() - 1);
        assertThat(testKForN.getInizio()).isEqualTo(UPDATED_INIZIO);
        assertThat(testKForN.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testKForN.getCondizione()).isEqualTo(UPDATED_CONDIZIONE);
        assertThat(testKForN.getOmaggio()).isEqualTo(UPDATED_OMAGGIO);
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
