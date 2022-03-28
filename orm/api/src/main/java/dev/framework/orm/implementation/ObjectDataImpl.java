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
import dev.framework.commons.version.Version;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.TableMeta;
import java.lang.reflect.Constructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

final class ObjectDataImpl implements ObjectData {

  private final Class<? extends RepositoryObject> delegate;
  private final Version version;
  private final Constructor<?> targetConstructor;

  private final TableMeta meta;

  ObjectDataImpl(
      final @NotNull Class<? extends RepositoryObject> delegate,
      final @NotNull Version version,
      final @NotNull TableMeta meta,
      final @NotNull Constructor<?> targetConstructor) {
    this.delegate = delegate;
    this.version = version;
    this.meta = meta;
    this.targetConstructor = targetConstructor;
  }

  @Override
  public @UnknownNullability Class<? extends RepositoryObject> delegate() {
    return delegate;
  }

  @Override
  public @NotNull TableMeta tableMeta() {
    return meta;
  }

  @Override
  public @NotNull Version version() {
    return version;
  }

  @Override
  public @NotNull Constructor<?> targetConstructor() {
    return targetConstructor;
  }
}
