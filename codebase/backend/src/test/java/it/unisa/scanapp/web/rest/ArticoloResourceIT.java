package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.ScanAppBackendApp;
import it.unisa.scanapp.domain.Articolo;
import it.unisa.scanapp.repository.ArticoloRepository;
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
 * Integration tests for the {@link ArticoloResource} REST controller.
 */
@SpringBootTest(classes = ScanAppBackendApp.class)
public class ArticoloResourceIT {

    private static final String DEFAULT_BARCODE = "6656990229063";
    private static final String UPDATED_BARCODE = "0511789374721";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_ETICHETTA = "AAAAAAAAAA";
    private static final String UPDATED_ETICHETTA = "BBBBBBBBBB";

    private static final Double DEFAULT_PREZZO = 0D;
    private static final Double UPDATED_PREZZO = 1D;
    private static final Double SMALLER_PREZZO = 0D - 1D;

    private static final String DEFAULT_MARCHIO = "AAAAAAAAAA";
    private static final String UPDATED_MARCHIO = "BBBBBBBBBB";

    private static final Double DEFAULT_PESO_LORDO = 0D;
    private static final Double UPDATED_PESO_LORDO = 1D;
    private static final Double SMALLER_PESO_LORDO = 0D - 1D;

    private static final Double DEFAULT_PESO_NETTO = 0D;
    private static final Double UPDATED_PESO_NETTO = 1D;
    private static final Double SMALLER_PESO_NETTO = 0D - 1D;

    private static final String DEFAULT_PROVENIENZA = "AAAAAAAAAA";
    private static final String UPDATED_PROVENIENZA = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITA = 0;
    private static final Integer UPDATED_QUANTITA = 1;
    private static final Integer SMALLER_QUANTITA = 0 - 1;

    @Autowired
    private ArticoloRepository articoloRepository;

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

    private MockMvc restArticoloMockMvc;

    private Articolo articolo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticoloResource articoloResource = new ArticoloResource(articoloRepository);
        this.restArticoloMockMvc = MockMvcBuilders.standaloneSetup(articoloResource)
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
    public static Articolo createEntity(EntityManager em) {
        Articolo articolo = new Articolo()
            .barcode(DEFAULT_BARCODE)
            .descrizione(DEFAULT_DESCRIZIONE)
            .etichetta(DEFAULT_ETICHETTA)
            .prezzo(DEFAULT_PREZZO)
            .marchio(DEFAULT_MARCHIO)
            .pesoLordo(DEFAULT_PESO_LORDO)
            .pesoNetto(DEFAULT_PESO_NETTO)
            .provenienza(DEFAULT_PROVENIENZA)
            .quantita(DEFAULT_QUANTITA);
        return articolo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articolo createUpdatedEntity(EntityManager em) {
        Articolo articolo = new Articolo()
            .barcode(UPDATED_BARCODE)
            .descrizione(UPDATED_DESCRIZIONE)
            .etichetta(UPDATED_ETICHETTA)
            .prezzo(UPDATED_PREZZO)
            .marchio(UPDATED_MARCHIO)
            .pesoLordo(UPDATED_PESO_LORDO)
            .pesoNetto(UPDATED_PESO_NETTO)
            .provenienza(UPDATED_PROVENIENZA)
            .quantita(UPDATED_QUANTITA);
        return articolo;
    }

    @BeforeEach
    public void initTest() {
        articolo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticolo() throws Exception {
        int databaseSizeBeforeCreate = articoloRepository.findAll().size();

        // Create the Articolo
        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isCreated());

        // Validate the Articolo in the database
        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeCreate + 1);
        Articolo testArticolo = articoloList.get(articoloList.size() - 1);
        assertThat(testArticolo.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testArticolo.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testArticolo.getEtichetta()).isEqualTo(DEFAULT_ETICHETTA);
        assertThat(testArticolo.getPrezzo()).isEqualTo(DEFAULT_PREZZO);
        assertThat(testArticolo.getMarchio()).isEqualTo(DEFAULT_MARCHIO);
        assertThat(testArticolo.getPesoLordo()).isEqualTo(DEFAULT_PESO_LORDO);
        assertThat(testArticolo.getPesoNetto()).isEqualTo(DEFAULT_PESO_NETTO);
        assertThat(testArticolo.getProvenienza()).isEqualTo(DEFAULT_PROVENIENZA);
        assertThat(testArticolo.getQuantita()).isEqualTo(DEFAULT_QUANTITA);
    }

    @Test
    @Transactional
    public void createArticoloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articoloRepository.findAll().size();

