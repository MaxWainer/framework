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

package dev.framework.stack.wrapper.nbt;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NBTCompound implements NBTBase {

  private final Map<String, NBTBase> members = Maps.newHashMap();

  @Override
  public @NotNull String asString() {
    return members.toString();
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public @NotNull NBTBase copy() {
    final NBTCompound compound = new NBTCompound();

    compound.putAll(new HashMap<>(members));

    return compound;
  }

  @Override
  public boolean isCompound() {
    return true;
  }

  @Override
  public NBTCompound asNBTCompound() {
    return this;
  }

  public int size() {
    return members.size();
  }

  public boolean containsKey(final @NotNull String key) {
    return members.containsKey(key);
  }

  public NBTBase get(final @NotNull String key) {
    return members.get(key);
  }

  @Nullable
  public NBTBase put(final @NotNull String key, final @NotNull NBTBase value) {
    return members.put(key, value);
  }

  public NBTBase remove(final @NotNull String key) {
    return members.remove(key);
  }

  public void putAll(@NotNull final Map<? extends String, ? extends NBTBase> m) {
    members.putAll(m);
  }

  public void clear() {
    members.clear();
  }

  @NotNull
  public Set<String> keySet() {
    return members.keySet();
  }

  @NotNull
  public Collection<NBTBase> values() {
    return members.values();
  }

  @NotNull
  public Set<Entry<String, NBTBase>> entrySet() {
    return members.entrySet();
  }

  public void forEach(final @NotNull BiConsumer<String, NBTBase> consumer) {
    for (final Entry<String, NBTBase> entry : this.entrySet()) {
      consumer.accept(entry.getKey(), entry.getValue());
    }
  }
}
