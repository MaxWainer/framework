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

import dev.framework.commons.Exceptions;
import dev.framework.commons.Reflections;
import dev.framework.commons.annotation.UtilityClass;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseJsonSerializable;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;

@UtilityClass
final class ORMHelper {

  private ORMHelper() {
    Exceptions.instantiationError();
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

        adapter = facade.jsonAdapters()
            .findOrThrow(type);
      } else {
        adapter = facade.jsonAdapters()
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

        keyAdapter = facade.jsonAdapters()
            .findOrThrow(keyType);
        valueAdapter = facade.jsonAdapters()
            .findOrThrow(valueType);
      } else {
        final Class<?>[] types = Reflections.genericTypes(field.getType());

        keyAdapter = facade.jsonAdapters()
            .findOrThrow(types[0]);
        valueAdapter = facade.jsonAdapters()
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
