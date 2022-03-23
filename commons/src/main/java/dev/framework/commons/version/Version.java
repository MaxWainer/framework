package dev.framework.commons.version;

import org.jetbrains.annotations.NotNull;

public interface Version extends Comparable<Version> {

  static Version of(final int major, final int minor, final int revision) {
    return new VersionImpl(major, minor, revision);
  }

  int major();

  int minor();

  int revision();

  @NotNull String asString();

}
