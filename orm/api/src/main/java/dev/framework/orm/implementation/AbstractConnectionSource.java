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

import com.zaxxer.hikari.HikariDataSource;
import dev.framework.commons.function.ThrowableFunctions.ThrowableConsumer;
import dev.framework.commons.function.ThrowableFunctions.ThrowableFunction;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractConnectionSource implements ConnectionSource {

  private final ORMFacade ormFacade;

  private final HikariDataSource dataSource;

  private Connection connection;

  protected AbstractConnectionSource(
      final @NotNull ConnectionCredentials connectionCredentials,
      final @NotNull ORMFacade ormFacade) {
    this.ormFacade = ormFacade;
    this.dataSource = createDataSource(connectionCredentials);
  }

  @Override
  public @NotNull Connection connection() {
    if (this.connection == null) {
      try {
        this.connection = this.dataSource.getConnection();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    return this.connection;
  }

  @Override
  public @NotNull <V> QueryResult<V> executeWithResult(@NotNull String query,
      @NotNull ThrowableConsumer<StatementAppender, SQLException> appender,
      @NotNull ThrowableFunction<ResultSetReader, V, SQLException> resultMapper) {
    return new QueryResultImpl<>(() -> {
      try (final PreparedStatement statement = connection().prepareStatement(query)) {
        appender.consume(new StatementAppenderImpl(statement, ormFacade));

        return resultMapper.apply(new ResultSetReaderImpl(statement.executeQuery(), ormFacade));
      } catch (SQLException e) {
        throw new CompletionException(e);
      }
    }, executorService());
  }

  @Override
  public @NotNull QueryResult<Void> execute(@NotNull String query) {
    return new QueryResultImpl<>(() -> {
      try (final PreparedStatement statement = connection().prepareStatement(query)) {
        statement.executeUpdate();
      } catch (SQLException e) {
        throw new CompletionException(e);
      }
    }, executorService());
  }

  @Override
  public @NotNull QueryResult<Void> execute(@NotNull String query,
      @NotNull ThrowableConsumer<StatementAppender, SQLException> appender) {
    return new QueryResultImpl<>(() -> {
      try (final PreparedStatement statement = connection().prepareStatement(query)) {
        appender.consume(new StatementAppenderImpl(statement, ormFacade));

        statement.executeUpdate();
      } catch (SQLException e) {
        throw new CompletionException(e);
      }
    }, executorService());
  }

  @Override
  public void close() throws Exception {
    this.connection().close(); // close connection
    this.dataSource.close(); // close datasource
  }

  protected abstract HikariDataSource createDataSource(
      final @NotNull ConnectionCredentials connectionCredentials);

  protected abstract ExecutorService executorService();

}
