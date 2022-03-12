package com.mcdev.framework.config.api;

import com.google.gson.JsonElement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

final class SectionImpl extends AbstractValue implements Section {

  SectionImpl(final @Nullable Object object,
      final @NotNull List<String> comments) {
    super(object, comments);
  }

  @Override
  public @NotNull Config config() {
    return null;
  }

  @Override
  public @NotNull <V> Optional<V> deserialize(final @NotNull String path) {
    return Optional.empty();
  }

  @Override
  public <V> void serialize(@NotNull final V object, final @NotNull String path) {

  }

  @Override
  public @NotNull MapValue map(final @NotNull String... path) {
    return null;
  }

  @Override
  public @NotNull ListValue list(final @NotNull String... path) {
    return null;
  }

  @Override
  public @NotNull Value get(final @NotNull String... path) {
    return null;
  }

  @Override
  public @NotNull Optional<Section> parentSection() {
    return Optional.empty();
  }

  @Override
  public @NotNull String currentPath() {
    return null;
  }

  @Override
  public @NotNull Optional<Section> subSection(final @NotNull String name) {
    return Optional.empty();
  }

  @Override
  public @NotNull @Unmodifiable List<String> comments() {
    return null;
  }

  @Override
  public void writeComment(final @NotNull String comment) {

  }

  @Override
  public void removeComment(final int index) {

  }
}
