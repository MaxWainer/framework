package dev.framework.orm.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.data.meta.TableMeta.BaseTable;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class TableMetaJsonSerializable implements JsonObjectAdapter<TableMeta> {

  @Override
  public @NotNull Class<TableMeta> identifier() {
    return TableMeta.class;
  }

  @Override
  public @NotNull TableMeta construct(@NotNull JsonElement element) {
    // constructing values
    final Set<ColumnMeta> columnMetaSet = new LinkedHashSet<>();

    // json values
    final JsonObject baseObject = element.getAsJsonObject();
    final JsonArray columnSet = baseObject.getAsJsonArray("columnSet");
    final JsonObject baseTableObject = baseObject.getAsJsonObject("baseTable");

    for (final JsonElement jsonElement : columnSet) {
      final JsonObject columnObject = jsonElement.getAsJsonObject();

      final ColumnMeta meta = ColumnMetaSerializer.INSTANCE.construct(columnObject);

      columnMetaSet.add(meta);
    }


    return new TableMetaImpl(columnMetaSet, BaseTableSerializer.INSTANCE.construct(baseTableObject));
  }

  @Override
  public @NotNull JsonElement deconstruct(@NotNull TableMeta meta) {
    final JsonObject object = new JsonObject();

    return object;
  }

  private static final class ColumnMetaSerializer implements
      JsonObjectAdapter<ColumnMeta> {

    public static final ColumnMetaSerializer INSTANCE = new ColumnMetaSerializer();

    @Override
    public @NotNull Class<ColumnMeta> identifier() {
      return ColumnMeta.class;
    }

    @Override
    public @NotNull ColumnMeta construct(@NotNull JsonElement element) {
      return null;
    }

    @Override
    public @NotNull JsonElement deconstruct(@NotNull ColumnMeta columnMeta) {
      return null;
    }
  }

  private static final class ColumnOptionsSerializer {

  }

  private static final class BaseTableSerializer implements JsonObjectAdapter<BaseTable> {

    public static final BaseTableSerializer INSTANCE = new BaseTableSerializer();

    @Override
    public @NotNull Class<BaseTable> identifier() {
      return null;
    }

    @Override
    public @NotNull BaseTable construct(@NotNull JsonElement element) {
      return null;
    }

    @Override
    public @NotNull JsonElement deconstruct(@NotNull BaseTable baseTable) {
      return null;
    }
  }

}
