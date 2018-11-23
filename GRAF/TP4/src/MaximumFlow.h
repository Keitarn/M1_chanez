/**
 * @authors Florian Joriot, Bastien Chanez
 */

#ifndef TP4_MAXIMUMFLOW_H
#define TP4_MAXIMUMFLOW_H

#include "Tools.h"
#include "Dijkstra.h"
#include "graph.h"

int MaxFlow(struct Graph **graph, int nodeDepart, int nodeFin, int algo);

#endif //TP4_MAXIMUMFLOW_H
