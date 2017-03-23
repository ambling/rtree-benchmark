package com.github.ambling.rtreebenchmark;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;

/**
 * Test on STR bulk loaded R*-tree with full nodes and various maximum children.
 */
public abstract class STRFullRTree extends RTreeBenchmark {
    abstract int maxChildren();

    @Override
    RTree<Object, Point> createOnGreek() {
        return RTree.maxChildren(maxChildren()).star().loadingFactor(1.0).<Object, Point> create(entriesGreek06);
    }

    @Override
    RTree<Object, Rectangle> createOn1k() {
        return RTree.maxChildren(maxChildren()).star().loadingFactor(1.0).<Object, Rectangle> create(entries1k06);
    }


    public static class M4 extends STRFullRTree {
        @Override
        int maxChildren() {
            return 4;
        }
    }

    public static class M10 extends STRFullRTree {
        @Override
        int maxChildren() {
            return 10;
        }
    }

    public static class M32 extends STRFullRTree {
        @Override
        int maxChildren() {
            return 32;
        }
    }

    public static class M128 extends STRFullRTree {
        @Override
        int maxChildren() {
            return 128;
        }
    }
}
