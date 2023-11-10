package org.example.domain;

import io.quarkus.panache.common.Sort;
import java.util.stream.Stream;
import org.example.implementation.FilterRuleEntity;

public interface FilterRuleRepository {
  Stream<FilterRuleEntity> findAllStream(Sort sort);
}
