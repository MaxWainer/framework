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
