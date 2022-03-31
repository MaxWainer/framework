package dev.framework.bukkit.menu.api.slot;

import dev.framework.commons.concurrent.annotation.ThreadSafe;
import dev.framework.commons.range.Ranges;
import java.io.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@ThreadSafe
public final class Position implements Serializable, Cloneable {

  public static @NotNull Position create(
      @Range(from = 1, to = 6) final int y,
      @Range(from = 1, to = 9) final int x) {
    return new Position(y, x);
  }

  private final int y;
  private final int x;

  Position(final int y, final int x) {
    Ranges.validateNumber(1, 6, y);
    Ranges.validateNumber(1, 9, x);

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
    return (6 * y) + x;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return new Position(y, x);
  }
}
