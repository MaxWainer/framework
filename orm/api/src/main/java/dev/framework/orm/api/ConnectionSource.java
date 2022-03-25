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

package dev.framework.orm.api;

import dev.framework.commons.function.ThrowableFunctions.ThrowableConsumer;
import dev.framework.commons.function.ThrowableFunctions.ThrowableFunction;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.Connection;
import java.sql.SQLException;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConnectionSource extends AutoCloseable {

  @NotNull
  Connection connection();

  @NotNull
  QueryResult<Void> execute(final @NotNull @Language("SQL") String query);

  @NotNull
  QueryResult<Void> execute(
      final @NotNull @Language("SQL") String query,
      final @NotNull ThrowableConsumer<StatementAppender, SQLException> appender);

  <V> @NotNull QueryResult<V> executeWithResult(
      final @NotNull @Language("SQL") String query,
      final @NotNull ThrowableConsumer<StatementAppender, SQLException> appender,
      final @NotNull ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper);

  default QueryResult<Void> executeReadOnly(
      final @NotNull @Language("SQL") String query,
      final @NotNull ThrowableConsumer<StatementAppender, SQLException> appender,
      final @NotNull ThrowableConsumer<ResultSetReader, SQLException> resultConsumer) {
    return executeWithResult(
        query,
        appender,
        result -> {
          resultConsumer.consume(result);
          return null;
        });
  }
}
