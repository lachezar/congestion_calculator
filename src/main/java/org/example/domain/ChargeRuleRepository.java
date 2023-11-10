package org.example.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import java.util.stream.Stream;
import org.example.implementation.ChargeRuleEntity;

public abstract class ChargeRuleRepository implements PanacheRepository<ChargeRuleEntity> {
  public Stream<ChargeRuleEntity> findAllStream(Sort sort) {
    return findAll(sort).stream();
  }
}
