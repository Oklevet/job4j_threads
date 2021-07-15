package io;

import java.util.function.Predicate;

public interface Input<T> {
    String input(Predicate<T> condition);
}
