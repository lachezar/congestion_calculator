package org.example.api;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.domain.CalculationRequest;
import org.example.domain.CalculationResponse;
import org.example.domain.CalculatorService;
import org.jetbrains.annotations.NotNull;

@Path("/congestion-tax")
public class CongestionTaxResource {

  @Inject CalculatorService calculatorService;

  @POST
  @Path("/calculate")
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public CalculationResponse calculate(@NotNull CalculationRequest request) {
    return calculatorService.calculate(request);
  }
}
