#ifndef TP2_GRAPH_H
#define TP2_GRAPH_H

#include <stdbool.h>

struct Neighbour {
    int neighbour;
    int weigh;
    struct Neighbour *nextNeighbour;
    struct Neighbour *previousNeighbour;
};

struct Graph {
    bool isDirected;
    int nbMaxNodes;
    struct Neighbour *adjList;
};

#endif //TP2_GRAPH_H
