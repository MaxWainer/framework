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

package dev.framework.commons;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MoreFiles {

  private MoreFiles() {
    MoreExceptions.instantiationError();
  }

  @NotNull
  public static Path cloneFromResource(
      final @NotNull Path targetFolder,
      final @NotNull String fileName)
      throws IOException {
    final InputStream resource = MoreFiles
        .class
        .getClassLoader()
        .getResourceAsStream('/' + fileName); // getting resource from classloader

    if (resource == null) { // if null, we throw an exception
      throw new IOException("Missing resource file: " + fileName);
    }

    final Path target = targetFolder.resolve(fileName); // resolve from target folder our file

    if (!Files.exists(target)) { // if it not exists, we copy from resource input stream
      Files.copy(resource, target);
    }

    return target; // return target
  }

  @Nullable
  public static Properties quickProperties(final @NotNull Path file) throws IOException {
    if (!file.toString().endsWith(".properties")) { // if our path isn't ends with properties,
      return null; // return null
    }

    try (final InputStream stream = Files.newInputStream(file)) { // else open stream
      final Properties properties = new Properties(); // create properties

      properties.load(stream); // load

      return properties;
    }
  }

}
