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

package dev.framework.commands.manager;

import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;

import dev.framework.commands.Command;
import dev.framework.commands.CommandException;
import dev.framework.commands.CommandMessages.Error;
import dev.framework.commands.context.CommandContext;
import dev.framework.commands.scope.Scoped;
import dev.framework.commands.sender.Sender;
import dev.framework.commands.sender.SenderFactory;
import dev.framework.commands.style.CommandStyleConfig;
import dev.framework.locale.TranslationManager;
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

  private static CommandContext wrapArray(final @NotNull String @NonNls [] args) {
    return new CommandContextImpl(
        new LinkedList<>(Arrays.asList(args))
    );
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
        final Sender wrappedSender = senderFactory.wrapSender(sender); // wrapping sender

        if (!command.canExecute(wrappedSender)) {
          return;
        }

        try {
          command.execute(wrappedSender, wrapArray(args)); // executing command from instance
        } catch (final CommandException exception) {
          wrappedSender.sendMessage(
              exception.errorMessage()); // sending message from commandexception
        } catch (final Throwable throwable) {
          // else we return error thrown wile executing command
          final Component message =
              throwable.getMessage() == null ? Error.INTERNAL_NOT_PROVIDED
                  : text(throwable.getMessage()); // wrap message to component
          final Component stackTrace =
              join(JoinConfiguration.separator(newline()), // join stacktrace to hover event
                  Stream.of(throwable.getStackTrace())
                      .map(element ->
                          text(element.toString())
                              .color(CommandStyleConfig.systemSoftErrorColor())
                      ).collect(Collectors.toSet()));

          wrappedSender
              .sendMessage(Error.INTERNAL.applyArguments(
                  message
                      .hoverEvent(HoverEvent.showText(stackTrace))
              )); // sending message

          throwable.printStackTrace(); // printing stacktrace
        }
      }

      @Override
      public @NotNull @Unmodifiable List<String> suggestions(final @NotNull H sender,
          final @NotNull String[] args) {
        final Sender wrappedSender = senderFactory.wrapSender(sender); // wrapping sender

        if (!command.canExecute(wrappedSender)) { // checking, does player can execute this command
          return Collections.emptyList(); // if no, return empty list
        }

        final List<String> suggestions =
            command.suggestions(wrappedSender, wrapArray(args)); // list suggestions

        final int currentPosition = args.length - 1; // getting latest argument

        return suggestions
            .stream()
            .filter(it -> it.startsWith(
                args[currentPosition])) // completing all suggestions basing on current position
            .collect(Collectors.toList());
      }
    };
  }

  // Basic handlers

  protected abstract void registerHandle(
      final @NotNull B handle); // register base command provider (Platform depend)

  protected abstract B wrapToInternal(final @NotNull String name,
      final @NotNull WrappedCommand<H> wrappedCommand); // wrapping to base command provider (Platform depend)

  // Basic handlers

  // wrapping interface
  protected interface WrappedCommand<V> {

    void execute(final @NotNull V sender, final @NotNull String[] args);

    @NotNull @Unmodifiable List<String> suggestions(final @NotNull V sender,
        final @NotNull String[] args);

  }

}
