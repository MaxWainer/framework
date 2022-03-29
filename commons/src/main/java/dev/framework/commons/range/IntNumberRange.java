package dev.framework.commons.range;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

final class IntNumberRange implements NumberRange<Integer> {

  private final int from, to;

  IntNumberRange(final int from, final int to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public boolean inRange(@NotNull Integer integer) {
    return integer >= from && integer <= to;
  }

  @Override
  public @NotNull Integer from() {
    return from;
  }

  @Override
  public @NotNull Integer to() {
    return to;
  }

  @Override
  public void forEach(@NotNull Consumer<Integer> consumer) {
    for (int i = from; i < to; i++) {
      consumer.accept(i);
    }
  }
}
