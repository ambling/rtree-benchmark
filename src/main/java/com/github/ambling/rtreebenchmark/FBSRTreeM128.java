package com.github.ambling.rtreebenchmark;

/**
 * Test on R*-tree on Flatbuffers with various maximum 128 children.
 */
public class FBSRTreeM128 extends FBSRTree {
    @Override
    RStarTree generator() {
        return new RStarTreeM128();
    }
}
