package com.github.ambling.rtreebenchmark;

/**
 * Test on R*-tree with maximum 128 children.
 */
public class RStarTreeM128 extends RStarTree {
    @Override
    int maxChildren() {
        return 128;
    }
}
