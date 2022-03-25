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

package dev.framework.commands.context.parser;

import dev.framework.commands.CommandMessages.Error;
import dev.framework.commands.context.ContextParser;
import dev.framework.commands.context.ParserResult;
import org.jetbrains.annotations.NotNull;

abstract class AbstractNumberParser<T extends Number> implements ContextParser<T> {

  @Override
  public @NotNull ParserResult<T> checkInput(final @NotNull String raw) {
    final T number = parseNumber0(raw); // parsing number

    if (number != null) { // if not null
      return ParserResult.success(number); // success
    }

    // else error
    return ParserResult.error(Error.PARSER_NUMBER.applyArguments(raw));
  }


  protected T parseNumber0(final @NotNull String raw) {
    try {
      return parseNumber1(raw); // parse it
    } catch (final NumberFormatException ignored) { // if we have NumberFormatException, just ignore it
    }

    return null; // return null
  }

  protected abstract T parseNumber1(final @NotNull String raw); // number depend wrapper
}
