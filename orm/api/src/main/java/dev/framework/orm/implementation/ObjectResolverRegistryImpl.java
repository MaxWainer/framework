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
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.registry.ObjectResolverRegistry;
import org.jetbrains.annotations.NotNull;

final class ObjectResolverRegistryImpl implements ObjectResolverRegistry {

  private final
  OptionalMap<Class<? extends RepositoryObject>, ObjectResolver<?>>
      repositoryCache = OptionalMaps.newConcurrentMap();

  private final ORMFacade facade;

  ObjectResolverRegistryImpl(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  @Override
  public @NotNull <V extends RepositoryObject> ObjectResolver<V> findResolver(
      @NotNull Class<? extends V> clazz) throws MissingRepositoryException {
    return (ObjectResolver<V>) repositoryCache.get(clazz)
        .orElseThrow(() -> new MissingRepositoryException(clazz));
  }

  @Override
  public <V extends RepositoryObject> void registerResolver(@NotNull Class<? extends V> clazz,
      @NotNull ObjectResolver<V> resolver) {
    repositoryCache.put(clazz, resolver);
  }

  @Override
  public <V extends RepositoryObject> void registerResolver(@NotNull Class<? extends V> clazz)
      throws MissingAnnotationException, MetaConstructionException {
    repositoryCache.put(clazz, new ObjectResolverImpl<>(facade.referenceClassFactory()
        .createReference(clazz)));
  }
}
