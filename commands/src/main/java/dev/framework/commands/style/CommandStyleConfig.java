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

import dev.framework.commands.style.CommandStyleConfigImpl.InitializerImpl;
import java.util.Properties;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public interface CommandStyleConfig {

  static CommandStyleConfig get() {
    return CommandStyleConfigImpl.INSTANCE;
  }

  static void updateFromProperties(final @NotNull Properties properties) {
    get().initialize(initializer().fromProperties(properties));
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

  static NamedTextColor systemDelimiterColor() {
    return get().delimiterColor();
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

  @NotNull NamedTextColor delimiterColor();

  void initialize(final @NotNull Initializer initializer);

  interface Initializer {

    Initializer primaryColor(final @NotNull NamedTextColor color);

    Initializer secondaryColor(final @NotNull NamedTextColor color);

    Initializer errorColor(final @NotNull NamedTextColor color);

    Initializer warnColor(final @NotNull NamedTextColor color);

    Initializer tipColor(final @NotNull NamedTextColor color);

    Initializer softErrorColor(final @NotNull NamedTextColor color);

    Initializer delimiterColor(final @NotNull NamedTextColor color);

    Initializer fromProperties(final @NotNull Properties properties);

    @NotNull CommandStyleConfig toConfig();

  }

}
