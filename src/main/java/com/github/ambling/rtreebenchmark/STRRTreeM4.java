package com.github.ambling.rtreebenchmark;

/**
 * Test on STR bulk loaded R*-tree with maximum 4 children.
 */
public class STRRTreeM4 extends STRRTree {
    @Override
    int maxChildren() {
        return 4;
    }
}
