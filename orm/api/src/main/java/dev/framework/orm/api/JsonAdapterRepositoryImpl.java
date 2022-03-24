package dev.framework.orm.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.framework.commons.Types;
import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.exception.UnknownAdapterException;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class JsonAdapterRepositoryImpl implements JsonAdapterRepository {

  private final OptionalMap<Class<?>, JsonObjectAdapter<?>> registry = OptionalMaps.newConcurrentMap();

  @Override
  public @NotNull Optional<@NotNull JsonObjectAdapter<?>> find(@NotNull Class<?> aClass) {
    return registry.get(aClass);
  }

  @Override
  public void register(@NotNull Class<?> aClass,
      @NotNull JsonObjectAdapter<?> jsonObjectAdapter) {
    registry.put(aClass, jsonObjectAdapter);
  }

  @Override
  public <T extends RepositoryObject> @NotNull T fromJson(
      @NotNull String json,
      @NotNull Class<T> type) throws UnknownAdapterException {
    final JsonObjectAdapter<T> adapter = findAdapter(type);

    final JsonElement element = JsonParser.parseString(json);

    return adapter.construct(element);
  }

  @Override
  public @NotNull <T extends RepositoryObject> String toJson(@NotNull T t)
      throws UnknownAdapterException {
    final JsonObjectAdapter<T> adapter = findAdapter(Types.contravarianceType(t));
    final JsonElement element = adapter.deconstruct(t);

    return element.toString();
  }

  private <T extends RepositoryObject> JsonObjectAdapter<T> findAdapter(
      final @NotNull Class<T> type) throws UnknownAdapterException {
    return (JsonObjectAdapter<T>) registry.get(type)
        .orElseThrow(() -> new UnknownAdapterException(type));
  }

}
