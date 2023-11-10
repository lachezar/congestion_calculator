package org.example.implementation;

import jakarta.inject.Singleton;
import java.time.ZoneId;
import org.example.domain.Configuration;

@Singleton
public class ConfigurationImpl implements Configuration {

  // TODO: read app specific config and put it here as a dependency for all modules
  public ZoneId defaultZone() {
    return ZoneId.systemDefault();
  }
}
