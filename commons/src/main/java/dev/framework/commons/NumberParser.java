package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public final class NumberParser {

  private NumberParser() {
    Exceptions.instantiationError();
  }

  public static NumberParseResult<Integer> parseInt(@NotNull String input) {
    try {
      return new NumberParseResult<>(Integer.parseInt(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static NumberParseResult<Float> parseFloat(@NotNull String input) {
    try {
      return new NumberParseResult<>(Float.parseFloat(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static NumberParseResult<BigDecimal> parseBigDecimal(@NotNull String input) {
    try {
      return new NumberParseResult<>(new BigDecimal(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static NumberParseResult<BigInteger> parseBigInteger(@NotNull String input) {
    try {
      return new NumberParseResult<>(new BigInteger(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static NumberParseResult<Double> parseDouble(@NotNull String input) {
    try {
      return new NumberParseResult<>(Double.parseDouble(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static NumberParseResult<Byte> parseByte(@NotNull String input) {
    try {
      return new NumberParseResult<>(Byte.parseByte(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static NumberParseResult<Long> parseLong(@NotNull String input) {
    try {
      return new NumberParseResult<>(Long.parseLong(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static NumberParseResult<Short> parseShort(@NotNull String input) {
    try {
      return new NumberParseResult<>(Short.parseShort(input), true);
    } catch (NumberFormatException ignored) {
      // Ignore exactly this exception
    }

    return new NumberParseResult<>(null, false);
  }

  public static class NumberParseResult<T extends Number> {

    private final @Nullable T number;
    private final boolean result;

    NumberParseResult(@Nullable final T number, final boolean result) {
      this.number = number;
      this.result = result;
    }

    @Nullable
    public T number() {
      return number;
    }

    public boolean result() {
      return result;
    }

    public T numberOr(final @NotNull T number) {
      return this.number == null ? number : this.number;
    }

    public <X extends Exception> T numberOrThrow(final @NotNull Supplier<? extends X> supplier)
        throws X {
      if (number == null) {
        throw supplier.get();
      }

      return number;
    }

  }
}