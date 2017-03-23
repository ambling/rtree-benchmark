package com.github.ambling.rtreebenchmark;

/**
 * Test on default RTree (quadratic splitter (Guttman)) with maximum 4 children.
 */
public class DefaultRTreeM4 extends DefaultRTree {
    @Override
    int maxChildren() {
        return 4;
    }
}
