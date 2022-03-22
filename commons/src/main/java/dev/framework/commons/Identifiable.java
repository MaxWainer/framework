package dev.framework.commons;

import org.jetbrains.annotations.NotNull;

public interface Identifiable<T> {

  @NotNull T identifier();

}
