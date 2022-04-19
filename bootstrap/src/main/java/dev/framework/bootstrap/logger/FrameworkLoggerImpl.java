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

package dev.framework.bootstrap.logger;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

final class FrameworkLoggerImpl implements FrameworkLogger {

  private final Logger logger;

  FrameworkLoggerImpl(final @NotNull Logger logger) {
    this.logger = logger;
  }

  @Override
  public void info(@NotNull String message) {
    logger.info(() -> message);
  }

  @Override
  public void debug(@NotNull String message) {
    logger.info(() -> "[DEBUG] " + message);
  }

  @Override
  public void warn(@NotNull String message) {
    logger.log(Level.WARNING, () -> message);
  }

  @Override
  public void error(@NotNull String message) {
    logger.severe(() -> message);
  }

  @Override
  public void exception(@NotNull String message, @NotNull Throwable throwable) {
    logger.log(Level.SEVERE, throwable, () -> message);
  }
}
