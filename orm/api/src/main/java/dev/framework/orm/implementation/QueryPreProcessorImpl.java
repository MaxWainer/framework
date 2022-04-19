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

import static java.util.Objects.requireNonNull;

import dev.framework.commons.function.ThrowableFunctions;
import dev.framework.commons.function.ThrowableFunctions.ThrowableConsumer;
import dev.framework.commons.function.ThrowableFunctions.ThrowableFunction;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.query.pre.QueryPreProcessor;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class QueryPreProcessorImpl<T> implements QueryPreProcessor<T> {

  private final ConnectionSource connectionSource;
  private final String query;

  private final ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender;
  private final ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable T, SQLException> resultMapper;

  private QueryPreProcessorImpl(
      final @NotNull ConnectionSource connectionSource,
      final @NotNull String query,
      final @NotNull ThrowableConsumer<StatementAppender, SQLException> appender,
      final @Nullable ThrowableFunction<ResultSetReader, T, SQLException> resultMapper) {
    this.connectionSource = connectionSource;
    this.query = query;
    this.appender = appender;
    this.resultMapper = resultMapper;
  }

  @Override
  public @NotNull QueryResult<T> result() {
    if (resultMapper != null) {
      return connectionSource.executeWithResult(query, appender, resultMapper);
    }

    return (QueryResult<T>) connectionSource.execute(query, appender);
  }

  public static final class QueryPreProcessorBuilderImpl<V> implements
      QueryPreProcessorBuilder<V> {

    private final ConnectionSource connectionSource;
    private final String query;

    private ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender = ThrowableConsumer.empty();
    private ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper = null;

    QueryPreProcessorBuilderImpl(
        final @NotNull ConnectionSource connectionSource,
        final @NotNull String query) {
      this.connectionSource = connectionSource;
      this.query = query;
    }

    @Override
    public QueryPreProcessor<V> build() {
      return new QueryPreProcessorImpl<>(connectionSource, query, requireNonNull(appender),
          resultMapper);
    }

    @Override
    public QueryPreProcessorBuilder<V> appender(
        @NotNull ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender) {
      this.appender = appender;
      return this;
    }

    @Override
    public QueryPreProcessorBuilder<V> resultMapper(
        @NotNull ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper) {
      this.resultMapper = resultMapper;
      return this;
    }
  }
}
