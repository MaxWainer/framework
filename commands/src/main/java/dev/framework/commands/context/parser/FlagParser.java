package dev.framework.commands.context.parser;

import dev.framework.commands.context.ContextParser;
import dev.framework.commands.context.ParserResult;
import org.jetbrains.annotations.NotNull;

final class FlagParser implements ContextParser<Boolean> {

  private final String flag;

  FlagParser(final @NotNull String flag) {
    this.flag = flag;
  }

  @Override
  public @NotNull ParserResult<Boolean> checkInput(@NotNull String raw) {
    return ParserResult.success(raw.equals(flag));
  }
}
