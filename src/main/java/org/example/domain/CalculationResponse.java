package org.example.domain;

import java.time.LocalDate;
import java.util.Map;

public record CalculationResponse(Map<LocalDate, Integer> taxByDate, Integer totalTax) {}
