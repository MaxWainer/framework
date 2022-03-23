package dev.framework.orm.query;

import dev.framework.commons.PrimitiveArrays;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public interface QueryResult<T> {

  @NotNull CompletableFuture<T> result();

  default void join() {
    result().join();
  }

  default CompletableFuture<Void> composeWith(final @NotNull QueryResult<?>... otherResults) {
    final QueryResult<?>[] results = PrimitiveArrays.appendHead(otherResults, this);

    return CompletableFuture.allOf(
        Arrays
            .stream(results) // collect all results into stream
            .map(QueryResult::result) // remap to CF
            .toArray(CompletableFuture[]::new) // move into array
    );
  }

}
