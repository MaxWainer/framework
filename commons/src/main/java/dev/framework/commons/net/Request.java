package dev.framework.commons.net;

import dev.framework.commons.Buildable;
import dev.framework.commons.exception.NotImplementedYet;
import dev.framework.commons.net.type.RequestType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.intellij.lang.annotations.Pattern;
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

    Builder contentLength(final int contentLength);

  }

}
