package io;

public interface Input<T> {
    String input(Predicate<T> condition);
}
