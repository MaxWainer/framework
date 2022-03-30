package dev.framework.commons.collection.xy;

import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface XYCollection<T> extends Iterable<T> {

  void insert(final int y, final int x, final @NotNull T element);

  void insertIfAbsent(final int y, final int x, final @NotNull T element);

  @NotNull Stream<T> stream();

  @NotNull Optional<T> at(final int y, final int x);

  @NotNull Optional<T> at(final int absolutePosition);

  @NotNull XYCollection<T> copy();

  int size();

  boolean hasAt(final int y, final int x);

  boolean hasAt(final int absolutePosition);

}
