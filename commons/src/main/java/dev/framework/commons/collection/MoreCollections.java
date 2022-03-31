package dev.framework.commons.collection;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import dev.framework.commons.collection.xy.XYCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class MoreCollections {

  private MoreCollections() {
    MoreExceptions.instantiationError();
  }

  public static <T, P> boolean hasDuplicates(
      final @NotNull Collection<T> initial,
      final @NotNull Function<T, P> primitiveMapper) {
    return initial.stream()
        .map(primitiveMapper)
        .collect(Collectors.toSet())
        .size() != initial.size();
  }

  public static <T> XYCollection<T> xyCollection(final int maxY, final int maxX) {
    return new XYCollectionImpl<>(maxY, maxX);
  }

  public static <T> XYCollection<T> immutable(final @NotNull XYCollection<T> collection) {
    return new ImmutableXYCollection<>(collection.copy());
  }

  private static final class ImmutableXYCollection<T> implements XYCollection<T> {

    private final XYCollection<T> delegate;

    private ImmutableXYCollection(final @NotNull XYCollection<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public void insert(int y, int x, @NotNull T element) {
      throw new UnsupportedOperationException("collection immutable");
    }

    @Override
    public void insertIfAbsent(int y, int x, @NotNull T element) {
      throw new UnsupportedOperationException("collection immutable");
    }

    @Override
    public @NotNull Stream<T> stream() {
      return delegate.stream();
    }

    @Override
    public @NotNull Optional<T> at(int y, int x) {
      return delegate.at(y, x);
    }

    @Override
    public @NotNull Optional<T> at(int absolutePosition) {
      return delegate.at(absolutePosition);
    }

    @Override
    public @NotNull XYCollection<T> copy() {
      return new ImmutableXYCollection<>(delegate.copy());
    }

    @Override
    public int size() {
      return delegate.size();
    }

    @Override
    public boolean hasAt(int y, int x) {
      return delegate.hasAt(y, x);
    }

    @Override
    public boolean hasAt(int absolutePosition) {
      return delegate.hasAt(absolutePosition);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
      return delegate.iterator();
    }
  }

}
