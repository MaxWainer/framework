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

package dev.framework.commands.style;

import java.util.Properties;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

// do I actually need describe it? :D
final class CommandStyleConfigImpl implements CommandStyleConfig {

  public static final CommandStyleConfig INSTANCE =
      new CommandStyleConfigImpl(
          NamedTextColor.GRAY,
          NamedTextColor.YELLOW,
          NamedTextColor.DARK_RED,
          NamedTextColor.GOLD,
          NamedTextColor.DARK_AQUA,
          NamedTextColor.RED,
          NamedTextColor.DARK_GRAY);

  private NamedTextColor primaryColor;
  private NamedTextColor secondaryColor;
  private NamedTextColor errorColor;
  private NamedTextColor warnColor;
  private NamedTextColor tipColor;
  private NamedTextColor softErrorColor;
  private NamedTextColor delimiterColor;

  CommandStyleConfigImpl(
      final @NotNull NamedTextColor primaryColor,
      final @NotNull NamedTextColor secondaryColor,
      final @NotNull NamedTextColor errorColor,
      final @NotNull NamedTextColor warnColor,
      final @NotNull NamedTextColor tipColor,
      final @NotNull NamedTextColor softErrorColor,
      final @NotNull NamedTextColor delimiterColor) {
    this.primaryColor = primaryColor;
    this.secondaryColor = secondaryColor;
    this.errorColor = errorColor;
    this.warnColor = warnColor;
    this.tipColor = tipColor;
    this.softErrorColor = softErrorColor;
    this.delimiterColor = delimiterColor;
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
  public @NotNull NamedTextColor delimiterColor() {
    return delimiterColor;
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
    private NamedTextColor delimiterColor;

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
    public Initializer delimiterColor(final @NotNull NamedTextColor color) {
      this.delimiterColor = color;
      return this;
    }

    @Override
    public Initializer fromProperties(final @NotNull Properties properties) {
      throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public @NotNull CommandStyleConfig toConfig() {
      return new CommandStyleConfigImpl(
          primaryColor,
          secondaryColor,
          errorColor,
          warnColor,
          tipColor,
          softErrorColor,
          delimiterColor);
    }
  }

}
