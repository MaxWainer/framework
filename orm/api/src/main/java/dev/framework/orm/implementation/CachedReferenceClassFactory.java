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

import com.google.common.collect.Maps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ReferenceClassFactory;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.ref.ReferenceClass;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class CachedReferenceClassFactory implements ReferenceClassFactory {

  private final ORMFacade facade;

  private final
  Map<Class<? extends RepositoryObject>, ReferenceClass<? extends RepositoryObject>>
      cache = Maps.newConcurrentMap();

  CachedReferenceClassFactory(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  @Override
  public @NotNull <T extends RepositoryObject> ReferenceClass<T> createReference(
      @NotNull Class<T> clazz) throws MissingAnnotationException, MetaConstructionException {
    if (cache.containsKey(clazz)) return (ReferenceClass<T>) cache.get(clazz);

    final ReferenceClass<T> referenceClass = new ReferenceClassImpl<>(
        facade.dataFactory().createFromClass(clazz),
        clazz
    );

    cache.put(clazz, referenceClass);

    return referenceClass;
  }
}
