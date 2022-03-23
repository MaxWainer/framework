package dev.framework.orm.adapters;

import dev.framework.orm.adapter.simple.StringColumnTypeAdapter;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public final class JavaInstantParsedColumnTypeAdapter implements StringColumnTypeAdapter<Instant> {

  @Override
  public @NotNull String to(@NotNull Instant instant) {
    return instant.toString();
  }

  @Override
  public @NotNull Instant from(@NotNull String data) {
    return Instant.parse(data);
  }

  @Override
  public int requiredStringSize() {
    return 0;
  }

  @Override
  public @NotNull Class<Instant> identifier() {
    return Instant.class;
  }
}
