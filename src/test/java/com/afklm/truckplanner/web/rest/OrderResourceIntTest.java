package com.afklm.truckplanner.web.rest;

import com.afklm.truckplanner.TruckplannerApp;

import com.afklm.truckplanner.domain.Order;
import com.afklm.truckplanner.repository.OrderRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.afklm.truckplanner.web.rest.TestUtil.sameInstant;
import static com.afklm.truckplanner.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.afklm.truckplanner.domain.enumeration.OrderType;
import com.afklm.truckplanner.domain.enumeration.OrderStatus;
import com.afklm.truckplanner.domain.enumeration.Mode;
/**
 * Test class for the OrderResource REST controller.
 *
 * @see OrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckplannerApp.class)
public class OrderResourceIntTest {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final OrderType DEFAULT_ORDER_TYPE = OrderType.NEW;
    private static final OrderType UPDATED_ORDER_TYPE = OrderType.SENT;

    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.PRE_ADVICE;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.FINAL;

    private static final LocalDate DEFAULT_REQUEST_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;

    private static final ZonedDateTime DEFAULT_TRUCKING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TRUCKING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DEPARTURE_TIME_LOCAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEPARTURE_TIME_LOCAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ARRIVAL_TIME_LOCAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ARRIVAL_TIME_LOCAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Mode DEFAULT_MODE = Mode.FTL;
    private static final Mode UPDATED_MODE = Mode.LTL;

    private static final Integer DEFAULT_REQUESTED_POSITIONS = 1;
    private static final Integer UPDATED_REQUESTED_POSITIONS = 2;

    @Autowired
    private OrderRepository orderRepository;

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

    private MockMvc restOrderMockMvc;

    private Order order;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderResource orderResource = new OrderResource(orderRepository);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource)
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
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .number(DEFAULT_NUMBER)
            .orderType(DEFAULT_ORDER_TYPE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .requestTimestamp(DEFAULT_REQUEST_TIMESTAMP)
            .origin(DEFAULT_ORIGIN)
            .destination(DEFAULT_DESTINATION)
            .weight(DEFAULT_WEIGHT)
            .volume(DEFAULT_VOLUME)
            .truckingDate(DEFAULT_TRUCKING_DATE)
            .departureTimeLocal(DEFAULT_DEPARTURE_TIME_LOCAL)
            .arrivalTimeLocal(DEFAULT_ARRIVAL_TIME_LOCAL)
            .mode(DEFAULT_MODE)
            .requestedPositions(DEFAULT_REQUESTED_POSITIONS);
        return order;
    }

    @Before
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testOrder.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrder.getRequestTimestamp()).isEqualTo(DEFAULT_REQUEST_TIMESTAMP);
        assertThat(testOrder.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testOrder.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testOrder.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testOrder.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testOrder.getTruckingDate()).isEqualTo(DEFAULT_TRUCKING_DATE);
        assertThat(testOrder.getDepartureTimeLocal()).isEqualTo(DEFAULT_DEPARTURE_TIME_LOCAL);
        assertThat(testOrder.getArrivalTimeLocal()).isEqualTo(DEFAULT_ARRIVAL_TIME_LOCAL);
        assertThat(testOrder.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testOrder.getRequestedPositions()).isEqualTo(DEFAULT_REQUESTED_POSITIONS);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requestTimestamp").value(hasItem(DEFAULT_REQUEST_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].truckingDate").value(hasItem(sameInstant(DEFAULT_TRUCKING_DATE))))
            .andExpect(jsonPath("$.[*].departureTimeLocal").value(hasItem(sameInstant(DEFAULT_DEPARTURE_TIME_LOCAL))))
            .andExpect(jsonPath("$.[*].arrivalTimeLocal").value(hasItem(sameInstant(DEFAULT_ARRIVAL_TIME_LOCAL))))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())))
            .andExpect(jsonPath("$.[*].requestedPositions").value(hasItem(DEFAULT_REQUESTED_POSITIONS)));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.requestTimestamp").value(DEFAULT_REQUEST_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.truckingDate").value(sameInstant(DEFAULT_TRUCKING_DATE)))
            .andExpect(jsonPath("$.departureTimeLocal").value(sameInstant(DEFAULT_DEPARTURE_TIME_LOCAL)))
            .andExpect(jsonPath("$.arrivalTimeLocal").value(sameInstant(DEFAULT_ARRIVAL_TIME_LOCAL)))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()))
            .andExpect(jsonPath("$.requestedPositions").value(DEFAULT_REQUESTED_POSITIONS));
    }

    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .number(UPDATED_NUMBER)
            .orderType(UPDATED_ORDER_TYPE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .requestTimestamp(UPDATED_REQUEST_TIMESTAMP)
            .origin(UPDATED_ORIGIN)
            .destination(UPDATED_DESTINATION)
            .weight(UPDATED_WEIGHT)
            .volume(UPDATED_VOLUME)
            .truckingDate(UPDATED_TRUCKING_DATE)
            .departureTimeLocal(UPDATED_DEPARTURE_TIME_LOCAL)
            .arrivalTimeLocal(UPDATED_ARRIVAL_TIME_LOCAL)
            .mode(UPDATED_MODE)
            .requestedPositions(UPDATED_REQUESTED_POSITIONS);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrder)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testOrder.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrder.getRequestTimestamp()).isEqualTo(UPDATED_REQUEST_TIMESTAMP);
        assertThat(testOrder.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testOrder.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testOrder.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testOrder.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testOrder.getTruckingDate()).isEqualTo(UPDATED_TRUCKING_DATE);
        assertThat(testOrder.getDepartureTimeLocal()).isEqualTo(UPDATED_DEPARTURE_TIME_LOCAL);
        assertThat(testOrder.getArrivalTimeLocal()).isEqualTo(UPDATED_ARRIVAL_TIME_LOCAL);
        assertThat(testOrder.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testOrder.getRequestedPositions()).isEqualTo(UPDATED_REQUESTED_POSITIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Create the Order

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);
        order2.setId(2L);
        assertThat(order1).isNotEqualTo(order2);
        order1.setId(null);
        assertThat(order1).isNotEqualTo(order2);
    }
}
