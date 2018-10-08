/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "Graph.h"

/**
 * createGraph allows you to create a graph by initializing the type of graph (isDirected), and to allocate memory for the maximum number of nodes (nbMaxNodes).
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @param nbMaxNodes
 * @param isDirected
 * @return
 */
int createGraph(struct Graph **graph, int nbMaxNodes, bool isDirected) {
    if (nbMaxNodes <= 0) {
        fprintf(stderr, "ERROR : createGraph() -> nbMaxNodes : %i, need a positive value\n", nbMaxNodes);
        return -1;
    }
    if ((*graph) != NULL) {
        fprintf(stderr, "WARNING : createGraph() -> the graph has been replaced\n");
        quit(graph);
    }

    (*graph) = (struct Graph *) malloc(sizeof(struct Graph *));
    if ((*graph) == NULL) {
        fprintf(stderr, "Unable to allocate\n");
        exit(EXIT_FAILURE);
    }
    (*graph)->isDirected = isDirected;
    (*graph)->nbMaxNodes = nbMaxNodes;
    (*graph)->adjList = (struct Neighbour **) malloc(nbMaxNodes * sizeof(struct Neighbour *));
    if ((*graph)->adjList == NULL) {
        fprintf(stderr, "Unable to allocate\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < nbMaxNodes; i++) {
        (*graph)->adjList[i] = NULL;
    }
    return 1;
}

/**
 * addNode allows you to add a node (node) to the graph.
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @param node
 * @return
 */
int addNode(struct Graph **graph, int node) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : addNode() -> uncreated graph\n");
        return -1;
    }

    if (!(0 < node && node <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, not included in ]%i,%i]\n", node, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if ((*graph)->adjList[node - 1] != NULL) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, already exists\n", node);
        return -1;
    }

    (*graph)->adjList[node - 1] = createList();
    return 1;
}

/**
 * addEdge allows to add to the graph a bridge between two nodes (from)(to) and to add a weight (weight) .
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @param from
 * @param weight
 * @param to
 * @return
 */
int addEdge(struct Graph **graph, int from, int weight, int to) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> uncreated graph\n");
        return -1;
    }

    if (!(0 < from && from <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addEdge() -> from : %i, not included in ]%i,%i]\n", from, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if (!(0 < to && to <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addEdge() -> to : %i, not included in ]%i,%i]\n", to, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if (weight <= 0) {
        fprintf(stderr, "ERROR : addEdge() -> weight : %i, incorrect weight\n", weight);
        return -1;
    }

    if ((*graph)->adjList[from - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> from : %i, does not exist\n", from);
        return -1;
    }

    if ((*graph)->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> to : %i, does not exist\n", to);
        return -1;
    }

    struct Neighbour *parcours = (*graph)->adjList[from - 1];
    while (parcours->neighbour != -1) {
        if (parcours->weigh == weight && parcours->neighbour == to) {
            fprintf(stderr, "ERROR : addEdge(), already exists\n");
            return -1;
        }
        parcours = parcours->nextNeighbour;
    }

    (*graph)->adjList[from - 1] = addNeighbourList((*graph)->adjList[from - 1], to, weight);
    if (!(*graph)->isDirected && from != to) {
        (*graph)->adjList[to - 1] = addNeighbourList((*graph)->adjList[to - 1], from, weight);
    }
    return 1;
}

/**
 * removeNode allows you to delete a node (node) from the graph.
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @param node
 * @return
 */
int removeNode(struct Graph **graph, int node) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : removeNode() -> uncreated graph\n");
        return -1;
    }

    if (!(0 < node && node <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeNode() -> node : %i, not included in ]%i,%i]\n", node, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if ((*graph)->adjList[node - 1] == NULL) {
        fprintf(stderr, "ERROR : removeNode() -> node : %i, does not exist\n", node);
        return -1;
    }

    (*graph)->adjList[node - 1] = removeList((*graph)->adjList[node - 1]);
    for (int i = 1; i <= (*graph)->nbMaxNodes; i++) {
        if ((*graph)->adjList[i - 1] == NULL) {
            continue;
        }
        (*graph)->adjList[i - 1] = removeMultipleNeighbour((*graph)->adjList[i - 1], node);
    }
    return 1;
}

/**
 * removeNode allows you to remove a bridge between two nodes (from)(to) with the corresponding weight (weight) from the graph.
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @param from
 * @param weight
 * @param to
 * @return
 */
int removeEdge(struct Graph **graph, int from, int weight, int to) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> uncreated graph\n");
        return -1;
    }

    if (!(0 < from && from <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeEdge() -> from : %i, not included in ]%i,%i]\n", from, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if (!(0 < to && to <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeEdge() -> to : %i, not included in ]%i,%i]\n", to, 0, (*graph)->nbMaxNodes);
        return -1;
    }

    if (weight <= 0) {
        fprintf(stderr, "ERROR : removeEdge() -> weight : %i, incorrect weight\n", weight);
        return -1;
    }

    if ((*graph)->adjList[from - 1] == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> from : %i, does not exist\n", from);
        return -1;
    }

    if ((*graph)->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> to : %i, does not exist\n", to);
        return -1;
    }

    struct Neighbour *test = removeNeighbourList((*graph)->adjList[from - 1], to, weight);
    if (test == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> (node,edge) : (%i,%i) , does not exist\n", to, weight);
    } else {
        (*graph)->adjList[from - 1] = test;
    }
    return 1;
}

/**
 * viewGraph allows you to display the graph in the console.
 * @param graph
 */
