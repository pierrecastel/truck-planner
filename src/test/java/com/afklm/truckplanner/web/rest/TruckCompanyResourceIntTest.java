package com.afklm.truckplanner.web.rest;

import com.afklm.truckplanner.TruckplannerApp;

import com.afklm.truckplanner.domain.TruckCompany;
import com.afklm.truckplanner.repository.TruckCompanyRepository;
import com.afklm.truckplanner.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.afklm.truckplanner.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.afklm.truckplanner.domain.enumeration.CommunicationMode;
/**
 * Test class for the TruckCompanyResource REST controller.
 *
 * @see TruckCompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckplannerApp.class)
public class TruckCompanyResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final CommunicationMode DEFAULT_COMUNICATION_MODE = CommunicationMode.API;
    private static final CommunicationMode UPDATED_COMUNICATION_MODE = CommunicationMode.MAIL;

    @Autowired
    private TruckCompanyRepository truckCompanyRepository;

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

    private MockMvc restTruckCompanyMockMvc;

    private TruckCompany truckCompany;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TruckCompanyResource truckCompanyResource = new TruckCompanyResource(truckCompanyRepository);
        this.restTruckCompanyMockMvc = MockMvcBuilders.standaloneSetup(truckCompanyResource)
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
    public static TruckCompany createEntity(EntityManager em) {
        TruckCompany truckCompany = new TruckCompany()
            .email(DEFAULT_EMAIL)
            .comunicationMode(DEFAULT_COMUNICATION_MODE);
        return truckCompany;
    }

    @Before
    public void initTest() {
        truckCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createTruckCompany() throws Exception {
        int databaseSizeBeforeCreate = truckCompanyRepository.findAll().size();

        // Create the TruckCompany
        restTruckCompanyMockMvc.perform(post("/api/truck-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truckCompany)))
            .andExpect(status().isCreated());

        // Validate the TruckCompany in the database
        List<TruckCompany> truckCompanyList = truckCompanyRepository.findAll();
        assertThat(truckCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        TruckCompany testTruckCompany = truckCompanyList.get(truckCompanyList.size() - 1);
        assertThat(testTruckCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTruckCompany.getComunicationMode()).isEqualTo(DEFAULT_COMUNICATION_MODE);
    }

    @Test
    @Transactional
    public void createTruckCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = truckCompanyRepository.findAll().size();

        // Create the TruckCompany with an existing ID
        truckCompany.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckCompanyMockMvc.perform(post("/api/truck-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truckCompany)))
            .andExpect(status().isBadRequest());

        // Validate the TruckCompany in the database
        List<TruckCompany> truckCompanyList = truckCompanyRepository.findAll();
        assertThat(truckCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTruckCompanies() throws Exception {
        // Initialize the database
        truckCompanyRepository.saveAndFlush(truckCompany);

        // Get all the truckCompanyList
        restTruckCompanyMockMvc.perform(get("/api/truck-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truckCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].comunicationMode").value(hasItem(DEFAULT_COMUNICATION_MODE.toString())));
    }
    
    @Test
    @Transactional
    public void getTruckCompany() throws Exception {
        // Initialize the database
        truckCompanyRepository.saveAndFlush(truckCompany);

        // Get the truckCompany
        restTruckCompanyMockMvc.perform(get("/api/truck-companies/{id}", truckCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(truckCompany.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.comunicationMode").value(DEFAULT_COMUNICATION_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTruckCompany() throws Exception {
        // Get the truckCompany
        restTruckCompanyMockMvc.perform(get("/api/truck-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTruckCompany() throws Exception {
        // Initialize the database
        truckCompanyRepository.saveAndFlush(truckCompany);

        int databaseSizeBeforeUpdate = truckCompanyRepository.findAll().size();

        // Update the truckCompany
        TruckCompany updatedTruckCompany = truckCompanyRepository.findById(truckCompany.getId()).get();
        // Disconnect from session so that the updates on updatedTruckCompany are not directly saved in db
        em.detach(updatedTruckCompany);
        updatedTruckCompany
            .email(UPDATED_EMAIL)
            .comunicationMode(UPDATED_COMUNICATION_MODE);

        restTruckCompanyMockMvc.perform(put("/api/truck-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTruckCompany)))
            .andExpect(status().isOk());

        // Validate the TruckCompany in the database
        List<TruckCompany> truckCompanyList = truckCompanyRepository.findAll();
        assertThat(truckCompanyList).hasSize(databaseSizeBeforeUpdate);
        TruckCompany testTruckCompany = truckCompanyList.get(truckCompanyList.size() - 1);
        assertThat(testTruckCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTruckCompany.getComunicationMode()).isEqualTo(UPDATED_COMUNICATION_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingTruckCompany() throws Exception {
        int databaseSizeBeforeUpdate = truckCompanyRepository.findAll().size();

        // Create the TruckCompany

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckCompanyMockMvc.perform(put("/api/truck-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truckCompany)))
            .andExpect(status().isBadRequest());

        // Validate the TruckCompany in the database
        List<TruckCompany> truckCompanyList = truckCompanyRepository.findAll();
        assertThat(truckCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTruckCompany() throws Exception {
        // Initialize the database
        truckCompanyRepository.saveAndFlush(truckCompany);

        int databaseSizeBeforeDelete = truckCompanyRepository.findAll().size();

        // Delete the truckCompany
        restTruckCompanyMockMvc.perform(delete("/api/truck-companies/{id}", truckCompany.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TruckCompany> truckCompanyList = truckCompanyRepository.findAll();
        assertThat(truckCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TruckCompany.class);
        TruckCompany truckCompany1 = new TruckCompany();
        truckCompany1.setId(1L);
        TruckCompany truckCompany2 = new TruckCompany();
        truckCompany2.setId(truckCompany1.getId());
        assertThat(truckCompany1).isEqualTo(truckCompany2);
        truckCompany2.setId(2L);
        assertThat(truckCompany1).isNotEqualTo(truckCompany2);
        truckCompany1.setId(null);
        assertThat(truckCompany1).isNotEqualTo(truckCompany2);
    }
}
