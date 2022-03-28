package dev.framework.orm.api;

import dev.framework.commons.MoreSets;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.exception.MissingFacadeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

public interface ORMProvider {

  @Internal
  Set<String> LOADING_BLACKLIST_NAMES = MoreSets.newImmutableHashSet(
      "dev.framework.orm.jdbcUrl",
      "dev.framework.orm.facadeClass",
      "dev.framework.orm.username",
      "dev.framework.orm.password");

  static ORMProvider instance() {
    return ORMProviderImpl.INSTANCE;
  }

  @NotNull ORMFacade createFacade(
      final @NotNull ConnectionCredentials connectionCredentials) throws MissingFacadeException;

  void registerFactory(
      final @NotNull String driver,
      final @NotNull ORMFacadeFactory facadeFactory);

  default @NotNull ORMFacade createFacade(
      final @NotNull Properties properties) throws MissingFacadeException {
    final Map<String, Object> additional = new HashMap<>();

    for (final String propertyName : properties.stringPropertyNames()) {
      if (!LOADING_BLACKLIST_NAMES.contains(propertyName)) {
        additional.put(propertyName, properties.get(propertyName));
      }
    }

    return createFacade(ConnectionCredentials.of(
        properties.getProperty("dev.framework.orm.facadeClass"),
        properties.getProperty("dev.framework.orm.jdbcUrl"),
        properties.getProperty("dev.framework.orm.username"),
        properties.getProperty("dev.framework.orm.password"),
        additional
    ));
  }

}
