package org.sql.projections.infrastructure.repository.projections;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class StoreVehicles {

    private String location;

    private List<VehicleSimple> vehicles = new ArrayList<>();

    public StoreVehicles() {
    }

    public StoreVehicles(String location, List<VehicleSimple> vehicles) {
        this.location = location;
        this.vehicles = vehicles;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<VehicleSimple> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleSimple> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreVehicles that = (StoreVehicles) o;
        return Objects.equals(location, that.location) && Objects.equals(vehicles, that.vehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, vehicles);
    }
}
