/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.github.ambling.rtreebenchmark;

import com.github.davidmoten.rtree.Entries;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;
import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An abstract class for common benchmark cases.
 */
@BenchmarkMode(Mode.Throughput)
@Fork(1)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public abstract class RTreeBenchmark {

    // the datasets, with 60% of the data to construct rtree, and 40% left for batch insertion
    protected final List<Entry<Object, Point>> entriesGreek = GreekEarthquakes.entriesList();

    protected final List<Entry<Object, Point>> entriesGreek06 =
            entriesGreek.subList(0, (int) (0.6 * entriesGreek.size()));

    protected final List<Entry<Object, Point>> entriesGreek04 =
            entriesGreek.subList((int) (0.6 * entriesGreek.size()), entriesGreek.size());

    protected final List<Entry<Object, Rectangle>> entries1k = entries1000();

    protected final List<Entry<Object, Rectangle>> entries1k06 =
            entries1k.subList(0, (int) (0.6 * entries1k.size()));

    protected final List<Entry<Object, Rectangle>> entries1k04 =
            entries1k.subList((int) (0.6 * entries1k.size()), entries1k.size());

    static List<Entry<Object, Rectangle>> entries1000() {
        List<Entry<Object, Rectangle>> list = new ArrayList<Entry<Object, Rectangle>>();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(RTreeBenchmark.class.getResourceAsStream("/1000.txt")));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] items = line.split(" ");
                double x = Double.parseDouble(items[0]);
                double y = Double.parseDouble(items[1]);
                list.add(Entries.entry(new Object(), Geometries.rectangle(x, y, x + 1, y + 1)));
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * The created RTree instance on Greek data that used to run tests.
     */
    protected RTree<Object, Point> rtreeGreek = createOnGreek();

    /**
     * The created RTree instance on 1000 rectangles data that used to run tests.
     */
    protected RTree<Object, Rectangle> rtree1k = createOn1k();

    /**
     * Create the Rtree on Greek data.
     */
    abstract RTree<Object, Point> createOnGreek();

    /**
     * Create the Rtree on generated rectangles data.
     */
    abstract RTree<Object, Rectangle> createOn1k();

    /**
     * The benchmark cases, which are inheritanted by subclasses.
     *
     * User can invoke one or some of the cases through a regular expression, e.g.
     * $ java -jar target/microbenchmarks.jar "com.github.ambling.rtreebenchmark.*.createIndexGreek"
     */
    @GenerateMicroBenchmark
    public RTree<Object, Point> createIndexGreek() {
        return createOnGreek();
    }

    @GenerateMicroBenchmark
    public RTree<Object, Rectangle> createIndex1k() {
        return createOn1k();
    }

    @GenerateMicroBenchmark
    public RTree<Object, Point> insertOneGreek() {
        return rtreeGreek.add(entriesGreek04.get(0));
    }

    @GenerateMicroBenchmark
    public RTree<Object, Rectangle> insertOne1k() {
        return rtree1k.add(entries1k04.get(0));
    }

    @GenerateMicroBenchmark
    public RTree<Object, Point> deleteOneGreek() {
        return rtreeGreek.delete(entriesGreek06.get(100));
    }

    @GenerateMicroBenchmark
    public RTree<Object, Rectangle> deleteOne1k() {
        return rtree1k.delete(entries1k06.get(100));
    }

    @GenerateMicroBenchmark
    public RTree<Object, Point> insertBatchGreek() {
        RTree<Object, Point> tree = rtreeGreek;
        for (Entry<Object, Point> entry: entriesGreek04) tree = tree.add(entry);
        return tree;
    }

    @GenerateMicroBenchmark
    public RTree<Object, Rectangle> insertBatch1k() {
        RTree<Object, Rectangle> tree = rtree1k;
        for (Entry<Object, Rectangle> entry: entries1k04) tree = tree.add(entry);
        return tree;
    }

    @GenerateMicroBenchmark
    public RTree<Object, Point> deleteBatchGreek() {
        RTree<Object, Point> tree = rtreeGreek;
        for (Entry<Object, Point> entry: entriesGreek06) tree = tree.delete(entry);
        return tree;
    }

    @GenerateMicroBenchmark
    public RTree<Object, Rectangle> deleteBatch1k() {
        RTree<Object, Rectangle> tree = rtree1k;
        for (Entry<Object, Rectangle> entry: entries1k06) tree = tree.delete(entry);
        return tree;
    }

    @GenerateMicroBenchmark
    public void searchOneGreek() {
        rtreeGreek.search(Geometries.rectangle(40, 27.0, 40.5, 27.5)).subscribe();
    }

    @GenerateMicroBenchmark
    public void searchOne1k() {
        rtree1k.search(Geometries.rectangle(500, 500, 630, 630)).subscribe();
    }

    @GenerateMicroBenchmark
    public void searchOneBackpressureGreek() {
        rtreeGreek.search(Geometries.rectangle(40, 27.0, 40.5, 27.5)).take(1000).subscribe();
    }

    @GenerateMicroBenchmark
    public void searchOneBackpressure1k() {
        rtree1k.search(Geometries.rectangle(500, 500, 630, 630)).take(600).subscribe();
    }

    @GenerateMicroBenchmark
    public void searchNearestGreek() {
        rtreeGreek.nearest(Geometries.point(40.0, 27.0), 1, 300).subscribe();
    }

    @GenerateMicroBenchmark
    public void searchNearest1k() {
        rtree1k.nearest(Geometries.point(500.0, 500.0), 10, 30).subscribe();
    }

}
