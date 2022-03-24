package dev.framework.menu.slot;

import dev.framework.menu.function.drag.DragActionConsumer;
import org.jetbrains.annotations.NotNull;

public interface DragSlot extends Slot {

  @NotNull DragActionConsumer topDrag();

  @NotNull DragActionConsumer bottomDrag();

}
