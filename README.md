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
The experiments are conducted on two datasets:    

- *Greek*: a collection of some 38,377 entries corresponding to the epicentres of earthquakes in Greece between 1964 and 2000. 
- *1k*: uniformly generated 1000 rectangles.

The benchmarked rtree types are:

- *DefaultRTree*: the original rtree implementation proposed by Guttman with quadratic split.
- *StarRTree*: R*-tree huristics.
- *STRRTree*: STR bulk loaded R*-tree, with a default full ratio (0.7) in each node.
- *STRFullRTree*: STR bulk loaded R*-tree, with full nodes.
- *FBSRTree*: R*-tree loaded from a Flatbuffers serialized byte array. 

We conduct several operations on each type of rtree with multiple `maxChildren` parameter. 

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
on going