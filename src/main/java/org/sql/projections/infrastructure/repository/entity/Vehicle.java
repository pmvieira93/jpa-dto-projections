package org.sql.projections.infrastructure.repository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

/**
 *
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue
    private UUID id;

    private String brand;

    private String model;

    private Integer horsePower;

    private Integer kms;

    private Double engine;

    private Integer year;

    private Integer month;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = true)
    private Store store;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, Integer horsePower, Integer kms, Double engine, Integer year, Integer month) {
        this.brand = brand;
        this.model = model;
        this.horsePower = horsePower;
        this.kms = kms;
        this.engine = engine;
        this.year = year;
        this.month = month;
    }

    public Vehicle(UUID id, String brand, String model, Integer horsePower, Integer kms, Double engine, Integer year, Integer month) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.horsePower = horsePower;
        this.kms = kms;
        this.engine = engine;
        this.year = year;
        this.month = month;
    }

    public Vehicle(String brand, String model, Integer horsePower, Integer kms, Double engine, Integer year, Integer month, Store store) {
        this.brand = brand;
        this.model = model;
        this.horsePower = horsePower;
        this.kms = kms;
        this.engine = engine;
        this.year = year;
        this.month = month;
        this.store = store;
    }

    public Vehicle(UUID id, String brand, String model, Integer horsePower, Integer kms, Double engine, Integer year, Integer month, Store store) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.horsePower = horsePower;
        this.kms = kms;
        this.engine = engine;
        this.year = year;
        this.month = month;
        this.store = store;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    public Integer getKms() {
        return kms;
    }

    public void setKms(Integer kms) {
        this.kms = kms;
    }

    public Double getEngine() {
        return engine;
    }

    public void setEngine(Double engine) {
        this.engine = engine;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(brand, vehicle.brand)
                && Objects.equals(model, vehicle.model) && Objects.equals(horsePower, vehicle.horsePower)
                && Objects.equals(kms, vehicle.kms) && Objects.equals(engine, vehicle.engine)
                && Objects.equals(year, vehicle.year) && Objects.equals(month, vehicle.month)
                && Objects.equals(store, vehicle.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, horsePower, kms, engine, year, month, store);
    }
}
