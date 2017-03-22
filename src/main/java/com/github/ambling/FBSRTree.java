package com.github.ambling;

import com.github.davidmoten.rtree.InternalStructure;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.Serializer;
import com.github.davidmoten.rtree.fbs.SerializerFlatBuffers;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;
import rx.functions.Func1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Test on R*-tree on Flatbuffers with various maximum children.
 *
 * To create RTree on Flatbuffers, we need to firstly create the RTree (R*-tree in this case)
 * through a generator which is implemented in subclasses, and then transform the tree to a
 * byte array. After that, the RTree on Flatbuffers is loaded from the byte array.
 *
 */
public abstract class FBSRTree extends RTreeBenchmark {
    abstract RStarTree generator();

    private final byte[] byteArrayGreek = createFBSByteArray(generator().createOnGreek());

    private final byte[] byteArray1k = createFBSByteArray(generator().createOn1k());

    private <Object, S extends Geometry> byte[] createFBSByteArray(RTree<Object, S> tree) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        Func1<Object, byte[]> serializer = new Func1<Object, byte[]>() {
            @Override
            public byte[] call(Object o) {
                return new byte[0];
            }
        };
        Func1<byte[], Object> deserializer = new Func1<byte[], Object>() {
            @Override
            public Object call(byte[] bytes) {
                return null;
            }
        };
        Serializer<Object, S> fbSerializer = SerializerFlatBuffers.create(serializer,
                deserializer);
        try {
            fbSerializer.write(tree, os);
            os.close();
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <Object, S extends Geometry> RTree<Object, S> createFBSTree(byte [] byteArray) {
        Func1<Object, byte[]> serializer = new Func1<Object, byte[]>() {
            @Override
            public byte[] call(Object o) {
                return new byte[0];
            }
        };
        Func1<byte[], Object> deserializer = new Func1<byte[], Object>() {
            @Override
            public Object call(byte[] bytes) {
                return null;
            }
        };
        Serializer<Object, S> fbSerializer = SerializerFlatBuffers.create(serializer,
                deserializer);
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(byteArray);
            return fbSerializer.read(is, byteArray.length, InternalStructure.SINGLE_ARRAY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    RTree<Object, Point> createOnGreek() {
        return createFBSTree(byteArrayGreek);
    }

    @Override
    RTree<Object, Rectangle> createOn1k() {
        return createFBSTree(byteArray1k);
    }
}
