package dev.framework.commons.net;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.Language;

@UtilityClass
final class NetConstants {

  private NetConstants() {
    MoreExceptions.instantiationError();
  }

  // https://stackoverflow.com/questions/32613720/java-regex-for-url
  @Language("RegExp")
  static final String RAW_URL_PATTERN = "^(http://|https://)?(www.)?([a-zA-Z\\d]+).[a-zA-Z\\d]*.[a-z]{3}.?([a-z]+)?(/[a-z\\d])*(/?|(\\?[a-z\\d]=[a-z\\d](&[a-z\\d]=[a-z\\d]*)?))$";

  static final Pattern URL_PATTERN = Pattern.compile(RAW_URL_PATTERN);

}