        // Create the Articolo with an existing ID
        articolo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        // Validate the Articolo in the database
        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBarcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setBarcode(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescrizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setDescrizione(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrezzoIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setPrezzo(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarchioIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setMarchio(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPesoLordoIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setPesoLordo(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPesoNettoIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setPesoNetto(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProvenienzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setProvenienza(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = articoloRepository.findAll().size();
        // set the field null
        articolo.setQuantita(null);

        // Create the Articolo, which fails.

        restArticoloMockMvc.perform(post("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticolos() throws Exception {
        // Initialize the database
        articoloRepository.saveAndFlush(articolo);

        // Get all the articoloList
        restArticoloMockMvc.perform(get("/api/articolos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articolo.getId().intValue())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())))
            .andExpect(jsonPath("$.[*].etichetta").value(hasItem(DEFAULT_ETICHETTA.toString())))
            .andExpect(jsonPath("$.[*].prezzo").value(hasItem(DEFAULT_PREZZO.doubleValue())))
            .andExpect(jsonPath("$.[*].marchio").value(hasItem(DEFAULT_MARCHIO.toString())))
            .andExpect(jsonPath("$.[*].pesoLordo").value(hasItem(DEFAULT_PESO_LORDO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoNetto").value(hasItem(DEFAULT_PESO_NETTO.doubleValue())))
            .andExpect(jsonPath("$.[*].provenienza").value(hasItem(DEFAULT_PROVENIENZA.toString())))
            .andExpect(jsonPath("$.[*].quantita").value(hasItem(DEFAULT_QUANTITA)));
    }
    
    @Test
    @Transactional
    public void getArticolo() throws Exception {
        // Initialize the database
        articoloRepository.saveAndFlush(articolo);

        // Get the articolo
        restArticoloMockMvc.perform(get("/api/articolos/{id}", articolo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articolo.getId().intValue()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()))
            .andExpect(jsonPath("$.etichetta").value(DEFAULT_ETICHETTA.toString()))
            .andExpect(jsonPath("$.prezzo").value(DEFAULT_PREZZO.doubleValue()))
            .andExpect(jsonPath("$.marchio").value(DEFAULT_MARCHIO.toString()))
            .andExpect(jsonPath("$.pesoLordo").value(DEFAULT_PESO_LORDO.doubleValue()))
            .andExpect(jsonPath("$.pesoNetto").value(DEFAULT_PESO_NETTO.doubleValue()))
            .andExpect(jsonPath("$.provenienza").value(DEFAULT_PROVENIENZA.toString()))
            .andExpect(jsonPath("$.quantita").value(DEFAULT_QUANTITA));
    }

    @Test
    @Transactional
    public void getNonExistingArticolo() throws Exception {
        // Get the articolo
        restArticoloMockMvc.perform(get("/api/articolos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticolo() throws Exception {
        // Initialize the database
        articoloRepository.saveAndFlush(articolo);

        int databaseSizeBeforeUpdate = articoloRepository.findAll().size();

        // Update the articolo
        Articolo updatedArticolo = articoloRepository.findById(articolo.getId()).get();
        // Disconnect from session so that the updates on updatedArticolo are not directly saved in db
        em.detach(updatedArticolo);
        updatedArticolo
            .barcode(UPDATED_BARCODE)
            .descrizione(UPDATED_DESCRIZIONE)
            .etichetta(UPDATED_ETICHETTA)
            .prezzo(UPDATED_PREZZO)
            .marchio(UPDATED_MARCHIO)
            .pesoLordo(UPDATED_PESO_LORDO)
            .pesoNetto(UPDATED_PESO_NETTO)
            .provenienza(UPDATED_PROVENIENZA)
            .quantita(UPDATED_QUANTITA);

        restArticoloMockMvc.perform(put("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticolo)))
            .andExpect(status().isOk());

        // Validate the Articolo in the database
        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeUpdate);
        Articolo testArticolo = articoloList.get(articoloList.size() - 1);
        assertThat(testArticolo.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testArticolo.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testArticolo.getEtichetta()).isEqualTo(UPDATED_ETICHETTA);
        assertThat(testArticolo.getPrezzo()).isEqualTo(UPDATED_PREZZO);
        assertThat(testArticolo.getMarchio()).isEqualTo(UPDATED_MARCHIO);
        assertThat(testArticolo.getPesoLordo()).isEqualTo(UPDATED_PESO_LORDO);
        assertThat(testArticolo.getPesoNetto()).isEqualTo(UPDATED_PESO_NETTO);
        assertThat(testArticolo.getProvenienza()).isEqualTo(UPDATED_PROVENIENZA);
        assertThat(testArticolo.getQuantita()).isEqualTo(UPDATED_QUANTITA);
    }

    @Test
    @Transactional
    public void updateNonExistingArticolo() throws Exception {
        int databaseSizeBeforeUpdate = articoloRepository.findAll().size();

        // Create the Articolo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticoloMockMvc.perform(put("/api/articolos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articolo)))
            .andExpect(status().isBadRequest());

        // Validate the Articolo in the database
        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticolo() throws Exception {
        // Initialize the database
        articoloRepository.saveAndFlush(articolo);

        int databaseSizeBeforeDelete = articoloRepository.findAll().size();

        // Delete the articolo
        restArticoloMockMvc.perform(delete("/api/articolos/{id}", articolo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Articolo> articoloList = articoloRepository.findAll();
        assertThat(articoloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Articolo.class);
        Articolo articolo1 = new Articolo();
        articolo1.setId(1L);
        Articolo articolo2 = new Articolo();
        articolo2.setId(articolo1.getId());
        assertThat(articolo1).isEqualTo(articolo2);
        articolo2.setId(2L);
        assertThat(articolo1).isNotEqualTo(articolo2);
        articolo1.setId(null);
        assertThat(articolo1).isNotEqualTo(articolo2);
    }
}
