package org.sql.projections.infrastructure.repository.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 *
 */
@RegisterForReflection
public record VehicleProjection(
        String brand,
        String model,
        Integer kms,
        Integer year
) {
}
