package org.example.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

// TODO: Find a way to keep the Jackson annotations out of this record
//  (something like Chimney to transform the domain structures into DTOs)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(
      value = FilterRule.VehicleTypeFilterRule.class,
      name = "VehicleTypeFilterRule"),
  @JsonSubTypes.Type(value = FilterRule.DateRangeFilterRule.class, name = "DateRangeFilterRule"),
  @JsonSubTypes.Type(value = FilterRule.WeekdayFilterRule.class, name = "WeekdayFilterRule")
})
public sealed interface FilterRule {

  Stream<CrossingEvent> apply(Stream<CrossingEvent> events, ZoneId defaultZone);

  record VehicleTypeFilterRule(VehicleType vehicleType) implements FilterRule {

    @Override
    public Stream<CrossingEvent> apply(@NotNull Stream<CrossingEvent> events, ZoneId defaultZone) {
      return events.filter(e -> vehicleType != e.vehicleType());
    }
  }

  record DateRangeFilterRule(Instant startInclusive, Instant endInclusive) implements FilterRule {

    @Override
    public Stream<CrossingEvent> apply(@NotNull Stream<CrossingEvent> events, ZoneId defaultZone) {
      return events.filter(
          e -> e.timestamp().isBefore(startInclusive) || e.timestamp().isAfter(endInclusive));
    }
  }

  record WeekdayFilterRule(DayOfWeek dow) implements FilterRule {

    @Override
    public Stream<CrossingEvent> apply(@NotNull Stream<CrossingEvent> events, ZoneId defaultZone) {
      return events.filter(
          e -> dow != LocalDate.ofInstant(e.timestamp(), defaultZone).getDayOfWeek());
    }
  }
}
