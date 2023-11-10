package org.example.domain;

import io.quarkus.panache.common.Sort;
import java.util.stream.Stream;
import org.example.implementation.ChargeRuleEntity;

public interface ChargeRuleRepository {
  Stream<ChargeRuleEntity> findAllStream(Sort sort);
}
