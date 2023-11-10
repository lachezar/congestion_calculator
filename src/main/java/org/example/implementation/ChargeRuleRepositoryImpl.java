package org.example.implementation;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Singleton;
import java.util.stream.Stream;
import org.example.domain.ChargeRuleRepository;

@Singleton
public class ChargeRuleRepositoryImpl
    implements ChargeRuleRepository, PanacheRepository<ChargeRuleEntity> {
  @Override
  public Stream<ChargeRuleEntity> findAllStream(Sort sort) {
    return findAll(sort).stream();
  }
}
