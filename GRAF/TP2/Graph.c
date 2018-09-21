#include <stdlib.h>
#include "Graph.h"

struct Graph * createGraph(struct Graph *graph, int nbMaxNodes, bool isDirected)
{
    graph =  malloc(sizeof(struct Graph));
    graph->isDirected = isDirected;
    graph->nbMaxNodes = nbMaxNodes;
    graph->adjList = malloc((sizeof(struct Neighbour)*nbMaxNodes));
    return graph;
}

struct Graph *loadGraph(struct Graph *graph) {
    return graph;
}

struct Graph *addNode(struct Graph *graph) {
    return graph;
}

struct Graph *addEdge(struct Graph *graph) {
    return graph;
}

struct Graph *removeNode(struct Graph *graph) {
    return graph;
}

struct Graph *removeEdge(struct Graph *graph) {
    return graph;
}

void viewGraph() {}

void saveGraph() {}