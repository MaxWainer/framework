package dev.framework.orm.dialect;

import dev.framework.orm.data.meta.ColumnMeta;
import org.jetbrains.annotations.NotNull;

public interface DialectProvider {

  @NotNull String protectValue(final @NotNull String value);

  @NotNull String columnMetaToString(final @NotNull ColumnMeta meta);

}
