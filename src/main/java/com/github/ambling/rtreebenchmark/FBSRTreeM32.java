package com.github.ambling.rtreebenchmark;

/**
 * Test on R*-tree on Flatbuffers with various maximum 32 children.
 */
public class FBSRTreeM32 extends FBSRTree {
    @Override
    RStarTree generator() {
        return new RStarTreeM32();
    }
}
