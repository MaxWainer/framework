package com.mcdev.framework.commands.style;

import com.mcdev.framework.commands.style.CommandStyleConfigImpl.InitializerImpl;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public interface CommandStyleConfig {

  static CommandStyleConfig get() {
    return CommandStyleConfigImpl.INSTANCE;
  }

  static NamedTextColor systemPrimaryColor() {
    return get().primaryColor();
  }

  static NamedTextColor systemSecondaryColor() {
    return get().secondaryColor();
  }

  static NamedTextColor systemTipColor() {
    return get().tipColor();
  }

  static NamedTextColor systemWarnColor() {
    return get().warnColor();
  }

  static NamedTextColor systemErrorColor() {
    return get().errorColor();
  }

  static NamedTextColor systemSoftErrorColor() {
    return get().softErrorColor();
  }

  static CommandStyleConfig.Initializer initializer() {
    return new InitializerImpl();
  }

  @NotNull NamedTextColor primaryColor();

  @NotNull NamedTextColor secondaryColor();

  @NotNull NamedTextColor errorColor();

  @NotNull NamedTextColor softErrorColor();

  @NotNull NamedTextColor warnColor();

  @NotNull NamedTextColor tipColor();

  void initialize(final @NotNull Initializer initializer);

  interface Initializer {

    Initializer primaryColor(final @NotNull NamedTextColor color);

    Initializer secondaryColor(final @NotNull NamedTextColor color);

    Initializer errorColor(final @NotNull NamedTextColor color);

    Initializer warnColor(final @NotNull NamedTextColor color);

    Initializer tipColor(final @NotNull NamedTextColor color);

    Initializer softErrorColor(final @NotNull NamedTextColor color);

    @NotNull CommandStyleConfig toConfig();

  }

}
