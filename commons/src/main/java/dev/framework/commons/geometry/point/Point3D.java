package dev.framework.commons.geometry.point;

import org.jetbrains.annotations.NotNull;

public interface Point3D<N extends Number> extends Point2D<N> {

  @NotNull N z();

}
