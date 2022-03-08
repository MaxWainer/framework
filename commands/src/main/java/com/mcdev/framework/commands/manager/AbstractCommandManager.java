package com.mcdev.framework.commands.manager;

import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;

import com.mcdev.framework.commands.Command;
import com.mcdev.framework.commands.CommandException;
import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.scope.Scoped;
import com.mcdev.framework.commands.sender.Sender;
import com.mcdev.framework.commands.sender.SenderFactory;
import com.mcdev.framework.commands.style.CommandStyleConfig;
import com.mcdev.framework.locale.TranslationManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * @param <H> Sender's factory handler
 * @param <S> Sender factory
 * @param <B> Command base
 */
public abstract class AbstractCommandManager<H, S extends SenderFactory<H>, B>
    implements CommandManager<H, S> {

  private final S senderFactory;

  private final Map<String, Command> registeredCommands = new ConcurrentHashMap<>();

  protected AbstractCommandManager(final @NotNull S senderFactory) {
    this.senderFactory = senderFactory;

    new TranslationManager("commands");
  }

  @Override
  public boolean registerCommand(final @NotNull Command command) {
    if (command instanceof Scoped) {
      return false;
    }

    if (registeredCommands.containsKey(command.name())) {
      return false;
    }

    final WrappedCommand<H> wrappedCommand = wrapCommand(command);

    registerHandle(wrapToInternal(command.name(), wrappedCommand));
    registeredCommands.put(command.name(), command);
    return true;
  }

  @Override
  public @NotNull S senderFactory() {
    return senderFactory;
  }

  private @NotNull WrappedCommand<H> wrapCommand(final @NotNull Command command) {
    return new WrappedCommand<H>() {
      @Override
      public void execute(final @NotNull H sender, final @NotNull String[] args) {
        final Sender wrappedSender = senderFactory.wrapSender(sender);

        if (!command.canExecute(wrappedSender)) {
          return;
        }

        try {
          command.execute(wrappedSender, wrapArray(args));
        } catch (final CommandException exception) {
          wrappedSender.sendMessage(exception.errorMessage());
        } catch (final Throwable throwable) {
          final Component message =
              throwable.getMessage() == null ? Error.INTERNAL_NOT_PROVIDED
                  : text(throwable.getMessage());
          final Component stackTrace =
              join(JoinConfiguration.separator(newline()),
                  Stream.of(throwable.getStackTrace())
                      .map(element ->
                          text(element.toString())
                              .color(CommandStyleConfig.systemSoftErrorColor())
                      ).collect(Collectors.toSet()));

          wrappedSender
              .sendMessage(Error.INTERNAL.applyArguments(
                  message
                      .hoverEvent(HoverEvent.showText(stackTrace))
              ));

          throwable.printStackTrace();
        }
      }

      @Override
      public @NotNull @Unmodifiable List<String> suggestions(final @NotNull H sender,
          final @NotNull String[] args) {
        final Sender wrappedSender = senderFactory.wrapSender(sender);

        if (!command.canExecute(wrappedSender)) {
          return Collections.emptyList();
        }

        final List<String> suggestions =
            command.suggestions(wrappedSender, wrapArray(args));

        final int currentPosition = args.length - 1;

        return suggestions
            .stream()
            .filter(it -> it.startsWith(args[currentPosition]))
            .collect(Collectors.toList());
      }
    };
  }

  private static CommandContext wrapArray(final @NotNull String @NonNls [] args) {
    return new CommandContextImpl(
        new LinkedList<>(Arrays.asList(args))
    );
  }

  // Basic handlers

  protected abstract void registerHandle(final @NotNull B handle);

  protected abstract B wrapToInternal(final @NotNull String name, final @NotNull WrappedCommand<H> wrappedCommand);

  // Basic handlers

  protected interface WrappedCommand<V> {

    void execute(final @NotNull V sender, final @NotNull String[] args);

    @NotNull @Unmodifiable List<String> suggestions(final @NotNull V sender,
        final @NotNull String[] args);

  }

}
