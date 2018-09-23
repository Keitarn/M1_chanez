#include <stdlib.h>
#include "Graph.h"

void createGraph(struct Graph *graph, int nbMaxNodes, bool isDirected) {
    //* graph = (struct Graph *) malloc(sizeof(struct Graph));
    graph->isDirected = isDirected;
    graph->nbMaxNodes = nbMaxNodes;
    graph->adjList = (struct Neighbour **) malloc(graph->nbMaxNodes * sizeof(struct Neighbour *));
}

void addNode(struct Graph *graph, int node) {
    if (!(0 < node && node <= graph->nbMaxNodes)) {
        printf("ERROR\n");
        return;
    }

    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->neighbour = -1;
    neighbour->weigh = -1;
    neighbour->nextNeighbour = neighbour;
    neighbour->previousNeighbour = neighbour;
    graph->adjList[node - 1] = neighbour;
    printf("%i\n", graph->adjList[node-1]->weigh);
}

void addEdge(struct Graph *graph, int from, int weight, int to) {
    if (from > graph->nbMaxNodes | from <= 0) {
        fprintf(stderr, "ERROR : addEdge() -> nodeFrom : %i, valeur de la node incorrect\n", from);
        return;
    }
    if (to > graph->nbMaxNodes | to <= 0) {
        fprintf(stderr, "ERROR : addEdge() -> nodeTo : %i, valeur de la node incorrect\n", to);
        return;
    }
    if (weight <= 0) {
        fprintf(stderr, "ERROR : addEdge() -> weight : %i, valeur du poids incorrect\n", weight);
        return;
    }

    if ((graph->adjList[from - 1]) != NULL) {
        if ((graph->adjList[to - 1]) != NULL) {
            struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
            neighbour->neighbour = to;
            neighbour->weigh = weight;
            neighbour->nextNeighbour = graph->adjList[from - 1];
            neighbour->previousNeighbour = graph->adjList[from - 1]->previousNeighbour;
            graph->adjList[from - 1]->previousNeighbour->nextNeighbour = neighbour;
            graph->adjList[from - 1]->previousNeighbour = neighbour;
            graph->adjList[from - 1] = neighbour;
        } else {
            fprintf(stderr, "ERROR : addEdge() -> node , la nodeFrom n'a pas été ajouté\n");
            return;
        }
    } else {
        fprintf(stderr, "ERROR : addEdge() -> node , la nodeTo n'a pas été ajouté\n");
        return;
    }


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