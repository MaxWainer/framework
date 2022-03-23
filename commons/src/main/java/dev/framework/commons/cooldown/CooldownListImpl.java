package dev.framework.commons.cooldown;

import dev.framework.commons.TimePair;
import org.jetbrains.annotations.NotNull;

final class CooldownListImpl<T> implements CooldownList<T> {

  @Override
  public @NotNull TimePair cooldownTime() {
    return null;
  }

  @Override
  public boolean check(@NotNull final T t) {
    return false;
  }
}
