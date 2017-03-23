package com.github.ambling.rtreebenchmark;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;

/**
 * Test on R*-tree with various maximum children.
 */
public abstract class RStarTree extends RTreeBenchmark {
    abstract int maxChildren();

    @Override
    RTree<Object, Point> createOnGreek() {
        return RTree.maxChildren(maxChildren()).star().<Object, Point> create().add(entriesGreek06);
    }

    @Override
    RTree<Object, Rectangle> createOn1k() {
        return RTree.maxChildren(maxChildren()).star().<Object, Rectangle> create().add(entries1k06);
    }


    public static class M4 extends RStarTree {
        @Override
        int maxChildren() {
            return 4;
        }
    }

    public static class M10 extends RStarTree {
        @Override
        int maxChildren() {
            return 10;
        }
    }

    public static class M32 extends RStarTree {
        @Override
        int maxChildren() {
            return 32;
        }
    }

    public static class M128 extends RStarTree {
        @Override
        int maxChildren() {
            return 128;
        }
    }
}
