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

import java.util.Collections;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface ConnectionCredentials {

  static ConnectionCredentials of(
      final @NotNull String facadeClassName,
      final @NotNull String jdbcUrl,
      final @NotNull String username,
      final @NotNull String password,
      final @NotNull Map<String, Object> options) {
    return new ConnectionCredentialsImpl(facadeClassName, jdbcUrl, username, password, options);
  }

  static ConnectionCredentials of(
      final @NotNull String facadeClassName,
      final @NotNull String jdbcUrl,
      final @NotNull String username,
      final @NotNull String password) {
    return of(facadeClassName, jdbcUrl, username, password, Collections.emptyMap());
  }

  static ConnectionCredentials of(
      final @NotNull String facadeClassName,
      final @NotNull String jdbcUrl) {
    return of(facadeClassName, jdbcUrl, "", "");
  }

  static ConnectionCredentials of(
      final @NotNull String jdbcUrl,
      final @NotNull String username,
      final @NotNull String password,
      final @NotNull Map<String, Object> options) {
    return new ConnectionCredentialsImpl(jdbcUrl, username, password, options);
  }

  static ConnectionCredentials of(
      final @NotNull String jdbcUrl,
      final @NotNull String username,
      final @NotNull String password) {
    return of(jdbcUrl, username, password, Collections.emptyMap());
  }

  static ConnectionCredentials of(
      final @NotNull String jdbcUrl) {
    return of(jdbcUrl, "", "");
  }

  @NotNull String jdbcUrl();

  @NotNull String username();

  @NotNull String password();

  @NotNull Map<String, Object> options();

}
