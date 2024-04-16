package org.sql.projections.infrastructure.repository.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Convert;
import org.sql.projections.ListVehicleAttributeConverter;

import java.util.List;

/**
 *
 */
@RegisterForReflection
public class StoreVehicleProjection {
    private String location;
    @Convert(converter = ListVehicleAttributeConverter.class)
    private List<VehicleProjection> vehicles;

    public StoreVehicleProjection() {
    }

    public StoreVehicleProjection(String location, List<VehicleProjection> vehicles) {
        this.location = location;
        this.vehicles = vehicles;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<VehicleProjection> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleProjection> vehicles) {
        this.vehicles = vehicles;
    }
}
