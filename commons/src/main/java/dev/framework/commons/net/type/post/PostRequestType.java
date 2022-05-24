package dev.framework.commons.net.type.post;

import dev.framework.commons.net.type.RequestType;
import org.jetbrains.annotations.NotNull;

public interface PostRequestType extends RequestType {

  @NotNull MediaType mediaType();

  @NotNull RequestBody requestBody();

  @Override
  default @NotNull String rawType() {
    return "POST";
  }
}
