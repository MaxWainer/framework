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

package dev.framework.orm.implementation.sqlite;

import dev.framework.orm.implementation.AbstractORMFacade;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.update.TableUpdater;
import org.jetbrains.annotations.NotNull;

public final class SQLiteORMFacade extends AbstractORMFacade {

  private final TableUpdater tableUpdater = new SQLiteTableUpdater(this);
  private final DialectProvider dialectProvider = new SQLiteDialectProvider();

  private final ConnectionSource source;

  public SQLiteORMFacade(final @NotNull ConnectionCredentials credentials) {
    this.source = new SQLiteConnectionSource(credentials, this);
  }

  @Override
  public @NotNull TableUpdater tableUpdater() {
    return tableUpdater;
  }

  @Override
  public @NotNull ConnectionSource connectionSource() {
    return source;
  }

  @Override
  public @NotNull DialectProvider dialectProvider() {
    return dialectProvider;
  }
}
