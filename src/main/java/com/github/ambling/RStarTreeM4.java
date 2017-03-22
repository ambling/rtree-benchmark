package com.github.ambling;

/**
 * Test on R*-tree with maximum 4 children.
 */
public class RStarTreeM4 extends RStarTree {
    @Override
    int maxChildren() {
        return 4;
    }
}
