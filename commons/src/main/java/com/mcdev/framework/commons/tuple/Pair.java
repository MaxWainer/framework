package com.mcdev.framework.commons.tuple;

public final class Pair<F, S> {

  private F first;
  private S second;

  public Pair(final F first, final S second) {
    this.first = first;
    this.second = second;
  }

  public F first() {
    return first;
  }

  public S second() {
    return second;
  }

  public void first(F first) {
    this.first = first;
  }

  public void second(S second) {
    this.second = second;
  }

  public static <V, T> Pair<V, T> of(final V v, final T t) {
    return new Pair<>(v, t);
  }

}
