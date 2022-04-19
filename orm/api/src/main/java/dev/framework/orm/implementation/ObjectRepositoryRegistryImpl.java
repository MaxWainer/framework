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
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.registry.ObjectRepositoryRegistry;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

final class ObjectRepositoryRegistryImpl implements ObjectRepositoryRegistry {

  private final ORMFacade facade;

  private final
  OptionalMap<Class<? extends RepositoryObject>, ObjectRepository<?, ?>>
      repositoryCache = OptionalMaps.newConcurrentMap();

  ObjectRepositoryRegistryImpl(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  @Override
  public @NotNull <I, O extends RepositoryObject<I>> ObjectRepository<I, O> findRepository(
      @NotNull Class<? extends O> clazz) throws MissingRepositoryException {
    return (ObjectRepository<I, O>) repositoryCache.get(clazz)
        .orElseThrow(() -> new MissingRepositoryException(clazz));
  }

  @Override
  public <I, O extends RepositoryObject<I>> void registerRepository(
      @NotNull Class<? extends O> clazz, @NotNull ObjectRepository<I, O> repository) {
    repositoryCache.put(clazz, repository);
  }

  @Override
  public <I, O extends RepositoryObject<I>> void registerRepository(
      @NotNull Class<? extends O> clazz)
      throws MissingAnnotationException, MetaConstructionException {
    final ReferenceClass<O> referenceClass = (ReferenceClass<O>) facade.referenceClassFactory()
        .createReference(clazz);

    repositoryCache.put(clazz,
        new ObjectRepositoryImpl<>(
            facade,
            new ObjectResolverImpl<>(referenceClass),
            referenceClass.objectData()
        ));

    facade.dataRegistry().registerObjectData(clazz, referenceClass.objectData());
    facade.resolverRegistry().registerResolver(clazz);
  }

  @Override
  public @NotNull Collection<ObjectRepository<?, ?>> registeredRepositories() {
    return repositoryCache.values();
  }
}
