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

  public static <V extends RepositoryObject<UUID>> Repository<UUID, V> uuidToObjectMemoryRepository() {
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
  }

}
