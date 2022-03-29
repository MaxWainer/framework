package dev.framework.commons.range;

import org.jetbrains.annotations.NotNull;

public interface Range<T> {

  boolean inRange(final @NotNull T t);

  @NotNull T from();

  @NotNull T to();

}
