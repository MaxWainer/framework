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

package dev.framework.commands;

import static dev.framework.commands.style.CommandStyleConfig.systemSecondaryColor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

import dev.framework.commands.schema.Argument;
import dev.framework.commands.schema.ArgumentSchema;
import dev.framework.commands.schema.ArgumentSchemaHolder;
import dev.framework.commands.scope.AbstractScopedParentCommand;
import dev.framework.commands.style.CommandStyleConfig;
import dev.framework.commons.Exceptions;
import dev.framework.locale.MessageMapper;
import java.util.function.Function;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;

public final class CommandMessages {

  private CommandMessages() {
    Exceptions.instantiationError();
  }

  private static TranslatableComponent prefix(final @NotNull String type) {
    return key("prefix." + type);
  }

  public static TranslatableComponent error() {
    return prefix("error")
        .color(CommandStyleConfig.systemErrorColor());
  }

  public static TranslatableComponent warn() {
    return prefix("warn")
        .color(CommandStyleConfig.systemWarnColor());
  }

  // prefixes

  public static TranslatableComponent tip() {
    return prefix("tip")
        .color(CommandStyleConfig.systemTipColor());
  }

  private static TranslatableComponent key(final @NotNull String key) {
    return translatable("command." + key)
        .color(CommandStyleConfig.systemPrimaryColor());
  }

  private static <V> Component remapArgument(
      final int index,
      final @NotNull Function<V, Component> mapper,
      final @NotNull Object... args) {
    return mapper.apply((V) args[index])
        .color(systemSecondaryColor());
  }

  private static Component remapString(
      final int index,
      final @NotNull Object... args) {
    return remapArgument(index, string -> text((String) string), args);
  }

  // basic utilities

  public static final class HelpTopic {

    public static final MessageMapper HEADER = args ->
        key("header")
            .args(remapString(0, args))
            .asComponent();
    public static final TranslatableComponent FOOTER_CENTER = key("footer.center")
        .color(CommandStyleConfig.systemSecondaryColor());
    public static final TranslatableComponent FOOTER =
        key("footer")
            .args(FOOTER_CENTER);
    public static final TranslatableComponent HEADER_DESCRIPTION = key("header.description");
    public static final MessageMapper DESCRIPTION_PLACEHOLDER = args ->
        key("header.description.placeholder")
            .args(Format.DELIMITER_HORIZONTAL, (ComponentLike) args[0])
            .asComponent();
    public static final TranslatableComponent HEADER_HELP = key("header.help");
    public static final TranslatableComponent HAS_SUB_ARGS_DESCRIPTION = key(
        "placeholder.command.format.has.sub.args.description");
    public static final TranslatableComponent PLACEHOLDER_COMMAND_FORMAT_HAS_SUB_ARGS =
        key("placeholder.command.format.has.sub.args")
            .hoverEvent(HoverEvent.showText(HAS_SUB_ARGS_DESCRIPTION))
            .color(CommandStyleConfig.systemDelimiterColor());
    public static final MessageMapper PLACEHOLDER_COMMAND_FORMAT = args -> {
      final Command command = (Command) args[0];

      final TextComponent.Builder appendix = text();
      if (command instanceof ArgumentSchemaHolder) {
        final ArgumentSchema argumentSchema = ((ArgumentSchemaHolder) command).argumentSchema();

        for (final Argument argument : argumentSchema.arguments()) {
          appendix.append(
              argument.optional()
                  ? Format.ARGUMENT_OPTIONAL.applyArguments(argument.name(), argument.description())
                  : Format.ARGUMENT_REQUIRED.applyArguments(argument.name(), argument.description())
          );
        }
      } else {
        if (command instanceof AbstractScopedParentCommand) { // if our command is scoped
          appendix
              .append(
                  ((AbstractScopedParentCommand<?>) command).specificAppendix()); // return appendix
        }

        if (!command.childCommands().isEmpty()) {
          appendix.append(
              PLACEHOLDER_COMMAND_FORMAT_HAS_SUB_ARGS); // if command have children, append suffix that it's not end
        }
      }

      return key("placeholder.command.format")
          .args(
              text(command.name(), systemSecondaryColor()), // append command name
              appendix
          );
    };
    public static final MessageMapper PLACEHOLDER_COMMAND = args -> {
      final TranslatableComponent component = key("placeholder.command");

      final Component command = (Component) args[0]; // get command
      final Component description = (Component) args[1]; // get description

      return component
          .args(Format.DELIMITER_HORIZONTAL, command, Format.DELIMITER_HORIZONTAL, description);
    };

