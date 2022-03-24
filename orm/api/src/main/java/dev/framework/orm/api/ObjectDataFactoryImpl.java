package dev.framework.orm.api;

import dev.framework.commons.Reflections;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.ColumnMetaImpl.BaseColumnImpl;
import dev.framework.orm.api.ColumnMetaImpl.BaseColumnImpl.BaseColumnOptions;
import dev.framework.orm.api.ColumnMetaImpl.BaseForeignKeyImpl;
import dev.framework.orm.api.ColumnMetaImpl.BaseGenericTypeImpl;
import dev.framework.orm.api.ColumnMetaImpl.BaseJsonCollectionImpl;
import dev.framework.orm.api.ColumnMetaImpl.BaseJsonMapImpl;
import dev.framework.orm.api.ColumnMetaImpl.BaseJsonSerializableImpl;
import dev.framework.orm.api.TableMetaImpl.BaseTableImpl;
import dev.framework.orm.api.TableMetaImpl.BaseTableImpl.BaseTableOptionsImpl;
import dev.framework.orm.api.annotation.Column;
import dev.framework.orm.api.annotation.ForeignKey;
import dev.framework.orm.api.annotation.GenericType;
import dev.framework.orm.api.annotation.InstanceConstructor;
import dev.framework.orm.api.annotation.JsonCollection;
import dev.framework.orm.api.annotation.JsonMap;
import dev.framework.orm.api.annotation.JsonSerializable;
import dev.framework.orm.api.annotation.ObjectVersion;
import dev.framework.orm.api.annotation.PrimaryKey;
import dev.framework.orm.api.annotation.Table;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.exception.MetaConstructionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class ObjectDataFactoryImpl implements ObjectDataFactory {

  @Override
  public @NotNull ObjectData createFromClass(
      @NotNull Class<? extends RepositoryObject> clazz) {
    try {
      final TableMeta meta = readTableMeta(clazz);
      final Version version = readVersion(clazz);

      return new ObjectDataImpl(clazz, version, meta,
          Reflections.findConstructorWithAnnotationOrThrow(clazz, InstanceConstructor.class));
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
  }

  private static Version readVersion(final @NotNull Class<? extends RepositoryObject> clazz)
      throws Throwable {
    final ObjectVersion annotatedVersion = clazz.getAnnotation(ObjectVersion.class);

    ensureAnnotation(annotatedVersion, clazz);

    return Version.of(
        annotatedVersion.major(),
        annotatedVersion.minor(),
        annotatedVersion.revision());
  }

  private static TableMeta readTableMeta(final @NotNull Class<? extends RepositoryObject> clazz)
      throws Throwable {
    final Table tableAnnotation = clazz.getAnnotation(Table.class);

    ensureAnnotation(tableAnnotation, clazz);

    final Table.TableOptions options = tableAnnotation.options();

    return new TableMetaImpl(
        readColumnSet(clazz),
        new BaseTableImpl(
            tableAnnotation.value(),
            new BaseTableOptionsImpl(
                options.engine(),
                options.standardCharset(),
                options.rowFormat(),
                options.compression())));
  }

  private static Set<ColumnMeta> readColumnSet(
      final @NotNull Class<? extends RepositoryObject> clazz) throws Throwable {
    final Set<ColumnMeta> columnSet = new LinkedHashSet<>();

    for (final Field declaredField : clazz.getDeclaredFields()) {
      columnSet.add(readFieldMeta(declaredField, clazz));
    }

    return columnSet;
  }

  private static ColumnMeta readFieldMeta(final @NotNull Field field,
      final @NotNull Class<? extends RepositoryObject> clazz) throws Throwable {
    final JsonMap map = field.getAnnotation(JsonMap.class);
    final JsonCollection collection = field.getAnnotation(JsonCollection.class);
    final GenericType genericType = field.getAnnotation(GenericType.class);
    final boolean primary = field.getAnnotation(PrimaryKey.class) != null;
    final Column column = field.getAnnotation(Column.class);
    final ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
    final JsonSerializable serializable = field.getAnnotation(JsonSerializable.class);

    ensureAnnotation(column, clazz);

    if (map != null) {
      if (!field.getType().isAssignableFrom(Map.class)) {
        throw new MetaConstructionException("Field " + field.getName() + ", is not map!", clazz);
      }

      if (genericType == null) {
        throw new MetaConstructionException(
            "Field " + field.getName() + ", map, but generic type is not defined!", clazz);
      }

      if (genericType.value().length != 2) {
        throw new MetaConstructionException(
            "Field " + field.getName() + ", map, but not all types provided!", clazz);
      }
    }

    if (collection != null) {
      if (!field.getType().isAssignableFrom(Collection.class)) {
        throw new MetaConstructionException("Field " + field.getName() + ", is not collection!",
            clazz);
      }

      if (genericType == null) {
        throw new MetaConstructionException(
            "Field " + field.getName() + ", collection, but generic type is not defined!", clazz);
      }
    }

    final Column.ColumnOptions columnOptions = column.options();

    return new ColumnMetaImpl(
        field,
        primary,
        new BaseJsonMapImpl(map.useTopLevelAnnotation()),
        new BaseJsonCollectionImpl(collection.useTopLevelAnnotation()),
        new BaseForeignKeyImpl(
            foreignKey.foreignField(),
            foreignKey.targetTable(),
            foreignKey.onDelete(),
            foreignKey.onUpdate()),
        new BaseJsonSerializableImpl(serializable.value()),
        new BaseColumnImpl(
            column.value(),
            column.typeAdapter(),
            new BaseColumnOptions(
                columnOptions.size(),
                columnOptions.nullable(),
                columnOptions.unique(),
                columnOptions.autoIncrement(),
                columnOptions.charset()),
            column.defaultValue()),
        new BaseGenericTypeImpl(genericType.value()));
  }

  private static void ensureAnnotation(
      final @NotNull Annotation annotation,
      final @NotNull Class<? extends RepositoryObject> clazz) throws MetaConstructionException {
    throw new MetaConstructionException(
        "Missing @" + annotation.annotationType().getSimpleName() + " annotation", clazz);
  }

}
