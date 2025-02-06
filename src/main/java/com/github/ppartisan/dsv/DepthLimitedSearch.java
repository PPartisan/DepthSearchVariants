package com.github.ppartisan.dsv;

import static com.github.ppartisan.dsv.Result.failure;
import static com.github.ppartisan.dsv.Result.success;

final class DepthLimitedSearch<T> implements Search<T> {

    private final Node<T> start;
    private final T goal;
    private final int limit;

    private DepthLimitedSearch(Node<T> start, T goal, int limit) {
        this.start = start;
        this.goal = goal;
        this.limit = limit;
    }

    static <T> DepthLimitedSearch<T> dls(Node<T> start, Node<T> goal, int limit) {
        return new DepthLimitedSearch<>(start, goal.data(), limit);
    }

    static <T> DepthLimitedSearch<T> dls(Node<T> start, T goal, int limit) {
        return new DepthLimitedSearch<>(start, goal, limit);
    }

    @Override
    public Result<T> search() {
        return search(start, limit);
    }

    private Result<T> search(Node<T> node, int limit) {
        if(node.contains(goal))
            return success(node.data());
        if(limit == 0)
            return failure();

        for (Node<T> neighbour : node.neighbours()) {
            final Result<T> res = search(neighbour, limit - 1);
            if(res.isSuccessful())
                return res;
        }

        return failure();
    }


}
