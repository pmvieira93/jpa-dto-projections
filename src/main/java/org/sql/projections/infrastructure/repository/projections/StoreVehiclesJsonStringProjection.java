package org.sql.projections.infrastructure.repository.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 *
 */
@RegisterForReflection
public class StoreVehiclesJsonStringProjection {

    private String location;

    //@Convert(converter = ListVehicleAttributeConverter.class)
    private String vehicles;

    public StoreVehiclesJsonStringProjection() {
    }

    public StoreVehiclesJsonStringProjection(String location, String vehicles) {
        this.location = location;
        this.vehicles = vehicles;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }
}
