package org.sql.projections.infrastructure.repository.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.UUID;

/**
 *
 */
@RegisterForReflection
public record StoreSingleProjection(UUID id, String location) {
}
