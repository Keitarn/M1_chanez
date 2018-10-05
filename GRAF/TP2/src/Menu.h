/**
 * @authors Florian Joriot, Bastien Chanez
 */

#ifndef TP2_MENU_H
#define TP2_MENU_H

#include "Graph.h"
#include <string.h>

void initiateMenu();

void messageBienvenu();

void afficheListCommande();

void viderBuffer();

bool newGraph();

void clear();

int callCreate(struct Graph **graph, bool create);

void callAddNode(struct Graph **graph);

void callDeleteNode(struct Graph **graph);

void callViewGraph(struct Graph **graph);

void callDeleteEdge(struct Graph **graph);

void callAddEdge(struct Graph **graph);

void callSaveGraph(struct Graph **graph);

void callLoadGraph(struct Graph **graph);

#endif //TP2_MENU_H
