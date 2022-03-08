package com.mcdev.framework.locale;

import java.util.Locale;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import org.jetbrains.annotations.NotNull;

public final class TranslationManager {

  private static final Locale RUSSIAN = new Locale("ru");

  public TranslationManager(final @NotNull String projectName) {
    final TranslationRegistry registry = TranslationRegistry.create(
        Key.key("mcdev", "main"));

    registry.defaultLocale(RUSSIAN);

    GlobalTranslator.get().addSource(registry);

//    final ResourceBundle resourceBundle = ResourceBundle.getBundle("mcdev_" + projectName, RUSSIAN,
//        UTF8ResourceBundleControl.get());
//
//    final ResourceBundle jsonBundle = new JsonResourceBundle("mcdev_" + projectName);

    registry.registerAll(RUSSIAN,
        new JsonResourceBundle("mcdev_" + projectName + '_' + RUSSIAN.getLanguage()), false);
  }
}
