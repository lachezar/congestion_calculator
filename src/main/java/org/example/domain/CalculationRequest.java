package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

// TODO: Find a way to keep the Jackson annotations out of this record
//  (something like Chimney to transform the domain structures into DTOs)
public record CalculationRequest(
    @JsonProperty(required = true) VehicleType vehicleType,
    @JsonProperty(required = true) List<LocalDateTime> crossingTimestamps) {}
