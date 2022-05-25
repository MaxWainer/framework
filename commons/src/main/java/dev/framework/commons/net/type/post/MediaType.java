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

package dev.framework.commons.net.type.post;

import java.nio.charset.Charset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MediaType {

  public static @NotNull MediaType create(final @NotNull String type,
      final @NotNull String subtype,
      final @NotNull Charset charset) {
    return new MediaType(type, subtype, charset);
  }

  public static @NotNull MediaType create(
      final @NotNull String type,
      final @NotNull String subtype) {
    return new MediaType(type, subtype);
  }

  private final String type;
  private final String subtype;
  private final Charset charset;

  MediaType(
      final @NotNull String type,
      final @NotNull String subtype,
      final @Nullable Charset charset) {
    this.type = type;
    this.subtype = subtype;
    this.charset = charset;
  }

  MediaType(
      final @NotNull String type,
      final @NotNull String subtype) {
    this(type, subtype, null);
  }

  @Override
  public String toString() {
    return String.format("%s/%s", type, subtype) + (charset == null ? "" : "; charset=" + charset);
  }
}
