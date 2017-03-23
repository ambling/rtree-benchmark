package com.github.ambling.rtreebenchmark;

/**
 * Test on STR bulk loaded R*-tree with full nodes and various 128 children.
 */
public class STRFullRTreeM128 extends STRFullRTree {
    @Override
    int maxChildren() {
        return 128;
    }
}
