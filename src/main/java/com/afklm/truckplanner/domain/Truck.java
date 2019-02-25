package com.afklm.truckplanner.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.afklm.truckplanner.domain.enumeration.Cool;

import com.afklm.truckplanner.domain.enumeration.TopType;

/**
 * A Truck.
 */
@Entity
@Table(name = "truck")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Truck implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_number")
    private String number;

    @Column(name = "truck_country")
    private String truckCountry;

    @Column(name = "trailer_country")
    private String trailerCountry;

    @Column(name = "truck_license")
    private String truckLicense;

    @Column(name = "trailer_license")
    private String trailerLicense;

    @Column(name = "adr")
    private Boolean adr;

    @Column(name = "big")
    private Boolean big;

    @Column(name = "roller_bed")
    private Boolean rollerBed;

    @Enumerated(EnumType.STRING)
    @Column(name = "cool")
    private Cool cool;

    @Column(name = "min_temperature")
    private Integer minTemperature;

    @Column(name = "max_temperature")
    private Integer maxTemperature;

    @Column(name = "maximum_height")
    private Integer maximumHeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "top_type")
    private TopType topType;

    @ManyToOne
    @JsonIgnoreProperties("trucks")
    private TruckCompany truckCompany;

    @ManyToOne
    @JsonIgnoreProperties("trucks")
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Truck number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTruckCountry() {
        return truckCountry;
    }

    public Truck truckCountry(String truckCountry) {
        this.truckCountry = truckCountry;
        return this;
    }

    public void setTruckCountry(String truckCountry) {
        this.truckCountry = truckCountry;
    }

    public String getTrailerCountry() {
        return trailerCountry;
    }

    public Truck trailerCountry(String trailerCountry) {
        this.trailerCountry = trailerCountry;
        return this;
    }

    public void setTrailerCountry(String trailerCountry) {
        this.trailerCountry = trailerCountry;
    }

    public String getTruckLicense() {
        return truckLicense;
    }

    public Truck truckLicense(String truckLicense) {
        this.truckLicense = truckLicense;
        return this;
    }

    public void setTruckLicense(String truckLicense) {
        this.truckLicense = truckLicense;
    }

    public String getTrailerLicense() {
        return trailerLicense;
    }

    public Truck trailerLicense(String trailerLicense) {
        this.trailerLicense = trailerLicense;
        return this;
    }

    public void setTrailerLicense(String trailerLicense) {
        this.trailerLicense = trailerLicense;
    }

    public Boolean isAdr() {
        return adr;
    }

    public Truck adr(Boolean adr) {
        this.adr = adr;
        return this;
    }

    public void setAdr(Boolean adr) {
        this.adr = adr;
    }

    public Boolean isBig() {
        return big;
    }

    public Truck big(Boolean big) {
        this.big = big;
        return this;
    }

    public void setBig(Boolean big) {
        this.big = big;
    }

    public Boolean isRollerBed() {
        return rollerBed;
    }

    public Truck rollerBed(Boolean rollerBed) {
        this.rollerBed = rollerBed;
        return this;
    }

    public void setRollerBed(Boolean rollerBed) {
        this.rollerBed = rollerBed;
    }

    public Cool getCool() {
        return cool;
    }

    public Truck cool(Cool cool) {
        this.cool = cool;
        return this;
    }

    public void setCool(Cool cool) {
        this.cool = cool;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public Truck minTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
        return this;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public Truck maxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getMaximumHeight() {
        return maximumHeight;
    }

    public Truck maximumHeight(Integer maximumHeight) {
        this.maximumHeight = maximumHeight;
        return this;
    }

    public void setMaximumHeight(Integer maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    public TopType getTopType() {
        return topType;
    }

    public Truck topType(TopType topType) {
        this.topType = topType;
        return this;
    }

    public void setTopType(TopType topType) {
        this.topType = topType;
    }

    public TruckCompany getTruckCompany() {
        return truckCompany;
    }

    public Truck truckCompany(TruckCompany truckCompany) {
        this.truckCompany = truckCompany;
        return this;
    }

    public void setTruckCompany(TruckCompany truckCompany) {
        this.truckCompany = truckCompany;
    }

    public Driver getDriver() {
        return driver;
    }

    public Truck driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
        Truck truck = (Truck) o;
        if (truck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), truck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Truck{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", truckCountry='" + getTruckCountry() + "'" +
            ", trailerCountry='" + getTrailerCountry() + "'" +
            ", truckLicense='" + getTruckLicense() + "'" +
            ", trailerLicense='" + getTrailerLicense() + "'" +
            ", adr='" + isAdr() + "'" +
            ", big='" + isBig() + "'" +
            ", rollerBed='" + isRollerBed() + "'" +
            ", cool='" + getCool() + "'" +
            ", minTemperature=" + getMinTemperature() +
            ", maxTemperature=" + getMaxTemperature() +
            ", maximumHeight=" + getMaximumHeight() +
            ", topType='" + getTopType() + "'" +
            "}";
    }
}
