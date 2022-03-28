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

import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ReferenceClassFactory;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.query.QueryFactory;
import dev.framework.orm.api.registry.ObjectDataRegistry;
import dev.framework.orm.api.registry.ObjectRepositoryRegistry;
import dev.framework.orm.api.registry.ObjectResolverRegistry;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractORMFacade implements ORMFacade {

  private static final String OBJECT_INFO_TABLE_NAME = "_DEV_FRMWRK_ObjectInfo";

  private final ObjectDataFactory dataFactory =
      new ObjectDataFactoryImpl();
  private final ColumnTypeAdapterRepository columnTypeAdapters =
      new ColumnTypeAdapterRepositoryImpl();
  private final JsonAdapterRepository jsonAdapters =
      new JsonAdapterRepositoryImpl();
  private final ObjectDataRegistry objectDataRegistry =
      new ObjectDataRegistryImpl();
  private final ObjectResolverRegistry objectResolverRegistry =
      new ObjectResolverRegistryImpl();

  private final ReferenceClassFactory referenceClassFactory =
      new CachedReferenceClassFactory(dataFactory);
  private final ObjectRepositoryRegistry objectRepositoryRegistry =
      new ObjectRepositoryRegistryImpl(this);
  private QueryFactory queryFactory;

  @Override
  public void open() {
    queryFactory = new QueryFactoryImpl(dialectProvider(), connectionSource());
  }

  @Override
  public void close() throws IOException {

  }

  @Override
  public @NotNull QueryFactory queryFactory() {
    return this.queryFactory;
  }

  @Override
  public @NotNull ReferenceClassFactory referenceClassFactory() {
    return this.referenceClassFactory;
  }

  @Override
  public @NotNull ObjectRepositoryRegistry objectRepositoryRegistry() {
    return this.objectRepositoryRegistry;
  }

  @Override
  public @NotNull ObjectDataRegistry objectDataRegistry() {
    return this.objectDataRegistry;
  }

  @Override
  public @NotNull ObjectResolverRegistry resolverRegistry() {
    return this.objectResolverRegistry;
  }

  @Override
  public @NotNull JsonAdapterRepository jsonAdaptersRepository() {
    return this.jsonAdapters;
  }

  @Override
  public @NotNull ColumnTypeAdapterRepository columnTypeAdaptersRepository() {
    return this.columnTypeAdapters;
  }

  @Override
  public @NotNull ObjectDataFactory objectDataFactory() {
    return this.dataFactory;
  }
}
