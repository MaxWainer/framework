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

package dev.framework.commons.test.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.framework.commons.net.Request;
import dev.framework.commons.net.call.NetCall;
import dev.framework.commons.net.call.result.CallResult;
import dev.framework.commons.net.client.NetClient;
import org.junit.jupiter.api.Test;

class TestRequest {

  private final NetClient client = NetClient.builder()
      .build();

  @Test
  void testGet() throws Exception {
    final NetCall call = client.call(Request.builder()
        .url("https://localhost:2301/empty")
        .get()
        .build());

    try (final CallResult result = call.get()) {
      final String raw = result.resultBody().rawBody();

      assertEquals(200, result.responseCode());
      assertEquals("{\"data\":\"empty\"}", raw);
    }
  }

}
