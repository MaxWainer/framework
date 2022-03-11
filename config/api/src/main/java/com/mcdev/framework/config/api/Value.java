package com.mcdev.framework.config.api;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Value {

  boolean isMap();

  boolean isList();

  boolean isPrimitive();

  @NotNull PrimitiveValue asPrimitive();

  @NotNull ListValue asList();

  @NotNull MapValue asMap();

  // null-value
  @Nullable Object raw();

  // setters
  void set(final @Nullable Object raw);

  boolean exists();

  // null-safe
  default @NotNull Optional<Object> rawSafe() {
    return Optional.ofNullable(raw());
  }
}
