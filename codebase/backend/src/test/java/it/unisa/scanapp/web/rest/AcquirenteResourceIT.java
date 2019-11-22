package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Acquirente;
import it.unisa.scanapp.repository.AcquirenteRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static it.unisa.scanapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AcquirenteResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class AcquirenteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCITA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCITA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_NASCITA = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NAZIONALITA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIONALITA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VEGANO = false;
    private static final Boolean UPDATED_VEGANO = true;

    private static final Boolean DEFAULT_VEGETARIANO = false;
    private static final Boolean UPDATED_VEGETARIANO = true;

    private static final Boolean DEFAULT_CELIACO = false;
    private static final Boolean UPDATED_CELIACO = true;

    @Autowired
    private AcquirenteRepository acquirenteRepository;

    @Mock
    private AcquirenteRepository acquirenteRepositoryMock;

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

    private MockMvc restAcquirenteMockMvc;

    private Acquirente acquirente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcquirenteResource acquirenteResource = new AcquirenteResource(acquirenteRepository);
        this.restAcquirenteMockMvc = MockMvcBuilders.standaloneSetup(acquirenteResource)
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
    public static Acquirente createEntity(EntityManager em) {
        Acquirente acquirente = new Acquirente()
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .dataNascita(DEFAULT_DATA_NASCITA)
            .nazionalita(DEFAULT_NAZIONALITA)
            .vegano(DEFAULT_VEGANO)
            .vegetariano(DEFAULT_VEGETARIANO)
            .celiaco(DEFAULT_CELIACO);
        return acquirente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acquirente createUpdatedEntity(EntityManager em) {
        Acquirente acquirente = new Acquirente()
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .dataNascita(UPDATED_DATA_NASCITA)
            .nazionalita(UPDATED_NAZIONALITA)
            .vegano(UPDATED_VEGANO)
            .vegetariano(UPDATED_VEGETARIANO)
            .celiaco(UPDATED_CELIACO);
        return acquirente;
    }

    @BeforeEach
    public void initTest() {
        acquirente = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcquirente() throws Exception {
        int databaseSizeBeforeCreate = acquirenteRepository.findAll().size();

        // Create the Acquirente
        restAcquirenteMockMvc.perform(post("/api/acquirentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acquirente)))
            .andExpect(status().isCreated());

        // Validate the Acquirente in the database
        List<Acquirente> acquirenteList = acquirenteRepository.findAll();
        assertThat(acquirenteList).hasSize(databaseSizeBeforeCreate + 1);
        Acquirente testAcquirente = acquirenteList.get(acquirenteList.size() - 1);
        assertThat(testAcquirente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAcquirente.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testAcquirente.getDataNascita()).isEqualTo(DEFAULT_DATA_NASCITA);
        assertThat(testAcquirente.getNazionalita()).isEqualTo(DEFAULT_NAZIONALITA);
        assertThat(testAcquirente.isVegano()).isEqualTo(DEFAULT_VEGANO);
        assertThat(testAcquirente.isVegetariano()).isEqualTo(DEFAULT_VEGETARIANO);
        assertThat(testAcquirente.isCeliaco()).isEqualTo(DEFAULT_CELIACO);
    }

    @Test
    @Transactional
    public void createAcquirenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acquirenteRepository.findAll().size();

        // Create the Acquirente with an existing ID
        acquirente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcquirenteMockMvc.perform(post("/api/acquirentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acquirente)))
            .andExpect(status().isBadRequest());

        // Validate the Acquirente in the database
        List<Acquirente> acquirenteList = acquirenteRepository.findAll();
        assertThat(acquirenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acquirenteRepository.findAll().size();
        // set the field null
        acquirente.setNome(null);

        // Create the Acquirente, which fails.

        restAcquirenteMockMvc.perform(post("/api/acquirentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acquirente)))
            .andExpect(status().isBadRequest());

        List<Acquirente> acquirenteList = acquirenteRepository.findAll();
        assertThat(acquirenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acquirenteRepository.findAll().size();
        // set the field null
        acquirente.setCognome(null);

        // Create the Acquirente, which fails.

        restAcquirenteMockMvc.perform(post("/api/acquirentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acquirente)))
            .andExpect(status().isBadRequest());

        List<Acquirente> acquirenteList = acquirenteRepository.findAll();
        assertThat(acquirenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcquirentes() throws Exception {
        // Initialize the database
        acquirenteRepository.saveAndFlush(acquirente);

        // Get all the acquirenteList
        restAcquirenteMockMvc.perform(get("/api/acquirentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acquirente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].dataNascita").value(hasItem(DEFAULT_DATA_NASCITA.toString())))
            .andExpect(jsonPath("$.[*].nazionalita").value(hasItem(DEFAULT_NAZIONALITA.toString())))
            .andExpect(jsonPath("$.[*].vegano").value(hasItem(DEFAULT_VEGANO.booleanValue())))
            .andExpect(jsonPath("$.[*].vegetariano").value(hasItem(DEFAULT_VEGETARIANO.booleanValue())))
            .andExpect(jsonPath("$.[*].celiaco").value(hasItem(DEFAULT_CELIACO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAcquirentesWithEagerRelationshipsIsEnabled() throws Exception {
        AcquirenteResource acquirenteResource = new AcquirenteResource(acquirenteRepositoryMock);
        when(acquirenteRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAcquirenteMockMvc = MockMvcBuilders.standaloneSetup(acquirenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAcquirenteMockMvc.perform(get("/api/acquirentes?eagerload=true"))
        .andExpect(status().isOk());

        verify(acquirenteRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAcquirentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        AcquirenteResource acquirenteResource = new AcquirenteResource(acquirenteRepositoryMock);
            when(acquirenteRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAcquirenteMockMvc = MockMvcBuilders.standaloneSetup(acquirenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAcquirenteMockMvc.perform(get("/api/acquirentes?eagerload=true"))
        .andExpect(status().isOk());

            verify(acquirenteRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAcquirente() throws Exception {
        // Initialize the database
        acquirenteRepository.saveAndFlush(acquirente);

        // Get the acquirente
        restAcquirenteMockMvc.perform(get("/api/acquirentes/{id}", acquirente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acquirente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.dataNascita").value(DEFAULT_DATA_NASCITA.toString()))
            .andExpect(jsonPath("$.nazionalita").value(DEFAULT_NAZIONALITA.toString()))
            .andExpect(jsonPath("$.vegano").value(DEFAULT_VEGANO.booleanValue()))
            .andExpect(jsonPath("$.vegetariano").value(DEFAULT_VEGETARIANO.booleanValue()))
            .andExpect(jsonPath("$.celiaco").value(DEFAULT_CELIACO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAcquirente() throws Exception {
        // Get the acquirente
        restAcquirenteMockMvc.perform(get("/api/acquirentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcquirente() throws Exception {
        // Initialize the database
        acquirenteRepository.saveAndFlush(acquirente);

        int databaseSizeBeforeUpdate = acquirenteRepository.findAll().size();

        // Update the acquirente
        Acquirente updatedAcquirente = acquirenteRepository.findById(acquirente.getId()).get();
        // Disconnect from session so that the updates on updatedAcquirente are not directly saved in db
        em.detach(updatedAcquirente);
        updatedAcquirente
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .dataNascita(UPDATED_DATA_NASCITA)
            .nazionalita(UPDATED_NAZIONALITA)
            .vegano(UPDATED_VEGANO)
            .vegetariano(UPDATED_VEGETARIANO)
            .celiaco(UPDATED_CELIACO);

        restAcquirenteMockMvc.perform(put("/api/acquirentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcquirente)))
            .andExpect(status().isOk());

        // Validate the Acquirente in the database
        List<Acquirente> acquirenteList = acquirenteRepository.findAll();
        assertThat(acquirenteList).hasSize(databaseSizeBeforeUpdate);
        Acquirente testAcquirente = acquirenteList.get(acquirenteList.size() - 1);
        assertThat(testAcquirente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAcquirente.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testAcquirente.getDataNascita()).isEqualTo(UPDATED_DATA_NASCITA);
        assertThat(testAcquirente.getNazionalita()).isEqualTo(UPDATED_NAZIONALITA);
        assertThat(testAcquirente.isVegano()).isEqualTo(UPDATED_VEGANO);
        assertThat(testAcquirente.isVegetariano()).isEqualTo(UPDATED_VEGETARIANO);
        assertThat(testAcquirente.isCeliaco()).isEqualTo(UPDATED_CELIACO);
    }

    @Test
    @Transactional
    public void updateNonExistingAcquirente() throws Exception {
        int databaseSizeBeforeUpdate = acquirenteRepository.findAll().size();

        // Create the Acquirente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcquirenteMockMvc.perform(put("/api/acquirentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acquirente)))
            .andExpect(status().isBadRequest());

        // Validate the Acquirente in the database
        List<Acquirente> acquirenteList = acquirenteRepository.findAll();
        assertThat(acquirenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcquirente() throws Exception {
        // Initialize the database
        acquirenteRepository.saveAndFlush(acquirente);

        int databaseSizeBeforeDelete = acquirenteRepository.findAll().size();

        // Delete the acquirente
        restAcquirenteMockMvc.perform(delete("/api/acquirentes/{id}", acquirente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Acquirente> acquirenteList = acquirenteRepository.findAll();
        assertThat(acquirenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acquirente.class);
        Acquirente acquirente1 = new Acquirente();
        acquirente1.setId(1L);
        Acquirente acquirente2 = new Acquirente();
        acquirente2.setId(acquirente1.getId());
        assertThat(acquirente1).isEqualTo(acquirente2);
        acquirente2.setId(2L);
        assertThat(acquirente1).isNotEqualTo(acquirente2);
        acquirente1.setId(null);
        assertThat(acquirente1).isNotEqualTo(acquirente2);
    }
}
