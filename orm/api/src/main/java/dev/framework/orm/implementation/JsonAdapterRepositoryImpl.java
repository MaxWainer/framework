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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.framework.commons.Types;
import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.exception.UnknownAdapterException;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class JsonAdapterRepositoryImpl implements JsonAdapterRepository {

  private final OptionalMap<Class<?>, JsonObjectAdapter<?>> registry = OptionalMaps.newConcurrentMap();

  @Override
  public @NotNull Optional<@NotNull JsonObjectAdapter<?>> find(@NotNull Class<?> aClass) {
    return registry.get(aClass);
  }

  @Override
  public void register(@NotNull Class<?> aClass,
      @NotNull JsonObjectAdapter<?> jsonObjectAdapter) {
    registry.put(aClass, jsonObjectAdapter);
  }

  @Override
  public <T extends RepositoryObject> @NotNull T fromJson(
      @NotNull String json,
      @NotNull Class<T> type) throws UnknownAdapterException {
    final JsonObjectAdapter<T> adapter = findAdapter(type);

    final JsonElement element = JsonParser.parseString(json);

    return adapter.construct(element);
  }

  @Override
  public @NotNull <T extends RepositoryObject> String toJson(@NotNull T t)
      throws UnknownAdapterException {
    final JsonObjectAdapter<T> adapter = (JsonObjectAdapter<T>) findAdapter(t.getClass());
    final JsonElement element = adapter.deconstruct(t);

    return element.toString();
  }

  @Override
  public @NotNull Optional<JsonObjectAdapter<?>> adapterInstance(
      @NotNull Class<? extends JsonObjectAdapter> clazz) {
    return registry.findValue(instance -> instance.getClass().equals(clazz));
  }

  private <T extends RepositoryObject> JsonObjectAdapter<T> findAdapter(
      final @NotNull Class<T> type) throws UnknownAdapterException {
    return (JsonObjectAdapter<T>) registry.get(type)
        .orElseThrow(() -> new UnknownAdapterException(type));
  }

}
