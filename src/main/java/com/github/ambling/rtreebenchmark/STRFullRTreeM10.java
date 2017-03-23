package com.github.ambling.rtreebenchmark;

/**
 * Test on STR bulk loaded R*-tree with full nodes and various 10 children.
 */
public class STRFullRTreeM10 extends STRFullRTree {
    @Override
    int maxChildren() {
        return 10;
    }
}
