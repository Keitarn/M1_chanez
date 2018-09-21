#include "Graph.h"

struct Graph *createGraph(struct Graph *graph) {
    return graph;
}

struct Graph *loadGraph(struct Graph *graph) {
    return graph;
}

struct Graph *addNode(struct Graph *graph, int node) {
    return graph;
}

struct Graph *addEdge(struct Graph *graph, int from, char name, int to) {
    return graph;
}

struct Graph *removeNode(struct Graph *graph) {
    return graph;
}

struct Graph *removeEdge(struct Graph *graph) {
    return graph;
}

void viewGraph(struct Graph *graph) {}

void saveGraph(struct Graph *graph, FILE *out, char *path) {
    out = fopen(path, "w+");
    if (out == NULL) {
        perror("ERROR : fa_pretty_print() -> fopen()");
    }

    fclose(out);
}

void quit() {}