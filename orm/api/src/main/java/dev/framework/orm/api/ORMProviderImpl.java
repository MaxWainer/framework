/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.orm.api;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.exception.MissingFacadeException;
import org.jetbrains.annotations.NotNull;

final class ORMProviderImpl implements ORMProvider {

  public static final ORMProviderImpl INSTANCE = new ORMProviderImpl();

  private final OptionalMap<String, ORMFacadeFactory> registry = OptionalMaps.newConcurrentMap();

  private ORMProviderImpl() {}

  @Override
  public @NotNull ORMFacade createFacade(final @NotNull ConnectionCredentials connectionCredentials)
      throws MissingFacadeException {

    final String url = connectionCredentials.jdbcUrl();

    final String driverId = url.split(":")[1];

    return fromDriver(driverId).create(connectionCredentials);
  }

  @Override
  public void registerFactory(
      final @NotNull String driver, final @NotNull ORMFacadeFactory facadeFactory) {
    registry.put(driver, facadeFactory);
  }

  @NotNull
  ORMFacadeFactory fromDriver(final @NotNull String driver) throws MissingFacadeException {
    return registry.get(driver).orElseThrow(() -> new MissingFacadeException(driver));
  }
}
