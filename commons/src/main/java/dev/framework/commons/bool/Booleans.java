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

package dev.framework.commons.bool;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.Nulls;
import dev.framework.commons.annotation.UtilityClass;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Booleans {

  public static final List<String> ALLOWED_STRINGS = Arrays.asList("true", "false", "no", "yes",
      "1", "0"); // all our allowed boolean strings
  private static final Map<String, Boolean> MAPPERS = new HashMap<>();

  static {
    MAPPERS.put("true", true);
    MAPPERS.put("false", false);
    MAPPERS.put("yes", true);
    MAPPERS.put("no", false);
    MAPPERS.put("1", true);
    MAPPERS.put("0", false);
  }

  private Booleans() {
    MoreExceptions.instantiationError();
  }

  public static Boolean parseBooleanStrict(final @NotNull String input) {
    Nulls.isNull(input, "input");

    final Boolean result = MAPPERS.get(input); // getting from mappers our input

    if (result == null) { // if null, we throw exception
      throw new BooleanFormatException();
    }

    return result; // else result
  }

}
