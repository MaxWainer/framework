package dev.framework.commons.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.framework.commons.MoreCollections;
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

  static class SomeObject {

    final String primitive;

    SomeObject(String primitive) {
      this.primitive = primitive;
    }
  }

}
