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

package dev.framework.orm.api.appender;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.exception.UnknownAdapterException;
import java.sql.SQLException;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface StatementAppender {

  StatementAppender nextString(final @NotNull String content) throws SQLException;

  StatementAppender next(final @NotNull Object content) throws SQLException;

  StatementAppender nextLong(final long content) throws SQLException;

  StatementAppender nextDouble(final double content) throws SQLException;

  StatementAppender nextInt(final int content) throws SQLException;

  StatementAppender nextColumn(final @NotNull ColumnMeta meta, final @NotNull Object object)
      throws Throwable;

  default StatementAppender nextLong(final Long content) throws SQLException {
    return nextLong(content.longValue());
  }

  default StatementAppender nextDouble(final Double content) throws SQLException {
    return nextDouble(content.doubleValue());
  }

  default StatementAppender nextInt(final Integer content) throws SQLException {
    return nextInt(content.intValue());
  }

  <T extends RepositoryObject> StatementAppender nextJsonAdaptive(final @NotNull T t)
      throws SQLException, UnknownAdapterException;

  default StatementAppender nextUUID(final @NotNull UUID content) throws SQLException {
    return nextString(content.toString());
  }
}
