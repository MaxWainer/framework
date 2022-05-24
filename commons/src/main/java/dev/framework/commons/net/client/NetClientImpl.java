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
