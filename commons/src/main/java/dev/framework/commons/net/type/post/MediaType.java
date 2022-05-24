package dev.framework.commons.net.type.post;

import java.nio.charset.Charset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MediaType {

  public static @NotNull MediaType create(final @NotNull String type,
      final @NotNull String subtype,
      final @NotNull Charset charset) {
    return new MediaType(type, subtype, charset);
  }

  public static @NotNull MediaType create(
      final @NotNull String type,
      final @NotNull String subtype) {
    return new MediaType(type, subtype);
  }

  private final String type;
  private final String subtype;
  private final Charset charset;

  MediaType(
      final @NotNull String type,
      final @NotNull String subtype,
      final @Nullable Charset charset) {
    this.type = type;
    this.subtype = subtype;
    this.charset = charset;
  }

  MediaType(
      final @NotNull String type,
      final @NotNull String subtype) {
    this(type, subtype, null);
  }

  @Override
  public String toString() {
    return String.format("%s/%s", type, subtype) + (charset == null ? "" : "; charset=" + charset);
  }
}
