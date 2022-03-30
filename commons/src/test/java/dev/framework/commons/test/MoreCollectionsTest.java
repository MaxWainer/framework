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
