package dev.framework.commons.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LinkedJsonArray extends JsonElement implements Iterable<JsonElement> {

  private final List<JsonElement> elements;

  public LinkedJsonArray() {
    this.elements = new LinkedList<>();
  }

  @Override
  public JsonElement deepCopy() {
    if (!elements.isEmpty()) {
      final LinkedJsonArray result = new LinkedJsonArray();
      for (final JsonElement element : elements) {
        result.add(element.deepCopy());
      }
      return result;
    }
    return new LinkedJsonArray();
  }

  public boolean add(final @NotNull JsonElement element) {
    return elements.add(element);
  }

  public boolean remove(final @NotNull JsonElement element) {
    return elements.remove(element);
  }

  public @Nullable JsonElement set(final int index, final @NotNull JsonElement element) {
    return elements.set(index, element);
  }

  public void add(final int index, final @NotNull JsonElement element) {
    elements.add(index, element);
  }

  public @Nullable JsonElement get(final int index) {
    try {
      return elements.get(index);
    } catch (final IndexOutOfBoundsException ignored) {
    }

    return null;
  }

  public boolean isEmpty() {
    return elements.isEmpty();
  }

  public int size() {
    return elements.size();
  }

  @Override
  public boolean isJsonArray() {
    return true;
  }

  @NotNull
  @Override
  public Iterator<JsonElement> iterator() {
    return elements.iterator();
  }
}
