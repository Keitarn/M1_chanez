#ifndef TP2_LIST_H
#define TP2_LIST_H

#include <bits/types/FILE.h>
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

struct Neighbour *createList(struct Neighbour *list);

struct Neighbour *removeList(struct Neighbour *list);

struct Neighbour *removeMultipleNeighbour(struct Neighbour *list, int to);

void viewList(struct Neighbour *list, int i);

void saveList(struct Neighbour *list, FILE* out, int i);

#endif //TP2_LIST_H
