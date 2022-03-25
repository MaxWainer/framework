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

package dev.framework.orm.api;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import dev.framework.orm.api.update.TableUpdater;
import java.io.Closeable;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface ORMFacade extends Closeable {

  <I, O extends RepositoryObject<I>> @NotNull ObjectRepository<I, O> findRepository(
      final @NotNull Class<? extends O> clazz) throws MissingRepositoryException;

  <I, O extends RepositoryObject<I>> void registerRepository(
      final @NotNull Class<? extends O> clazz, final @NotNull ObjectRepository<I, O> repository);

  <I, O extends RepositoryObject<I>> void registerRepository(
      final @NotNull Class<? extends O> clazz) throws MissingAnnotationException, MetaConstructionException;

  @NotNull JsonAdapterRepository jsonAdapters();

  @NotNull ColumnTypeAdapterRepository columnTypeAdapters();

  @NotNull TableUpdater tableUpdater();

  @NotNull ConnectionSource connectionSource();

  @NotNull Optional<ObjectData> findData(final @NotNull Class<? extends RepositoryObject> clazz);

  @NotNull ObjectDataFactory dataFactory();

  @NotNull DialectProvider dialectProvider();

  void open();

}
