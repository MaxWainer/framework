package dev.framework.commons.collection;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import dev.framework.commons.collection.xy.XYCollection;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
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

}
