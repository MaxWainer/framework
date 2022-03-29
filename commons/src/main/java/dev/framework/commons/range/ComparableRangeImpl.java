package dev.framework.commons.range;

import org.jetbrains.annotations.NotNull;

final class ComparableRangeImpl<T extends Comparable<T>> implements ComparableRange<T> {

  private final T from, to;

  ComparableRangeImpl(final @NotNull T from, final @NotNull T to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public boolean inRange(@NotNull T t) {
    final int fromResult = t.compareTo(from);
    final int toResult = t.compareTo(to);

    return (fromResult == 0 || fromResult > 0) && (toResult == 0 || toResult < 0);
  }

  @Override
  public @NotNull T from() {
    return from;
  }

  @Override
  public @NotNull T to() {
    return to;
  }
}
