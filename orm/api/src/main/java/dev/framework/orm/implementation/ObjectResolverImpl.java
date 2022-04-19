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

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.ref.ReferenceObject;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class ObjectResolverImpl<T extends RepositoryObject> implements ObjectResolver<T> {

  private final ReferenceClass<T> clazz;

  ObjectResolverImpl(final @NotNull ReferenceClass<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public @NotNull ReferenceObject<T> constructObject(@NotNull ResultSetReader reader)
      throws Throwable {
    final TableMeta tableMeta = clazz.objectData().tableMeta();

    final List linkedList = new LinkedList();
    for (final ColumnMeta meta : tableMeta) {
      try {
        final Optional optional = reader.readColumn(meta);

        linkedList.add(optional.orElse(null));
      } catch (final SQLException exception) {
        // ignore no such column
        if (!exception.getMessage().contains("no such column")) {
          throw new RuntimeException(exception);
        }

        linkedList.add(null);
      }
    }

    return clazz.newInstance(linkedList.toArray());
  }

  @Override
  public void fillConstructor(T object,
      @NotNull StatementAppender appender) throws Throwable {
    final TableMeta tableMeta = clazz.objectData().tableMeta();

    for (final ColumnMeta meta : tableMeta) {
      appender.nextColumn(meta, object);
    }
  }

  @Override
  public void fillUpdater(@NotNull ReferenceObject<T> object, @NotNull StatementAppender appender)
      throws Throwable {
    final TableMeta tableMeta = clazz.objectData().tableMeta();

    for (final ColumnMeta meta : tableMeta.truncatedColumnMetaSet()) {
      appender.nextColumn(meta, object.asObject());
    }

    final ColumnMeta identifierMeta = tableMeta.identifyingColumn();
    appender.next(object.filedData(identifierMeta.field().getName()));
  }
}
