package com.mcdev.framework.commands.context;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public final class ParserResult<T> {

  public static <V> ParserResult<V> success(final @NotNull V v) {
    return new ParserResult<>(v, null);
  }

  public static <V> ParserResult<V> error(final @NotNull Component errorMessage) {
    return new ParserResult<>(null, errorMessage);
  }

  private final T result;
  private final Component errorMessage;

  ParserResult(final T result, final Component errorMessage) {
    this.result = result;
    this.errorMessage = errorMessage;
  }

  public T result() {
    return result;
  }

  public boolean error() {
    return errorMessage != null;
  }

  public Component errorMessage() {
    return errorMessage;
  }
}
