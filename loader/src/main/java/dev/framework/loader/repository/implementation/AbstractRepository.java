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

package dev.framework.loader.repository.implementation;

import dev.framework.commons.StaticLogger;
import dev.framework.loader.repository.Repository;
import dev.framework.loader.repository.dependency.Dependencies;
import dev.framework.loader.repository.dependency.Dependency;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractRepository implements Repository {

  private static final Logger LOGGER = StaticLogger.getLogger();

  private static void loadFromUrl(
      final HttpURLConnection connection,
      final Path destinationDirectory,
      final Path dependencyFile) throws IOException {
    // Checking is destinationDirectory directory
    if (!Files.isDirectory(destinationDirectory)) {
      throw new IllegalArgumentException("destinationDirectory is not directory");
    }

    // We should use here try-with-resource construction
    try (final InputStream inputStream = connection.getInputStream()) {
      // Copying from input stream to destination directory
      Files.copy(inputStream, dependencyFile, StandardCopyOption.REPLACE_EXISTING);
    }
  }

  @Override
  public @Nullable Path loadDependency(@NotNull final Dependency dependency,
      @NotNull final Path to) {
    try {
      // Build file name
      final String fileName = Dependencies.fileNameOf(dependency);

      // Resolve dependency file
      final Path dependencyFile = to.resolve(fileName);
      final Path relocatedDependencyFile = to.resolve(
          Dependencies.fileNameOf(dependency, "relocated"));

      if (Files.exists(relocatedDependencyFile)) {
        return relocatedDependencyFile;
      }

      // If exists, return it
      if (Files.exists(dependencyFile)) {
        return dependencyFile;
      }

      LOGGER.info(() -> "Trying loading dependency " + dependency.artifactId() + "...");

      final long start = System.currentTimeMillis();

      // Building url
      final String stringUrl = this.repositoryUrl()
          + dependency.groupId().replace("{}", "/") + '/'
          + dependency.artifactId().replace("{}", "/") + '/'
          + dependency.version() + '/'
          + fileName;

      // Create url
      final URL url = new URL(stringUrl);

      // Create and open url connection
      final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

      // We need get from connection data
      urlConnection.setRequestMethod("GET");
      // Enable do input
      urlConnection.setDoInput(true);
      // Connecting
      urlConnection.connect();

      // Loading from url
      loadFromUrl(urlConnection, to, dependencyFile);

      // All good, printing loading time
      LOGGER.info(() -> "Successfully loaded dependency " + dependency.artifactId() + ", in " + (
          System.currentTimeMillis() - start) + "ms!");
      return dependencyFile;
    } catch (final IOException exception) {
      // Something went wrong, print info
      throw new UnsupportedOperationException(
          "An exception acquired while loading dependency " + dependency.artifactId() + "!",
          exception);
    }
  }

  @NotNull
  protected abstract String repositoryUrl();

}
