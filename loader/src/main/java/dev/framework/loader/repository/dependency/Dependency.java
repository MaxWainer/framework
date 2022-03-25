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

package dev.framework.loader.repository.dependency;

import org.jetbrains.annotations.NotNull;

public final class Dependency {

  private final String repository;
  private final String groupId;
  private final String artifactId;
  private final String version;

  private Dependency(
      final @NotNull String repository,
      final @NotNull String groupId,
      final @NotNull String artifactId,
      final @NotNull String version) {
    this.repository = repository;
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
  }

  public static @NotNull Dependency of(final @NotNull String value)
      throws MalformatedDependencyException {
    try {
      final String[] data = value.split(":");

      return new Dependency(data[0], data[1], data[2], data[3]);
    } catch (final IndexOutOfBoundsException ignored) {
      // Ignore this exception
    }

    throw new MalformatedDependencyException(
        "Entered value is not dependency " + value
            + ", example: <repository>:com.example:example:1.0");
  }

  @NotNull
  public String repository() {
    return this.repository;
  }

  @NotNull
  public String version() {
    return this.version;
  }

  @NotNull
  public String artifactId() {
    return this.artifactId;
  }

  @NotNull
  public String groupId() {
    return this.groupId;
  }

  @Override
  public String toString() {
    return "Dependency{" +
        "repository='" + this.repository + '\'' +
        ", groupId='" + this.groupId + '\'' +
        ", artifactId='" + this.artifactId + '\'' +
        ", version='" + this.version + '\'' +
        '}';
  }

  public static final class MalformatedDependencyException extends RuntimeException {

    public MalformatedDependencyException(final @NotNull String message) {
      super(message);
    }

  }

}
