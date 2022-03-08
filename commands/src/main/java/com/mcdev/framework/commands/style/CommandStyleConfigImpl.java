package com.mcdev.framework.commands.style;

import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

final class CommandStyleConfigImpl implements CommandStyleConfig {

  public static final CommandStyleConfig INSTANCE =
      new CommandStyleConfigImpl(
          NamedTextColor.WHITE,
          NamedTextColor.YELLOW,
          NamedTextColor.DARK_RED,
          NamedTextColor.GOLD,
          NamedTextColor.DARK_AQUA,
          NamedTextColor.RED);

  private NamedTextColor primaryColor;
  private NamedTextColor secondaryColor;
  private NamedTextColor errorColor;
  private NamedTextColor warnColor;
  private NamedTextColor tipColor;
  private NamedTextColor softErrorColor;

  CommandStyleConfigImpl(
      final @NotNull NamedTextColor primaryColor,
      final @NotNull NamedTextColor secondaryColor,
      final @NotNull NamedTextColor errorColor,
      final @NotNull NamedTextColor warnColor,
      final @NotNull NamedTextColor tipColor,
      final @NotNull NamedTextColor softErrorColor) {
    this.primaryColor = primaryColor;
    this.secondaryColor = secondaryColor;
    this.errorColor = errorColor;
    this.warnColor = warnColor;
    this.tipColor = tipColor;
    this.softErrorColor = softErrorColor;
  }

  @Override
  public @NotNull NamedTextColor primaryColor() {
    return primaryColor;
  }

  @Override
  public @NotNull NamedTextColor secondaryColor() {
    return secondaryColor;
  }

  @Override
  public @NotNull NamedTextColor errorColor() {
    return errorColor;
  }

  @Override
  public @NotNull NamedTextColor softErrorColor() {
    return softErrorColor;
  }

  @Override
  public @NotNull NamedTextColor warnColor() {
    return warnColor;
  }

  @Override
  public @NotNull NamedTextColor tipColor() {
    return tipColor;
  }

  @Override
  public void initialize(final @NotNull Initializer initializer) {
    final CommandStyleConfig config = initializer.toConfig();

    this.primaryColor = config.primaryColor();
    this.secondaryColor = config.primaryColor();
  }

  static final class InitializerImpl implements Initializer {

    private NamedTextColor primaryColor;
    private NamedTextColor secondaryColor;
    private NamedTextColor errorColor;
    private NamedTextColor warnColor;
    private NamedTextColor tipColor;
    private NamedTextColor softErrorColor;

    @Override
    public Initializer primaryColor(final @NotNull NamedTextColor color) {
      this.primaryColor = color;
      return this;
    }

    @Override
    public Initializer secondaryColor(final @NotNull NamedTextColor color) {
      this.secondaryColor = color;
      return this;
    }

    @Override
    public Initializer errorColor(final @NotNull NamedTextColor color) {
      this.errorColor = color;
      return this;
    }

    @Override
    public Initializer warnColor(final @NotNull NamedTextColor color) {
      this.warnColor = color;
      return this;
    }

    @Override
    public Initializer tipColor(final @NotNull NamedTextColor color) {
      this.tipColor = color;
      return this;
    }

    @Override
    public Initializer softErrorColor(final @NotNull NamedTextColor color) {
      this.softErrorColor = color;
      return this;
    }

    @Override
    public @NotNull CommandStyleConfig toConfig() {
      return new CommandStyleConfigImpl(
          primaryColor,
          secondaryColor,
          errorColor,
          warnColor,
          tipColor,
          softErrorColor);
    }
  }

}
