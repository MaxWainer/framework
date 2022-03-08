package com.mcdev.framework.commands.result;

public final class Result<T> {

  public static Result<?> ERROR = new Result<>(null, true);

  private final T value;
  private final boolean error;

  public Result(final T value, final boolean error) {
    this.value = value;
    this.error = error;
  }

  public T value() {
    return value;
  }

  public boolean error() {
    return error;
  }
}
