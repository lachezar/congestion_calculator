package org.example.implementation;

import jakarta.persistence.*;
import org.example.domain.FilterRule;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "filter_rule")
public class FilterRuleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.JSON)
  private FilterRule definition;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FilterRule getDefinition() {
    return definition;
  }

  public void setDefinition(FilterRule definition) {
    this.definition = definition;
  }

  public FilterRuleEntity setAll(Long id, FilterRule definition) {
    this.id = id;
    this.definition = definition;
    return this;
  }
}
