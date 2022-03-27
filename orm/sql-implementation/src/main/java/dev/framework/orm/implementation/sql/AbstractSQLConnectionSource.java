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

package dev.framework.orm.implementation.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.framework.orm.implementation.AbstractConnectionSource;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSQLConnectionSource extends AbstractConnectionSource {

  private final ExecutorService service = Executors.newCachedThreadPool();

  protected AbstractSQLConnectionSource(
      @NotNull ConnectionCredentials connectionCredentials, @NotNull ORMFacade ormFacade) {
    super(connectionCredentials, ormFacade);
  }

  @Override
  protected HikariDataSource createDataSource(
      @NotNull ConnectionCredentials connectionCredentials) {
    final HikariConfig config = new HikariConfig();

    final String link = connectionCredentials.jdbcUrl();

    config.setJdbcUrl(link);

    if (driverClassName() != null) config.setDriverClassName(driverClassName());

    config.setPassword(connectionCredentials.password());
    config.setUsername(connectionCredentials.username());

    for (final Entry<String, Object> entry : connectionCredentials.options().entrySet()) {
      config.addDataSourceProperty(entry.getKey(), entry.getValue());
    }

    return new HikariDataSource(config);
  }

  @Override
  protected ExecutorService executorService() {
    return service;
  }

  protected abstract @Nullable String driverClassName();
}
