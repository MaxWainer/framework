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

  @Override
  public int compareTo(@NotNull Version o) {
    return asString().compareTo(o.asString());
  }

  @Override
  public String toString() {
    return asString();
  }
}
