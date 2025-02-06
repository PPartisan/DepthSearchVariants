package com.github.ppartisan.dsv;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

final class Node<T> {

    private final T data;
    private final List<Node<T>> neighbours;

    private Node(T data, List<Node<T>> neighbours) {
        this.data = requireNonNull(data);
        this.neighbours = requireNonNull(neighbours);
    }

    @SafeVarargs
    static <T> Node<T> node(T data, Node<T>... neighbours) {
        final List<Node<T>> list = (neighbours == null || neighbours.length == 0)
                ? List.of()
                : List.of(neighbours);
        return new Node<>(data, list);
    }

    T data() {
        return data;
    }

    List<Node<T>> neighbours() {
        return List.copyOf(neighbours);
    }

    boolean contains(T data) {
        return Objects.deepEquals(data, this.data);
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }

}
