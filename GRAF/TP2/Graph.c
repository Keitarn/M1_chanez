#include <stdlib.h>
#include "Graph.h"

void createGraph(struct Graph *graph, int nbMaxNodes, bool isDirected) {
    graph->isDirected = isDirected;
    graph->nbMaxNodes = nbMaxNodes;
    graph->adjList = (struct Neighbour **) malloc(graph->nbMaxNodes * sizeof(struct Neighbour *));
}

void addNode(struct Graph *graph, int node) {
    if (!(0 < node && node <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, non comprise dans ]%i,%i]:\n", node, 0,
                graph->nbMaxNodes);
        return;
    }
    if (graph->adjList[node - 1] != NULL) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, existe déjà: \n", node);
        return;
    }

    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->neighbour = -1;
    neighbour->weigh = -1;
    neighbour->nextNeighbour = neighbour;
    neighbour->previousNeighbour = neighbour;
    graph->adjList[node - 1] = neighbour;
}

void addEdge(struct Graph *graph, int from, int weight, int to) {
    if (!(0 < from && from <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, non comprise dans ]%i,%i]:\n", from, 0,
                graph->nbMaxNodes);
        return;
    }
    if (!(0 < to && to <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, non comprise dans ]%i,%i]:\n", to, 0,
                graph->nbMaxNodes);
        return;
    }
    if (weight <= 0) {
        fprintf(stderr, "ERROR : addEdge() -> weight : %i, poids incorrect\n", weight);
        return;
    }
    if (graph->adjList[from - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> from : %i, n'existe pas\n", from);
        return;
    }
    if (graph->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> to : %i, n'existe pas\n", to);
        return;
    }

    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->neighbour = to;
    neighbour->weigh = weight;
    neighbour->nextNeighbour = graph->adjList[from - 1];
    neighbour->previousNeighbour = graph->adjList[from - 1]->previousNeighbour;
    graph->adjList[from - 1]->previousNeighbour->nextNeighbour = neighbour;
    graph->adjList[from - 1]->previousNeighbour = neighbour;
    graph->adjList[from - 1] = neighbour;
}

void removeNode(struct Graph *graph, int node) {
}

void removeEdge(struct Graph *graph, int from, int weigth, int to) {
}

void viewGraph(struct Graph *graph) {}

void loadGraph(struct Graph *graph) {
}

void saveGraph(struct Graph *graph, FILE *out, char *path) {
    out = fopen(path, "w+");
    if (out == NULL) {
        perror("ERROR : saveGraph() -> fopen()");
    }

    fclose(out);
}

void quit() {}