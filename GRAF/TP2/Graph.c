#include <stdlib.h>
#include "Graph.h"

void createGraph(struct Graph **graph, int nbMaxNodes, bool isDirected) {
    *graph = (struct Graph *) malloc(sizeof(struct Graph));
    (*graph)->isDirected = isDirected;
    (*graph)->nbMaxNodes = nbMaxNodes;
    (*graph)->adjList = (struct Neighbour **) malloc((sizeof(struct Neighbour *) * nbMaxNodes));
}

void addNode(struct Graph **graph, int node) {

struct Graph *loadGraph(struct Graph *graph) {
    return graph;
}

struct Graph *addNode(struct Graph *graph, int node) {
    return graph;
}

void addEdge(struct Graph **graph, int from, char name, int to) {
}

void removeNode(struct Graph **graph, int node) {
}

void removeEdge(struct Graph **graph, int from, char name, int to) {
}

void viewGraph(struct Graph **graph) {}

void loadGraph(struct Graph **graph) {
}

void saveGraph(struct Graph **graph, FILE *out, char *path) {
    out = fopen(path, "w+");
    if (out == NULL) {
        perror("ERROR : saveGraph() -> fopen()");
    }

    fclose(out);
}

void quit() {}