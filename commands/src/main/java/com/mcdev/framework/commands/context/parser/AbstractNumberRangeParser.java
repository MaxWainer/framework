package com.mcdev.framework.commands.context.parser;

import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.context.ParserResult;
import org.jetbrains.annotations.NotNull;

abstract class AbstractNumberRangeParser<T extends Number> extends AbstractNumberParser<T> {

  private final T from, to;

  protected AbstractNumberRangeParser(final @NotNull T from, final @NotNull T to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public @NotNull ParserResult<T> checkInput(final @NotNull String raw) {
    final T number = parseNumber0(raw);

    if (number != null) {
      if (!checkNumbers(number)) {
        return ParserResult.error(
            Error.PARSER_NUMBER_OUT_OF_RANGE.applyArguments(raw, from.toString(), to.toString()));
      }

      return ParserResult.success(number);
    }

    return ParserResult.error(Error.PARSER_NUMBER.applyArguments(raw));
  }

  protected abstract boolean checkNumbers(final @NotNull T inputNumber);

}
