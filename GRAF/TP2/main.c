#include <stdio.h>
#include "Graph.h"

int main() {

    struct Graph *graph;

    createGraph(&graph, 4, false);
    addNode(&graph, 0); // error
    addNode(&graph, 1);
    addNode(&graph, 2);
    addNode(&graph, 3);
    addNode(&graph, 4);
    addNode(&graph, 5); // error
    addEdge(&graph,1,40,2);
    addEdge(&graph,0,40,2); // error
    return 0;
}