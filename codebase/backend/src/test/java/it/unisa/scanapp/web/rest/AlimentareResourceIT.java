package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Alimentare;
import it.unisa.scanapp.repository.AlimentareRepository;
import it.unisa.scanapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static it.unisa.scanapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlimentareResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class AlimentareResourceIT {

    private static final Integer DEFAULT_GIORNI_ALLA_SCADENZA = 0;
    private static final Integer UPDATED_GIORNI_ALLA_SCADENZA = 1;
    private static final Integer SMALLER_GIORNI_ALLA_SCADENZA = 0 - 1;

    private static final String DEFAULT_STAGIONE = "AAAAAAAAAA";
    private static final String UPDATED_STAGIONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BIO = false;
    private static final Boolean UPDATED_BIO = true;

    @Autowired
    private AlimentareRepository alimentareRepository;

    @Mock
    private AlimentareRepository alimentareRepositoryMock;

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

    private MockMvc restAlimentareMockMvc;

    private Alimentare alimentare;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlimentareResource alimentareResource = new AlimentareResource(alimentareRepository);
        this.restAlimentareMockMvc = MockMvcBuilders.standaloneSetup(alimentareResource)
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
    public static Alimentare createEntity(EntityManager em) {
        Alimentare alimentare = new Alimentare()
            .giorniAllaScadenza(DEFAULT_GIORNI_ALLA_SCADENZA)
            .stagione(DEFAULT_STAGIONE)
            .bio(DEFAULT_BIO);
        return alimentare;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alimentare createUpdatedEntity(EntityManager em) {
        Alimentare alimentare = new Alimentare()
            .giorniAllaScadenza(UPDATED_GIORNI_ALLA_SCADENZA)
            .stagione(UPDATED_STAGIONE)
            .bio(UPDATED_BIO);
        return alimentare;
    }

    @BeforeEach
    public void initTest() {
        alimentare = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlimentare() throws Exception {
        int databaseSizeBeforeCreate = alimentareRepository.findAll().size();

        // Create the Alimentare
        restAlimentareMockMvc.perform(post("/api/alimentares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentare)))
            .andExpect(status().isCreated());

        // Validate the Alimentare in the database
        List<Alimentare> alimentareList = alimentareRepository.findAll();
        assertThat(alimentareList).hasSize(databaseSizeBeforeCreate + 1);
        Alimentare testAlimentare = alimentareList.get(alimentareList.size() - 1);
        assertThat(testAlimentare.getGiorniAllaScadenza()).isEqualTo(DEFAULT_GIORNI_ALLA_SCADENZA);
        assertThat(testAlimentare.getStagione()).isEqualTo(DEFAULT_STAGIONE);
        assertThat(testAlimentare.isBio()).isEqualTo(DEFAULT_BIO);
    }

    @Test
    @Transactional
    public void createAlimentareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alimentareRepository.findAll().size();

        // Create the Alimentare with an existing ID
        alimentare.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlimentareMockMvc.perform(post("/api/alimentares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentare)))
            .andExpect(status().isBadRequest());

        // Validate the Alimentare in the database
        List<Alimentare> alimentareList = alimentareRepository.findAll();
        assertThat(alimentareList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGiorniAllaScadenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alimentareRepository.findAll().size();
        // set the field null
        alimentare.setGiorniAllaScadenza(null);

        // Create the Alimentare, which fails.

        restAlimentareMockMvc.perform(post("/api/alimentares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentare)))
            .andExpect(status().isBadRequest());

        List<Alimentare> alimentareList = alimentareRepository.findAll();
        assertThat(alimentareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlimentares() throws Exception {
        // Initialize the database
        alimentareRepository.saveAndFlush(alimentare);

        // Get all the alimentareList
        restAlimentareMockMvc.perform(get("/api/alimentares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alimentare.getId().intValue())))
            .andExpect(jsonPath("$.[*].giorniAllaScadenza").value(hasItem(DEFAULT_GIORNI_ALLA_SCADENZA)))
            .andExpect(jsonPath("$.[*].stagione").value(hasItem(DEFAULT_STAGIONE.toString())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAlimentaresWithEagerRelationshipsIsEnabled() throws Exception {
        AlimentareResource alimentareResource = new AlimentareResource(alimentareRepositoryMock);
        when(alimentareRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAlimentareMockMvc = MockMvcBuilders.standaloneSetup(alimentareResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAlimentareMockMvc.perform(get("/api/alimentares?eagerload=true"))
        .andExpect(status().isOk());

        verify(alimentareRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAlimentaresWithEagerRelationshipsIsNotEnabled() throws Exception {
        AlimentareResource alimentareResource = new AlimentareResource(alimentareRepositoryMock);
            when(alimentareRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAlimentareMockMvc = MockMvcBuilders.standaloneSetup(alimentareResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAlimentareMockMvc.perform(get("/api/alimentares?eagerload=true"))
        .andExpect(status().isOk());

            verify(alimentareRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAlimentare() throws Exception {
        // Initialize the database
        alimentareRepository.saveAndFlush(alimentare);

        // Get the alimentare
        restAlimentareMockMvc.perform(get("/api/alimentares/{id}", alimentare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alimentare.getId().intValue()))
            .andExpect(jsonPath("$.giorniAllaScadenza").value(DEFAULT_GIORNI_ALLA_SCADENZA))
            .andExpect(jsonPath("$.stagione").value(DEFAULT_STAGIONE.toString()))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlimentare() throws Exception {
        // Get the alimentare
        restAlimentareMockMvc.perform(get("/api/alimentares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlimentare() throws Exception {
        // Initialize the database
        alimentareRepository.saveAndFlush(alimentare);

        int databaseSizeBeforeUpdate = alimentareRepository.findAll().size();

        // Update the alimentare
        Alimentare updatedAlimentare = alimentareRepository.findById(alimentare.getId()).get();
        // Disconnect from session so that the updates on updatedAlimentare are not directly saved in db
        em.detach(updatedAlimentare);
        updatedAlimentare
            .giorniAllaScadenza(UPDATED_GIORNI_ALLA_SCADENZA)
            .stagione(UPDATED_STAGIONE)
            .bio(UPDATED_BIO);

        restAlimentareMockMvc.perform(put("/api/alimentares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlimentare)))
            .andExpect(status().isOk());

        // Validate the Alimentare in the database
        List<Alimentare> alimentareList = alimentareRepository.findAll();
        assertThat(alimentareList).hasSize(databaseSizeBeforeUpdate);
        Alimentare testAlimentare = alimentareList.get(alimentareList.size() - 1);
        assertThat(testAlimentare.getGiorniAllaScadenza()).isEqualTo(UPDATED_GIORNI_ALLA_SCADENZA);
        assertThat(testAlimentare.getStagione()).isEqualTo(UPDATED_STAGIONE);
        assertThat(testAlimentare.isBio()).isEqualTo(UPDATED_BIO);
    }

    @Test
    @Transactional
    public void updateNonExistingAlimentare() throws Exception {
        int databaseSizeBeforeUpdate = alimentareRepository.findAll().size();

        // Create the Alimentare

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlimentareMockMvc.perform(put("/api/alimentares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentare)))
            .andExpect(status().isBadRequest());

        // Validate the Alimentare in the database
        List<Alimentare> alimentareList = alimentareRepository.findAll();
        assertThat(alimentareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlimentare() throws Exception {
        // Initialize the database
        alimentareRepository.saveAndFlush(alimentare);

        int databaseSizeBeforeDelete = alimentareRepository.findAll().size();

        // Delete the alimentare
        restAlimentareMockMvc.perform(delete("/api/alimentares/{id}", alimentare.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alimentare> alimentareList = alimentareRepository.findAll();
        assertThat(alimentareList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alimentare.class);
        Alimentare alimentare1 = new Alimentare();
        alimentare1.setId(1L);
        Alimentare alimentare2 = new Alimentare();
        alimentare2.setId(alimentare1.getId());
        assertThat(alimentare1).isEqualTo(alimentare2);
        alimentare2.setId(2L);
        assertThat(alimentare1).isNotEqualTo(alimentare2);
        alimentare1.setId(null);
        assertThat(alimentare1).isNotEqualTo(alimentare2);
    }
}
