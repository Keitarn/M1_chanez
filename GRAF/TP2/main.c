#include <stdio.h>
#include "Graph.h"

int main() {

    struct Graph graph;

    createGraph(&graph, 4, false);
    addNode(&graph, 0); // error
    addNode(&graph, 1);
    addNode(&graph, 2);
    addNode(&graph, 2); // error
    addNode(&graph, 4);
    addNode(&graph, 5); // error
    addEdge(&graph, 1, 11, 1);
    addEdge(&graph, 1, 12, 2);
    addEdge(&graph, 1, 12, 2);
    addEdge(&graph, 2, 12, 1);
    addEdge(&graph, 1, 13, 4);
    addEdge(&graph, 1, 14, 5); // error
    addEdge(&graph, 1, -1, 5); // error
    addEdge(&graph, 1, 40, 3); // error
    addEdge(&graph, 3, 40, 4); // error
    addEdge(&graph, 0, 40, 2); // error

    viewGraph(&graph);

    FILE *file = NULL;
    saveGraph(&graph, file, "graph.txt");

    return 0;
}