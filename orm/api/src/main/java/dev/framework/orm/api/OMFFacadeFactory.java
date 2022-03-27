package dev.framework.orm.api;

import dev.framework.orm.api.credentials.ConnectionCredentials;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface OMFFacadeFactory {

  @NotNull ORMFacade create(final @NotNull ConnectionCredentials credentials);

}
