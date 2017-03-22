package com.github.ambling;

/**
 * Test on R*-tree with maximum 10 children.
 */
public class RStarTreeM10 extends RStarTree {
    @Override
    int maxChildren() {
        return 10;
    }
}
