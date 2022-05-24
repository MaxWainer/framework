package dev.framework.commons.geometry;

import org.jetbrains.annotations.NotNull;

public final class BoundingBox extends AbstractBounded<Double> {

  public BoundingBox(@NotNull Double x, @NotNull Double y, @NotNull Double z) {
    super(x, y, z);
  }
}
