package com.github.ambling;

/**
 * Test on STR bulk loaded R*-tree with maximum 32 children.
 */
public class STRRTreeM32 extends STRRTree {
    @Override
    int maxChildren() {
        return 32;
    }
}
