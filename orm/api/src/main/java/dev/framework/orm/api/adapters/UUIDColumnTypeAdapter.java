package dev.framework.orm.api.adapters;

import dev.framework.orm.api.adapter.simple.StringColumnTypeAdapter;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class UUIDColumnTypeAdapter implements StringColumnTypeAdapter<UUID> {

  @Override
  public @NotNull Class<UUID> identifier() {
    return UUID.class;
  }

  @Override
  public @NotNull String toPrimitive(@NotNull UUID uuid) {
    return uuid.toString();
  }

  @Override
  public @NotNull UUID fromPrimitive(@NotNull String data) {
    return UUID.fromString(data);
  }

  @Override
  public @NotNull Class<String> primitiveType() {
    return String.class;
  }
}
