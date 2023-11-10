package org.example.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

// TODO: Find a way to keep the Jackson annotations out of this record
//  (something like Chimney to transform the domain structures into DTOs)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ChargeRule.TimeWindowChargeRule.class, name = "TimeWindowChargeRule"),
  @JsonSubTypes.Type(value = ChargeRule.MaxPerHourChargeRule.class, name = "MaxPerHourChargeRule"),
  @JsonSubTypes.Type(value = ChargeRule.MaxPerDayChargeRule.class, name = "MaxPerDayChargeRule")
})
public sealed interface ChargeRule {

  Stream<TaxEvent> apply(Stream<TaxEvent> events, ZoneId defaultZone);

  record TimeWindowChargeRule(
      @NotNull LocalTime start, @NotNull LocalTime end, @NotNull Integer price)
      implements ChargeRule {

    public Stream<TaxEvent> apply(@NotNull Stream<TaxEvent> events, ZoneId defaultZone) {
      return events.map(
          e -> {
            final LocalTime time = LocalTime.ofInstant(e.timestamp(), defaultZone);
            return (start.isBefore(time) || start.equals(time))
                    && (end.equals(time) || end.isAfter(time))
                ? e.copyAndSetPrice(price)
                : e;
          });
    }
  }

  record MaxPerHourChargeRule(@NotNull Integer maxPrice) implements ChargeRule {

    public Stream<TaxEvent> apply(@NotNull Stream<TaxEvent> events, ZoneId defaultZone) {
      List<TaxEvent> sink = events.filter(e -> e.amount().orElse(0) > 0).toList();
      if (sink.size() > 1) {
        Iterator<TaxEvent> iter = sink.iterator();
        TaxEvent aggregationPoint = iter.next();
        Instant bufferEnd = aggregationPoint.timestamp().plus(60, ChronoUnit.MINUTES);
        List<TaxEvent> aggregationResult = new LinkedList<>();
        while (iter.hasNext()) {
          TaxEvent next = iter.next();
          if (next.timestamp().isAfter(bufferEnd)) {
            aggregationResult.add(aggregationPoint);
            aggregationPoint = next;
            bufferEnd = aggregationPoint.timestamp().plus(60, ChronoUnit.MINUTES);
          } else {
            Integer max =
                aggregationPoint
                    .amount()
                    .flatMap(x -> next.amount().map(y -> Math.max(x, y)))
                    .orElseThrow();
            aggregationPoint = aggregationPoint.copyAndSetPrice(max);
          }
        }
        aggregationResult.add(aggregationPoint);
        return aggregationResult.stream();
      } else {
        return sink.stream();
      }
    }
  }

  record MaxPerDayChargeRule(@NotNull Integer maxPrice) implements ChargeRule {

    public Stream<TaxEvent> apply(@NotNull Stream<TaxEvent> events, ZoneId defaultZone) {
      Map<LocalDate, Integer> x =
          events.collect(
              Collectors.groupingBy(
                  e -> LocalDate.ofInstant(e.timestamp(), defaultZone),
                  Collectors.mapping(
                      e -> e.amount().orElse(0), Collectors.reducing(0, Integer::sum))));

      Stream<TaxEvent> res =
          x.entrySet().stream()
              .map(
                  p ->
                      new TaxEvent(
                          p.getKey().atStartOfDay(defaultZone).toInstant(),
                          Optional.of(Math.min(p.getValue(), maxPrice))));
      return res;
    }
  }
}
