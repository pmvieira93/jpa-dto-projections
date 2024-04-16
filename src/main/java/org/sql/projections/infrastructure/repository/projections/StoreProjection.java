package org.sql.projections.infrastructure.repository.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

/**
 *
 */
@RegisterForReflection
public record StoreProjection(
        String location,
        List<VehicleProjection> vehicles
) {
}
