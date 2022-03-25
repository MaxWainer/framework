/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.commands.context.parser;

import dev.framework.commands.context.ContextParser;
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

  static ContextParser<Boolean> bool() {
    return new BooleanParser();
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

  // double
  static ContextParser<Double> doubleNumber() {
    return new AbstractNumberParser<Double>() {
      @Override
      protected Double parseNumber1(final @NotNull String raw) {
        return Double.parseDouble(raw);
      }
    };
  }

  static ContextParser<Double> integerRange(
      final double from, final double to) {
    return new AbstractNumberRangeParser<Double>(from, to) {
      @Override
      protected boolean checkNumbers(final @NotNull Double inputNumber) {
        return inputNumber >= from || inputNumber <= to;
      }

      @Override
      protected Double parseNumber1(final @NotNull String raw) {
        return Double.parseDouble(raw);
      }
    };
  }
}
