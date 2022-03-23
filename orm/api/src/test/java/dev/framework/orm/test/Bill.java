package dev.framework.orm.test;

import dev.framework.commons.repository.RepositoryObject;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class Bill implements RepositoryObject<UUID> {



  @Override
  public @NotNull UUID identifier() {
    return null;
  }
}