    private HelpTopic() {
      Exceptions.instantiationError();
    }

    private static TranslatableComponent key(final @NotNull String key) {
      return CommandMessages.key("help.topic." + key);
    }
  }

  public static final class Error {

    public static final MessageMapper PARSER_NUMBER = args -> error().append(
        key("parser.number").args(
            remapString(0, args)));
    public static final MessageMapper PARSER_NUMBER_OUT_OF_RANGE = args ->
        error().append(key("parser.number.out.of.range")
            .args(
                remapString(0, args), // input
                remapString(1, args), // from
                remapString(2, args) // to
            ));
    public static final MessageMapper PARSER_BOOLEAN = args ->
        error().append(key("parser.boolean").args(remapString(0, args))); // arg0 = input
    public static final MessageMapper PARSER_PATTERN = args ->
        error().append(key("parser.pattern").args(remapString(0, args),
            remapString(1, args))); // arg0 = input, arg1 = pattern
    public static final MessageMapper PARSER_STRING = args ->
        error().append(key("parser.string")
            .args(remapString(0, args),
                remapString(1, args))); // arg0 = input, arg1 = all allowed strings
    public static final TranslatableComponent UNUSABLE = key("unusable");
    public static final MessageMapper INTERNAL = args ->
        error().append(key("internal")
            .args(
                ((ComponentLike) args[0]) // error message
                    .asComponent()
                    .color(CommandStyleConfig.systemErrorColor())
            ));
    public static final TranslatableComponent INTERNAL_NOT_PROVIDED = error().append(
        key("internal.not.provided"));
    public static final MessageMapper UNKNOWN_VALUE = args ->
        error().append(key("unknown.value")
            .args(
                remapString(0, args) // arg0 = input
            ));
    public static final TranslatableComponent VALUE_NOT_PROVIDED = key("value.not.provided");
    public static final MessageMapper MISSING_PARAMETER = args ->
        error().append(key("missing.parameter")
            .args(
                remapString(0, args) // arg0 = param name
            ));
    public static final TranslatableComponent FULL_UNUSABLE = key("full.unusable");

    private Error() {
      Exceptions.instantiationError();
    }

    private static TranslatableComponent key(final @NotNull String key) {
      return CommandMessages
          .key("error." + key)
          .color(CommandStyleConfig.systemSoftErrorColor());
    }
  }

  public static final class Format {

    public static final MessageMapper ARGUMENT_OPTIONAL = args ->
        key("argument.optional")
            .color(CommandStyleConfig.systemDelimiterColor())
            .args(
                remapString(0, args)
                    .color(CommandStyleConfig.systemPrimaryColor())
                    .hoverEvent(HoverEvent.showText(
                        remapString(1, args).color(CommandStyleConfig.systemPrimaryColor())))
                // arg0 = arg name
            )
            .asComponent();
    public static final MessageMapper ARGUMENT_REQUIRED = args -> {
      final Object rawFirst = args[1];

      final Component first;
      if (rawFirst instanceof Component) {
        first = (Component) rawFirst;
      } else {
        first = remapString(1, args);
      }

      return key("argument.required")
          .color(CommandStyleConfig.systemDelimiterColor())
          .args(
              remapString(0, args)
                  .color(CommandStyleConfig.systemPrimaryColor())
                  .hoverEvent(
                      HoverEvent.showText(first.color(CommandStyleConfig.systemPrimaryColor())))
          )
          .asComponent();
    };
    public static final TranslatableComponent DELIMITER_HORIZONTAL = key(
        "delimiter.horizontal").color(CommandStyleConfig.systemDelimiterColor());

    private Format() {
      Exceptions.instantiationError();
    }

    private static TranslatableComponent key(final @NotNull String key) {
      return CommandMessages.key("format." + key);
    }
  }

}
