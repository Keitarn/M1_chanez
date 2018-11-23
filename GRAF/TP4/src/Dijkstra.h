/**
 * @authors Florian Joriot, Bastien Chanez
 */

#ifndef TP4_DIJKSTRA_H
#define TP4_DIJKSTRA_H

#include "List.h"
#include "Graph.h"

struct Neighbour *dijkstra(struct Graph **graph, int nodeDepart, int nodeFin);

#endif //TP4_DIJKSTRA_H
