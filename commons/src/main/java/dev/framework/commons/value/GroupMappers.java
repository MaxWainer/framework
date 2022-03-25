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

package dev.framework.commons.value;

public interface GroupMappers {

  static <T extends Number> GroupMapper<T> createRussianNumber() {
    return (group, wrapped) -> {
      final long
          number = wrapped.longValue(), // getting actual number
          closed = number % 10L; // closing it

      final String grouped =
          // if closed is zero or bigger or equals to 5, or number higher than 11 and lower that 20
          (closed == 0 || closed >= 5 || (number >= 11L && number <= 20L))
              ? group.empty() // we return empty group
              : ((closed == 1) // if closed is 1
                  ? group.firstGroup()    // we return first group
                  : group.secondGroup()); // else second

      return String.format("%s %s", number, grouped); // formatting it
    };
  }

}
