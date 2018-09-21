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

struct Graph *createGraph(struct Graph *graph, int nbMaxNodes, bool isDirected);

struct Graph *loadGraph();

struct Graph *addNode();

struct Graph *addEdge();

struct Graph *removeNode();

struct Graph *removeEdge();

void viewGraph();

void saveGraph();

#endif //TP2_GRAPH_H
