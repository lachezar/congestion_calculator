package org.example.domain;

import java.time.Instant;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record TaxEvent(Instant timestamp, Optional<Integer> amount) {

  @Contract("_ -> new")
  @NotNull
  public TaxEvent copyAndSetPrice(Integer price) {
    return new TaxEvent(timestamp, Optional.of(price));
  }
}
