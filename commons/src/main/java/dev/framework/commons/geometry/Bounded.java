package dev.framework.commons.geometry;

import dev.framework.commons.geometry.point.Point3D;
import org.jetbrains.annotations.NotNull;

public interface Bounded<N extends Number> extends Point3D<N> {

  // max
  @NotNull N maxX();

  @NotNull N maxY();

  @NotNull N maxZ();

  // min
  @NotNull N minX();

  @NotNull N minY();

  @NotNull N minZ();

  // center
  @NotNull N centerX();

  @NotNull N centerY();

  @NotNull N centerZ();

  boolean contains(final @NotNull Bounded<?> other);

}
