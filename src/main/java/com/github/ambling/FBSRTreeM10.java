package com.github.ambling;

/**
 * Test on R*-tree on Flatbuffers with various maximum 10 children.
 */
public class FBSRTreeM10 extends FBSRTree {
    @Override
    RStarTree generator() {
        return new RStarTreeM10();
    }
}
