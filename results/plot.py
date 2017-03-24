import matplotlib.colors as cl
import matplotlib.pyplot as plt
import numpy as np

class ResultReader:

    def __init__(self):
        self.lines = list()
        self.op = ""
        self.ds = ""
        self.markers = ['o', '*', '+', '^', 'x', 'd', 's', 'p']
        # http://ksrowell.com/blog-visualizing-data/2012/02/02/optimal-colors-for-graphs/
        self.colors = [(57, 106, 177), (218, 124, 48), (62, 100, 81),
                       (204, 37, 41), (83, 81, 84), (107, 76, 154),
                       (146, 36, 40), (148, 139, 61)]
        self.colors = map(lambda x: (1.0 * x[0] / 255,
                                     1.0 * x[1] / 255,
                                     1.0 * x[2] / 255),
                          self.colors)
        self.data = {}

    def read(self, path):
        with open(path, 'r') as f:
            self.lines = f.readlines()

    def get_match(self):
        for line in self.lines:
            if line.startswith("c.g.a.r"):
                tokens = line.split()
                indexName = tokens[0].split(".")[4]
                operation = tokens[0].split(".")[6]
                childrenNumber = int(tokens[0].split(".")[5][1:])
                if operation == self.op + self.ds:
                    throughput = float(tokens[3])
                    if not self.data.has_key(indexName):
                        self.data[indexName] = {}
                    self.data[indexName][childrenNumber] = throughput

    def draw(self, save, filename):
        figdata = plt.figure()
        for i, d in enumerate(self.data.items()):
            xaxis = list()
            yaxis = list()
            for childrenNumber in d[1]:
                xaxis.append(childrenNumber)
                yaxis.append(d[1][childrenNumber])

            marker = self.markers[i]
            color = self.colors[i]
            plt.plot(np.array(xaxis), np.array(yaxis),
                    marker=marker, color=color, linestyle='-', label=d[0])

        plt.title(self.op + " on " + self.ds, y=1.13, fontweight='bold')
        plt.xticks((4, 10, 32, 128), (4, 10, 32, 128))
        plt.xlabel("max children")
        if self.op == "createIndex":
            plt.yscale("log")
        plt.ylabel("throughput op/s")
        # the location of the legend works good for most figures
        lgd = plt.legend(loc='upper right', bbox_to_anchor=(1, 1.15),
                   numpoints=1, ncol=3, frameon=False)
        if save:
            figdata.savefig(filename, bbox_extra_artists=(lgd,), bbox_inches='tight')
        else:
            plt.show()


if __name__ == "__main__":

    opList = ["createIndex", "searchOne", "searchNearest",
              "insertOne", "insertBatch", "deleteOne", "deleteBatch",
              "searchOneBackpressure"]
    dataList = ["1k", "Greek"]

    rr = ResultReader()
    rr.read("rtreebm.txt")

    for _op in opList:
        for _ds in dataList:
            rr.op = _op
            rr.ds = _ds
            rr.get_match()
            rr.draw(True, _op + "_" + _ds + ".png")
