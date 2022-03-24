package dev.framework.orm.api.credentials;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface ConnectionCredentials {

  static ConnectionCredentials of(
      final @NotNull String connectionLink,
      final @NotNull String username,
      final @NotNull String password,
      final @NotNull Map<String, Object> options) {
    return new ConnectionCredentialsImpl(connectionLink, username, password, options);
  }

  @NotNull String connectionLink();

  @NotNull String username();

  @NotNull String password();

  @NotNull Map<String, Object> options();

}
