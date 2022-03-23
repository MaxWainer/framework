package dev.framework.orm;

import dev.framework.commons.repository.RepositoryObject;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class ObjectRepositoryImpl<I, T extends RepositoryObject<I>>
        implements ObjectRepository<I, T> {

    @Override
    public @NotNull Optional<@NotNull T> find(@NotNull I i) {
        return Optional.empty();
    }

    @Override
    public void register(@NotNull I i, @NotNull T t) {

    }

    @Override
    public void delete(@NotNull I i) {

    }

    @Override
    public void update(@NotNull I i, @NotNull T t) {

    }
}
