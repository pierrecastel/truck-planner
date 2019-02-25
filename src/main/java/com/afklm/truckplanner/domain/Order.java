package com.afklm.truckplanner.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.afklm.truckplanner.domain.enumeration.OrderType;

import com.afklm.truckplanner.domain.enumeration.OrderStatus;

import com.afklm.truckplanner.domain.enumeration.Mode;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_number")
    private Long number;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "request_timestamp")
    private LocalDate requestTimestamp;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "trucking_date")
    private ZonedDateTime truckingDate;

    @Column(name = "departure_time_local")
    private ZonedDateTime departureTimeLocal;

    @Column(name = "arrival_time_local")
    private ZonedDateTime arrivalTimeLocal;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_mode")
    private Mode mode;

    @Column(name = "requested_positions")
    private Integer requestedPositions;

    @OneToOne
    @JoinColumn(unique = true)
    private Truck truck;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Delivery delivery;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private TruckCompany truckCompany;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public Order number(Long number) {
        this.number = number;
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Order orderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Order orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getRequestTimestamp() {
        return requestTimestamp;
    }

    public Order requestTimestamp(LocalDate requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
        return this;
    }

    public void setRequestTimestamp(LocalDate requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getOrigin() {
        return origin;
    }

    public Order origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public Order destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getWeight() {
        return weight;
    }

    public Order weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public Order volume(Double volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public ZonedDateTime getTruckingDate() {
        return truckingDate;
    }

    public Order truckingDate(ZonedDateTime truckingDate) {
        this.truckingDate = truckingDate;
        return this;
    }

    public void setTruckingDate(ZonedDateTime truckingDate) {
        this.truckingDate = truckingDate;
    }

    public ZonedDateTime getDepartureTimeLocal() {
        return departureTimeLocal;
    }

    public Order departureTimeLocal(ZonedDateTime departureTimeLocal) {
        this.departureTimeLocal = departureTimeLocal;
        return this;
    }

    public void setDepartureTimeLocal(ZonedDateTime departureTimeLocal) {
        this.departureTimeLocal = departureTimeLocal;
    }

    public ZonedDateTime getArrivalTimeLocal() {
        return arrivalTimeLocal;
    }

    public Order arrivalTimeLocal(ZonedDateTime arrivalTimeLocal) {
        this.arrivalTimeLocal = arrivalTimeLocal;
        return this;
    }

    public void setArrivalTimeLocal(ZonedDateTime arrivalTimeLocal) {
        this.arrivalTimeLocal = arrivalTimeLocal;
    }

    public Mode getMode() {
        return mode;
    }

    public Order mode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Integer getRequestedPositions() {
        return requestedPositions;
    }

    public Order requestedPositions(Integer requestedPositions) {
        this.requestedPositions = requestedPositions;
        return this;
    }

    public void setRequestedPositions(Integer requestedPositions) {
        this.requestedPositions = requestedPositions;
    }

    public Truck getTruck() {
        return truck;
    }

    public Order truck(Truck truck) {
        this.truck = truck;
        return this;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Order delivery(Delivery delivery) {
        this.delivery = delivery;
        return this;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public TruckCompany getTruckCompany() {
        return truckCompany;
    }

    public Order truckCompany(TruckCompany truckCompany) {
        this.truckCompany = truckCompany;
        return this;
    }

    public void setTruckCompany(TruckCompany truckCompany) {
        this.truckCompany = truckCompany;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        if (order.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", orderType='" + getOrderType() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", requestTimestamp='" + getRequestTimestamp() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", destination='" + getDestination() + "'" +
            ", weight=" + getWeight() +
            ", volume=" + getVolume() +
            ", truckingDate='" + getTruckingDate() + "'" +
            ", departureTimeLocal='" + getDepartureTimeLocal() + "'" +
            ", arrivalTimeLocal='" + getArrivalTimeLocal() + "'" +
            ", mode='" + getMode() + "'" +
            ", requestedPositions=" + getRequestedPositions() +
            "}";
    }
}
