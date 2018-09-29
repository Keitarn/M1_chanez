#include <stdio.h>
#include "Graph.h" // à retirer
#include "Menu.h"

int main() {

    struct Graph graph;
    struct Graph graph1;

    createGraph(&graph, -1, true); // error
    createGraph(&graph, 0, true); // error
    createGraph(&graph, 8, true);
    createGraph(&graph, 4, true);

    addNode(&graph, 1);
    addNode(&graph, 2);
    addNode(&graph, 4);

    addNode(&graph1, 1); // error
    addNode(&graph, 0); // error
    addNode(&graph, 5); // error
    addNode(&graph, 3); // error

    addEdge(&graph, 1, 11, 1);
    addEdge(&graph, 1, 12, 2);
    addEdge(&graph, 2, 12, 1);
    addEdge(&graph, 1, 13, 4);

    addEdge(&graph1, 1, 13, 4);
    addEdge(&graph, 0, 14, 5); // error
    addEdge(&graph, 1, -1, 4); // error
    addEdge(&graph, 1, 40, 3); // error
    addEdge(&graph, 3, 40, 4); // error

    viewGraph(&graph);
    viewGraph(&graph1); // error

    removeEdge(&graph, 1, 12, 2);
    removeEdge(&graph1, 1, 12, 2); // error
    removeEdge(&graph, 0, 12, 2); // error
    removeEdge(&graph, 1, 12, 5); // error
    removeEdge(&graph, 3, 12, 2); // error

    removeNode(&graph, 1);
    removeNode(&graph1, 1); // error
    removeNode(&graph, 1); // error
    removeNode(&graph, 3); // error
    removeNode(&graph, 0); // error
    removeNode(&graph, 5); // error

    addEdge(&graph1, 1, 13, 4); // error

    viewGraph(&graph);

    quit(&graph1); // error

    saveGraph(&graph, "graph.txt");
    saveGraph(&graph1, "graph.txt"); // error

    loadGraph(&graph1, "graph.txt");
    loadGraph(&graph1, "graph1.txt"); // error

    viewGraph(&graph1);

    quit(&graph);
    quit(&graph1);

//    initiateMenu();

    return 0;
}