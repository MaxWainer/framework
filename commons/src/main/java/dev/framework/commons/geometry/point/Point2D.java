package dev.framework.commons.geometry.point;

import org.jetbrains.annotations.NotNull;

public interface Point2D<N extends Number> extends Point<N> {

  @NotNull N x();

  @NotNull N y();

}
