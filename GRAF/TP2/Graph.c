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
        fprintf(stderr, "ERROR : addNode() -> nodeFrom : %i, valeur de la node incorrect\n", from);
    }
    if (to > (*graph)->nbMaxNodes | to <= 0) {
        fprintf(stderr, "ERROR : addNode() -> nodeTo : %i, valeur de la node incorrect\n", to);
    }
    if (weight <= 0) {
        fprintf(stderr, "ERROR : addNode() -> weight : %i, valeur du poids incorrect\n", weight);
    }
//todo need fix
//    if ((*graph)->adjList[from - 1] == NULL && (*graph)->adjList[to - 1] == NULL) {
//        fprintf(stderr, "ERROR : addNode() -> node , l'une des deux node n'a pas été ajouté\n");
//    }

//todo there is a bug to fix
    struct Neighbour *neighbourTo = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbourTo->neighbour = to;
    neighbourTo->weigh = weight;
    neighbourTo->nextNeighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbourTo->nextNeighbour = &(*graph)->adjList[from - 1];
    neighbourTo->previousNeighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbourTo->previousNeighbour = (*graph)->adjList[from - 1].previousNeighbour;

    (*graph)->adjList[from - 1] = *neighbourTo;


    struct Neighbour *neighbourFrom = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbourFrom->neighbour = from;
    neighbourFrom->weigh = weight;
    neighbourFrom->nextNeighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbourFrom->nextNeighbour = &(*graph)->adjList[to - 1];
    neighbourFrom->previousNeighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbourFrom->previousNeighbour = (*graph)->adjList[to - 1].previousNeighbour;

    (*graph)->adjList[to - 1] = *neighbourFrom;
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