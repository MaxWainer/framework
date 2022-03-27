package dev.framework.orm.api.query.types;

import dev.framework.commons.Identifiable;
import dev.framework.orm.api.data.meta.ColumnMeta;
import java.util.Arrays;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;

public interface UpdateQuery extends WhereOptions<UpdateQuery>, TableScope<UpdateQuery> {

  UpdateQuery set(final @NotNull String... columns);

  default UpdateQuery set(final @NotNull ColumnMeta... columns) {
    return set(
        Arrays
            .stream(columns)
            .map(Identifiable::identifier)
            .toArray(String[]::new)
    );
  }

  default UpdateQuery set(final @NotNull Iterable<? extends ColumnMeta> columns) {
    return set(
        StreamSupport.stream(columns.spliterator(), false)
            .map(Identifiable::identifier)
            .toArray(String[]::new)
    );
  }

}
