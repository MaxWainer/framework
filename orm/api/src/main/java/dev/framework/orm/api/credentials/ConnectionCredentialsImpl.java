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

package dev.framework.orm.api.credentials;

import dev.framework.orm.api.ORMFacade;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class ConnectionCredentialsImpl implements ConnectionCredentials {

  private final String jdbcUrl, username, password;
  private final Map<String, Object> options;

  ConnectionCredentialsImpl(
      final @NotNull String facadeClassName,
      final @NotNull String jdbcUrl,
      final @NotNull String username,
      final @NotNull String password,
      final @NotNull Map<String, Object> options) {
    this(jdbcUrl, username, password, options);

    try {
      final Class<?> clazz = Class.forName(facadeClassName);

      if (!ORMFacade.class.isAssignableFrom(clazz)) {
        throw new UnsupportedOperationException(clazz + " is not facade!");
      }
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Unable to find suitable driver!", e);
    }
  }

  ConnectionCredentialsImpl(
      final @NotNull String jdbcUrl,
      final @NotNull String username,
      final @NotNull String password,
      final @NotNull Map<String, Object> options) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
    this.options = options;
  }

  @Override
  public @NotNull String jdbcUrl() {
    return jdbcUrl;
  }

  @Override
  public @NotNull String username() {
    return username;
  }

  @Override
  public @NotNull String password() {
    return password;
  }

  @Override
  public @NotNull Map<String, Object> options() {
    return options;
  }
}
