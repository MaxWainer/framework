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

package dev.framework.commons.cooldown;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dev.framework.commons.time.TemporalValue;
import java.time.temporal.ChronoUnit;
import org.jetbrains.annotations.NotNull;

final class CooldownListImpl<T> implements CooldownList<T> {

  private final TemporalValue<ChronoUnit> cooldownTime;

  private final LoadingCache<T, Waiter<T>> cache = CacheBuilder.newBuilder()
      .concurrencyLevel(1)
      .build(new CacheLoader<T, Waiter<T>>() {
        @Override
        public Waiter<T> load(T key) throws Exception {
          return null;
        }
      });

  CooldownListImpl(
      final @NotNull TemporalValue<ChronoUnit> cooldownTime) {
    this.cooldownTime = cooldownTime;
  }

  @Override
  public @NotNull TemporalValue<ChronoUnit> cooldownTime() {
    return null;
  }

  @Override
  public @NotNull Waiter<T> check(@NotNull T t) {
    return null;
  }
}
