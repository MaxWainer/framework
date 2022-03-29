package dev.framework.commons.collection.xy;

import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface XYCollection<T> extends Iterable<T> {

  void insert(final int x, final int y, final @NotNull T element);

  @NotNull Stream<T> stream();

  @NotNull Optional<T> at(final int x, final int y);

  @NotNull XYCollection<T> copy();

}
