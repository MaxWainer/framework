package dev.framework.orm.api;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.exception.MissingFacadeException;
import org.jetbrains.annotations.NotNull;

final class ORMProviderImpl implements ORMProvider {

  public static final ORMProviderImpl INSTANCE = new ORMProviderImpl();

  private final OptionalMap<String, OMFFacadeFactory> registry = OptionalMaps.newConcurrentMap();

  private ORMProviderImpl() {
  }

  @Override
  public @NotNull ORMFacade createFacade(
      final @NotNull ConnectionCredentials connectionCredentials) throws MissingFacadeException {
    final String url = connectionCredentials.jdbcUrl();

    final String driverId = url.split(":")[1];

    return fromDriver(driverId).create(connectionCredentials);
  }

  @Override
  public void registerFactory(final @NotNull String driver,
      final @NotNull OMFFacadeFactory facadeFactory) {
    registry.put(driver, facadeFactory);
  }

  @NotNull OMFFacadeFactory fromDriver(final @NotNull String driver)
      throws MissingFacadeException {
    return registry.get(driver)
        .orElseThrow(() -> new MissingFacadeException(driver));
  }

}
