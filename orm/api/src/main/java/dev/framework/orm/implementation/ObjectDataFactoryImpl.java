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

import dev.framework.commons.Reflections;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.annotation.Column;
import dev.framework.orm.api.annotation.GenericType;
import dev.framework.orm.api.annotation.IdentifierField;
import dev.framework.orm.api.annotation.InstanceConstructor;
import dev.framework.orm.api.annotation.JsonCollection;
import dev.framework.orm.api.annotation.JsonMap;
import dev.framework.orm.api.annotation.JsonSerializable;
import dev.framework.orm.api.annotation.ObjectVersion;
import dev.framework.orm.api.annotation.PrimaryKey;
import dev.framework.orm.api.annotation.Table;
import dev.framework.orm.api.annotation.TargetImplementation;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.implementation.ColumnMetaImpl.BaseColumnImpl;
import dev.framework.orm.implementation.ColumnMetaImpl.BaseColumnImpl.BaseColumnOptionsImpl;
import dev.framework.orm.implementation.ColumnMetaImpl.BaseGenericTypeImpl;
import dev.framework.orm.implementation.ColumnMetaImpl.BaseJsonCollectionImpl;
import dev.framework.orm.implementation.ColumnMetaImpl.BaseJsonMapImpl;
import dev.framework.orm.implementation.ColumnMetaImpl.BaseJsonSerializableImpl;
import dev.framework.orm.implementation.TableMetaImpl.BaseTableImpl;
import dev.framework.orm.implementation.TableMetaImpl.BaseTableImpl.BaseTableOptionsImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class ObjectDataFactoryImpl implements ObjectDataFactory {

  @Override
  public @NotNull ObjectData createFromClass(
      @NotNull Class<? extends RepositoryObject> clazz)
      throws MissingAnnotationException, MetaConstructionException {
    final Class<? extends RepositoryObject> readingClass = restoreClass(clazz);

    final TableMeta meta = readTableMeta(readingClass); // read meta
    final Version version = readVersion(readingClass); // read version

    return new ObjectDataImpl(readingClass, version, meta,
        Reflections.findConstructorWithAnnotationOrThrow(
            readingClass,
            InstanceConstructor.class));
  }

  public static Version readVersion(final @NotNull Class<? extends RepositoryObject> clazz)
      throws MetaConstructionException {
    final Class<? extends RepositoryObject> readingClass = restoreClass(clazz);

    final ObjectVersion annotatedVersion = readingClass.getAnnotation(ObjectVersion.class);

    ensureAnnotation(ObjectVersion.class, readingClass);

    return Version.of(
        annotatedVersion.major(),
        annotatedVersion.minor(),
        annotatedVersion.revision());
  }

  private static Class<? extends RepositoryObject> restoreClass(
      final @NotNull Class<? extends RepositoryObject> clazz) throws MetaConstructionException {
    // check is it abstract class or interface
    if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
      final TargetImplementation targetImplementation = clazz.getAnnotation(
          TargetImplementation.class); // get target implementation

      ensureAnnotation(TargetImplementation.class, clazz); // ensure that this annotation present

      return targetImplementation.value(); // set readingClass to founded target
    }

    return clazz;
  }

  private static TableMeta readTableMeta(final @NotNull Class<? extends RepositoryObject> clazz)
      throws MissingAnnotationException, MetaConstructionException {
    final Table tableAnnotation = clazz.getAnnotation(Table.class);

    ensureAnnotation(Table.class, clazz);

    final Table.TableOptions options = tableAnnotation.options();

    final Set<ColumnMeta> set = readColumnSet(clazz);

    final boolean anyIdentifying = set.stream().anyMatch(ColumnMeta::identifying);

    if (!anyIdentifying) {
      throw new MissingAnnotationException(IdentifierField.class);
    }

    final int count = (int) set.stream().filter(ColumnMeta::identifying).count();

    if (count != 1) {
      throw new MetaConstructionException("Object can have only one @IdentifierField", clazz);
    }

    return new TableMetaImpl(
        set,
        new BaseTableImpl(
            tableAnnotation.value(),
            new BaseTableOptionsImpl(
                options.engine(),
                options.standardCharset(),
                options.rowFormat(),
                options.compression())));
  }

  private static Set<ColumnMeta> readColumnSet(
      final @NotNull Class<? extends RepositoryObject> clazz) throws MetaConstructionException {
    final Set<ColumnMeta> columnSet = new LinkedHashSet<>();

    for (final Field declaredField : clazz.getDeclaredFields()) {
      columnSet.add(readFieldMeta(declaredField, clazz));
    }

    return columnSet;
  }

  private static ColumnMeta readFieldMeta(final @NotNull Field field,
      final @NotNull Class<? extends RepositoryObject> clazz) throws MetaConstructionException {
    final JsonMap map = field.getAnnotation(JsonMap.class);
    final JsonCollection collection = field.getAnnotation(JsonCollection.class);
    final GenericType genericType = field.getAnnotation(GenericType.class);
    final boolean primary = field.getAnnotation(PrimaryKey.class) != null;
    final Column column = field.getAnnotation(Column.class);
    final JsonSerializable serializable = field.getAnnotation(JsonSerializable.class);

    if (column == null) {
      throw new MetaConstructionException(
          "Missing @Column annotation on field " + field.toGenericString(), clazz);
    }

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
      if (!Collection.class.isAssignableFrom(field.getType())) {
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
        map == null ? null : new BaseJsonMapImpl(map.useTopLevelAnnotation()),
        collection == null ? null : new BaseJsonCollectionImpl(collection.useTopLevelAnnotation()),
        serializable == null ? null : new BaseJsonSerializableImpl(serializable.value()),
        new BaseColumnImpl(
            column.value(),
            column.typeAdapter(),
            new BaseColumnOptionsImpl(
                columnOptions.size(),
                columnOptions.nullable(),
                columnOptions.unique(),
                columnOptions.autoIncrement(),
                columnOptions.charset()),
            column.defaultValue()),
        genericType == null ? null : new BaseGenericTypeImpl(genericType.value()),
        field.isAnnotationPresent(IdentifierField.class));
  }

  private static void ensureAnnotation(
      final @NotNull Class<? extends Annotation> annotation,
      final @NotNull Class<? extends RepositoryObject> clazz) throws MetaConstructionException {
    if (!clazz.isAnnotationPresent(annotation)) {
      throw new MetaConstructionException(
          "Missing @" + annotation.getSimpleName() + " annotation", clazz);
    }
  }

}
