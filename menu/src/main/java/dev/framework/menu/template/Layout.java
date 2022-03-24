package dev.framework.menu.template;

import dev.framework.menu.slot.CharSlots;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public interface Layout {

  @NotNull CharSlots find(final char ch);

  @NotNull Set<String> rawLines();

}
