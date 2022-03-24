package dev.framework.orm.api;

import dev.framework.commons.Reflections;
import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.annotation.InstanceConstructor;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.exception.UnknownAdapterException;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import java.io.IOException;
import java.util.Optional;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractORMFacade implements ORMFacade {

  public static final String OBJECT_INFO_TABLE_NAME = "_DEV_FRMWRK_ObjectInfo";

  private final OptionalMap<Class<? extends RepositoryObject>, ObjectData> objectDataCache = OptionalMaps.newConcurrentMap();

  private final OptionalMap<Class<? extends RepositoryObject>, ObjectRepository<?, ?>> repositoryCache = OptionalMaps.newConcurrentMap();

  private final ObjectDataFactory dataFactory = new ObjectDataFactoryImpl();
  private final ColumnTypeAdapterRepository columnTypeAdapters = new ColumnTypeAdapterRepositoryImpl();
  private final JsonAdapterRepository jsonAdapters = new JsonAdapterRepositoryImpl();

  @Override
  public <I, O extends RepositoryObject<I>> void registerRepository(
      @NotNull Class<? extends O> clazz, @NotNull ObjectRepository<I, O> repository) {
    repositoryCache.put(clazz, repository);
  }

  @Override
  public @NotNull <I, O extends RepositoryObject<I>> ObjectRepository<I, O> findRepository(
      @NotNull Class<? extends O> clazz) throws MissingRepositoryException {
    return (ObjectRepository<I, O>) repositoryCache.get(clazz)
        .orElseThrow(() -> new MissingRepositoryException(clazz));
  }

  @Override
  public @NotNull JsonAdapterRepository jsonAdapters() {
    return this.jsonAdapters;
  }

  @Override
  public @NotNull ColumnTypeAdapterRepository columnTypeAdapters() {
    return this.columnTypeAdapters;
  }

  @Override
  public @NotNull Optional<ObjectData> findData(@NotNull Class<? extends RepositoryObject> clazz) {
    return objectDataCache.get(clazz);
  }

  @Override
  public void replaceData(@NotNull ObjectData data, @NotNull TableMeta newMeta) {
    this.objectDataCache.useIfExists(data.delegate(), (value, key) -> {
      value.replaceTableMeta(newMeta);
      return value;
    });
  }

  @Override
  public @NotNull ObjectDataFactory dataFactory() {
    return dataFactory;
  }

  @Override
  public void open() {
    final String protectedTableName = dialectProvider().protectValue(OBJECT_INFO_TABLE_NAME);

    @Language("SQL") final String createQuery = String.format(
        "CREATE TABLE IF NOT EXISTS %s (%s VARCHAR(255) NOT NULL, %s VARCHAR(10) NOT NULL, %s LONGTEXT NOT NULL)",
        protectedTableName, dialectProvider().protectValue("RUNTIME_CLASS_PATH"),
        dialectProvider().protectValue("OBJECT_VERSION"),
        dialectProvider().protectValue("TABLE_META"));

    // create registry
    connectionSource().execute(createQuery).join();

    @Language("SQL") final String selectQuery = String.format("SELECT * FROM %s",
        protectedTableName);

    connectionSource().executeWithResult(selectQuery, appender -> {
    }, reader -> {
      try {
        while (reader.next()) {
          final String rawClassPath = reader.readString("RUNTIME_CLASS_PATH");
          final String rawVersion = reader.readString("OBJECT_VERSION");
          final String rawTableMeta = reader.readString("TABLE_META");

          final Class<? extends RepositoryObject> classPath;
          final Version version;
          try {
            classPath = (Class<? extends RepositoryObject>) Class.forName(rawClassPath);
            version = Version.parseSequence(rawVersion);
          } catch (Throwable e) {
            throw new RuntimeException(e);
          }

          objectDataCache.put(classPath,
              new ObjectDataImpl(
                  classPath,
                  version,
                  jsonAdapters.fromJson(rawTableMeta, TableMeta.class),
                  Reflections.findConstructorWithAnnotationOrThrow(classPath, InstanceConstructor.class)));
        }
      } catch (UnknownAdapterException e) {
        e.printStackTrace();
      }
      return null;
    });
  }

  @Override
  public void close() throws IOException {

  }
}
