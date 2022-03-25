/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.orm.implementation;

import dev.framework.commons.function.ThrowableFunctions;
import dev.framework.commons.function.ThrowableFunctions.ThrowableSupplier;
import dev.framework.orm.api.query.QueryResult;
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
