package com.mcdev.framework.commands;

import static com.mcdev.framework.commands.style.CommandStyleConfig.systemSecondaryColor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

import com.mcdev.framework.commands.schema.Argument;
import com.mcdev.framework.commands.schema.ArgumentSchema;
import com.mcdev.framework.commands.schema.ArgumentSchemaHolder;
import com.mcdev.framework.commands.scope.AbstractScopedParentCommand;
import com.mcdev.framework.commands.style.CommandStyleConfig;
import com.mcdev.framework.locale.MessageMapper;
import java.util.function.Function;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;

public final class CommandMessages {

  private CommandMessages() {
    throw new AssertionError();
  }

  public static final class HelpTopic {

    private HelpTopic() {
      throw new AssertionError();
    }

    public static final MessageMapper HEADER = args ->
        key("header")
            .args(remapString(0, args))
            .asComponent();

    public static final TranslatableComponent HEADER_DESCRIPTION = key("header.description");

    public static final MessageMapper DESCRIPTION_PLACEHOLDER = args ->
        key("header.description.placeholder")
            .args((ComponentLike) args[0])
            .asComponent();

    public static final TranslatableComponent HEADER_HELP = key("header.help");

    public static final TranslatableComponent PLACEHOLDER_COMMAND_FORMAT_HAS_SUB_ARGS = key(
        "placeholder.command.format.has.sub.args");

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
        if (command instanceof AbstractScopedParentCommand) {
          appendix
              .append(((AbstractScopedParentCommand<?>) command).specificAppendix());
        }

        if (!command.childCommands().isEmpty()) {
          appendix.append(PLACEHOLDER_COMMAND_FORMAT_HAS_SUB_ARGS);
        }
      }

      return key("placeholder.command.format")
          .args(
              text(command.name(), systemSecondaryColor()),
              appendix
          );
    };

    public static final MessageMapper PLACEHOLDER_COMMAND = args -> {
      final TranslatableComponent component = key("placeholder.command");

      final Component command = (Component) args[0];
      final Component description = (Component) args[1];

      return component
          .args(command, description);
    };

    public static final TranslatableComponent ARGUMENT = key("argument");

    public static final MessageMapper HEADER_SCOPED = args ->
        key("header.scoped")
            .asComponent();

    private static TranslatableComponent key(final @NotNull String key) {
      return CommandMessages.key("help.topic." + key);
    }
  }

  public static final class Error {

    private Error() {
      throw new AssertionError();
    }

    public static final MessageMapper PARSER_NUMBER = args ->
        error().append(key("parser.number").args(remapString(0, args)));

    public static final MessageMapper PARSER_NUMBER_OUT_OF_RANGE = args ->
        error().append(
            key("parser.number.out.of.range")
                .args(
                    remapString(0, args),
                    remapString(1, args),
                    remapString(2, args)
                )
        );

    public static final MessageMapper PARSER_BOOLEAN = args ->
        error().append(key("parser.boolean").args(remapString(0, args)));

    public static final MessageMapper PARSER_PATTERN = args ->
        error().append(key("parser.pattern").args(remapString(0, args), remapString(1, args)));

    public static final MessageMapper PARSER_STRING = args ->
        error().append(
            key("parser.string")
                .args(remapString(0, args), remapString(1, args))
        );

    public static final TranslatableComponent UNUSABLE = error().append(key("unusable"));

    public static final MessageMapper INTERNAL = args -> error()
        .append(
            key("internal")
                .args(
                    ((ComponentLike) args[0])
                        .asComponent()
                        .color(CommandStyleConfig.systemErrorColor())
                )
        );

    public static final TranslatableComponent INTERNAL_NOT_PROVIDED = key("internal.not.provided");

    public static final MessageMapper UNKNOWN_VALUE = args -> error()
        .append(
            key("unknown.value")
                .args(
                    remapString(0, args)
                )
        );

    public static final MessageMapper MISSING_PARAMETER = args -> error()
        .append(
            key("missing.parameter")
                .args(
                    remapString(0, args)
                )
        );

    private static TranslatableComponent key(final @NotNull String key) {
      return CommandMessages
          .key("error." + key)
          .color(CommandStyleConfig.systemSoftErrorColor());
    }
  }

  public static final class Format {

    private Format() {
      throw new AssertionError();
    }

    public static final MessageMapper ARGUMENT_OPTIONAL = args ->
        key("argument.optional")
            .args(
                remapString(0, args)
                    .hoverEvent(HoverEvent.showText(remapString(1, args)))
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
          .args(
              remapString(0, args)
                  .hoverEvent(HoverEvent.showText(first))
          )
          .asComponent();
    };

    public static final TranslatableComponent ALIAS_DELIMITER = key("argument.alias.delimiter");

    private static TranslatableComponent key(final @NotNull String key) {
      return CommandMessages.key("format." + key);
    }
  }

  // prefixes

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

  public static TranslatableComponent tip() {
    return prefix("tip")
        .color(CommandStyleConfig.systemTipColor());
  }

  // basic utilities

  private static TranslatableComponent key(final @NotNull String key) {
    return translatable("mcdev.command." + key)
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

}
