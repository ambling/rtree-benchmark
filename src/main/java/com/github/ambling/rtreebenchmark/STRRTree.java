package com.github.ambling.rtreebenchmark;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;

/**
 * Test on STR bulk loaded R*-tree with various maximum children.
 */
public abstract class STRRTree extends RTreeBenchmark {
    abstract int maxChildren();

    @Override
    RTree<Object, Point> createOnGreek() {
        return RTree.maxChildren(maxChildren()).star().<Object, Point> create(entriesGreek06);
    }

    @Override
    RTree<Object, Rectangle> createOn1k() {
        return RTree.maxChildren(maxChildren()).star().<Object, Rectangle> create(entries1k06);
    }


    public static class M4 extends STRRTree {
        @Override
        int maxChildren() {
            return 4;
        }
    }

    public static class M10 extends STRRTree {
        @Override
        int maxChildren() {
            return 10;
        }
    }

    public static class M32 extends STRRTree {
        @Override
        int maxChildren() {
            return 32;
        }
    }

    public static class M128 extends STRRTree {
        @Override
        int maxChildren() {
            return 128;
        }
    }
}
