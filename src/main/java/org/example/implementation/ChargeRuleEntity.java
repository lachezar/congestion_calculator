package org.example.implementation;

import jakarta.persistence.*;
import org.example.domain.ChargeRule;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "charge_rule")
public class ChargeRuleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.JSON)
  private ChargeRule definition;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ChargeRule getDefinition() {
    return definition;
  }

  public void setDefinition(ChargeRule definition) {
    this.definition = definition;
  }

  public ChargeRuleEntity setAll(Long id, ChargeRule definition) {
    this.id = id;
    this.definition = definition;
    return this;
  }
}
