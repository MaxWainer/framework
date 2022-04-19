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

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.ref.ReferenceObject;
import org.jetbrains.annotations.NotNull;

final class ReferenceClassImpl<T extends RepositoryObject> implements ReferenceClass<T> {

  private final ObjectData objectData;
  private final Class<T> clazz;

  ReferenceClassImpl(final @NotNull ObjectData objectData, final @NotNull Class<T> clazz) {
    this.objectData = objectData;
    this.clazz = clazz;
  }

  @Override
  public @NotNull ReferenceObject<T> newInstance(@NotNull Object... args) throws Throwable {
    return new ReferenceObjectImpl<>(
        (T) ORMHelper.handleConstructor(objectData.targetConstructor(), args),
        clazz,
        objectData);
  }

  @Override
  public @NotNull ObjectData objectData() {
    return objectData;
  }
}
