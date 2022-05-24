package dev.framework.commons.net.type;

import dev.framework.commons.net.type.post.MediaType;
import dev.framework.commons.net.type.post.RequestBody;
import org.jetbrains.annotations.NotNull;

public interface RequestTypes {

  RequestType GET = GetRequest.INSTANCE;

  static @NotNull RequestType post(
      final @NotNull MediaType mediaType,
      final @NotNull RequestBody requestBody) {
    return new PostRequestTypeImpl(mediaType, requestBody);
  }

  static @NotNull RequestType jsonPost(
      final @NotNull RequestBody requestBody) {
    return new PostRequestTypeImpl(MediaType.create("application", "json"), requestBody);
  }

}
