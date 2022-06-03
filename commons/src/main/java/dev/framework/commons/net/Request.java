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

import dev.framework.commons.Buildable;
import dev.framework.commons.exception.NotImplementedYet;
import dev.framework.commons.net.type.RequestType;
import dev.framework.commons.net.type.RequestTypes;
import dev.framework.commons.net.type.post.MediaType;
import dev.framework.commons.net.type.post.RequestBody;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface Request {

  static Request.Builder builder() {
    throw new NotImplementedYet();
  }

  @NotNull URL url();

  @NotNull @Unmodifiable Map<String, String> headers();

  @NotNull RequestType requestType();

  interface Builder extends Buildable<Request> {

    Builder url(final @NotNull URL url);

    Builder url(final @NotNull @URLPattern String rawUrl)
        throws MalformedURLException;

    Builder appendHeader(final @NotNull String key, final @NotNull String value);

    default Builder appendAuthenticationHeader(final @NotNull String value) {
      return appendHeader("Authentication", value);
    }

    Builder type(final @NotNull RequestType requestType);

    default Builder get() {
      return type(RequestTypes.GET);
    }

    default Builder post(final @NotNull MediaType mediaType,
        final @NotNull RequestBody requestBody) {
      return type(RequestTypes.post(mediaType, requestBody));
    }

    Builder contentLength(final int contentLength);

  }

}
