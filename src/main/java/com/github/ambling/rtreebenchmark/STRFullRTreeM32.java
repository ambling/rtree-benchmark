package com.github.ambling.rtreebenchmark;

/**
 * Test on STR bulk loaded R*-tree with full nodes and various 32 children.
 */
public class STRFullRTreeM32 extends STRFullRTree {
    @Override
    int maxChildren() {
        return 32;
    }
}
