package dev.framework.commons.version;

import org.jetbrains.annotations.NotNull;

final class VersionImpl implements Version {

  private final int major, minor, revision;

  VersionImpl(int major, int minor, int revision) {
    this.major = major;
    this.minor = minor;
    this.revision = revision;
  }

  @Override
  public int major() {
    return major;
  }

  @Override
  public int minor() {
    return minor;
  }

  @Override
  public int revision() {
    return revision;
  }

  @Override
  public @NotNull String asString() {
    return String.format("%s.%s.%s", major, minor, revision);
  }

  private int versionSum() {
    return Integer.parseInt(
        String.valueOf(major) + minor + revision
    );
  }

  @Override
  public int compareTo(@NotNull Version o) {
    if (!(o instanceof VersionImpl)) return -1;
    final VersionImpl oImp = (VersionImpl) o;

    return Integer.compare(versionSum(), oImp.versionSum());
  }
}
