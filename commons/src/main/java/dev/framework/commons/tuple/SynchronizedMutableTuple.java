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

package dev.framework.commons.tuple;

import dev.framework.commons.concurrent.annotation.ThreadSafe;

@ThreadSafe
final class SynchronizedMutableTuple<K, V> extends UnsafeMutableTuple<K, V> {

  private final Object[] mutex;

  SynchronizedMutableTuple(final K key, final V value, final Object[] mutex) {
    super(key, value);
    this.mutex = mutex;
  }

  SynchronizedMutableTuple(final K key, final V value) {
    this(key, value, new Object[0]);
  }

  @Override
  public K key() {
    synchronized (mutex) {
      return super.key();
    }
  }

  @Override
  public V value() {
    synchronized (mutex) {
      return super.value();
    }
  }

  @Override
  public void updateKey(final K key) {
    synchronized (mutex) {
      super.updateKey(key);
    }
  }

  @Override
  public void updateValue(final V value) {
    synchronized (mutex) {
      super.updateValue(value);
    }
  }
}
