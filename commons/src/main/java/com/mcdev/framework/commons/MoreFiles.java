package com.mcdev.framework.commons;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MoreFiles {

  private MoreFiles() {
    throw new AssertionError();
  }

  @Nullable
  public static Path cloneFromResource(final @NotNull Path targetFolder,
      final @NotNull String fileName)
      throws IOException {
    final InputStream resource = MoreFiles
        .class
        .getClassLoader()
        .getResourceAsStream('/' + fileName);

    if (resource == null) {
      return null;
    }

    final Path target = targetFolder.resolve(fileName);

    if (!Files.exists(target)) {
      Files.copy(resource, target);
    }

    return target;
  }

  @Nullable
  public static Properties quickProperties(final @NotNull Path file) throws IOException {
    if (!file.toString().endsWith(".properties")) {
      return null;
    }

    try (final InputStream stream = Files.newInputStream(file)) {
      final Properties properties = new Properties();

      properties.load(stream);

      return properties;
    }
  }

}
