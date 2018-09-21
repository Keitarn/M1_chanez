#ifndef TP2_GRAPH_H
#define TP2_GRAPH_H

#include <stdbool.h>
#include <stdio.h>

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

struct Graph *createGraph(struct Graph *graph);

struct Graph *loadGraph(struct Graph *graph);

struct Graph *addNode(struct Graph *graph);

struct Graph *addEdge(struct Graph *graph);

struct Graph *removeNode(struct Graph *graph);

struct Graph *removeEdge(struct Graph *graph);

void viewGraph(struct Graph *graph);

void saveGraph(struct Graph *graph, FILE *out, char *path);

void quit();

#endif //TP2_GRAPH_H
