package com.mcdev.framework.config;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PrimitiveValue extends Value {

  @Nullable String string();

  @Nullable Boolean bool();

  @Nullable Integer intNumber();

  @Nullable Float floatNumber();

  @Nullable Double doubleNumber();

  @Nullable Long longNumber();

  @Nullable Short shortNumber();

  @Nullable Byte byteNumber();

  <V> @Nullable V as(final @NotNull Class<V> as);

  // null-safe values

  default @NotNull Optional<String> stringSafe() {
    return Optional.ofNullable(string());
  }

  default @NotNull Optional<Boolean> boolSafe() {
    return Optional.ofNullable(bool());
  }

  default @NotNull Optional<Integer> intNumberSafe() {
    return Optional.ofNullable(intNumber());
  }

  default @NotNull Optional<Float> floatNumberSafe() {
    return Optional.ofNullable(floatNumber());
  }

  default @NotNull Optional<Double> doubleNumberSafe() {
    return Optional.ofNullable(doubleNumber());
  }

  default @NotNull Optional<Long> longNumberSafe() {
    return Optional.ofNullable(longNumber());
  }

  default @NotNull Optional<Short> shortNumberSafe() {
    return Optional.ofNullable(shortNumber());
  }

  default @NotNull Optional<Byte> byteNumberSafe() {
    return Optional.ofNullable(byteNumber());
  }

  default <V> @NotNull Optional<V> asSafe(final @NotNull Class<V> as) {
    return Optional.ofNullable(as(as));
  }

}
