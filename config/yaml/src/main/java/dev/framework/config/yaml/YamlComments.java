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

package dev.framework.config.yaml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public final class YamlComments {

  public static void main(String[] args) throws URISyntaxException, IOException {
    final DumperOptions options = new DumperOptions();

    final Yaml yaml = new Yaml(options);

    final URL url = YamlComments.class
        .getClassLoader()
        .getResource("example.yaml");

    final List<String> yamlLines = Files.readAllLines(Paths.get(url.toURI()));

    for (final String yamlLine : yamlLines) {
      if (yamlLine.startsWith("#") || yamlLine.trim().isEmpty()) {
        System.out.println("Found comment " + yamlLine);
      }
    }

    final String rawYaml = String.join("\n", yamlLines);

    System.out.println(rawYaml);

    final Map<String, Object> obj = yaml.load(rawYaml);
    System.out.println(obj);
  }

}
