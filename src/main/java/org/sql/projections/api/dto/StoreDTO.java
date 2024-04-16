package org.sql.projections.api.dto;

import org.sql.projections.infrastructure.repository.projections.VehicleProjection;

import java.util.List;

/**
 *
 */
public record StoreDTO(String location, List<VehicleProjection> vehicles) {
}
