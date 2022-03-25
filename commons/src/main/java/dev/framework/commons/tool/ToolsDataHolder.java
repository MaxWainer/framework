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

package dev.framework.commons.tool;

import java.util.Optional;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ToolsDataHolder {

  @Nullable ToolData getToolData(final @NotNull String name);

  default @NotNull Optional<ToolData> getToolDataSafe(final @NotNull String name) {
    return Optional.ofNullable(getToolData(name));
  }

  default @NotNull ToolData getOrCreateToolData(final @NotNull String name) {
    final ToolData toolData = getToolData(name);

    return toolData == null ? addToolData(name, ToolData.create()) : toolData;
  }

  @NotNull ToolData addToolData(
      final @NotNull String name,
      final @NotNull ToolData data);

  void modifyToolData(
      final @NotNull String name,
      final @NotNull UnaryOperator<ToolData> operator);

}