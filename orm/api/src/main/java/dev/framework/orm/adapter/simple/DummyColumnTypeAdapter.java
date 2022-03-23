package dev.framework.orm.adapter.simple;

import org.jetbrains.annotations.NotNull;

public final class DummyColumnTypeAdapter implements ColumnTypeAdapter {

  public static final DummyColumnTypeAdapter INSTANCE = new DummyColumnTypeAdapter();

  @Override
  public @NotNull Object to(@NotNull Object o) {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }

  @NotNull
  @Override
  public Object from(@NotNull Object data) {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }

  @Override
  public int requiredStringSize() {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }

  @Override
  public boolean utf8Required() {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }

  @NotNull
  @Override
  public Object identifier() {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }
}
