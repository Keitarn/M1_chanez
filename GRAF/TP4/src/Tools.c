/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "Tools.h"

int GraphCopy(struct Graph **graph, struct Graph **copy) {
    int tabWeight[(*graph)->nbMaxNodes];
    struct Neighbour *parcours = NULL;

    createGraph(copy, (*graph)->nbMaxNodes, true);

    for (int i = 0; i < (*graph)->nbMaxNodes; i++) {
        if ((*graph)->adjList[i] == NULL) {
            continue;
        }
        addNode(copy, i + 1);
    }

    for (int i = 0; i < (*graph)->nbMaxNodes; i++) {
        tabWeight[i] = 0;
        if ((*graph)->adjList[i] == NULL) {
            continue;
        }
        parcours = (*graph)->adjList[i];
        while (parcours->neighbour != -1) {
            tabWeight[(parcours->neighbour) - 1] += parcours->weigh;
            parcours = parcours->nextNeighbour;
        }
        for (int j = 0; j < (*graph)->nbMaxNodes; j++) {
            addEdge(copy, i + 1, tabWeight[j], j + 1);
        }
    }

}