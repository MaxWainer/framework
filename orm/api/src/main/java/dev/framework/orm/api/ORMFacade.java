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

import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.query.QueryFactory;
import dev.framework.orm.api.registry.ObjectDataRegistry;
import dev.framework.orm.api.registry.ObjectRepositoryRegistry;
import dev.framework.orm.api.registry.ObjectResolverRegistry;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import dev.framework.orm.api.update.TableUpdater;
import java.io.Closeable;
import org.jetbrains.annotations.NotNull;

public interface ORMFacade extends Closeable {

  /**
   * Get registry of object repositories
   *
   * @return {@link ObjectRepositoryRegistry}
   */
  @NotNull ObjectRepositoryRegistry repositoryRegistry();

  /**
   * Get object data registry
   *
   * @return {@link ObjectDataRegistry}
   */
  @NotNull ObjectDataRegistry dataRegistry();

  @NotNull ObjectResolverRegistry resolverRegistry();

  @NotNull JsonAdapterRepository jsonAdaptersRepository();

  @NotNull ColumnTypeAdapterRepository columnTypeAdaptersRepository();

  @NotNull TableUpdater tableUpdater();

  @NotNull QueryFactory queryFactory();

  @NotNull ConnectionSource connectionSource();

  @NotNull DialectProvider dialectProvider();

  @NotNull ObjectDataFactory dataFactory();

  @NotNull ReferenceClassFactory referenceClassFactory();

  void open() throws MissingRepositoryException;

}
