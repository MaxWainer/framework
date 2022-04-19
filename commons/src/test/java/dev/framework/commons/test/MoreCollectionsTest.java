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

package dev.framework.commons.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.framework.commons.collection.MoreCollections;
import dev.framework.commons.collection.xy.XYCollection;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MoreCollectionsTest {

  @Test
  void duplicateTest() {
    final List<SomeObject> objects = new ArrayList<>();

    objects.add(new SomeObject("one"));
    objects.add(new SomeObject("one"));
    objects.add(new SomeObject("two"));

    assertTrue(MoreCollections.hasDuplicates(objects, it -> it.primitive));
  }

  @Test
  void xyCollection() {
    final XYCollection<String> collection = MoreCollections.xyCollection(5, 10);

    // absolute should be: 10 * 3(30) + 4 = 34
    collection.insert(3, 4, "X3Y4");

    assertTrue(collection.hasAt(3, 4));

    assertEquals(collection.at(3, 4).orElse(null), "X3Y4");

    final int absolute = (10 * 3) + 4; // 34

    assertEquals(collection.at(absolute).orElse(null), "X3Y4");
  }

  static class SomeObject {

    final String primitive;

    SomeObject(String primitive) {
      this.primitive = primitive;
    }
  }
}
