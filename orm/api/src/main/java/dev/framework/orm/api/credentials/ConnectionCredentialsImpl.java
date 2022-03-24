package dev.framework.orm.api.credentials;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class ConnectionCredentialsImpl implements ConnectionCredentials {

  private final String connectionLink, username, password;
  private final Map<String, Object> options;

  ConnectionCredentialsImpl(
      final @NotNull String connectionLink,
      final @NotNull String username,
      final @NotNull String password,
      final @NotNull Map<String, Object> options) {
    this.connectionLink = connectionLink;
    this.username = username;
    this.password = password;
    this.options = options;
  }

  @Override
  public @NotNull String connectionLink() {
    return connectionLink;
  }

  @Override
  public @NotNull String username() {
    return username;
  }

  @Override
  public @NotNull String password() {
    return password;
  }

  @Override
  public @NotNull Map<String, Object> options() {
    return options;
  }
}
