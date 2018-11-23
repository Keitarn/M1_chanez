/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "MaximumFlow.h"

int MaxFlow(struct Graph **graph, int nodeDepart, int nodeFin, int algo) {
    if ((*graph) == NULL) {
        return -1;
    }
    if (nodeDepart >= (*graph)->nbMaxNodes || nodeDepart < 0) {
        return -1;
    }
    if (nodeFin >= (*graph)->nbMaxNodes || nodeFin < 0) {
        return -1;
    }

    struct Graph *graphCopy = NULL;
    GraphCopy(graph, &graphCopy);

    switch (algo) {
        case 1:
            dijkstra(&graphCopy, nodeDepart, nodeFin);
            break;
        default:
            printf("Problème choix algo, mauvais int recuperer");
            break;
    }


}