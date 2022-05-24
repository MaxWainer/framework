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
