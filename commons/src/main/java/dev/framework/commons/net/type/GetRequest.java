package dev.framework.commons.net.type;

import org.jetbrains.annotations.NotNull;

final class GetRequest implements RequestType {

  public static final GetRequest INSTANCE = new GetRequest();

  private GetRequest() {}

  @Override
  public @NotNull String rawType() {
    return "GET";
  }
}
