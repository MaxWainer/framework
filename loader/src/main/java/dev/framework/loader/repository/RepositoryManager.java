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

package dev.framework.loader.repository;

import dev.framework.commons.StaticLogger;
import dev.framework.loader.repository.implementation.UndetectedRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public final class RepositoryManager {

  private static final Logger LOGGER = StaticLogger.logger();

  private final Map<String, Repository> registeredRepositories = new HashMap<>();

  public void registerRepository(final @NotNull String alias,
      final @NotNull Repository repository) {
    this.registeredRepositories.putIfAbsent(alias, repository);
  }

  public @NotNull Optional<Repository> repository(final @NotNull String alias) {
    return Optional.ofNullable(this.registeredRepositories.get(alias));
  }

  public @NotNull Repository repositorySafe(final @NotNull String value) {
    return this.repository(value).orElseGet(() -> {
      try {
        // To check is entered value URL
        new URL(value);
        return new UndetectedRepository(value);
      } catch (final MalformedURLException exception) {
        LOGGER.log(Level.SEVERE, exception, () -> "Malformed url " + value + "!");
      }
      throw new UnsupportedOperationException(
          "Looks like entered value " + value + ", is not url!");
    });
  }

}
