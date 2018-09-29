//
// Created by keitarn on 29/09/18.
//

#include "List .h"


struct Neighbour * createNeigbour();
void DeleteNeighbour(struct Neighbour *Neig );
struct Neighbour * AddNeighbourList(struct Neighbour *list, int to, int weight );
struct Neighbour * RemoveNeighbourList(struct Neighbour *list, int to, int weight );
void removeList(struct Neighbour *list);