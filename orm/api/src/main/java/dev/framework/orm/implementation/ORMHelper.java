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

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.Reflections;
import dev.framework.commons.annotation.UtilityClass;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.adapter.json.DummyJsonObjectAdapter;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import dev.framework.orm.api.adapter.simple.DummyColumnTypeAdapter;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseColumn;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseJsonSerializable;
import dev.framework.orm.api.exception.UnknownAdapterException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
final class ORMHelper {

  private ORMHelper() {
    MoreExceptions.instantiationError();
  }

  static @NotNull JsonObjectAdapter findJsonAdapter(
      final @NotNull ORMFacade facade,
      final @NotNull BaseJsonSerializable serializable,
      final @NotNull Field field) throws UnknownAdapterException {
    final Class<? extends JsonObjectAdapter> adapter = serializable.value();

    if (adapter == DummyJsonObjectAdapter.class) {
      return facade.jsonAdaptersRepository()
          .findOrThrow(field.getType());
    }

    return facade
        .jsonAdaptersRepository()
        .adapterInstance(adapter)
        .orElseThrow(() -> new UnknownAdapterException(adapter));
  }

  static @Nullable ColumnTypeAdapter findColumnAdapter(
      final @NotNull ORMFacade facade,
      final @NotNull BaseColumn baseColumn) throws UnknownAdapterException {
    final Class<? extends ColumnTypeAdapter> columnAdapterClass = baseColumn.typeAdapter();

    if (columnAdapterClass != DummyColumnTypeAdapter.class) {
      final ColumnTypeAdapter columnTypeAdapter = facade
          .columnTypeAdaptersRepository()
          .adapterInstance(columnAdapterClass)
          .orElseThrow(() -> new UnknownAdapterException(columnAdapterClass));

      return columnTypeAdapter;
    }

    return null;
  }

  static Object handleConstructor(
      final @NotNull Constructor<?> constructor,
      final @NotNull Object[] data) throws Throwable {
    final MethodHandle handle = Reflections
        .trustedLookup()
        .unreflectConstructor(constructor);

    return handle.invokeWithArguments(data);
  }

  static JsonObjectAdapter collectionAdapter(
      final @NotNull ORMFacade facade,
      final @NotNull Field field,
      final @NotNull ColumnMeta meta) {
    final BaseJsonSerializable serializable = meta.serializerOptions();

    if (meta.collection()) {
      boolean useTopLevel = meta.collectionOptions().useTopLevelAnnotation();

      if (serializable == null) {
        useTopLevel = false;
      }

      final JsonObjectAdapter adapter;
      if (useTopLevel) {
        final Class<?> type = meta.genericType().value()[0];

        adapter = facade.jsonAdaptersRepository()
            .findOrThrow(type);
      } else {
        adapter = facade.jsonAdaptersRepository()
            .findOrThrow(Reflections.genericTypes(field.getType())[0]);
      }

      return adapter;
    }

    return null;
  }

  static JsonObjectAdapter[] mapAdapter(
      final @NotNull ORMFacade facade,
      final @NotNull Field field,
      final @NotNull ColumnMeta meta) {
    final BaseJsonSerializable serializable = meta.serializerOptions();

    if (meta.map()) {
      boolean useTopLevel = meta.mapOptions().useTopLevelAnnotation();

      if (serializable == null) {
        useTopLevel = false;
      }

      final JsonObjectAdapter keyAdapter;
      final JsonObjectAdapter valueAdapter;
      if (useTopLevel) {
        final Class<?> keyType = meta.genericType().value()[0];
        final Class<?> valueType = meta.genericType().value()[1];

        keyAdapter = facade.jsonAdaptersRepository()
            .findOrThrow(keyType);
        valueAdapter = facade.jsonAdaptersRepository()
            .findOrThrow(valueType);
      } else {
        final Class<?>[] types = Reflections.genericTypes(field.getType());

        keyAdapter = facade.jsonAdaptersRepository()
            .findOrThrow(types[0]);
        valueAdapter = facade.jsonAdaptersRepository()
            .findOrThrow(types[1]);
      }

      return new JsonObjectAdapter[]{keyAdapter, valueAdapter};
    }

    return null;
  }

  static Object fieldData(final @NotNull Field field, final @NotNull Object object)
      throws Throwable {
    final MethodHandle handle = Reflections.trustedLookup().unreflectGetter(field);

    return handle.bindTo(object).invoke();
  }

}
