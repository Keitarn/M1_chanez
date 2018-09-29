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

int createGraph(struct Graph **graph, int nbMaxNodes, bool isDirected);

int addNode(struct Graph *graph, int node);

int addEdge(struct Graph *graph, int from, int weight, int to);

int removeNode(struct Graph *graph, int node);

int removeEdge(struct Graph *graph, int from, int weight, int to);

void viewGraph(struct Graph *graph);

int loadGraph(struct Graph *graph, char *path);

int saveGraph(struct Graph *graph, char *path);

void quit(struct Graph *graph);

#endif //TP2_GRAPH_H
