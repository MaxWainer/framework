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

package dev.framework.loader.resource;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.framework.commons.MoreExceptions;
import dev.framework.loader.repository.dependency.Dependency;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public final class ResourceFileLoader {

  private ResourceFileLoader() {
    MoreExceptions.instantiationError();
  }

  public static ResourceFile readFile(
      final @NotNull Reader reader) throws IOException {
    final JsonObject jsonObject = new JsonParser().parse(reader)
        .getAsJsonObject(); // older GSON version support

    final ImmutableMap<Dependency, String> dependencies = ImmutableMap.copyOf(
        jsonObject.getAsJsonObject("dependencies") // list all dependencies
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                entry ->
                    Dependency.of(entry.getKey()), // get from key our dependency
                entry ->
                    entry.getValue().getAsString()) // second will be relocating path
            )
    );

    return new ResourceFile(dependencies);
  }

}
