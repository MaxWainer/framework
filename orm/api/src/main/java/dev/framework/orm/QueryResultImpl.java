package dev.framework.orm;

import dev.framework.commons.function.ThrowableFunctions;
import dev.framework.commons.function.ThrowableFunctions.ThrowableSupplier;
import dev.framework.orm.query.QueryResult;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import org.jetbrains.annotations.NotNull;

final class QueryResultImpl<T> implements QueryResult<T> {

  private final ThrowableSupplier<T, SQLException> supplier;
  private final ExecutorService service;

  QueryResultImpl(
      final @NotNull ThrowableSupplier<T, SQLException> supplier,
      final @NotNull ExecutorService service) {
    this.supplier = supplier;
    this.service = service;
  }

  QueryResultImpl(
      final @NotNull ThrowableFunctions.ThrowableRunnable<SQLException> runnable,
      final @NotNull ExecutorService service) {
    this.supplier = () -> {
      runnable.run();
      return null;
    };
    this.service = service;
  }

  @Override
  public @NotNull CompletableFuture<T> result() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return supplier.get();
      } catch (SQLException e) {
        throw new CompletionException(e);
      }
    }, service);
  }
}
