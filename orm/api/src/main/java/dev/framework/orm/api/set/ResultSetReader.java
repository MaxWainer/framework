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

package dev.framework.orm.api.set;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.exception.UnknownAdapterException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.jetbrains.annotations.NotNull;

public interface ResultSetReader {

  boolean next() throws SQLException;

  OptionalLong readLong(final @NotNull String column) throws SQLException;

  OptionalInt readInt(final @NotNull String column) throws SQLException;

  OptionalDouble readDouble(final @NotNull String column) throws SQLException;

  boolean readBoolean(final @NotNull String column) throws SQLException;

  @NotNull Optional<Object> readColumn(final @NotNull ColumnMeta meta)
      throws SQLException, UnknownAdapterException, MissingRepositoryException;

  <T extends RepositoryObject> Optional<T> readJsonAdaptive(final @NotNull String column, final @NotNull Class<T> type)
      throws SQLException, UnknownAdapterException;

  <T> Optional<T> readColumnAdaptive(final @NotNull String column, final @NotNull Class<T> targetType)
      throws SQLException;

  @NotNull
  Optional<String> readString(final @NotNull String column) throws SQLException;

  @NotNull ResultSetMetaData metadata() throws SQLException;
}
