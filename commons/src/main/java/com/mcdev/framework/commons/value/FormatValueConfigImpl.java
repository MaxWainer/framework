package com.mcdev.framework.commons.value;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

final class FormatValueConfigImpl<U> implements FormatValueConfig<U> {

  private final boolean allowZero;
  private final Set<U> excludedUnits;
  private final CharSequence prefix;
  private final CharSequence suffix;
  private final CharSequence delimiter;

  FormatValueConfigImpl(
      final boolean allowZero,
      final @NotNull Set<U> excludedUnits,
      final @NotNull CharSequence prefix,
      final @NotNull CharSequence suffix,
      final @NotNull CharSequence delimiter) {
    this.allowZero = allowZero;
    this.excludedUnits = excludedUnits;
    this.prefix = prefix;
    this.suffix = suffix;
    this.delimiter = delimiter;
  }

  @Override
  public boolean allowZero() {
    return allowZero;
  }

  @Override
  public @NotNull @Unmodifiable Set<U> excludedUnits() {
    return excludedUnits;
  }

  @Override
  public @NotNull CharSequence prefix() {
    return prefix;
  }

  @Override
  public @NotNull CharSequence suffix() {
    return suffix;
  }

  @Override
  public @NotNull CharSequence delimiter() {
    return delimiter;
  }

  static final class BuilderImpl<V> implements Builder<V> {

    private boolean allowZero = true;

    private CharSequence prefix = "", suffix = "", delimiter = "";

    private final Set<V> excludedUnits = new HashSet<>();

    @Override
    public Builder<V> exclude(@NotNull final V @NonNls ... units) {
      this.excludedUnits.addAll(Arrays.asList(units));
      return this;
    }

    @Override
    public Builder<V> allow(@NotNull final V @NonNls ... units) {
      Arrays.asList(units).forEach(this.excludedUnits::remove);
      return this;
    }

    @Override
    public Builder<V> prefix(final @NotNull CharSequence prefix) {
      this.prefix = prefix;
      return this;
    }

    @Override
    public Builder<V> suffix(final @NotNull CharSequence suffix) {
      this.suffix = suffix;
      return this;
    }

    @Override
    public Builder<V> delimiter(final @NotNull CharSequence delimiter) {
      this.delimiter = delimiter;
      return this;
    }

    @Override
    public Builder<V> allowZero(final boolean allowZero) {
      this.allowZero = allowZero;
      return this;
    }

    @Override
    public FormatValueConfigImpl<V> build() {
      return new FormatValueConfigImpl<>(allowZero, excludedUnits, prefix, suffix, delimiter);
    }
  }
}