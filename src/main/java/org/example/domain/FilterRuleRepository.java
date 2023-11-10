package org.example.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import java.util.stream.Stream;
import org.example.implementation.FilterRuleEntity;

public abstract class FilterRuleRepository implements PanacheRepository<FilterRuleEntity> {
  public Stream<FilterRuleEntity> findAllStream(Sort sort) {
    return findAll(sort).stream();
  }
}
