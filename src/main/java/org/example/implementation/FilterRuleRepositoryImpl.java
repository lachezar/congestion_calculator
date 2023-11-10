package org.example.implementation;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Singleton;
import java.util.stream.Stream;
import org.example.domain.FilterRuleRepository;

@Singleton
public class FilterRuleRepositoryImpl
    implements FilterRuleRepository, PanacheRepository<FilterRuleEntity> {

  @Override
  public Stream<FilterRuleEntity> findAllStream(Sort sort) {
    return findAll(sort).stream();
  }
}
