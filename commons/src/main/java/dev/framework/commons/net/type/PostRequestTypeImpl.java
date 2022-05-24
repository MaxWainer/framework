package dev.framework.commons.net.type;

import dev.framework.commons.net.type.post.MediaType;
import dev.framework.commons.net.type.post.PostRequestType;
import dev.framework.commons.net.type.post.RequestBody;
import org.jetbrains.annotations.NotNull;

final class PostRequestTypeImpl implements PostRequestType {

  private final MediaType mediaType;
  private final RequestBody requestBody;

  PostRequestTypeImpl(final @NotNull MediaType mediaType, RequestBody requestBody) {
    this.mediaType = mediaType;
    this.requestBody = requestBody;
  }

  @Override
  public @NotNull MediaType mediaType() {
    return mediaType;
  }

  @Override
  public @NotNull RequestBody requestBody() {
    return requestBody;
  }
}
