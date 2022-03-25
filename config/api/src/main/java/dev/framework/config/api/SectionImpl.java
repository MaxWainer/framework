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

package dev.framework.config.api;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

final class SectionImpl extends AbstractValue implements Section {

  SectionImpl(final @Nullable Object object,
      final @NotNull List<String> comments) {
    super(object, comments);
  }

  @Override
  public @NotNull Config config() {
    return null;
  }

  @Override
  public @NotNull <V> Optional<V> deserialize(final @NotNull String path) {
    return Optional.empty();
  }

  @Override
  public <V> void serialize(@NotNull final V object, final @NotNull String path) {

  }

  @Override
  public @NotNull MapValue map(final @NotNull String... path) {
    return null;
  }

  @Override
  public @NotNull ListValue list(final @NotNull String... path) {
    return null;
  }

  @Override
  public @NotNull Value get(final @NotNull String... path) {
    return null;
  }

  @Override
  public @NotNull Optional<Section> parentSection() {
    return Optional.empty();
  }

  @Override
  public @NotNull String currentPath() {
    return null;
  }

  @Override
  public @NotNull Optional<Section> subSection(final @NotNull String name) {
    return Optional.empty();
  }

  @Override
  public @NotNull @Unmodifiable List<String> comments() {
    return null;
  }

  @Override
  public void writeComment(final @NotNull String comment) {

  }

  @Override
  public void removeComment(final int index) {

  }
}
