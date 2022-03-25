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

package dev.framework.packet.wrapper.packet.out;

import dev.framework.packet.wrapper.packet.WindowPacket;
import dev.framework.packet.wrapper.packet.bound.OutPacket;
import org.jetbrains.annotations.NotNull;

public final class WrappedOutWindowOpenPacket implements OutPacket, WindowPacket {

  private final int windowId;
  private final WindowType windowType;
  private final String title;
  private final int rows;

  public WrappedOutWindowOpenPacket(
      final int windowId,
      final @NotNull WindowType windowType,
      final @NotNull String title,
      final int rows) {
    this.windowId = windowId;
    this.windowType = windowType;
    this.title = title;
    this.rows = rows;
  }

  @NotNull
  public WindowType windowType() {
    return windowType;
  }

  @NotNull
  public String title() {
    return title;
  }

  public int rows() {
    return rows;
  }

  @Override
  public int windowId() {
    return windowId;
  }

  @Override
  public String toString() {
    return "WrappedOutWindowOpenPacket{" +
        "windowId=" + windowId +
        ", windowType=" + windowType +
        ", title='" + title + '\'' +
        ", rows=" + rows +
        '}';
  }

  public enum WindowType {
    CHEST
  }
}
