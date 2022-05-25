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

package dev.framework.commons.net.client;

import dev.framework.commons.Buildable;
import dev.framework.commons.net.Request;
import dev.framework.commons.net.call.NetCall;
import java.net.Authenticator;
import java.net.ProxySelector;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import org.jetbrains.annotations.NotNull;

public interface NetClient {

  static NetClient.Builder builder() {
    return new NetClientImpl.BuilderImpl();
  }

  @NotNull NetCall call(final @NotNull Request request);

  interface Builder extends Buildable<NetClient> {

    Builder hostnameVerifier(final @NotNull HostnameVerifier hostnameVerifier);

    Builder timeout(final long timeout);

    default Builder timeout(final @NotNull TimeUnit unit, final long timeout) {
      return timeout(unit.toMillis(timeout));
    }

    Builder sslSocketFactory(
        final @NotNull SSLSocketFactory sslSocketFactory,
        final @NotNull X509TrustManager x509TrustManager);

    Builder socketFactory(
        final @NotNull SocketFactory socketFactory);

    Builder sslSocketFactory(final @NotNull SSLSocketFactory sslSocketFactory);

    Builder authenticator(final @NotNull Authenticator authenticator);

    Builder proxySelector(final @NotNull ProxySelector proxySelector);

  }

}
