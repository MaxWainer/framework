package dev.framework.menu.slot;

import dev.framework.commons.Identifiable;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public interface CharSlots extends Identifiable<Character> {

  @NotNull Set<Integer> slots();

}
