package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface InsertQuery extends TableScope<InsertQuery> {

  InsertQuery values(final int count);

  InsertQuery values(final @NotNull String ...values);

}
