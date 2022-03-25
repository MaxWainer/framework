/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
