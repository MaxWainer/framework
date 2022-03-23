package dev.framework.commons.cooldown;

import dev.framework.commons.TimePair;
import org.jetbrains.annotations.NotNull;

public interface CooldownList<T> {

  @NotNull TimePair cooldownTime();

  boolean check(final @NotNull T t);

}
