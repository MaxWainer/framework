package com.mcdev.framework.commands.context.parser;

import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.context.ContextParser;
import com.mcdev.framework.commands.context.ParserResult;
import java.util.List;
import org.jetbrains.annotations.NotNull;

final class ExactStringsParser implements ContextParser<String> {

  private final List<String> strings;

  ExactStringsParser(final @NotNull List<String> strings) {
    this.strings = strings;
  }

  @Override
  public @NotNull ParserResult<String> checkInput(final @NotNull String raw) {
    return strings.contains(raw) ? ParserResult.success(raw) : ParserResult.error(
        Error.PARSER_STRING.applyArguments(raw, String.join(", ", strings))
    );
  }

}
