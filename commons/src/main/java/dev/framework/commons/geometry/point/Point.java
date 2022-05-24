package dev.framework.commons.geometry.point;

import dev.framework.commons.stream.Streamed;
import org.jetbrains.annotations.NotNull;

public interface Point<N extends Number> extends Streamed<N, Point<N>> {

  boolean contains(final @NotNull Point<?> other);

}
