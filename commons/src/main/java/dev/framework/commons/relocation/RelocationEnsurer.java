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

package dev.framework.commons.relocation;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.TraceExposer;
import dev.framework.commons.annotation.UtilityClass;

@UtilityClass
public final class RelocationEnsurer {

  private RelocationEnsurer() {
    MoreExceptions.instantiationError();
  }

  public static void ensureRelocation(final char ...pkg) {
    final String basicPackage = new String(
        pkg
    ); // avoid relocation

    final String checkingClassName = TraceExposer.callerClassName();

    if (basicPackage.startsWith(checkingClassName)) {
      MoreExceptions.nagAuthor(checkingClassName
          + " is not relocated, this part of framework is very sensitive to it, suggesting to nag plugin developer and resolve issue! "
          + "To author: Add relocation to entire framework dependencies, if someone gonna use it not the same version, this may produce silly issues!");
    }
  }

}
