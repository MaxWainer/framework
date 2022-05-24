package dev.framework.commons.net;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.regex.Pattern;

@UtilityClass
final class NetConstants {

  private NetConstants() {
    MoreExceptions.instantiationError();
  }

  static final String RAW_URL_PATTERN = "b(https?|ftp|file)://[-a-zA-Z\\d+&@#/%?=~_|!:,.;]*[-a-zA-Z\\d+&@#/%=~_|]";

  static final Pattern URL_PATTERN = Pattern.compile(RAW_URL_PATTERN);

}
