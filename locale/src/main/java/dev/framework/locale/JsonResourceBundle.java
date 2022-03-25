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

package dev.framework.locale;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public final class JsonResourceBundle extends ResourceBundle {

  private final JsonObject bundleObject;

  public JsonResourceBundle(final @NotNull String bundleName) {
    final String fullName = bundleName + ".json";

    final InputStream inputStream = getClass()
        .getClassLoader()
        .getResourceAsStream(fullName);

    if (inputStream == null) {
      throw new IllegalArgumentException("Unknown resource bundle: " + fullName);
    }

    try (final Reader reader = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      bundleObject = new JsonParser().parse(reader).getAsJsonObject(); // legacy support
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected Object handleGetObject(@NotNull final String key) {
    return bundleObject.get(key).getAsJsonPrimitive().getAsString();
  }

  @NotNull
  @Override
  public Enumeration<String> getKeys() {
    return Collections.enumeration(bundleObject.entrySet().stream().map(Entry::getKey).collect(
        Collectors.toList()));
  }
}
