package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Transazione;
import it.unisa.scanapp.repository.TransazioneRepository;
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
 * Integration tests for the {@link TransazioneResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class TransazioneResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private TransazioneRepository transazioneRepository;

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

    private MockMvc restTransazioneMockMvc;

    private Transazione transazione;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransazioneResource transazioneResource = new TransazioneResource(transazioneRepository);
        this.restTransazioneMockMvc = MockMvcBuilders.standaloneSetup(transazioneResource)
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
    public static Transazione createEntity(EntityManager em) {
        Transazione transazione = new Transazione()
            .data(DEFAULT_DATA);
        return transazione;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transazione createUpdatedEntity(EntityManager em) {
        Transazione transazione = new Transazione()
            .data(UPDATED_DATA);
        return transazione;
    }

    @BeforeEach
    public void initTest() {
        transazione = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransazione() throws Exception {
        int databaseSizeBeforeCreate = transazioneRepository.findAll().size();

        // Create the Transazione
        restTransazioneMockMvc.perform(post("/api/transaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transazione)))
            .andExpect(status().isCreated());

        // Validate the Transazione in the database
        List<Transazione> transazioneList = transazioneRepository.findAll();
        assertThat(transazioneList).hasSize(databaseSizeBeforeCreate + 1);
        Transazione testTransazione = transazioneList.get(transazioneList.size() - 1);
        assertThat(testTransazione.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createTransazioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transazioneRepository.findAll().size();

        // Create the Transazione with an existing ID
        transazione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransazioneMockMvc.perform(post("/api/transaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transazione)))
            .andExpect(status().isBadRequest());

        // Validate the Transazione in the database
        List<Transazione> transazioneList = transazioneRepository.findAll();
        assertThat(transazioneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = transazioneRepository.findAll().size();
        // set the field null
        transazione.setData(null);

        // Create the Transazione, which fails.

        restTransazioneMockMvc.perform(post("/api/transaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transazione)))
            .andExpect(status().isBadRequest());

        List<Transazione> transazioneList = transazioneRepository.findAll();
        assertThat(transazioneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransaziones() throws Exception {
        // Initialize the database
        transazioneRepository.saveAndFlush(transazione);

        // Get all the transazioneList
        restTransazioneMockMvc.perform(get("/api/transaziones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getTransazione() throws Exception {
        // Initialize the database
        transazioneRepository.saveAndFlush(transazione);

        // Get the transazione
        restTransazioneMockMvc.perform(get("/api/transaziones/{id}", transazione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transazione.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransazione() throws Exception {
        // Get the transazione
        restTransazioneMockMvc.perform(get("/api/transaziones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransazione() throws Exception {
        // Initialize the database
        transazioneRepository.saveAndFlush(transazione);

        int databaseSizeBeforeUpdate = transazioneRepository.findAll().size();

        // Update the transazione
        Transazione updatedTransazione = transazioneRepository.findById(transazione.getId()).get();
        // Disconnect from session so that the updates on updatedTransazione are not directly saved in db
        em.detach(updatedTransazione);
        updatedTransazione
            .data(UPDATED_DATA);

        restTransazioneMockMvc.perform(put("/api/transaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransazione)))
            .andExpect(status().isOk());

        // Validate the Transazione in the database
        List<Transazione> transazioneList = transazioneRepository.findAll();
        assertThat(transazioneList).hasSize(databaseSizeBeforeUpdate);
        Transazione testTransazione = transazioneList.get(transazioneList.size() - 1);
        assertThat(testTransazione.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingTransazione() throws Exception {
        int databaseSizeBeforeUpdate = transazioneRepository.findAll().size();

        // Create the Transazione

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransazioneMockMvc.perform(put("/api/transaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transazione)))
            .andExpect(status().isBadRequest());

        // Validate the Transazione in the database
        List<Transazione> transazioneList = transazioneRepository.findAll();
        assertThat(transazioneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransazione() throws Exception {
        // Initialize the database
        transazioneRepository.saveAndFlush(transazione);

        int databaseSizeBeforeDelete = transazioneRepository.findAll().size();

        // Delete the transazione
        restTransazioneMockMvc.perform(delete("/api/transaziones/{id}", transazione.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transazione> transazioneList = transazioneRepository.findAll();
        assertThat(transazioneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transazione.class);
        Transazione transazione1 = new Transazione();
        transazione1.setId(1L);
        Transazione transazione2 = new Transazione();
        transazione2.setId(transazione1.getId());
        assertThat(transazione1).isEqualTo(transazione2);
        transazione2.setId(2L);
        assertThat(transazione1).isNotEqualTo(transazione2);
        transazione1.setId(null);
        assertThat(transazione1).isNotEqualTo(transazione2);
    }
}
