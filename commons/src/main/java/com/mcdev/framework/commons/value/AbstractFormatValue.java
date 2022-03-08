package com.mcdev.framework.commons.value;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public abstract class AbstractFormatValue<T, U> implements FormatValue<T, U> {

  protected final T delegate;
  protected final U baseUnit;

  protected final FormatValueConfig<U> config;

  protected AbstractFormatValue(
      final @UnknownNullability T delegate,
      final @NotNull U baseUnit,
      final @NotNull FormatValueConfig<U> config) {
    this.delegate = delegate;
    this.baseUnit = baseUnit;
    this.config = config;
  }

  protected AbstractFormatValue(
      final @UnknownNullability T delegate,
      final @NotNull U baseUnit) {
    this(delegate, baseUnit, FormatValueConfig.simple());
  }

  @Override
  public T delegate() {
    return delegate;
  }

  @Override
  public @NotNull U baseUnit() {
    return baseUnit;
  }
}
