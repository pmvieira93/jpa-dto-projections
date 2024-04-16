package org.sql.projections;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.sql.projections.infrastructure.repository.entity.Store;
import org.sql.projections.infrastructure.repository.entity.Vehicle;
import org.sql.projections.infrastructure.repository.projections.VehicleProjection;

import java.util.List;
import java.util.UUID;

/**
 *
 */
@ApplicationScoped
public class VehicleRepository implements PanacheRepository<Vehicle> {

    public List<VehicleProjection> findVehiclesByStore(Store store) {
        return find("store", store).project(VehicleProjection.class).list();
    }


    public List<VehicleProjection> findVehiclesByStore(UUID storeId) {
        return find("store.id", storeId).project(VehicleProjection.class).list();
    }
}
