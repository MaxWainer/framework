package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface UpdateQuery extends  WhereSubQuery<UpdateQuery> {

  UpdateQuery set(final @NotNull String ...columns);

}
