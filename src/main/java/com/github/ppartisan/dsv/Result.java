package com.github.ppartisan.dsv;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.stream.Collectors.joining;

final class Result<T> {

    private final T data;
    private final boolean isSuccessful;
    private final List<T> path;

    private Result(T data, boolean isSuccessful, List<T> path) {
        this.data = data;
        this.isSuccessful = isSuccessful;
        this.path = path;
    }

    Result<T> with(List<T> path){
        return new Result<>(data, isSuccessful, path);
    }

    static <T> Result<T> success(T data) {
        return new Result<>(data, true, List.of());
    }

    static <T> Result<T> success(T data, List<T> path) {
        return new Result<>(data, true, path);
    }

    static <T> Result<T> failure() {
        return new Result<>(null, false, List.of());
    }

    static <T> Result<T> failure(List<T> path) {
        return new Result<>(null, false, path);
    }

    boolean isSuccessful() {
        return isSuccessful;
    }

    boolean isFailure() {
        return !isSuccessful();
    }

    List<T> path() {
        return List.copyOf(path);
    }

    Result<T> assertResult(Predicate<Result<T>> toAssert) {
        if(!toAssert.test(this))
            System.out.println("Failed assert.");
        System.out.println("Assertion passed.");
        return this;
    }

    void also(Consumer<Result<T>> toRun) {
        toRun.accept(this);
    }

    static <T> Consumer<Result<T>> print() {
        return System.out::println;
    }

    static <T> Predicate<Result<T>> success() {
        return Result::isSuccessful;
    }

    static <T> Predicate<Result<T>> failed() {
        return Result::isFailure;
    }

    @Override
    public String toString() {
        final String node = isSuccessful ? "Success" : "Failure";
        final String path = this.path.stream().map(String::valueOf).collect(joining(" → ", "[", "]"));
        return String.format("%s: %s", node, path);
    }
}
