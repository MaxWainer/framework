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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.framework.commons.Types;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseJsonSerializable;
import dev.framework.orm.api.exception.UnknownAdapterException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class StatementAppenderImpl implements StatementAppender {

  private final PreparedStatement preparedStatement;
  private final ORMFacade facade;

  private int counter = 1;

  StatementAppenderImpl(
      final @NotNull PreparedStatement preparedStatement, final @NotNull ORMFacade facade) {
    this.preparedStatement = preparedStatement;
    this.facade = facade;
  }

  @Override
  public StatementAppender nextString(@NotNull String content) throws SQLException {
    preparedStatement.setString(counter++, content);
    return this;
  }

  @Override
  public StatementAppender next(@NotNull Object content) throws SQLException {
    if (!typeWrite(content)) {
      throw new SQLException("Unknown column type: " + content);
    }

    return this;
  }

  @Override
  public StatementAppender nextLong(long content) throws SQLException {
    preparedStatement.setLong(counter++, content);
    return this;
  }

  @Override
  public StatementAppender nextDouble(double content) throws SQLException {
    preparedStatement.setDouble(counter++, content);
    return this;
  }

  @Override
  public StatementAppender nextInt(int content) throws SQLException {
    preparedStatement.setInt(counter++, content);
    return this;
  }

  @Override
  public StatementAppender nextColumn(
      @NotNull ColumnMeta meta,
      @NotNull Object object) throws Throwable {
    final Field field = meta.field();
    final Object fieldObject = ORMHelper.fieldData(field, object);

    if (typeWrite(fieldObject)) {
      return this;
    }

    if (meta.collection()) {
      final JsonObjectAdapter adapter = ORMHelper.collectionAdapter(facade, field, meta);

      final Collection list = (Collection) fieldObject;

      final JsonArray array = new JsonArray();
      for (final Object o : list) {
        array.add(adapter.deconstruct(o));
      }

      nextString(array.toString());
      return this;
    }

    if (meta.map()) {
      final JsonObjectAdapter[] adapters = ORMHelper.mapAdapter(facade, field, meta);

      final Map map = (Map) fieldObject;

      final JsonArray array = new JsonArray();
      for (final Object o : map.entrySet()) {
        final Entry entry = (Entry) o;

        final JsonObject localObject = new JsonObject();

        localObject.add("key", adapters[0].deconstruct(entry.getKey()));
        localObject.add("value", adapters[1].deconstruct(entry.getValue()));

        array.add(localObject);
      }

      nextString(array.toString());

      return this;
    }

    if (meta.jsonSerializable()) {
      final BaseJsonSerializable serializable = meta.serializerOptions();

      final Class<? extends JsonObjectAdapter> adapter = serializable.value();

      final JsonObjectAdapter instance = facade.jsonAdapters()
          .adapterInstance(adapter)
          .orElseThrow(() -> new UnknownAdapterException(adapter));

      nextString(instance.deconstruct(fieldObject).toString());

      return this;
    }

    return this;
  }

  @Override
  public <T extends RepositoryObject> StatementAppender nextJsonAdaptive(@NotNull T t)
      throws SQLException, UnknownAdapterException {
    return nextString(facade.jsonAdapters().toJson(t));
  }

  private boolean typeWrite(final @NotNull Object content)
      throws SQLException {
    if (writePrimitive(content, content.getClass())) {
      return true;
    }

    final Optional<ColumnTypeAdapter<?, ?>> optional = facade.columnTypeAdapters()
        .find(content.getClass());

    if (!optional.isPresent()) {
      return false;
    }

    final ColumnTypeAdapter typeAdapter = optional.get();

    final Class<?> primitiveType = typeAdapter.primitiveType();

    writePrimitive(typeAdapter.to(content), primitiveType);

    return true;
  }

  private boolean writePrimitive(final @NotNull Object content, final @NotNull Class type)
      throws SQLException {
    if (!Types.isPrimitive(type)) {
      return false;
    }

    if (Types.asBoxedPrimitive(type) == Long.class) {
      nextLong((Long) content);
    } else if (Types.asBoxedPrimitive(type) == Integer.class) {
      nextInt((Integer) content);
    } else if (Types.asBoxedPrimitive(type) == Double.class) {
      nextDouble((Double) content);
    } else if (Types.asBoxedPrimitive(type) == String.class) {
      nextString((String) content);
    } else {
      nextString(content.toString());
    }

    return true;
  }

}
