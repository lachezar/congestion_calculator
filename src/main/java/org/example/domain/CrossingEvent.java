package org.example.domain;

import java.time.Instant;

public record CrossingEvent(VehicleType vehicleType, Instant timestamp) {}
