rtree benchmark
=========

A comprehensive understanding of the rtree's performance need a comprehensive benchmark.
  
Dave Moten provides us an awesome pure-java [implementation of rtree](https://github.com/davidmoten/rtree), which
 supports multiple split strategies (e.g. quadratic and R*-tree huristics) and features (e.g. STR bulk loading and 
 Flatbuffers backed structure). This project is created to present a comprehensive performance evaluation on the 
 various rtree types.
 
Note: this is an on-going work, helps or suggests are very appreciated.


### How to run

The project is based on [jmh](http://openjdk.java.net/projects/code-tools/jmh/), and is supported to be used as the 
official guide recommends:

```bash
$ mvn clean install
$ java -jar target/microbenchmarks.jar
```

*Important note*: since currently this benchmark runs on a snapshot version of rtree (to test the feature of STR bulk loading), 
you may need to pull the latest code from master branch of the rtree project and build and deploy the snapshot maven
library to your local environment.

### Results
We conduct the experiments on a desktop server with:
- 4-core Intel(R) Core(TM) i5-4590 CPU @ 3.30GHz
- 16GB DDR3 Memory
- CentOS Linux 7 with linux kernel 3.10.0-327.el7.x86_64
- openjdk-1.8.0.71-2.b15.el7_2.x86_64

The experiments are conducted on two datasets:    

- *Greek*: a collection of some 38,377 entries corresponding to the epicentres of earthquakes in Greece between 1964 and 2000. 
- *1k*: uniformly generated 1000 rectangles.

The benchmarked rtree types are:

- *DefaultRTree*: the original rtree implementation proposed by Guttman with quadratic split.
- *StarRTree*: R*-tree huristics.
- *STRRTree*: STR bulk loaded R*-tree, with a default full ratio (0.7) in each node.
- *STRFullRTree*: STR bulk loaded R*-tree, with full nodes.
- *FBSRTree*: R*-tree loaded from a Flatbuffers serialized byte array. 

We test the performane of several operations on each type of rtree against various `maxChildren` parameter. 
TODO: we should test the performance against dataset size.

The results are plotted with [plot.py](results/plot.py) and are presented as below.

*Note: since the creation time of indexes span a large range, we use a logarithmic scale for the y axis.*

| Greek | 1k |
| :-------------: | :-----------: |
| <img src="results/createIndex_Greek.png?raw=true" /> | <img src="results/createIndex_1k.png?raw=true" /> |
| <img src="results/insertOne_Greek.png?raw=true" /> | <img src="results/insertOne_1k.png?raw=true" /> |
| <img src="results/insertBatch_Greek.png?raw=true" /> | <img src="results/insertBatch_1k.png?raw=true" /> |
| <img src="results/deleteOne_Greek.png?raw=true" /> | <img src="results/deleteOne_1k.png?raw=true" /> |
| <img src="results/deleteBatch_Greek.png?raw=true" /> | <img src="results/deleteBatch_1k.png?raw=true" /> |
| <img src="results/searchOne_Greek.png?raw=true" /> | <img src="results/searchOne_1k.png?raw=true" /> |
| <img src="results/searchOneBackpressure_Greek.png?raw=true" /> | <img src="results/searchOneBackpressure_1k.png?raw=true" /> |
| <img src="results/searchNearest_Greek.png?raw=true" /> | <img src="results/searchNearest_1k.png?raw=true" /> |


Full results are presented in [rtreebm.txt](results/rtreebm.txt).

### Analysis

##### Creation
- *FBSRTree* is much faster than others, because it just loads the context and the root node, and it lazily loaded the nodes when need. 
- *STR-Rtree* shows an order of magnitude of speedup comparing against the normal (R*-tree or DefaultRtree) creation process, 
and shows little difference about the node full ratio (but maybe differ in index size).  
- *R*-tree* is the slowest to construct.

##### Insertion
- *Default Rtree* shows the best insertion performance, both in one insertion and batch insertion.
- *STR-Rtree* is not bad in single insertion if not full. Full STR-Rtree works as bad as the FBSRtree for one insertion.
- If there are a lot of insertions occurs (need node split anyway), the performance of STR-Rtree is similar to R*-tree.
- *FBS RTree* is slow at insertion.
- It seems that less children is better for insertion.

##### Deletion
- It shows similar deletion performance for *DefaultRTree* and *R\*-tree* (for deletion, STR-Rtree is also R*-tree).
- *FBS RTree* is 10x slower at deletion.
- It seems that 10 children per node is good for deletion.

##### Range Search
- *R\*-tree* shows the best search performance for most cases.
- *STR-Rtree* is not bad, and non-full nodes shows better than full nodes.
- *FBS RTree* is 10x slower at search.
- Backpressure shows performance decline.
- Small nodes (less children) is better at search for most cases.
 
##### Nearest Search
- Currently (rtree-0.8-RC11-SNAPSHOT), the nearest search is based on a range search, therefore the results are similar. 