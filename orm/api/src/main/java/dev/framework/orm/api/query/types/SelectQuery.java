package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface SelectQuery extends WhereOptions<SelectQuery>, TableScope<SelectQuery> {

  SelectQuery everything();

  default SelectQuery from(final @NotNull String table) {
    return table(table);
  }

  SelectQuery columns(final @NotNull String... columns);

  SelectQuery join(
      final @NotNull String table,
      final @NotNull String localColumn,
      final @NotNull String joiningColumn);

  SelectQuery join(
      final @NotNull String table,
      final @NotNull String localColumn,
      final @NotNull String joiningColumn,
      final @NotNull String joinAlias);
}
