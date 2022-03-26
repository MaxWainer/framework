package dev.framework.orm.api.exception;

import org.jetbrains.annotations.NotNull;

public final class QueryNotCompletedException extends Exception {

  public QueryNotCompletedException(final @NotNull String message) {
    super(message);
  }

}
