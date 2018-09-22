#include <stdlib.h>
#include "Graph.h"

void createGraph(struct Graph **graph, int nbMaxNodes, bool isDirected) {
    (*graph) = (struct Graph *) malloc(sizeof(struct Graph));
    (*graph)->isDirected = isDirected;
    (*graph)->nbMaxNodes = nbMaxNodes;
    (*graph)->adjList = (struct Neighbour *) malloc((*graph)->nbMaxNodes * sizeof(struct Neighbour));
}

void addNode(struct Graph **graph, int node) {
    if (!(0 < node && node <= (*graph)->nbMaxNodes)) {
        printf("ERROR\n");
        return;
    }

    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->neighbour = -1;
    neighbour->weigh = -1;
    neighbour->nextNeighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->nextNeighbour = neighbour;
    neighbour->previousNeighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->previousNeighbour = neighbour;

   (*graph)->adjList[node - 1] = *neighbour;
}

void addEdge(struct Graph **graph, int from, int weight, int to) {
    if (from > (*graph)->nbMaxNodes | from <= 0) {
        fprintf(stderr, "ERROR : addNode() -> nodeFrom : %i, valeur de la node incorrect", from);
    }
    if (to > (*graph)->nbMaxNodes | to <= 0) {
        fprintf(stderr, "ERROR : addNode() -> nodeTo : %i, valeur de la node incorrect", to);
    }
    if (weight <= 0) {
        fprintf(stderr, "ERROR : addNode() -> weight : %i, valeur du poids incorrect", weight);
    }
    if (&(*graph)->adjList[from - 1] == NULL & &(*graph)->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : addNode() -> node , l'une des deux node n'a pas été ajouté");
    }

    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->neighbour = to;
    neighbour->weigh = weight;
   *neighbour->nextNeighbour = (*graph)->adjList[from - 1];
    neighbour->previousNeighbour = (*graph)->adjList[from - 1].previousNeighbour;
    (*graph)->adjList[from - 1].previousNeighbour = neighbour;
    (*graph)->adjList[from - 1] = *neighbour;


}

void removeNode(struct Graph **graph, int node) {
}

void removeEdge(struct Graph **graph, int from, int weigth, int to) {
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