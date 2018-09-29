//
// Created by keitarn on 29/09/18.
//

#ifndef TP2_LIST_H
#define TP2_LIST_H

struct Neighbour {
    int neighbour;
    int weigh;
    struct Neighbour *nextNeighbour;
    struct Neighbour *previousNeighbour;
};

struct Neighbour * createNeigbour();
void DeleteNeighbour(struct Neighbour *Neig );
struct Neighbour * AddNeighbourList(struct Neighbour *list, int to, int weight );
struct Neighbour * RemoveNeighbourList(struct Neighbour *list, int to, int weight );
void removeList(struct Neighbour *list);
#endif //TP2_LIST_H
