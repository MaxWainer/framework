package dev.framework.orm;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.data.ObjectData;
import dev.framework.orm.data.ObjectDataGenerator;
import dev.framework.orm.dialect.DialectProvider;
import dev.framework.orm.update.TableUpdater;
import org.jetbrains.annotations.NotNull;

public interface ORMFacade {

  <I, O extends RepositoryObject<I>> @NotNull ObjectRepository<I, O> findRepository(
      final @NotNull Class<? extends O> clazz);

  @NotNull TableUpdater tableUpdater();

  @NotNull ConnectionSource dataSource();

  @NotNull ObjectData findData(final @NotNull Class<? extends RepositoryObject> clazz);

  void replaceData(final @NotNull ObjectData oldData, final @NotNull ObjectData newData);

  @NotNull ObjectDataGenerator dataGenerator();

  @NotNull DialectProvider dialectProvider();

}
