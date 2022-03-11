package com.mcdev.framework.config;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Section extends AutoCloseable {

  @NotNull Config config();

  <V> @NotNull Optional<V> deserialize(final @NotNull String path);

  <V> void serialize(final @NotNull V object, final @NotNull String path);

  @NotNull MapValue map(final @NotNull String... path);

  @NotNull ListValue list(final @NotNull String... path);

  @NotNull Value get(final @NotNull String... path);

  @NotNull Optional<Section> parentSection();

  @NotNull String currentPath();

  @NotNull Optional<Section> subSection(final @NotNull String name);

}
