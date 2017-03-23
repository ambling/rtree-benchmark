package com.github.ambling.rtreebenchmark;

/**
 * Test on STR bulk loaded R*-tree with maximum 128 children.
 */
public class STRRTreeM128 extends STRRTree {
    @Override
    int maxChildren() {
        return 128;
    }
}
