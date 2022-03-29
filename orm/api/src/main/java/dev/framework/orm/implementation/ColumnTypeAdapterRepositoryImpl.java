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

package dev.framework.orm.implementation;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import dev.framework.orm.api.adapters.JavaInstantAsStringColumnTypeAdapter;
import dev.framework.orm.api.adapters.JavaInstantAsLongColumnTypeAdapter;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import dev.framework.orm.api.adapters.UUIDColumnTypeAdapter;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class ColumnTypeAdapterRepositoryImpl implements ColumnTypeAdapterRepository {

  private final OptionalMap<Class<?>, ColumnTypeAdapter<?, ?>> registry = OptionalMaps.newConcurrentMap();

  ColumnTypeAdapterRepositoryImpl() {
    register(new UUIDColumnTypeAdapter());
    register(new JavaInstantAsStringColumnTypeAdapter());
    register(new JavaInstantAsLongColumnTypeAdapter());
  }

  @Override
  public @NotNull Optional<@NotNull ColumnTypeAdapter<?, ?>> find(@NotNull Class<?> aClass) {
    return registry.get(aClass);
  }

  @Override
  public void register(@NotNull Class<?> aClass,
      @NotNull ColumnTypeAdapter<?, ?> columnTypeAdapter) {
    registry.put(aClass, columnTypeAdapter);
  }

  @Override
  public @NotNull Optional<ColumnTypeAdapter<?, ?>> adapterInstance(
      @NotNull Class<? extends ColumnTypeAdapter> clazz) {
    return Optional.empty();
  }
}
