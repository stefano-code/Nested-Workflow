package com.android.workflow;

public interface Predicate<T> {
  boolean test(T input);
  @Override
  boolean equals(Object object);
}
