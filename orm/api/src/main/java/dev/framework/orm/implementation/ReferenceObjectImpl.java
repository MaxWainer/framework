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

import dev.framework.commons.Reflections;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.ref.ReferenceObject;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;

final class ReferenceObjectImpl<T extends RepositoryObject> implements ReferenceObject<T> {

  private final T instance;
  private final Class<? extends T> clazz;
  private final ObjectData data;

  ReferenceObjectImpl(final @NotNull T instance, final @NotNull Class<? extends T> clazz,
      final @NotNull ObjectData data) {
    this.instance = instance;
    this.clazz = clazz;
    this.data = data;
  }

  @Override
  public @NotNull T asObject() {
    return instance;
  }

  @Override
  public Object filedData(@NotNull String fieldName) throws Throwable {
    final Field field = clazz.getDeclaredField(fieldName);

    final MethodHandle handle = Reflections.trustedLookup().unreflectGetter(field);

    return handle.bindTo(instance).invokeWithArguments();
  }

  @Override
  public @NotNull ObjectData objectData() {
    return data;
  }
}
