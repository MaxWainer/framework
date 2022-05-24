package dev.framework.commons.stream;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface Streamed<T, H extends Streamed<T, H>> extends List<T> {

  @NotNull <K> Streamed<K, ? extends Streamed<K, ?>> map(final @NotNull Function<T, K> mapper);

  @NotNull H filter(final @NotNull Predicate<T> predicate);

  @NotNull Optional<T> first();

  @NotNull Optional<T> last();

  @NotNull Stream<T> stream();

}
