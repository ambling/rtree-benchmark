package com.github.ambling.rtreebenchmark;

/**
 * Test on default RTree (quadratic splitter (Guttman)) with maximum 32 children.
 */
public class DefaultRTreeM32 extends DefaultRTree {
    @Override
    int maxChildren() {
        return 32;
    }
}
