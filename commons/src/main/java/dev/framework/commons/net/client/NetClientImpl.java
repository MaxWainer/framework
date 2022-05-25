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

import dev.framework.commons.net.Request;
import dev.framework.commons.net.call.NetCall;
import java.net.Authenticator;
import java.net.ProxySelector;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import org.jetbrains.annotations.NotNull;

final class NetClientImpl implements NetClient {

  private final HostnameVerifier hostnameVerifier;
  private final long timeoutMills;
  private final SSLSocketFactory sslSocketFactory;
  private final SocketFactory socketFactory;
  private final X509TrustManager x509TrustManager;
  private final ProxySelector proxySelector;
  private final Authenticator authenticator;

  NetClientImpl(
      final @NotNull HostnameVerifier hostnameVerifier,
      final long timeoutMills,
      final @NotNull SSLSocketFactory sslSocketFactory,
      final @NotNull SocketFactory socketFactory,
      final @NotNull X509TrustManager x509TrustManager,
      final @NotNull ProxySelector proxySelector,
      final @NotNull Authenticator authenticator) {
    this.hostnameVerifier = hostnameVerifier;
    this.timeoutMills = timeoutMills;
    this.sslSocketFactory = sslSocketFactory;
    this.socketFactory = socketFactory;
    this.x509TrustManager = x509TrustManager;
    this.proxySelector = proxySelector;
    this.authenticator = authenticator;
  }

  @Override
  public @NotNull NetCall call(@NotNull Request request) {
    return new NetCallImpl(request, this);
  }

  static final class BuilderImpl implements Builder {

    private HostnameVerifier hostnameVerifier = UnsafeHostnameVerifier.INSTANCE;
    private long timeoutMills = TimeUnit.SECONDS.toMillis(30);
    private SSLSocketFactory sslSocketFactory;
    private SocketFactory socketFactory;
    private X509TrustManager x509TrustManager;
    private ProxySelector proxySelector;
    private Authenticator authenticator;

    @Override
    public Builder hostnameVerifier(@NotNull HostnameVerifier hostnameVerifier) {
      this.hostnameVerifier = hostnameVerifier;
      return this;
    }

    @Override
    public Builder timeout(long timeout) {
      return null;
    }

    @Override
    public Builder sslSocketFactory(@NotNull SSLSocketFactory sslSocketFactory,
        @NotNull X509TrustManager x509TrustManager) {
      return null;
    }

    @Override
    public Builder socketFactory(@NotNull SocketFactory socketFactory) {
      return null;
    }

    @Override
    public Builder sslSocketFactory(@NotNull SSLSocketFactory sslSocketFactory) {
      return null;
    }

    @Override
    public Builder authenticator(@NotNull Authenticator authenticator) {
      return null;
    }

    @Override
    public Builder proxySelector(@NotNull ProxySelector proxySelector) {
      return null;
    }

    @Override
    public NetClient build() {
      return null;
    }
  }


  private static final class UnsafeHostnameVerifier implements HostnameVerifier {

    static final UnsafeHostnameVerifier INSTANCE = new UnsafeHostnameVerifier();

    @Override
    public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  }

}
