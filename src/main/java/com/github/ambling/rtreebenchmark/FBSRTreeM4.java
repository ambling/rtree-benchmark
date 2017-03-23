package com.github.ambling.rtreebenchmark;

/**
 * Test on R*-tree on Flatbuffers with various maximum 4 children.
 */
public class FBSRTreeM4 extends FBSRTree {
    @Override
    RStarTree generator() {
        return new RStarTreeM4();
    }
}
