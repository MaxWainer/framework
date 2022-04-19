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

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.query.pre.QueryPreProcessor;
import dev.framework.orm.api.query.types.Query;
import dev.framework.orm.implementation.QueryPreProcessorImpl.QueryPreProcessorBuilderImpl;
import java.util.StringJoiner;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

abstract class AbstractQuery<T extends Query> implements Query<T> {

  protected final StringBuilder builder = new StringBuilder();
  protected final DialectProvider dialectProvider;

  protected final ConnectionSource connectionSource;

  protected AbstractQuery(final @NotNull DialectProvider dialectProvider,
      final @NotNull ConnectionSource connectionSource) {
    this.dialectProvider = dialectProvider;
    this.connectionSource = connectionSource;
  }

  @Override
  public @NotNull String buildQuery() throws QueryNotCompletedException {
    if (!check()) {
      throw new QueryNotCompletedException("Query not completed, currently: " + builder);
    }

    return buildQueryUnexcepting();
  }

  @Override
  public T append(@NotNull String raw) {
    this.builder.append(raw);
    return self();
  }

  @Override
  public T subQuery(@NotNull Query<?> sub)
      throws UnsupportedQueryConcatenationQuery, QueryNotCompletedException {
    if (!subQuerySupported(sub)) {
      throw new UnsupportedQueryConcatenationQuery(
          "Query " + sub + " is not supported by " + getClass().getName());
    }

    this.builder.append(" (").append(sub.buildQuery()).append(") ");

    return self();
  }

  protected void appendColumns(final @NotNull String delimiter,
      final @NotNull String columnSuffix,
      final @NotNull String... columns) {
    final StringJoiner joiner = new StringJoiner(delimiter);

    for (final String column : columns) {
      joiner.add(dialectProvider.protectValue(column) + columnSuffix);
    }

    this.builder.append(joiner).append(" ");
  }

  @NotNull
  @Override
  public <V> QueryPreProcessor.QueryPreProcessorBuilder<V> preProcess()
      throws QueryNotCompletedException {
    return new QueryPreProcessorBuilderImpl<>(connectionSource, buildQuery());
  }

  @Override
  public @NotNull QueryResult<Void> executeUnexcepting() {
    return connectionSource.execute(buildQueryUnexcepting());
  }

  @Override
  public @NotNull QueryResult<Void> execute() throws QueryNotCompletedException {
    return connectionSource.execute(buildQuery());
  }

  @NotNull
  @Override
  @Internal
  public <V> QueryPreProcessor.QueryPreProcessorBuilder<V> preProcessUnexcepting() {
    return new QueryPreProcessorBuilderImpl<>(connectionSource, buildQueryUnexcepting());
  }

//  protected @NotNull StringJoiner createJoiner(final @NotNull String delimiter) {
//    return new StringJoiner(
//        dialectProvider.protectValue(delimiter),
//        String.valueOf(dialectProvider.open()),
//        String.valueOf(dialectProvider.close()));
//  }

  @Override
  @Internal
  public @NotNull String buildQueryUnexcepting() {
    return this.builder.toString().trim().replaceAll("\\s{2,}", " ");
  }

  protected abstract T self();

  protected abstract boolean subQuerySupported(final @NotNull Query<?> sub);

  protected abstract boolean check();

  @Override
  public String toString() {
    return "AbstractQuery{" +
        "builder=" + builder +
        '}';
  }
}
