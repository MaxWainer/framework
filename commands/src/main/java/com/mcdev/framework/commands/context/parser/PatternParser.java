package com.mcdev.framework.commands.context.parser;

import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.context.ContextParser;
import com.mcdev.framework.commands.context.ParserResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

final class PatternParser implements ContextParser<String> {

  private final Pattern pattern;

  PatternParser(final @NotNull Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public @NotNull ParserResult<String> checkInput(final @NotNull String raw) {
    final Matcher matcher = pattern.matcher(raw);

    if (matcher.find()) {
      return ParserResult.success(raw);
    }

    return ParserResult.error(Error.PARSER_PATTERN.applyArguments(raw, pattern.pattern()));
  }

}
