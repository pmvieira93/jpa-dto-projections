package org.sql.projections.infrastructure.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 */
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue
    private UUID id;

    private String location;

    private Integer numberOfWorkers;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Vehicle> vehicles;

    public Store() {
    }

    public Store(String location, Integer numberOfWorkers) {
        this.location = location;
        this.numberOfWorkers = numberOfWorkers;
    }

    public Store(UUID id, String location, Integer numberOfWorkers, List<Vehicle> vehicles) {
        this.id = id;
        this.location = location;
        this.numberOfWorkers = numberOfWorkers;
        this.vehicles = vehicles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(Integer numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id)
                && Objects.equals(location, store.location)
                && Objects.equals(numberOfWorkers, store.numberOfWorkers)
                && Objects.equals(vehicles, store.vehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, numberOfWorkers, vehicles);
    }
}
