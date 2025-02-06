package com.github.ppartisan.dsv;

import java.util.LinkedList;
import java.util.List;

import static com.github.ppartisan.dsv.DepthLimitedSearch.dls;

final class IterativeDeepeningSearch<T> implements Search<T> {

    private final Node<T> start;
    private final T goal;
    private final DLS<T> search;

    private final List<T> path;

    private IterativeDeepeningSearch(
            Node<T> start,
            T goal,
            DLS<T> search
    ) {
        this.start = start;
        this.goal = goal;
        this.search = search;
        this.path = new LinkedList<>();
    }

    static <T> Search<T> ids(Node<T> start, Node<T> goal) {
        return ids(start, goal.data());
    }

    static <T> Search<T> ids(Node<T> start, T goal) {
        return new IterativeDeepeningSearch<>(
                start,
                goal,
                (a, b, c) -> dls(a, b, c).search()
        );
    }

    @Override
    public Result<T> search() {
        return search(start, 0);
    }

    private Result<T> search(Node<T> root, int depth) {
        final Result<T> result = search.dls(root, goal, depth);
        path.addAll(result.path());
        return result.isSuccessful() ? result.with(path) : search(root, depth + 1);
    }

    interface DLS<T> {
        Result<T> dls(Node<T> start, T goal, int limit);
    }

}
