package framework.commons.repository;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Repository<I, T extends RepositoryObject<I>> {

  @NotNull Optional<@NotNull T> find(final @NotNull I i);

  void register(final @NotNull I i, final @NotNull T t);

}
