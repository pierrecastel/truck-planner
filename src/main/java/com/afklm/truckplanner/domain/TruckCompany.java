package com.afklm.truckplanner.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.afklm.truckplanner.domain.enumeration.CommunicationMode;

/**
 * A TruckCompany.
 */
@Entity
@Table(name = "truck_company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TruckCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "comunication_mode")
    private CommunicationMode comunicationMode;

    @OneToMany(mappedBy = "truckCompany")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Truck> trucks = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public TruckCompany email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CommunicationMode getComunicationMode() {
        return comunicationMode;
    }

    public TruckCompany comunicationMode(CommunicationMode comunicationMode) {
        this.comunicationMode = comunicationMode;
        return this;
    }

    public void setComunicationMode(CommunicationMode comunicationMode) {
        this.comunicationMode = comunicationMode;
    }

    public Set<Truck> getTrucks() {
        return trucks;
    }

    public TruckCompany trucks(Set<Truck> trucks) {
        this.trucks = trucks;
        return this;
    }

    public TruckCompany addTruck(Truck truck) {
        this.trucks.add(truck);
        truck.setTruckCompany(this);
        return this;
    }

    public TruckCompany removeTruck(Truck truck) {
        this.trucks.remove(truck);
        truck.setTruckCompany(null);
        return this;
    }

    public void setTrucks(Set<Truck> trucks) {
        this.trucks = trucks;
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
        TruckCompany truckCompany = (TruckCompany) o;
        if (truckCompany.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), truckCompany.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TruckCompany{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", comunicationMode='" + getComunicationMode() + "'" +
            "}";
    }
}
