package dev.framework.orm.adapters;

import dev.framework.orm.adapter.simple.ColumnTypeAdapter;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public final class JavaInstantLongColumnTypeAdapter implements ColumnTypeAdapter<Long, Instant> {

  @Override
  public @NotNull Long to(@NotNull Instant instant) {
    return instant.getEpochSecond();
  }

  @Override
  public @NotNull Instant from(@NotNull Long data) {
    return Instant.ofEpochSecond(data);
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
