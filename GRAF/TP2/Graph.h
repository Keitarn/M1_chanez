#ifndef TP2_GRAPH_H
#define TP2_GRAPH_H

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include "List.h"

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

void loadGraph(struct Graph *graph, char *path);

void saveGraph(struct Graph *graph, char *path);

void quit(struct Graph *graph);

#endif //TP2_GRAPH_H
