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

package dev.framework.commands.context;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public final class ParserResult<T> {

  private final T result;
  private final Component errorMessage;

  ParserResult(final T result, final Component errorMessage) {
    this.result = result;
    this.errorMessage = errorMessage;
  }

  public static <V> ParserResult<V> success(final @NotNull V v) {
    return new ParserResult<>(v, null);
  }

  public static <V> ParserResult<V> error(final @NotNull Component errorMessage) {
    return new ParserResult<>(null, errorMessage);
  }

  public T result() {
    return result;
  }

  public boolean error() {
    return errorMessage != null;
  }

  public Component errorMessage() {
    return errorMessage;
  }
}
