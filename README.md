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
on going

### Analysis
on going