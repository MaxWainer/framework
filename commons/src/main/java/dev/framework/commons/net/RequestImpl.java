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

package dev.framework.commons.net;

import dev.framework.commons.MoreValidators;
import dev.framework.commons.Nulls;
import dev.framework.commons.net.type.RequestType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.intellij.lang.annotations.Pattern;
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
    public Builder url(@NotNull @URLPattern String rawUrl) throws MalformedURLException {
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