void viewGraph(struct Graph **graph) {
    if ((*graph) == NULL) {
        return;
    }

    printf("# maximum number of nodes\n");
    printf("%i\n", (*graph)->nbMaxNodes);
    printf("# directed\n");
    printf("%s\n", ((*graph)->isDirected ? "y" : "n"));
    printf("# node: neighbours\n");
    for (int i = 0; i < (*graph)->nbMaxNodes; ++i) {
        if ((*graph)->adjList[i] == NULL) {
            continue;
        }
        printf("%i: ", i + 1);
        viewList((*graph)->adjList[i]);
        printf("\n");
    }
}

/**
 * loadGraph allows you to load a graph saved in a file with its path (path);
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @param path
 * @return
 */
int loadGraph(struct Graph **graph, char *path) {
    FILE *in;
    in = fopen(path, "r");
    if (!in) {
        fprintf(stderr, "ERROR : loadGraph() -> fopen()\n");
        return -1;
    }

    int position = 0;
    char buffer[200];
    int indice = -1;
    int nbMaxNodes = 0;
    bool isDirected = false;
    int *nodes = NULL;
    int *tabWeight = NULL;
    int *tabTo = NULL;

    // we assume that the files are exactly the same format so we zaap the comment lines and read what we are interested in.
    while (fgets(buffer, 200, in) != NULL) {
        indice++;
        switch (indice) {
            case 0:
            case 2:
            case 4:
                break;
            case 1:
                nbMaxNodes = atoi(strtok(buffer, "\n"));
                break;
            case 3:
                isDirected = buffer[0] == 'y';
                break;
            default:
		// all nodes and bridges are added to tables that will be used to create the graph.
                nodes = realloc(nodes, (indice - 4) * sizeof(int));
                int parcoursChaine = 0;
                int node = (int) buffer[parcoursChaine] - '0';
                parcoursChaine++;
                while (buffer[parcoursChaine] != ':') {
                    node *= 10;
                    node += (int) buffer[parcoursChaine] - '0';
                    parcoursChaine++;
                }
                nodes[indice - 5] = node;

                int weight = 0;
                int to = 0;
                int tailleChaine = (int) strlen(buffer);
                for (parcoursChaine = parcoursChaine; parcoursChaine < tailleChaine; parcoursChaine++) {
                    if (buffer[parcoursChaine] == '(') {
                        parcoursChaine++;
                        to = (int) buffer[parcoursChaine] - '0';
                        parcoursChaine++;
                        while (buffer[parcoursChaine] != ':') {
                            to *= 10;
                            to += (int) buffer[parcoursChaine] - '0';
                            parcoursChaine++;
                        }
                        parcoursChaine += 2;
                        weight = (int) buffer[parcoursChaine] - '0';
                        parcoursChaine++;
                        while (buffer[parcoursChaine] != ')') {
                            weight *= 10;
                            weight += (int) buffer[parcoursChaine] - '0';
                            parcoursChaine++;
                        }

                        tabWeight = realloc(tabWeight, (position + 1) * sizeof(int));
                        tabTo = realloc(tabTo, (position + 1) * sizeof(int));
                        tabWeight[position] = weight;
                        tabTo[position] = to;
                        position++;
                    }

                }

                tabWeight = realloc(tabWeight, (position + 1) * sizeof(int));
                tabTo = realloc(tabTo, (position + 1) * sizeof(int));
                tabWeight[position] = -1;
                tabTo[position] = -1;
                position++;
                break;
        }
    }
    if ((*graph) != NULL) {
        quit(graph);
    }

    // add nodes
    createGraph(graph, nbMaxNodes, isDirected);
    for (int i = 0; i < indice - 4; ++i) {
        addNode(graph, nodes[i]);

    }

    // add edges
    int node = 0;
    for (int i = 0; i < position; ++i) {
        if (tabTo[i] == -1) {
            node++;
        } else {
            addEdge(graph, nodes[node], tabWeight[i], tabTo[i]);
        }
    }
    free(tabTo);
    free(tabWeight);
    free(nodes);
    return 1;
}

/**
 * saveGraph allows you to save the graph in a file with its path (path).
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @param path
 * @return
 */
int saveGraph(struct Graph **graph, char *path) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : saveGraph() -> uncreated graph\n");
        return -1;
    }

    FILE *out;
    out = fopen(path, "w+");
    if (!out) {
        fprintf(stderr, "ERROR : saveGraph() -> fopen()\n");
        return -1;
    }

    fprintf(out, "# maximum number of nodes\n");
    fprintf(out, "%i\n", (*graph)->nbMaxNodes);
    fprintf(out, "# directed\n");
    fprintf(out, "%s\n", ((*graph)->isDirected ? "y" : "n"));
    fprintf(out, "# node: neighbours\n");
    for (int i = 0; i < (*graph)->nbMaxNodes; ++i) {
        if ((*graph)->adjList[i] == NULL) {
            continue;
        }
        fprintf(out, "%i: ", i + 1);
        saveList((*graph)->adjList[i], out);
        fprintf(out, "\n");
    }
    fclose(out);
    return 1;
}

/**
 * quit allows you to unallocate the memory allocated for the graph.
 * If the function has unexpected settings or a problem returns nothing.
 * @param graph
 */
void quit(struct Graph **graph) {
    if ((*graph) == NULL) {
        return;
    }

    for (int i = 0; i < (*graph)->nbMaxNodes; i++) {
        if ((*graph)->adjList[i] == NULL) {
            continue;
        }
        (*graph)->adjList[i] = removeList((*graph)->adjList[i]);
    }
    free((*graph)->adjList);
    free((*graph));
    *graph = NULL;
}
