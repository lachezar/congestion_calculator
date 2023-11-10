package org.example.domain;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.example.implementation.ChargeRuleEntity;
import org.example.implementation.FilterRuleEntity;
import org.jetbrains.annotations.NotNull;

@Singleton
public class CalculatorService {

  @Inject FilterRuleRepository filterRuleRepository;

  @Inject ChargeRuleRepository chargeRuleRepository;

  @Inject Configuration config;

  public CalculationResponse calculate(@NotNull CalculationRequest request) {
    Stream<FilterRule> filterRules =
        filterRuleRepository
            .findAllStream(Sort.ascending("id"))
            .map(FilterRuleEntity::getDefinition);
    Stream<CrossingEvent> crossingEvents =
        request.crossingTimestamps().stream()
            .map(
                ts ->
                    new CrossingEvent(
                        request.vehicleType(), ts.atZone(config.defaultZone()).toInstant()));
    Stream<CrossingEvent> filteredEvents =
        filterRules.reduce(
            crossingEvents,
            (Stream<CrossingEvent> acc, FilterRule r) -> r.apply(acc, config.defaultZone()),
            Stream::concat);
    Stream<ChargeRule> chargeRules =
        chargeRuleRepository
            .findAllStream(Sort.ascending("id"))
            .map(ChargeRuleEntity::getDefinition);
    Stream<TaxEvent> taxEvents =
        chargeRules.reduce(
            filteredEvents.map(e -> new TaxEvent(e.timestamp(), Optional.empty())),
            (Stream<TaxEvent> acc, ChargeRule r) -> r.apply(acc, config.defaultZone()),
            Stream::concat);
    CalculationResponse response =
        taxEvents
            .map(
                e ->
                    Tuple2.<LocalDate, Integer>of(
                        LocalDate.ofInstant(e.timestamp(), config.defaultZone()),
                        e.amount().orElse(0)))
            .collect(
                Collectors.teeing(
                    Collectors.toUnmodifiableMap(Tuple2::getItem1, Tuple2::getItem2),
                    Collectors.reducing(0, Tuple2::getItem2, Integer::sum),
                    CalculationResponse::new));
    return response;
  }
}
