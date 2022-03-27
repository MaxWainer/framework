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

package dev.framework.loader.helper;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.StaticLogger;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public final class JVMHelper {

  private static final Logger LOGGER = StaticLogger.getLogger();

  private static final Set<JavaVersion> REFLECTION_LOADABLE_VERSION = EnumSet.of(
      JavaVersion.JAVA_8
  );

  private static final Set<JavaVersion> THE_UNSAFE_LOADABLE_VERSION = EnumSet.of(
      JavaVersion.JAVA_9,
      JavaVersion.JAVA_10,
      JavaVersion.JAVA_11,
      JavaVersion.JAVA_12,
      JavaVersion.JAVA_13,
      JavaVersion.JAVA_14,
      JavaVersion.JAVA_15,
      JavaVersion.JAVA_16,
      JavaVersion.JAVA_17
  );

  public static final JavaVersion CURRENT_VERSION = detectVersion();

  private JVMHelper() {
    MoreExceptions.instantiationError();
  }

  public static boolean isReflectionSupported() {
    return REFLECTION_LOADABLE_VERSION.contains(CURRENT_VERSION);
  }

  public static boolean isTheUnsafeSupported() {
    return THE_UNSAFE_LOADABLE_VERSION.contains(CURRENT_VERSION);
  }

  private static JavaVersion detectVersion() {
    final String stringVersion = System.getProperty("java.version");

    LOGGER.info(() -> "Running plugin on version " + stringVersion);

    final JavaVersion detected = JavaVersion.fromString(stringVersion);

    if (detected == JavaVersion.UNDETECTED) {
      LOGGER.warning("=====================================================");
      LOGGER.warning("> You are using java version which is not support by framework!");
      LOGGER.warning("> Please, switch it to most relevant version, such as");
      LOGGER.warning(">  Java 1.8, if you using lower than this version!");
      LOGGER.warning("=====================================================");
    }

    if (THE_UNSAFE_LOADABLE_VERSION.contains(detected)) {
      LOGGER.info("Using the unsafe java class loading strategy!");
    }

    if (REFLECTION_LOADABLE_VERSION.contains(detected)) {
      LOGGER.info("Using reflection java class loading strategy!");
    }

    return detected;
  }

  public enum JavaVersion {
    JAVA_8,
    JAVA_9,
    JAVA_10,
    JAVA_11,
    JAVA_12,
    JAVA_13,
    JAVA_14,
    JAVA_15,
    JAVA_16,
    JAVA_17,
    UNDETECTED;

    static JavaVersion fromString(final @NotNull String propertyVersion) {
      if (propertyVersion.startsWith("1.")) {
        return JAVA_8;
      }

      if (propertyVersion.contains("17")) {
        return JAVA_17;
      }

      if (propertyVersion.contains("16")) {
        return JAVA_16;
      }

      if (propertyVersion.contains("15")) {
        return JAVA_15;
      }

      if (propertyVersion.contains("4")) {
        return JAVA_14;
      }

      if (propertyVersion.contains("13")) {
        return JAVA_13;
      }

      if (propertyVersion.contains("12")) {
        return JAVA_12;
      }

      if (propertyVersion.contains("11")) {
        return JAVA_11;
      }

      if (propertyVersion.contains("10")) {
        return JAVA_10;
      }

      if (propertyVersion.contains("9")) {
        return JAVA_9;
      }

      return UNDETECTED;
    }
  }

}
