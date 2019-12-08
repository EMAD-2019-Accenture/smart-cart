package it.unisa.scanapp.web.rest;

import it.unisa.scanapp.SmartCartApp;
import it.unisa.scanapp.domain.Discount;
import it.unisa.scanapp.repository.DiscountRepository;
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

import it.unisa.scanapp.domain.enumeration.DiscountType;
/**
 * Integration tests for the {@link DiscountResource} REST controller.
 */
@SpringBootTest(classes = SmartCartApp.class)
public class DiscountResourceIT {

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_AMOUNT = 0D;
    private static final Double UPDATED_AMOUNT = 1D;
    private static final Double SMALLER_AMOUNT = 0D - 1D;

    private static final Double DEFAULT_PERVENT_VALUE = 0D;
    private static final Double UPDATED_PERVENT_VALUE = 1D;
    private static final Double SMALLER_PERVENT_VALUE = 0D - 1D;

    private static final Integer DEFAULT_CONDITION = 0;
    private static final Integer UPDATED_CONDITION = 1;
    private static final Integer SMALLER_CONDITION = 0 - 1;

    private static final Integer DEFAULT_FREE = 0;
    private static final Integer UPDATED_FREE = 1;
    private static final Integer SMALLER_FREE = 0 - 1;

    private static final DiscountType DEFAULT_TYPE = DiscountType.SIMPLE_DISCOUNT;
    private static final DiscountType UPDATED_TYPE = DiscountType.PERCENT_DISCOUNT;

    @Autowired
    private DiscountRepository discountRepository;

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

    private MockMvc restDiscountMockMvc;

    private Discount discount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscountResource discountResource = new DiscountResource(discountRepository);
        this.restDiscountMockMvc = MockMvcBuilders.standaloneSetup(discountResource)
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
    public static Discount createEntity(EntityManager em) {
        Discount discount = new Discount()
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .amount(DEFAULT_AMOUNT)
            .perventValue(DEFAULT_PERVENT_VALUE)
            .condition(DEFAULT_CONDITION)
            .free(DEFAULT_FREE)
            .type(DEFAULT_TYPE);
        return discount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discount createUpdatedEntity(EntityManager em) {
        Discount discount = new Discount()
            .start(UPDATED_START)
            .end(UPDATED_END)
            .amount(UPDATED_AMOUNT)
            .perventValue(UPDATED_PERVENT_VALUE)
            .condition(UPDATED_CONDITION)
            .free(UPDATED_FREE)
            .type(UPDATED_TYPE);
        return discount;
    }

    @BeforeEach
    public void initTest() {
        discount = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscount() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();

        // Create the Discount
        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isCreated());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate + 1);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testDiscount.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testDiscount.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDiscount.getPerventValue()).isEqualTo(DEFAULT_PERVENT_VALUE);
        assertThat(testDiscount.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testDiscount.getFree()).isEqualTo(DEFAULT_FREE);
        assertThat(testDiscount.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createDiscountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();

        // Create the Discount with an existing ID
        discount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setStart(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setEnd(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setAmount(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPerventValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setPerventValue(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setCondition(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setFree(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setType(null);

        // Create the Discount, which fails.

        restDiscountMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscounts() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList
        restDiscountMockMvc.perform(get("/api/discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discount.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].perventValue").value(hasItem(DEFAULT_PERVENT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].free").value(hasItem(DEFAULT_FREE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", discount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discount.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.perventValue").value(DEFAULT_PERVENT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION))
            .andExpect(jsonPath("$.free").value(DEFAULT_FREE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscount() throws Exception {
        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Update the discount
        Discount updatedDiscount = discountRepository.findById(discount.getId()).get();
        // Disconnect from session so that the updates on updatedDiscount are not directly saved in db
        em.detach(updatedDiscount);
        updatedDiscount
            .start(UPDATED_START)
            .end(UPDATED_END)
            .amount(UPDATED_AMOUNT)
            .perventValue(UPDATED_PERVENT_VALUE)
            .condition(UPDATED_CONDITION)
            .free(UPDATED_FREE)
            .type(UPDATED_TYPE);

        restDiscountMockMvc.perform(put("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscount)))
            .andExpect(status().isOk());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getStart()).isEqualTo(UPDATED_START);
        assertThat(testDiscount.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testDiscount.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDiscount.getPerventValue()).isEqualTo(UPDATED_PERVENT_VALUE);
        assertThat(testDiscount.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testDiscount.getFree()).isEqualTo(UPDATED_FREE);
        assertThat(testDiscount.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Create the Discount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountMockMvc.perform(put("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discount)))
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeDelete = discountRepository.findAll().size();

        // Delete the discount
        restDiscountMockMvc.perform(delete("/api/discounts/{id}", discount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discount.class);
        Discount discount1 = new Discount();
        discount1.setId(1L);
        Discount discount2 = new Discount();
        discount2.setId(discount1.getId());
        assertThat(discount1).isEqualTo(discount2);
        discount2.setId(2L);
        assertThat(discount1).isNotEqualTo(discount2);
        discount1.setId(null);
        assertThat(discount1).isNotEqualTo(discount2);
    }
}
