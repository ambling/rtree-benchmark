package com.github.ambling;

/**
 * Test on default RTree (quadratic splitter (Guttman)) with maximum 128 children.
 */
public class DefaultRTreeM128 extends DefaultRTree {
    @Override
    int maxChildren() {
        return 128;
    }
}
