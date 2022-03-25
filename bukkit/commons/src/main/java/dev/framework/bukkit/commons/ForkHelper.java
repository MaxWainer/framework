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

package dev.framework.bukkit.commons;

import dev.framework.commons.Exceptions;
import dev.framework.commons.LoggerCompat;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;

public final class ForkHelper {

  private static final Logger LOGGER = LoggerCompat.getLogger();
  private static final Set<Fork> SUPPORTED_FORKS = EnumSet.of(Fork.PAPER);
  public static final Fork CURRENT_FORK = detectFork();

  private ForkHelper() {
    Exceptions.instantiationError();
  }

  private static Fork detectFork() {
    final Fork fork;
    if (hasClass("com.destroystokyo.paper.VersionHistoryManager$VersionData")) {
      fork = Fork.PAPER;
    } else if (hasClass("org.bukkit.entity.Player$Spigot")) {
      fork = Fork.SPIGOT;
    } else {
      fork = Fork.UNSUPPORTED;
    }

    // If fork is not supported, we print
    // corresponding message
    if (!SUPPORTED_FORKS.contains(fork)) {
      LOGGER.warning("=====================================================");
      LOGGER.warning("> You are using fork which is poorly support by framework!");
      LOGGER.warning(">  I'd suggest use paper: https://papermc.io/downloads");
      LOGGER.warning("=====================================================");
    }

    // If current for is paper, print message
    if (fork == Fork.PAPER) {
      LOGGER.info("Good boi");
    }

    return fork;
  }

  private static boolean hasClass(final String className) {
    try {
      Class.forName(className);

      return true;
    } catch (final ClassNotFoundException ignored) {
    }

    return false;
  }

  public enum Fork {
    PAPER,
    SPIGOT,
    UNSUPPORTED
  }

}
