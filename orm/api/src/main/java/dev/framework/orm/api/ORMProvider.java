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

import dev.framework.commons.collection.MoreSets;
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
      "dev.framework.orm.facadeClass",
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
