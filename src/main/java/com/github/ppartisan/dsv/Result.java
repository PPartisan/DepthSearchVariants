package com.github.ppartisan.dsv;

import java.util.Optional;
import java.util.function.Predicate;

final class Result<T> {

    private final T data;
    private final boolean isSuccessful;

    private Result(T data, boolean isSuccessful) {
        this.data = data;
        this.isSuccessful = isSuccessful;
    }

    static <T> Result<T> success(T data) {
        return new Result<>(data, true);
    }

    static <T> Result<T> failure() {
        return new Result<>(null, false);
    }

    boolean isSuccessful() {
        return isSuccessful;
    }

    boolean isFailure() {
        return !isSuccessful();
    }

    Optional<T> data() {
        return Optional.ofNullable(data);
    }

    void assertThat(Predicate<Result<T>> toAssert) {
        if(!toAssert.test(this))
            throw new AssertionError("Failed assert.");
        System.out.println("Assertion passed.");
    }
}
