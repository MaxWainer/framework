package dev.framework.orm.api;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import dev.framework.orm.api.annotation.ForeignKey;
import dev.framework.orm.api.annotation.ForeignKey.Action;
import dev.framework.orm.api.data.meta.ColumnMeta;
import java.lang.reflect.Field;
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
  private final BaseForeignKey foreignKey;
  private final BaseJsonSerializable serializable;
  private final BaseColumn column;
  private final BaseGenericType genericType;

  ColumnMetaImpl(
      final @NotNull Field field,
      final boolean primaryKey,
      final @Nullable BaseJsonMap map,
      final @Nullable BaseJsonCollection collection,
      final @Nullable BaseForeignKey foreignKey,
      final @Nullable BaseJsonSerializable serializable,
      final @NotNull BaseColumn column,
      final @Nullable BaseGenericType genericType) {
    this.primaryKey = primaryKey;
    this.identifier = column.value();
    this.field = field;
    this.map = map;
    this.collection = collection;
    this.foreignKey = foreignKey;
    this.serializable = serializable;
    this.column = column;
    this.genericType = genericType;
  }

  @Override
  public @NotNull String identifier() {
    return identifier;
  }

  @Override
  public boolean foreign() {
    return foreignKey != null;
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
  public @NotNull BaseJsonMap mapOptions() {
    if (map()) {
      return Objects.requireNonNull(map);
    }

    throw new UnsupportedOperationException("Annotation JsonMap, not preset!");
  }

  @Override
  public @NotNull BaseJsonCollection collectionOptions() {
    if (collection()) {
      return Objects.requireNonNull(collection);
    }

    throw new UnsupportedOperationException("Annotation JsonCollection, not preset!");
  }

  @Override
  public @NotNull BaseForeignKey foreignKeyOptions() {
    if (foreign()) {
      return Objects.requireNonNull(foreignKey);
    }

    throw new UnsupportedOperationException("Annotation ForeignKey, not preset!");
  }

  @Override
  public @NotNull BaseJsonSerializable serializerOptions() {
    if (jsonSerializable()) {
      return Objects.requireNonNull(serializable);
    }

    throw new UnsupportedOperationException("Annotation JsonSerializable, not preset!");
  }

  @Override
  public @NotNull BaseGenericType genericType() {
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
  }

  static final class BaseForeignKeyImpl implements BaseForeignKey {

    private final String foreignField;
    private final Class<? extends RepositoryObject> targetTable;
    private final ForeignKey.Action deleteActions;
    private final ForeignKey.Action updateActions;

    BaseForeignKeyImpl(
        final @NotNull String foreignField,
        final @NotNull Class<? extends RepositoryObject> targetTable,
        final @NotNull Action deleteActions,
        final @NotNull Action updateActions) {
      this.foreignField = foreignField;
      this.targetTable = targetTable;
      this.deleteActions = deleteActions;
      this.updateActions = updateActions;
    }

    @Override
    public @NotNull String foreignField() {
      return foreignField;
    }

    @Override
    public @NotNull Class<? extends RepositoryObject> targetTable() {
      return targetTable;
    }

    @Override
    public @NotNull ForeignKey.Action onDelete() {
      return deleteActions;
    }

    @Override
    public @NotNull ForeignKey.Action onUpdate() {
      return updateActions;
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

    static final class BaseColumnOptions implements BaseColumn.BaseColumnOptions {

      private final int size;
      private final boolean nullable;
      private final boolean unique;
      private final boolean autoIncrement;
      private final String charset;

      BaseColumnOptions(
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
    }

  }
}
