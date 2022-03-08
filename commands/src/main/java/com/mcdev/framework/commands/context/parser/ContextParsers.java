package com.mcdev.framework.commands.context.parser;

import com.mcdev.framework.commands.context.ContextParser;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

public interface ContextParsers {

  static ContextParser<String> regexPattern(final @RegExp @NotNull String pattern) {
    return new PatternParser(Pattern.compile(pattern));
  }

  static ContextParser<String> exactStrings(final @NotNull String... strings) {
    return new ExactStringsParser(Arrays.asList(strings));
  }

  // integer
  static ContextParser<Integer> integer() {
    return new AbstractNumberParser<Integer>() {
      @Override
      protected Integer parseNumber1(final @NotNull String raw) {
        return Integer.parseInt(raw);
      }
    };
  }

  static ContextParser<Integer> integerRange(
      final int from, final int to) {
    return new AbstractNumberRangeParser<Integer>(from, to) {
      @Override
      protected boolean checkNumbers(final @NotNull Integer inputNumber) {
        return inputNumber >= from || inputNumber <= to;
      }

      @Override
      protected Integer parseNumber1(final @NotNull String raw) {
        return Integer.parseInt(raw);
      }
    };
  }

  // float
  static ContextParser<Float> floatNumber() {
    return new AbstractNumberParser<Float>() {
      @Override
      protected Float parseNumber1(final @NotNull String raw) {
        return Float.parseFloat(raw);
      }
    };
  }

  static ContextParser<Float> floatRange(
      final float from, final float to) {
    return new AbstractNumberRangeParser<Float>(from, to) {
      @Override
      protected boolean checkNumbers(final @NotNull Float inputNumber) {
        return inputNumber >= from || inputNumber <= to;
      }

      @Override
      protected Float parseNumber1(final @NotNull String raw) {
        return Float.parseFloat(raw);
      }
    };
  }

  // long
  static ContextParser<Long> longNumber() {
    return new AbstractNumberParser<Long>() {
      @Override
      protected Long parseNumber1(final @NotNull String raw) {
        return Long.parseLong(raw);
      }
    };
  }

  static ContextParser<Long> longRange(
      final long from, final long to) {
    return new AbstractNumberRangeParser<Long>(from, to) {
      @Override
      protected boolean checkNumbers(final @NotNull Long inputNumber) {
        return inputNumber >= from || inputNumber <= to;
      }

      @Override
      protected Long parseNumber1(final @NotNull String raw) {
        return Long.parseLong(raw);
      }
    };
  }
}
