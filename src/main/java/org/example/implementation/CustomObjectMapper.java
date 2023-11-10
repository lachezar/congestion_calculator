package org.example.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.arc.Arc;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;

public class CustomObjectMapper implements org.hibernate.type.format.FormatMapper {

  // TODO: you can't re-use the global object mapper, so this is a workaround
  // see https://github.com/quarkusio/quarkus/issues/32029
  private final org.hibernate.type.format.FormatMapper delegate =
      new JacksonJsonFormatMapper(
          Arc.container()
              .instance(ObjectMapper.class)
              .get()
              .registerModule(new JavaTimeModule())
              .disable(
                  SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS,
                  SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));

  public <T> T fromString(
      CharSequence charSequence, JavaType<T> javaType, WrapperOptions wrapperOptions) {
    return delegate.fromString(charSequence, javaType, wrapperOptions);
  }

  public <T> String toString(T value, JavaType<T> javaType, WrapperOptions wrapperOptions) {
    return delegate.toString(value, javaType, wrapperOptions);
  }
}
