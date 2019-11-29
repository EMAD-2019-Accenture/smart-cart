package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.SmartCartApp;
import it.unisa.scanapp.domain.PercentDiscount;
import it.unisa.scanapp.repository.PercentDiscountRepository;
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
 * Integration tests for the {@link PercentDiscountResource} REST controller.
 */
@SpringBootTest(classes = SmartCartApp.class)
public class PercentDiscountResourceIT {

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_VALUE = 0D;
    private static final Double UPDATED_VALUE = 1D;
    private static final Double SMALLER_VALUE = 0D - 1D;

    @Autowired
    private PercentDiscountRepository percentDiscountRepository;

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

    private MockMvc restPercentDiscountMockMvc;

    private PercentDiscount percentDiscount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PercentDiscountResource percentDiscountResource = new PercentDiscountResource(percentDiscountRepository);
        this.restPercentDiscountMockMvc = MockMvcBuilders.standaloneSetup(percentDiscountResource)
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
    public static PercentDiscount createEntity(EntityManager em) {
        PercentDiscount percentDiscount = new PercentDiscount()
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .value(DEFAULT_VALUE);
        return percentDiscount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PercentDiscount createUpdatedEntity(EntityManager em) {
        PercentDiscount percentDiscount = new PercentDiscount()
            .start(UPDATED_START)
            .end(UPDATED_END)
            .value(UPDATED_VALUE);
        return percentDiscount;
    }

    @BeforeEach
    public void initTest() {
        percentDiscount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPercentDiscount() throws Exception {
        int databaseSizeBeforeCreate = percentDiscountRepository.findAll().size();

        // Create the PercentDiscount
        restPercentDiscountMockMvc.perform(post("/api/percent-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentDiscount)))
            .andExpect(status().isCreated());

        // Validate the PercentDiscount in the database
        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeCreate + 1);
        PercentDiscount testPercentDiscount = percentDiscountList.get(percentDiscountList.size() - 1);
        assertThat(testPercentDiscount.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPercentDiscount.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testPercentDiscount.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPercentDiscountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = percentDiscountRepository.findAll().size();

        // Create the PercentDiscount with an existing ID
        percentDiscount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPercentDiscountMockMvc.perform(post("/api/percent-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentDiscount)))
            .andExpect(status().isBadRequest());

        // Validate the PercentDiscount in the database
        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = percentDiscountRepository.findAll().size();
        // set the field null
        percentDiscount.setStart(null);

        // Create the PercentDiscount, which fails.

        restPercentDiscountMockMvc.perform(post("/api/percent-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentDiscount)))
            .andExpect(status().isBadRequest());

        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = percentDiscountRepository.findAll().size();
        // set the field null
        percentDiscount.setEnd(null);

        // Create the PercentDiscount, which fails.

        restPercentDiscountMockMvc.perform(post("/api/percent-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentDiscount)))
            .andExpect(status().isBadRequest());

        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = percentDiscountRepository.findAll().size();
        // set the field null
        percentDiscount.setValue(null);

        // Create the PercentDiscount, which fails.

        restPercentDiscountMockMvc.perform(post("/api/percent-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentDiscount)))
            .andExpect(status().isBadRequest());

        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPercentDiscounts() throws Exception {
        // Initialize the database
        percentDiscountRepository.saveAndFlush(percentDiscount);

        // Get all the percentDiscountList
        restPercentDiscountMockMvc.perform(get("/api/percent-discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(percentDiscount.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPercentDiscount() throws Exception {
        // Initialize the database
        percentDiscountRepository.saveAndFlush(percentDiscount);

        // Get the percentDiscount
        restPercentDiscountMockMvc.perform(get("/api/percent-discounts/{id}", percentDiscount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(percentDiscount.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPercentDiscount() throws Exception {
        // Get the percentDiscount
        restPercentDiscountMockMvc.perform(get("/api/percent-discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePercentDiscount() throws Exception {
        // Initialize the database
        percentDiscountRepository.saveAndFlush(percentDiscount);

        int databaseSizeBeforeUpdate = percentDiscountRepository.findAll().size();

        // Update the percentDiscount
        PercentDiscount updatedPercentDiscount = percentDiscountRepository.findById(percentDiscount.getId()).get();
        // Disconnect from session so that the updates on updatedPercentDiscount are not directly saved in db
        em.detach(updatedPercentDiscount);
        updatedPercentDiscount
            .start(UPDATED_START)
            .end(UPDATED_END)
            .value(UPDATED_VALUE);

        restPercentDiscountMockMvc.perform(put("/api/percent-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPercentDiscount)))
            .andExpect(status().isOk());

        // Validate the PercentDiscount in the database
        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeUpdate);
        PercentDiscount testPercentDiscount = percentDiscountList.get(percentDiscountList.size() - 1);
        assertThat(testPercentDiscount.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPercentDiscount.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testPercentDiscount.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPercentDiscount() throws Exception {
        int databaseSizeBeforeUpdate = percentDiscountRepository.findAll().size();

        // Create the PercentDiscount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPercentDiscountMockMvc.perform(put("/api/percent-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentDiscount)))
            .andExpect(status().isBadRequest());

        // Validate the PercentDiscount in the database
        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePercentDiscount() throws Exception {
        // Initialize the database
        percentDiscountRepository.saveAndFlush(percentDiscount);

        int databaseSizeBeforeDelete = percentDiscountRepository.findAll().size();

        // Delete the percentDiscount
        restPercentDiscountMockMvc.perform(delete("/api/percent-discounts/{id}", percentDiscount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PercentDiscount> percentDiscountList = percentDiscountRepository.findAll();
        assertThat(percentDiscountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PercentDiscount.class);
        PercentDiscount percentDiscount1 = new PercentDiscount();
        percentDiscount1.setId(1L);
        PercentDiscount percentDiscount2 = new PercentDiscount();
        percentDiscount2.setId(percentDiscount1.getId());
        assertThat(percentDiscount1).isEqualTo(percentDiscount2);
        percentDiscount2.setId(2L);
        assertThat(percentDiscount1).isNotEqualTo(percentDiscount2);
        percentDiscount1.setId(null);
        assertThat(percentDiscount1).isNotEqualTo(percentDiscount2);
    }
}
