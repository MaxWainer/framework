package dev.framework.orm.implementation.sql;

import dev.framework.orm.api.AbstractORMFacade;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.update.TableUpdater;
import org.jetbrains.annotations.NotNull;

public final class SQLORMFacade extends AbstractORMFacade {

  @Override
  public @NotNull TableUpdater tableUpdater() {
    return null;
  }

  @Override
  public @NotNull ConnectionSource connectionSource() {
    return null;
  }

  @Override
  public @NotNull DialectProvider dialectProvider() {
    return null;
  }
}
