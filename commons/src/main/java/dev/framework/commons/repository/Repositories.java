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

package dev.framework.commons.repository;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Simple class, which is implements bunch of memory-based repositories
 */
@UtilityClass
public final class Repositories {

  private Repositories() {
    MoreExceptions.instantiationError();
  }

  public static <V extends RepositoryObject<UUID>> @NotNull Repository<UUID, V> uuidToObjectMemoryRepository() {
    return uuidToObjectMemoryRepository(ConcurrentHashMap::new);
  }

  public static <V extends RepositoryObject<UUID>> @NotNull Repository<UUID, V> uuidToObjectMemoryRepository(
      final @NotNull Supplier<Map<UUID, V>> mapFactory) {
    return new UUIDToObjectMemoryRepository<>(mapFactory.get());
  }

  public static <I, V extends RepositoryObject<I>> @NotNull Repository<I, V> objectMemoryRepository(
      final @NotNull Supplier<Map<I, V>> mapFactory) {
    return new ObjectMemoryRepository<>(mapFactory.get());
  }

  public static <I, V extends RepositoryObject<I>> @NotNull Repository<I, V> objectMemoryRepository() {
    return objectMemoryRepository(ConcurrentHashMap::new);
  }

  private static class ObjectMemoryRepository<I, O extends RepositoryObject<I>>
      implements Repository<I, O> {

    private final Map<I, O> data;

    private ObjectMemoryRepository(final @NotNull Map<I, O> data) {
      this.data = data;
    }

    @Override
    public @NotNull Optional<@NotNull O> find(final @NotNull I uuid) {
      return Optional.ofNullable(data.get(uuid));
    }

    @Override
    public void register(final @NotNull I uuid, @NotNull final O o) {
      data.putIfAbsent(uuid, o);
    }

    @Override
    public void delete(@NotNull I uuid) {
      this.data.remove(uuid);
    }

    @Override
    public void update(@NotNull I uuid, @NotNull O o) {
      this.data.replace(uuid, o);
    }

    @Override
    public @NotNull @Unmodifiable Map<I, O> listObject() {
      return data;
    }
  }

  private static final class UUIDToObjectMemoryRepository<O extends RepositoryObject<UUID>> extends
      ObjectMemoryRepository<UUID, O> {

    private UUIDToObjectMemoryRepository(@NotNull Map<UUID, O> data) {
      super(data);
    }
  }
}
