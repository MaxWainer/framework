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
