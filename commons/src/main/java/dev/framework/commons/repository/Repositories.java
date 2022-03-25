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

import dev.framework.commons.Exceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Repositories {

  private Repositories() {
    Exceptions.instantiationError();
  }

  public static <V extends RepositoryObject<UUID>>
  Repository<UUID, V> uuidToObjectMemoryRepository() {
    return uuidToObjectMemoryRepository(HashMap::new);
  }

  public static <V extends RepositoryObject<UUID>> Repository<UUID, V> uuidToObjectMemoryRepository(
      final @NotNull Supplier<Map<UUID, V>> mapFactory) {
    return new UUIDToObjectMemoryRepository<>(mapFactory.get());
  }

  private static final class UUIDToObjectMemoryRepository<O extends RepositoryObject<UUID>>
      implements Repository<UUID, O> {

    private final Map<UUID, O> data;

    private UUIDToObjectMemoryRepository(final @NotNull Map<UUID, O> data) {
      this.data = data;
    }

    @Override
    public @NotNull Optional<@NotNull O> find(final @NotNull UUID uuid) {
      return Optional.ofNullable(data.get(uuid));
    }

    @Override
    public void register(final @NotNull UUID uuid, @NotNull final O o) {
      data.putIfAbsent(uuid, o);
    }

    @Override
    public void delete(@NotNull UUID uuid) {
      this.data.remove(uuid);
    }

    @Override
    public void update(@NotNull UUID uuid, @NotNull O o) {
      this.data.replace(uuid, o);
    }
  }
}
