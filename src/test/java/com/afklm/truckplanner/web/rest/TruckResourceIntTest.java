package com.afklm.truckplanner.web.rest;

import com.afklm.truckplanner.TruckplannerApp;

import com.afklm.truckplanner.domain.Truck;
import com.afklm.truckplanner.repository.TruckRepository;
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

import com.afklm.truckplanner.domain.enumeration.Cool;
import com.afklm.truckplanner.domain.enumeration.TopType;
/**
 * Test class for the TruckResource REST controller.
 *
 * @see TruckResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckplannerApp.class)
public class TruckResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TRUCK_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_TRUCK_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_TRAILER_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_TRAILER_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_TRUCK_LICENSE = "AAAAAAAAAA";
    private static final String UPDATED_TRUCK_LICENSE = "BBBBBBBBBB";

    private static final String DEFAULT_TRAILER_LICENSE = "AAAAAAAAAA";
    private static final String UPDATED_TRAILER_LICENSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ADR = false;
    private static final Boolean UPDATED_ADR = true;

    private static final Boolean DEFAULT_BIG = false;
    private static final Boolean UPDATED_BIG = true;

    private static final Boolean DEFAULT_ROLLER_BED = false;
    private static final Boolean UPDATED_ROLLER_BED = true;

    private static final Cool DEFAULT_COOL = Cool.NO;
    private static final Cool UPDATED_COOL = Cool.FNA;

    private static final Integer DEFAULT_MIN_TEMPERATURE = 1;
    private static final Integer UPDATED_MIN_TEMPERATURE = 2;

    private static final Integer DEFAULT_MAX_TEMPERATURE = 1;
    private static final Integer UPDATED_MAX_TEMPERATURE = 2;

    private static final Integer DEFAULT_MAXIMUM_HEIGHT = 1;
    private static final Integer UPDATED_MAXIMUM_HEIGHT = 2;

    private static final TopType DEFAULT_TOP_TYPE = TopType.SOFT;
    private static final TopType UPDATED_TOP_TYPE = TopType.HARD;

    @Autowired
    private TruckRepository truckRepository;

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

    private MockMvc restTruckMockMvc;

    private Truck truck;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TruckResource truckResource = new TruckResource(truckRepository);
        this.restTruckMockMvc = MockMvcBuilders.standaloneSetup(truckResource)
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
    public static Truck createEntity(EntityManager em) {
        Truck truck = new Truck()
            .number(DEFAULT_NUMBER)
            .truckCountry(DEFAULT_TRUCK_COUNTRY)
            .trailerCountry(DEFAULT_TRAILER_COUNTRY)
            .truckLicense(DEFAULT_TRUCK_LICENSE)
            .trailerLicense(DEFAULT_TRAILER_LICENSE)
            .adr(DEFAULT_ADR)
            .big(DEFAULT_BIG)
            .rollerBed(DEFAULT_ROLLER_BED)
            .cool(DEFAULT_COOL)
            .minTemperature(DEFAULT_MIN_TEMPERATURE)
            .maxTemperature(DEFAULT_MAX_TEMPERATURE)
            .maximumHeight(DEFAULT_MAXIMUM_HEIGHT)
            .topType(DEFAULT_TOP_TYPE);
        return truck;
    }

    @Before
    public void initTest() {
        truck = createEntity(em);
    }

    @Test
    @Transactional
    public void createTruck() throws Exception {
        int databaseSizeBeforeCreate = truckRepository.findAll().size();

        // Create the Truck
        restTruckMockMvc.perform(post("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truck)))
            .andExpect(status().isCreated());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeCreate + 1);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testTruck.getTruckCountry()).isEqualTo(DEFAULT_TRUCK_COUNTRY);
        assertThat(testTruck.getTrailerCountry()).isEqualTo(DEFAULT_TRAILER_COUNTRY);
        assertThat(testTruck.getTruckLicense()).isEqualTo(DEFAULT_TRUCK_LICENSE);
        assertThat(testTruck.getTrailerLicense()).isEqualTo(DEFAULT_TRAILER_LICENSE);
        assertThat(testTruck.isAdr()).isEqualTo(DEFAULT_ADR);
        assertThat(testTruck.isBig()).isEqualTo(DEFAULT_BIG);
        assertThat(testTruck.isRollerBed()).isEqualTo(DEFAULT_ROLLER_BED);
        assertThat(testTruck.getCool()).isEqualTo(DEFAULT_COOL);
        assertThat(testTruck.getMinTemperature()).isEqualTo(DEFAULT_MIN_TEMPERATURE);
        assertThat(testTruck.getMaxTemperature()).isEqualTo(DEFAULT_MAX_TEMPERATURE);
        assertThat(testTruck.getMaximumHeight()).isEqualTo(DEFAULT_MAXIMUM_HEIGHT);
        assertThat(testTruck.getTopType()).isEqualTo(DEFAULT_TOP_TYPE);
    }

    @Test
    @Transactional
    public void createTruckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = truckRepository.findAll().size();

        // Create the Truck with an existing ID
        truck.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckMockMvc.perform(post("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truck)))
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrucks() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the truckList
        restTruckMockMvc.perform(get("/api/trucks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truck.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].truckCountry").value(hasItem(DEFAULT_TRUCK_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].trailerCountry").value(hasItem(DEFAULT_TRAILER_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].truckLicense").value(hasItem(DEFAULT_TRUCK_LICENSE.toString())))
            .andExpect(jsonPath("$.[*].trailerLicense").value(hasItem(DEFAULT_TRAILER_LICENSE.toString())))
            .andExpect(jsonPath("$.[*].adr").value(hasItem(DEFAULT_ADR.booleanValue())))
            .andExpect(jsonPath("$.[*].big").value(hasItem(DEFAULT_BIG.booleanValue())))
            .andExpect(jsonPath("$.[*].rollerBed").value(hasItem(DEFAULT_ROLLER_BED.booleanValue())))
            .andExpect(jsonPath("$.[*].cool").value(hasItem(DEFAULT_COOL.toString())))
            .andExpect(jsonPath("$.[*].minTemperature").value(hasItem(DEFAULT_MIN_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].maxTemperature").value(hasItem(DEFAULT_MAX_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].maximumHeight").value(hasItem(DEFAULT_MAXIMUM_HEIGHT)))
            .andExpect(jsonPath("$.[*].topType").value(hasItem(DEFAULT_TOP_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get the truck
        restTruckMockMvc.perform(get("/api/trucks/{id}", truck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(truck.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.truckCountry").value(DEFAULT_TRUCK_COUNTRY.toString()))
            .andExpect(jsonPath("$.trailerCountry").value(DEFAULT_TRAILER_COUNTRY.toString()))
            .andExpect(jsonPath("$.truckLicense").value(DEFAULT_TRUCK_LICENSE.toString()))
            .andExpect(jsonPath("$.trailerLicense").value(DEFAULT_TRAILER_LICENSE.toString()))
            .andExpect(jsonPath("$.adr").value(DEFAULT_ADR.booleanValue()))
            .andExpect(jsonPath("$.big").value(DEFAULT_BIG.booleanValue()))
            .andExpect(jsonPath("$.rollerBed").value(DEFAULT_ROLLER_BED.booleanValue()))
            .andExpect(jsonPath("$.cool").value(DEFAULT_COOL.toString()))
            .andExpect(jsonPath("$.minTemperature").value(DEFAULT_MIN_TEMPERATURE))
            .andExpect(jsonPath("$.maxTemperature").value(DEFAULT_MAX_TEMPERATURE))
            .andExpect(jsonPath("$.maximumHeight").value(DEFAULT_MAXIMUM_HEIGHT))
            .andExpect(jsonPath("$.topType").value(DEFAULT_TOP_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTruck() throws Exception {
        // Get the truck
        restTruckMockMvc.perform(get("/api/trucks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

        // Update the truck
        Truck updatedTruck = truckRepository.findById(truck.getId()).get();
        // Disconnect from session so that the updates on updatedTruck are not directly saved in db
        em.detach(updatedTruck);
        updatedTruck
            .number(UPDATED_NUMBER)
            .truckCountry(UPDATED_TRUCK_COUNTRY)
            .trailerCountry(UPDATED_TRAILER_COUNTRY)
            .truckLicense(UPDATED_TRUCK_LICENSE)
            .trailerLicense(UPDATED_TRAILER_LICENSE)
            .adr(UPDATED_ADR)
            .big(UPDATED_BIG)
            .rollerBed(UPDATED_ROLLER_BED)
            .cool(UPDATED_COOL)
            .minTemperature(UPDATED_MIN_TEMPERATURE)
            .maxTemperature(UPDATED_MAX_TEMPERATURE)
            .maximumHeight(UPDATED_MAXIMUM_HEIGHT)
            .topType(UPDATED_TOP_TYPE);

        restTruckMockMvc.perform(put("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTruck)))
            .andExpect(status().isOk());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testTruck.getTruckCountry()).isEqualTo(UPDATED_TRUCK_COUNTRY);
        assertThat(testTruck.getTrailerCountry()).isEqualTo(UPDATED_TRAILER_COUNTRY);
        assertThat(testTruck.getTruckLicense()).isEqualTo(UPDATED_TRUCK_LICENSE);
        assertThat(testTruck.getTrailerLicense()).isEqualTo(UPDATED_TRAILER_LICENSE);
        assertThat(testTruck.isAdr()).isEqualTo(UPDATED_ADR);
        assertThat(testTruck.isBig()).isEqualTo(UPDATED_BIG);
        assertThat(testTruck.isRollerBed()).isEqualTo(UPDATED_ROLLER_BED);
        assertThat(testTruck.getCool()).isEqualTo(UPDATED_COOL);
        assertThat(testTruck.getMinTemperature()).isEqualTo(UPDATED_MIN_TEMPERATURE);
        assertThat(testTruck.getMaxTemperature()).isEqualTo(UPDATED_MAX_TEMPERATURE);
        assertThat(testTruck.getMaximumHeight()).isEqualTo(UPDATED_MAXIMUM_HEIGHT);
        assertThat(testTruck.getTopType()).isEqualTo(UPDATED_TOP_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

        // Create the Truck

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc.perform(put("/api/trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truck)))
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        int databaseSizeBeforeDelete = truckRepository.findAll().size();

        // Delete the truck
        restTruckMockMvc.perform(delete("/api/trucks/{id}", truck.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Truck.class);
        Truck truck1 = new Truck();
        truck1.setId(1L);
        Truck truck2 = new Truck();
        truck2.setId(truck1.getId());
        assertThat(truck1).isEqualTo(truck2);
        truck2.setId(2L);
        assertThat(truck1).isNotEqualTo(truck2);
        truck1.setId(null);
        assertThat(truck1).isNotEqualTo(truck2);
    }
}
