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

package dev.framework.bukkit.commons;

import static java.lang.Math.max;
import static java.lang.Math.min;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class Cuboid {

  private final Location firstLocation;
  private final Location secondLocation;

  private final int minX, minY, minZ, maxX, maxY, maxZ;

  public Cuboid(final @NotNull Location firstLocation, final @NotNull Location secondLocation) {
    this.firstLocation = firstLocation;
    this.secondLocation = secondLocation;

    if (!firstLocation.getWorld().equals(secondLocation.getWorld())) {
      throw new UnsupportedOperationException();
    }

    this.minX = min(firstLocation.getBlockX(), secondLocation.getBlockX());
    this.minY = min(firstLocation.getBlockY(), secondLocation.getBlockY());
    this.minZ = min(firstLocation.getBlockZ(), secondLocation.getBlockZ());

    this.maxX = max(firstLocation.getBlockX(), secondLocation.getBlockX());
    this.maxY = max(firstLocation.getBlockY(), secondLocation.getBlockY());
    this.maxZ = max(firstLocation.getBlockZ(), secondLocation.getBlockZ());
  }

  @NotNull
  public Location secondLocation() {
    return secondLocation;
  }

  @NotNull
  public Location firstLocation() {
    return firstLocation;
  }

  public boolean clashesWith(final @NotNull Cuboid other) {
    final boolean first = in(other.firstLocation);
    final boolean second = in(other.secondLocation);

    return !first && !second;
  }

  public boolean in(final @NotNull Location location) {
    final int
        x = location.getBlockX(),
        y = location.getBlockY(),
        z = location.getBlockZ();

    return
        x >= this.minX && x <= this.maxX
            && y >= this.minY && y <= this.maxY
            && z >= this.minZ && z <= this.maxZ;
  }

  @Override
  public String toString() {
    return "[" +
        "minX=" + minX +
        ", minY=" + minY +
        ", minZ=" + minZ +
        ", maxX=" + maxX +
        ", maxY=" + maxY +
        ", maxZ=" + maxZ +
        ']';
  }
}
