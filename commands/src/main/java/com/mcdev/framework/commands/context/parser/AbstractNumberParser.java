package com.mcdev.framework.commands.context.parser;

import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.context.ContextParser;
import com.mcdev.framework.commands.context.ParserResult;
import org.jetbrains.annotations.NotNull;

abstract class AbstractNumberParser<T extends Number> implements ContextParser<T> {

  @Override
  public @NotNull ParserResult<T> checkInput(final @NotNull String raw) {
    final T number = parseNumber0(raw);

    if (number != null) {
      return ParserResult.success(number);
    }

    return ParserResult.error(Error.PARSER_NUMBER.applyArguments(raw));
  }


  protected T parseNumber0(final @NotNull String raw) {
    try {
      return parseNumber1(raw);
    } catch (final NumberFormatException ignored) {
    }

    return null;
  }

  protected abstract T parseNumber1(final @NotNull String raw);
}
