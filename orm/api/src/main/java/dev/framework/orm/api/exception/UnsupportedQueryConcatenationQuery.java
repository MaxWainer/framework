package dev.framework.orm.api.exception;

import org.jetbrains.annotations.NotNull;

public final class UnsupportedQueryConcatenationQuery extends Exception {

  public UnsupportedQueryConcatenationQuery(final @NotNull String message) {
    super(message);
  }

}
