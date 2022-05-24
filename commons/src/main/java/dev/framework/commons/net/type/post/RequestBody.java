package dev.framework.commons.net.type.post;

import org.jetbrains.annotations.NotNull;

public final class RequestBody {

  public static @NotNull RequestBody create(final @NotNull String content) {
    return new RequestBody(content);
  }

  private final String content;

  RequestBody(final @NotNull String content) {
    this.content = content;
  }

  @NotNull
  public String content() {
    return content;
  }
}
