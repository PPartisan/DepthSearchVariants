package com.github.ppartisan.dsv;

import static com.github.ppartisan.dsv.DepthLimitedSearch.dls;
import static com.github.ppartisan.dsv.IterativeDeepeningSearch.ids;
import static com.github.ppartisan.dsv.Node.node;
import static com.github.ppartisan.dsv.Result.*;

final class Main {
    public static void main(String[] args) {
        /*
        Simulates tree:
               A
              / \
             B   C
            /   / \
           D   E   F
                \
                 G
         */
        final Node<Character> G = node('G');
        final Node<Character> F = node('F');
        final Node<Character> D = node('D');
        final Node<Character> B = node('B', D);
        final Node<Character> E = node('E', G);
        final Node<Character> C = node('C', E, F);
        final Node<Character> A = node('A', B, C);

        dls(A, G, 2)
                .search()
                .assertResult(failed())
                .also(print());

        dls(A, G, 3)
                .search()
                .assertResult(success())
                .also(print());

        ids(A, G).search()
                .assertResult(success())
                .also(print());
    }
}
