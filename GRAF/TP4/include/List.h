/**
 * @authors Florian Joriot, Bastien Chanez
 */

#ifndef TP2_LIST_H
#define TP2_LIST_H

#include <stdlib.h>
#include <stdio.h>

struct Neighbour {
    int neighbour;
    int weigh;
    struct Neighbour *nextNeighbour;
    struct Neighbour *previousNeighbour;
};

struct Neighbour *addNeighbourList(struct Neighbour *list, int to, int weight);

struct Neighbour *removeNeighbourList(struct Neighbour *list, int to, int weight);

struct Neighbour *createList();

struct Neighbour *removeList(struct Neighbour *list);

struct Neighbour *removeMultipleNeighbour(struct Neighbour *list, int to);

void viewList(struct Neighbour *list);

void saveList(struct Neighbour *list, FILE *out);

#endif //TP2_LIST_H
