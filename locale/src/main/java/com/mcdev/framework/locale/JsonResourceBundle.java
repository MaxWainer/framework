package com.mcdev.framework.locale;

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

    if (inputStream == null)
      throw new IllegalArgumentException("Unknown resource bundle: " + fullName);

    try (final Reader reader = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      bundleObject = new JsonParser().parse(reader).getAsJsonObject();
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
