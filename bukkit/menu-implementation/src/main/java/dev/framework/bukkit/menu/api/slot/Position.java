package dev.framework.bukkit.menu.api.slot;

import dev.framework.commons.concurrent.annotation.ThreadSafe;
import dev.framework.commons.range.Ranges;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@ThreadSafe
public final class Position {

  public static @NotNull Position create(
      @Range(from = 1, to = 9) final int x,
      @Range(from = 1, to = 6) final int y) {
    return new Position(x, y);
  }

  private final int x, y;

  Position(final int x, final int y) {
    Ranges.validateNumber(1, 9, x);
    Ranges.validateNumber(1, 6, y);

    this.x = x;
    this.y = y;
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  public int realPosition() {
    return -1;
  }

}
