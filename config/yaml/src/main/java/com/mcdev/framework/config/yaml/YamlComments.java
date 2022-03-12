package com.mcdev.framework.config.yaml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public final class YamlComments {

  public static void main(String[] args) throws URISyntaxException, IOException {
    final Yaml yaml = new Yaml();
    final URL url = YamlComments.class
        .getClassLoader()
        .getResource("example.yaml");

    final String rawYaml = String.join("\n", Files.readAllLines(Paths.get(url.toURI())));

    System.out.println(rawYaml);

    final Map<String, Object> obj = yaml.load(rawYaml);
    System.out.println(obj);
  }

}
