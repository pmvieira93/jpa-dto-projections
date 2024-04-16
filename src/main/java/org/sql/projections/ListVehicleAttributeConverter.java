package org.sql.projections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.sql.projections.infrastructure.repository.projections.StoreVehicleProjection;
import org.sql.projections.infrastructure.repository.projections.VehicleProjection;

import java.util.List;

/**
 * It's not working and not Handler when read the projection on {@link StoreRepository} with {@link StoreVehicleProjection}
 */
@ApplicationScoped
@Converter
public class ListVehicleAttributeConverter implements AttributeConverter<List<VehicleProjection>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<VehicleProjection> maps) {
        try {
            return objectMapper.writeValueAsString(maps);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VehicleProjection> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, new TypeReference<List<VehicleProjection>>() {
            });
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return List.of();
        }
    }
}
