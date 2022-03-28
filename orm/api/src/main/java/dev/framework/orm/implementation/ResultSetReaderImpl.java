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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.framework.commons.Types;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseForeignKey;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.exception.UnknownAdapterException;
import dev.framework.orm.api.set.ResultSetReader;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import org.jetbrains.annotations.NotNull;

final class ResultSetReaderImpl implements ResultSetReader {

  private final ResultSet resultSet;
  private final ORMFacade facade;

  ResultSetReaderImpl(final @NotNull ResultSet resultSet, final @NotNull ORMFacade facade) {
    this.resultSet = resultSet;
    this.facade = facade;
  }

  @Override
  public boolean next() throws SQLException {
    return resultSet.next();
  }

  @Override
  public OptionalLong readLong(@NotNull String column) throws SQLException {
    return OptionalLong.of(resultSet.getLong(column));
  }

  @Override
  public OptionalInt readInt(@NotNull String column) throws SQLException {
    return OptionalInt.of(resultSet.getInt(column));
  }

  @Override
  public OptionalDouble readDouble(@NotNull String column) throws SQLException {
    return OptionalDouble.of(resultSet.getDouble(column));
  }

  @Override
  public boolean readBoolean(@NotNull String column) throws SQLException {
    return resultSet.getBoolean(column);
  }

  @Override
  public @NotNull Optional<Object> readColumn(@NotNull ColumnMeta meta)
      throws SQLException, UnknownAdapterException, MissingRepositoryException {
    final Optional typed = readColumnAdaptive(meta.identifier(), meta.field().getType());

    if (typed.isPresent()) {
      return Optional.of(typed.get());
    }

    final ColumnTypeAdapter columnTypeAdapter = ORMHelper.findColumnAdapter(facade,
        meta.baseColumn());
    if (columnTypeAdapter != null) {
      final Object result = readPrimitive(meta.identifier(), columnTypeAdapter.primitiveType());

      return result == null ? Optional.empty()
          : Optional.of(columnTypeAdapter.fromPrimitive(result));
    }

    final Object primitive = readPrimitive(meta.identifier(), meta.field().getType());

    if (primitive != null) {
      return Optional.of(primitive);
    }

    final Field field = meta.field();

    if (meta.foreign()) {
      final BaseForeignKey foreignKey = meta.foreignKeyOptions();

      final ObjectData targetTable = foreignKey.targetTable();

      final Class<?> foreignFieldType = foreignKey
          .foreignFieldMeta()
          .field()
          .getType();

      final Optional<?> optional = readColumnAdaptive(foreignKey.foreignField(), foreignFieldType);

      if (!optional.isPresent()) {
        return Optional.empty();
      }

      final ObjectRepository<Object, RepositoryObject<Object>> foreignRepository = facade
          .repositoryRegistry()
          .findRepository(targetTable.delegate());

      final Object id = optional.get();

      return Optional.of(foreignRepository.findAll(foreignKey.foreignField(), id));
    }

    final JsonElement json = JsonParser
        .parseString(readString(meta.identifier())
            .orElseThrow(UnsupportedOperationException::new));

    if (meta.collection()) {
      final JsonObjectAdapter adapter = ORMHelper.collectionAdapter(facade, field, meta);

      final List outList = new ArrayList();

      for (final JsonElement element : json.getAsJsonArray()) {
        outList.add(adapter.construct(element));
      }

      return Optional.of(outList);
    }

    if (meta.map()) {
      final JsonObjectAdapter[] adapters = ORMHelper.mapAdapter(facade, field, meta);

      final Map outMap = new HashMap();

      for (final JsonElement element : json.getAsJsonArray()) {
        final JsonObject object = element.getAsJsonObject();

        outMap.put(
            adapters[0].construct(object.get("key")),
            adapters[1].construct(object.get("value"))
        );
      }

      return Optional.of(outMap);
    }

    if (meta.jsonSerializable()) {
      final JsonObjectAdapter instance = ORMHelper.findJsonAdapter(facade, meta.serializerOptions(),
          field);

      return Optional.of(instance.construct(json));
    }

    return Optional.empty();
  }

  @Override
  public <T extends RepositoryObject> Optional<T> readJsonAdaptive(
      @NotNull String column, @NotNull Class<T> type) throws SQLException, UnknownAdapterException {
    final Optional<String> optionalRaw = readString(column);

    if (!optionalRaw.isPresent()) {
      return Optional.empty();
    }

    return Optional.ofNullable(facade.jsonAdaptersRepository().fromJson(optionalRaw.get(), type));
  }

  @Override
  public <T> Optional<T> readColumnAdaptive(@NotNull String column, @NotNull Class<T> type)
      throws SQLException {
    final Optional<ColumnTypeAdapter<?, ?>> optAdapter = facade.columnTypeAdaptersRepository()
        .find(type);

    if (!optAdapter.isPresent()) {
      return Optional.empty();
    }

    final ColumnTypeAdapter adapter = optAdapter.get();

    final Class<?> primitiveType = adapter.primitiveType();

    final Object read = readPrimitive(column, primitiveType);

    if (read == null) {
      return Optional.empty();
    }

    return Optional.ofNullable((T) adapter.fromPrimitive(read));
  }

  @Override
  public @NotNull Optional<String> readString(@NotNull String column) throws SQLException {
    return Optional.ofNullable(resultSet.getString(column));
  }

  @Override
  public @NotNull ResultSetMetaData metadata() throws SQLException {
    return resultSet.getMetaData();
  }

  private <T extends RepositoryObject> Optional<T> readJsonAdaptive0(
      @NotNull String column, @NotNull Class<T> type) throws SQLException {
    try {
      return readJsonAdaptive(column, type);
    } catch (final UnknownAdapterException ignored) {
    }

    return Optional.empty();
  }

  private Object readPrimitive(final @NotNull String column, final @NotNull Class type)
      throws SQLException {
    if (!Types.isPrimitive(type)) {
      return null;
    }

    if (Types.asBoxedPrimitive(type) == Long.class) {
      return resultSet.getLong(column);
    }

    if (Types.asBoxedPrimitive(type) == Integer.class) {
      return resultSet.getInt(column);
    }

    if (Types.asBoxedPrimitive(type) == Double.class) {
      return resultSet.getDouble(column);
    }

    if (Types.asBoxedPrimitive(type) == String.class) {
      return resultSet.getString(column);
    }

    return null;
  }
}
