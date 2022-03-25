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

package dev.framework.commands.schema;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class ArgumentSchemaImpl implements ArgumentSchema {

  public static final ArgumentSchema EMPTY = new ArgumentSchemaImpl(new HashSet<>());

  private final Set<Argument> targets;

  ArgumentSchemaImpl(final @NotNull Set<Argument> targets) {
    this.targets = targets;
  }

  @Override
  public @NotNull Set<Argument> arguments() {
    return targets;
  }

  static final class BuilderImpl implements Builder {

    private final Set<Argument> targets = new LinkedHashSet<>();

    @Override
    public Builder assertArgument(final @NotNull Argument argument) {
      this.targets.add(argument);
      return this;
    }

    @Override
    public ArgumentSchema build() {
      return new ArgumentSchemaImpl(targets);
    }
  }

}
