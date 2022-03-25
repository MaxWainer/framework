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

import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import dev.framework.orm.api.data.meta.ColumnMeta;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

final class ColumnMetaImpl implements ColumnMeta {

  private final String identifier;
  private final Field field;

  private final boolean primaryKey;
  private final BaseJsonMap map;
  private final BaseJsonCollection collection;
  private final BaseJsonSerializable serializable;
  private final BaseColumn column;
  private final BaseGenericType genericType;
  private final boolean identifying;

  ColumnMetaImpl(
      final @NotNull Field field,
      final boolean primaryKey,
      final @Nullable BaseJsonMap map,
      final @Nullable BaseJsonCollection collection,
      final @Nullable BaseJsonSerializable serializable,
      final @NotNull BaseColumn column,
      final @Nullable BaseGenericType genericType,
      final boolean identifying) {
    this.primaryKey = primaryKey;
    this.identifier = column.value();
    this.field = field;
    this.map = map;
    this.collection = collection;
    this.serializable = serializable;
    this.column = column;
    this.genericType = genericType;
    this.identifying = identifying;
  }

  @Override
  public @NotNull String identifier() {
    return identifier;
  }

  @Override
  public boolean primaryKey() {
    return primaryKey;
  }

  @Override
  public boolean map() {
    return map != null;
  }

  @Override
  public boolean collection() {
    return collection != null;
  }

  @Override
  public boolean jsonSerializable() {
    return serializable != null;
  }

  @Override
  public boolean identifying() {
    return identifying;
  }

  @Override
  public @Nullable BaseJsonMap mapOptions() {
    if (map()) {
      return Objects.requireNonNull(map);
    }

    throw new UnsupportedOperationException("Annotation JsonMap, not preset!");
  }

  @Override
  public @Nullable BaseJsonCollection collectionOptions() {
    if (collection()) {
      return Objects.requireNonNull(collection);
    }

    throw new UnsupportedOperationException("Annotation JsonCollection, not preset!");
  }

  @Override
  public @Nullable BaseJsonSerializable serializerOptions() {
    if (jsonSerializable()) {
      return Objects.requireNonNull(serializable);
    }

    throw new UnsupportedOperationException("Annotation JsonSerializable, not preset!");
  }

  @Override
  public @Nullable BaseGenericType genericType() {
    return genericType;
  }

  @Override
  public @NotNull BaseColumn baseColumn() {
    return column;
  }

  @Override
  public @NotNull Field field() {
    return field;
  }

  static final class BaseGenericTypeImpl implements BaseGenericType {

    private final Class<?>[] value;

    BaseGenericTypeImpl(final @NotNull Class<?>[] value) {
      this.value = value;
    }

    @Override
    public @NotNull Class<?>[] value() {
      return value;
    }

    @Override
    public String toString() {
      return "BaseGenericTypeImpl{" +
          "value=" + Arrays.toString(value) +
          '}';
    }
  }

  static final class BaseJsonMapImpl implements BaseJsonMap {

    private final boolean useTopLevelAnnotation;

    BaseJsonMapImpl(final boolean useTopLevelAnnotation) {
      this.useTopLevelAnnotation = useTopLevelAnnotation;
    }

    @Override
    public boolean useTopLevelAnnotation() {
      return useTopLevelAnnotation;
    }

    @Override
    public String toString() {
      return "BaseJsonMapImpl{" +
          "useTopLevelAnnotation=" + useTopLevelAnnotation +
          '}';
    }
  }

  static final class BaseJsonCollectionImpl implements BaseJsonCollection {

    private final boolean useTopLevelAnnotation;

    BaseJsonCollectionImpl(final boolean useTopLevelAnnotation) {
      this.useTopLevelAnnotation = useTopLevelAnnotation;
    }

    @Override
    public boolean useTopLevelAnnotation() {
      return useTopLevelAnnotation;
    }

    @Override
    public String toString() {
      return "BaseJsonCollectionImpl{" +
          "useTopLevelAnnotation=" + useTopLevelAnnotation +
          '}';
    }
  }

  static final class BaseJsonSerializableImpl implements BaseJsonSerializable {

    private final Class<? extends JsonObjectAdapter> value;

    BaseJsonSerializableImpl(
        final @NotNull Class<? extends JsonObjectAdapter> value) {
      this.value = value;
    }

    @Override
    public Class<? extends JsonObjectAdapter> value() {
      return value;
    }

    @Override
    public String toString() {
      return "BaseJsonSerializableImpl{" +
          "value=" + value +
          '}';
    }
  }

  static final class BaseColumnImpl implements BaseColumn {

    private final String value;
    private final Class<? extends ColumnTypeAdapter> typeAdapter;
    private final BaseColumnOptions options;
    private final String defaultValue;

    BaseColumnImpl(
        final @NotNull String value,
        final @NotNull Class<? extends ColumnTypeAdapter> typeAdapter,
        final @NotNull BaseColumnOptions options,
        final @UnknownNullability String defaultValue) {
      this.value = value;
      this.typeAdapter = typeAdapter;
      this.options = options;
      this.defaultValue = defaultValue;
    }

    @Override
    public @NotNull String value() {
      return value;
    }

    @Override
    public @NotNull Class<? extends ColumnTypeAdapter> typeAdapter() {
      return typeAdapter;
    }

    @Override
    public BaseColumn.@NotNull BaseColumnOptions options() {
      return options;
    }

    @Override
    public @UnknownNullability String defaultValue() {
      return defaultValue;
    }

    static final class BaseColumnOptionsImpl implements BaseColumn.BaseColumnOptions {

      private final int size;
      private final boolean nullable;
      private final boolean unique;
      private final boolean autoIncrement;
      private final String charset;

      BaseColumnOptionsImpl(
          final int size,
          final boolean nullable,
          final boolean unique,
          final boolean autoIncrement,
          final @NotNull String charset) {
        this.size = size;
        this.nullable = nullable;
        this.unique = unique;
        this.autoIncrement = autoIncrement;
        this.charset = charset;
      }

      @Override
      public int size() {
        return size;
      }

      @Override
      public boolean nullable() {
        return nullable;
      }

      @Override
      public boolean unique() {
        return unique;
      }

      @Override
      public boolean autoIncrement() {
        return autoIncrement;
      }

      @Override
      public @NotNull String charset() {
        return charset;
      }

      @Override
      public String toString() {
        return "BaseColumnOptionsImpl{" +
            "size=" + size +
            ", nullable=" + nullable +
            ", unique=" + unique +
            ", autoIncrement=" + autoIncrement +
            ", charset='" + charset + '\'' +
            '}';
      }
    }

    @Override
    public String toString() {
      return "BaseColumnImpl{" +
          "value='" + value + '\'' +
          ", typeAdapter=" + typeAdapter +
          ", options=" + options +
          ", defaultValue='" + defaultValue + '\'' +
          '}';
    }
  }

  @Override
  public String toString() {
    return "ColumnMetaImpl{" +
        "identifier='" + identifier + '\'' +
        ", field=" + field +
        ", primaryKey=" + primaryKey +
        ", map=" + map +
        ", collection=" + collection +
        ", serializable=" + serializable +
        ", column=" + column +
        ", genericType=" + genericType +
        ", identifying=" + identifying +
        '}';
  }
}
