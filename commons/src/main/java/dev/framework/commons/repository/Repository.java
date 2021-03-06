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

package dev.framework.commons.repository;

import dev.framework.commons.TraceExposer;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface Repository<I, T extends RepositoryObject> {

  @NotNull Optional<@NotNull T> find(final @NotNull I i);

  void register(final @NotNull I i, final @NotNull T t);

  default void register(final @NotNull T t) {
    register((I) t.identifier(), t);
  }

  default @NotNull @Unmodifiable Map<I, T> listObject() {
    throw new UnsupportedOperationException(TraceExposer.callerClassName() + ", repository is not support listing!");
  }

  default void delete(final @NotNull I i) {
    throw new UnsupportedOperationException(
        TraceExposer.callerClassName() + ", repository is not support deleting!");
  }

  default void update(final @NotNull I i, final @NotNull T t) {
    throw new UnsupportedOperationException(
        TraceExposer.callerClassName() + ", repository is not support updating!");
  }

  default boolean exists(final @NotNull I i) {
    return find(i).isPresent();
  }

  default @NotNull T findOrThrow(final @NotNull I i) {
    return find(i).orElseThrow(() -> new IllegalArgumentException("No value present by key: " + i));
  }

}
