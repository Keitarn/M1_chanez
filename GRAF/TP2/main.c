#include <stdio.h>
#include "Graph.h"

int main() {

    struct Graph *graph = NULL;
    createGraph(&graph, 4, false);
    addNode(&graph, 1);
    addNode(&graph, 2);
    addNode(&graph, 3);
    addNode(&graph, 4);
    addNode(&graph, 5);

    return 0;
}