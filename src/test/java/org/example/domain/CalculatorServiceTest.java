package org.example.domain;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.example.implementation.ChargeRuleEntity;
import org.example.implementation.FilterRuleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

  @Mock Configuration configurationMock;

  @Mock FilterRuleRepository filterRuleRepositoryMock;

  @Mock ChargeRuleRepository chargeRuleRepositoryMock;

  @InjectMocks CalculatorService calculatorService;

  @BeforeEach
  public void setup() {
    Mockito.when(configurationMock.defaultZone()).thenReturn(ZoneId.of("Europe/Stockholm"));
    Mockito.when(filterRuleRepositoryMock.findAllStream(Mockito.any()))
        .thenReturn(
            Stream.of(
                    new FilterRule.VehicleTypeFilterRule(VehicleType.Bus),
                    new FilterRule.VehicleTypeFilterRule(VehicleType.Emergency),
                    new FilterRule.VehicleTypeFilterRule(VehicleType.Diplomat),
                    new FilterRule.VehicleTypeFilterRule(VehicleType.Foreign),
                    new FilterRule.VehicleTypeFilterRule(VehicleType.Military),
                    new FilterRule.VehicleTypeFilterRule(VehicleType.Motorcycle),
                    new FilterRule.WeekdayFilterRule(DayOfWeek.SATURDAY),
                    new FilterRule.WeekdayFilterRule(DayOfWeek.SUNDAY),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2012-12-31T23:00:00Z"),
                        Instant.parse("2013-01-01T23:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-01-04T23:00:00Z"),
                        Instant.parse("2013-01-06T23:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-03-27T22:00:00Z"),
                        Instant.parse("2013-04-01T22:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-04-29T22:00:00Z"),
                        Instant.parse("2013-05-01T22:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-05-07T22:00:00Z"),
                        Instant.parse("2013-05-09T22:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-06-04T22:00:00Z"),
                        Instant.parse("2013-06-06T22:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-06-20T22:00:00Z"),
                        Instant.parse("2013-06-21T22:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-06-30T22:00:00Z"),
                        Instant.parse("2013-07-31T22:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-10-31T23:00:00Z"),
                        Instant.parse("2013-11-01T23:00:00Z")),
                    new FilterRule.DateRangeFilterRule(
                        Instant.parse("2013-12-23T23:00:00Z"),
                        Instant.parse("2013-12-26T23:00:00Z")))
                .map(r -> new FilterRuleEntity().setAll(0L, r)));
    Mockito.when(chargeRuleRepositoryMock.findAllStream(Mockito.any()))
        .thenReturn(
            Stream.of(
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(6, 0, 0), LocalTime.of(6, 29, 59, 999_999_999), 8),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(6, 30, 0), LocalTime.of(6, 59, 59, 999_999_999), 13),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(7, 0, 0), LocalTime.of(7, 59, 59, 999_999_999), 18),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(8, 0, 0), LocalTime.of(8, 29, 59, 999_999_999), 13),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(8, 30, 0), LocalTime.of(14, 59, 59, 999_999_999), 8),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(15, 0, 0), LocalTime.of(15, 29, 59, 999_999_999), 13),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(15, 30, 0), LocalTime.of(16, 59, 59, 999_999_999), 18),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(17, 0, 0), LocalTime.of(17, 59, 59, 999_999_999), 13),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(18, 0, 0), LocalTime.of(18, 29, 59, 999_999_999), 8),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(18, 30, 0), LocalTime.of(23, 59, 59, 999_999_999), 0),
                    new ChargeRule.TimeWindowChargeRule(
                        LocalTime.of(8, 0, 0), LocalTime.of(5, 59, 59, 999_999_999), 0),
                    new ChargeRule.MaxPerHourChargeRule(60),
                    new ChargeRule.MaxPerDayChargeRule(60))
                .map(r -> new ChargeRuleEntity().setAll(0L, r)));
  }

  @Test
  public void testCalculationWithLiableVehicle() {
    CalculationRequest request =
        new CalculationRequest(
            VehicleType.Car,
            Stream.of(
                    "2013-01-14 21:00:00",
                    "2013-01-15 21:00:00",
                    "2013-02-07 06:23:27",
                    "2013-02-07 15:27:00",
                    "2013-02-08 06:27:00",
                    "2013-02-08 06:20:27",
                    "2013-02-08 14:35:00",
                    "2013-02-08 15:29:00",
                    "2013-02-08 15:47:00",
                    "2013-02-08 16:01:00",
                    "2013-02-08 16:48:00",
                    "2013-02-08 17:49:00",
                    "2013-02-08 18:29:00",
                    "2013-02-08 18:35:00",
                    "2013-03-26 14:25:00",
                    "2013-03-28 14:07:27")
                .map(ts -> LocalDateTime.parse(ts.replace(' ', 'T')))
                .toList());
    CalculationResponse actual = calculatorService.calculate(request);
    CalculationResponse expected =
        new CalculationResponse(
            Map.of(
                LocalDate.of(2013, 3, 26), 8,
                LocalDate.of(2013, 2, 7), 21,
                LocalDate.of(2013, 2, 8), 60),
            89);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testCalculationWithExemptVehicle() {
    CalculationRequest request =
        new CalculationRequest(
            VehicleType.Diplomat,
            Stream.of(
                    "2013-01-14 21:00:00",
                    "2013-01-15 21:00:00",
                    "2013-02-07 06:23:27",
                    "2013-02-07 15:27:00",
                    "2013-02-08 06:27:00",
                    "2013-02-08 06:20:27",
                    "2013-02-08 14:35:00",
                    "2013-02-08 15:29:00",
                    "2013-02-08 15:47:00",
                    "2013-02-08 16:01:00",
                    "2013-02-08 16:48:00",
                    "2013-02-08 17:49:00",
                    "2013-02-08 18:29:00",
                    "2013-02-08 18:35:00",
                    "2013-03-26 14:25:00",
                    "2013-03-28 14:07:27")
                .map(ts -> LocalDateTime.parse(ts.replace(' ', 'T')))
                .toList());
    CalculationResponse actual = calculatorService.calculate(request);
    CalculationResponse expected = new CalculationResponse(Map.of(), 0);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testCalculationWithNoCrossingEvents() {
    CalculationRequest request = new CalculationRequest(VehicleType.Car, List.of());
    CalculationResponse actual = calculatorService.calculate(request);
    CalculationResponse expected = new CalculationResponse(Map.of(), 0);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  public void testCalculationWithNoRules() {
    // We need at least a charge rule that aggregates per day
    Mockito.when(filterRuleRepositoryMock.findAllStream(Mockito.any())).thenReturn(Stream.empty());
    Mockito.when(chargeRuleRepositoryMock.findAllStream(Mockito.any()))
        .thenReturn(
            Stream.of(
                new ChargeRuleEntity()
                    .setAll(0L, new ChargeRule.MaxPerDayChargeRule(Integer.MAX_VALUE))));
    CalculationRequest request =
        new CalculationRequest(
            VehicleType.Car,
            Stream.of(
                    "2013-01-14 21:00:00",
                    "2013-01-15 21:00:00",
                    "2013-02-07 06:23:27",
                    "2013-02-07 15:27:00",
                    "2013-02-08 06:27:00",
                    "2013-02-08 06:20:27",
                    "2013-02-08 14:35:00",
                    "2013-02-08 15:29:00",
                    "2013-02-08 15:47:00",
                    "2013-02-08 16:01:00",
                    "2013-02-08 16:48:00",
                    "2013-02-08 17:49:00",
                    "2013-02-08 18:29:00",
                    "2013-02-08 18:35:00",
                    "2013-03-26 14:25:00",
                    "2013-03-28 14:07:27")
                .map(ts -> LocalDateTime.parse(ts.replace(' ', 'T')))
                .toList());
    CalculationResponse actual = calculatorService.calculate(request);
    Assertions.assertArrayEquals(
        new Integer[] {0, 0, 0, 0, 0, 0}, actual.taxByDate().values().toArray());
    Assertions.assertEquals(0, actual.totalTax());
  }
}
