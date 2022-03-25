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

package dev.framework.bootstrap;

import com.google.inject.Binder;
import org.jetbrains.annotations.NotNull;

/**
 * Basic interface which provides main functional for basic processing, pre-configured bindings
 * ({@link BaseProcessable#preconfigure(Binder)}), startup logic ({@link BaseProcessable#load()}),
 * disabling logic ({@link BaseProcessable#unload()})
 */
public interface BaseProcessable {

  /**
   * Injection pre-configuration for bindings
   *
   * @param binder Guice binder, to provide some specific modules
   */
  default void preconfigure(final @NotNull Binder binder) {
  }

  /**
   * Startup logic
   */
  default void load() {
  }

  /**
   * Disabling logic
   */
  default void unload() {
  }

}
