package com.github.ambling.rtreebenchmark;

/**
 * Test on STR bulk loaded R*-tree with full nodes and various 4 children.
 */
public class STRFullRTreeM4 extends STRFullRTree {
    @Override
    int maxChildren() {
        return 4;
    }
}
