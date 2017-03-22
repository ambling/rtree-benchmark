package com.github.ambling;

/**
 * Test on default RTree (quadratic splitter (Guttman)) with maximum 10 children.
 */
public class DefaultRTreeM10 extends DefaultRTree {
    @Override
    int maxChildren() {
        return 10;
    }
}
