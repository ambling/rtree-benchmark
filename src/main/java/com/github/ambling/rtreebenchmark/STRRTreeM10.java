package com.github.ambling.rtreebenchmark;

/**
 * Test on STR bulk loaded R*-tree with maximum 10 children.
 */
public class STRRTreeM10 extends STRRTree {
    @Override
    int maxChildren() {
        return 10;
    }
}
