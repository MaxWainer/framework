package dev.framework.orm;

import dev.framework.orm.update.TableUpdater;
import org.jetbrains.annotations.NotNull;

public interface ORMFacadeFactory {

  void createFacade(
      final @NotNull ConnectionSource connectionSource,
      final @NotNull TableUpdater tableUpdater);

}
