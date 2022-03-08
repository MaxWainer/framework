package com.mcdev.framework.commons.value;

import com.mcdev.framework.commons.value.FormatValueConfigImpl.BuilderImpl;
import java.util.Set;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import com.mcdev.framework.commons.Buildable;

public interface FormatValueConfig<U> {

  static <T> Builder<T> builder() {
    return new BuilderImpl<>();
  }

  static <T> FormatValueConfig<T> simple() {
    return FormatValueConfig.<T>builder()
        .build();
  }

  boolean allowZero();

  @NotNull @Unmodifiable Set<U> excludedUnits();

  @NotNull CharSequence prefix();

  @NotNull CharSequence suffix();

  @NotNull CharSequence delimiter();

  default boolean allowed(final @NotNull U u) {
    return !excludedUnits().contains(u);
  }

  default boolean disallowed(final @NotNull U u) {
    return !allowed(u);
  }

  interface Builder<V> extends Buildable<FormatValueConfig<V>> {

    Builder<V> exclude(final @NotNull V @NonNls ...units);

    Builder<V> allow(final @NotNull V @NonNls ...units);

    Builder<V> prefix(final @NotNull CharSequence prefix);

    Builder<V> suffix(final @NotNull CharSequence suffix);

    Builder<V> delimiter(final @NotNull CharSequence delimiter);

    Builder<V> allowZero(final boolean allowZero);

  }

}
