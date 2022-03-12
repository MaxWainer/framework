package com.mcdev.framework.config.api;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractValue extends AbstractCommentable implements Value {

  private Object object;

  protected AbstractValue(final @Nullable Object object, final @NotNull List<String> comments) {
    super(comments);
    this.object = object;
  }

  @Override
  public boolean isMap() {
    return this instanceof MapValue;
  }

  @Override
  public boolean isList() {
    return this instanceof ListValue;
  }

  @Override
  public boolean isPrimitive() {
    return this instanceof PrimitiveValue;
  }

  @Override
  public @NotNull PrimitiveValue asPrimitive() {
    if (isPrimitive()) {
      return (PrimitiveValue) this;
    }

    throw new IllegalArgumentException("Not primitive value!");
  }

  @Override
  public @NotNull ListValue asList() {
    if (isList()) {
      return (ListValue) this;
    }

    throw new IllegalArgumentException("Not list value!");
  }

  @Override
  public @NotNull MapValue asMap() {
    if (isMap()) {
      return (MapValue) this;
    }

    throw new IllegalArgumentException("Not map value!");
  }

  @Override
  public @Nullable Object raw() {
    return object;
  }

  @Override
  public void set(final @Nullable Object raw) {
    object = raw;
  }

  @Override
  public boolean exists() {
    return object != null;
  }
}
