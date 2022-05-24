package dev.framework.commons.net;

import dev.framework.commons.MoreValidators;
import dev.framework.commons.Nulls;
import dev.framework.commons.net.type.RequestType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

final class RequestImpl implements Request {

  private final Map<String, String> headers;
  private final URL url;
  private final RequestType requestType;

  private final int contentLength;

  RequestImpl(
      final @NotNull Map<String, String> headers,
      final @NotNull URL url,
      final @NotNull RequestType requestType,
      final int contentLength) {
    Nulls.isNull(url, "url");
    Nulls.isNull(requestType, "requestType");
    MoreValidators.validateExpression(contentLength <= 0, "contentLength cannot be negative");

    this.headers = headers;
    this.url = url;
    this.requestType = requestType;
    this.contentLength = contentLength;
  }

  @Override
  public @NotNull URL url() {
    return url;
  }

  @Override
  public @NotNull @Unmodifiable Map<String, String> headers() {
    return Collections.unmodifiableMap(headers);
  }

  @Override
  public @NotNull RequestType requestType() {
    return requestType;
  }

  static final class BuilderImpl implements Request.Builder {

    private final Map<String, String> headers = new HashMap<>();
    private URL url;
    private RequestType requestType;
    private int contentLength = 0;

    @Override
    public Builder url(@NotNull URL url) {
      this.url = url;
      return this;
    }

    @Override
    public Builder url(@NotNull String rawUrl) throws MalformedURLException {
      return url(new URL(rawUrl));
    }

    @Override
    public Builder appendHeader(@NotNull String key, @NotNull String value) {
      this.headers.putIfAbsent(key, value);
      return this;
    }

    @Override
    public Builder type(@NotNull RequestType requestType) {
      this.requestType = requestType;
      return this;
    }

    @Override
    public Builder contentLength(int contentLength) {
      this.contentLength = contentLength;
      return this;
    }

    @Override
    public Request build() {
      return new RequestImpl(headers, url, requestType, contentLength);
    }
  }
}
