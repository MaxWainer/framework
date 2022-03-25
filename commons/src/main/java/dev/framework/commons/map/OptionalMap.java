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

package dev.framework.commons.map;

import dev.framework.commons.function.Compositor;
import java.util.Optional;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public interface OptionalMap<K, V> extends BlindMap<K, V> {

  @NotNull Optional<V> get(final @NotNull K key);

  default boolean exists(final @NotNull K key) {
    return get(key).isPresent();
  }

  default Optional<V> findValue(final @NotNull Predicate<V> predicate) {
    return values()
        .stream()
        .filter(predicate)
        .findFirst();
  }

  default void useIfExists(final @NotNull K key,
      final @NotNull Compositor<V, K> valueUpdater) {
    final Optional<V> possible = get(key);

    possible.ifPresent(v -> replace(key, valueUpdater.apply(v, key)));
  }

}
