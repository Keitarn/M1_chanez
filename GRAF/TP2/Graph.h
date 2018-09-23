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
    struct Neighbour **adjList;
};

void createGraph(struct Graph *graph, int nbMaxNodes, bool isDirected);

void addNode(struct Graph *graph, int node);

void addEdge(struct Graph *graph, int from, int weight, int to);

void removeNode(struct Graph *graph, int node);

void removeEdge(struct Graph *graph, int from, int weight, int to);

void viewGraph(struct Graph *graph);

void loadGraph(struct Graph *graph);

void saveGraph(struct Graph *graph, FILE *out, char *path);

void quit();

#endif //TP2_GRAPH_H
