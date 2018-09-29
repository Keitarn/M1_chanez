#include "Graph.h"


void createGraph(struct Graph *graph, int nbMaxNodes, bool isDirected) {
    graph->isDirected = isDirected;
    graph->nbMaxNodes = nbMaxNodes;
    graph->adjList = (struct Neighbour **) malloc(graph->nbMaxNodes * sizeof(struct Neighbour *));
}

void addNode(struct Graph *graph, int node) {
    if (!(0 < node && node <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, non comprise dans ]%i,%i]:\n", node, 0,
                graph->nbMaxNodes);
        return;
    }
    if (graph->adjList[node - 1] != NULL) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, existe déjà: \n", node);
        return;
    }

    graph->adjList[node - 1] = createList();
}

void addEdge(struct Graph *graph, int from, int weight, int to) {
    if (!(0 < from && from <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addEdge() -> from : %i, non comprise dans ]%i,%i]:\n", from, 0,
                graph->nbMaxNodes);
        return;
    }
    if (!(0 < to && to <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addEdge() -> to : %i, non comprise dans ]%i,%i]:\n", to, 0,
                graph->nbMaxNodes);
        return;
    }
    if (weight <= 0) {
        fprintf(stderr, "ERROR : addEdge() -> weight : %i, poids incorrect\n", weight);
        return;
    }
    if (graph->adjList[from - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> from : %i, n'existe pas\n", from);
        return;
    }
    if (graph->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> to : %i, n'existe pas\n", to);
        return;
    }

    struct Neighbour *parcours = graph->adjList[from - 1];
    while (parcours->neighbour != -1) {
        if (parcours->weigh == weight && parcours->neighbour == to) {
            fprintf(stderr, "ERROR : addEdge(), existe déja\n");
            return;
        }
        parcours = parcours->nextNeighbour;
    }

    graph->adjList[from - 1] = addNeighbourList(graph->adjList[from - 1], to, weight);

    if (graph->isDirected == false && from != to) {

        graph->adjList[to - 1] = addNeighbourList(graph->adjList[to - 1], from, weight);

    }
}

void removeNode(struct Graph *graph, int node) {
    if (!(0 < node && node <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeNode() -> node : %i, non comprise dans ]%i,%i]:\n", node, 0,
                graph->nbMaxNodes);
        return;
    }

    if (graph->adjList[node - 1] == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> node : %i, n'existe pas\n", node);
        return;
    }

    graph->adjList[node - 1] = removeList(graph->adjList[node - 1]);

    for (int i = 1; i <= graph->nbMaxNodes; i++) {
        if (graph->adjList[i - 1] == NULL) {
            continue;
        }
        graph->adjList[i - 1] = removeMultipleNeighbour(graph->adjList[i - 1], node);
    }
}

void removeEdge(struct Graph *graph, int from, int weight, int to) {
    if (!(0 < from && from <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeEdge() -> from : %i, non comprise dans ]%i,%i]:\n", from, 0, graph->nbMaxNodes);
        return;
    }
    if (!(0 < to && to <= graph->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeEdge() -> to : %i, non comprise dans ]%i,%i]:\n", to, 0, graph->nbMaxNodes);
        return;
    }
    if (weight <= 0) {
        fprintf(stderr, "ERROR : removeEdge() -> weight : %i, poids incorrect\n", weight);
        return;
    }
    if (graph->adjList[from - 1] == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> from : %i, n'existe pas\n", from);
        return;
    }
    if (graph->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> to : %i, n'existe pas\n", to);
        return;
    }

    struct Neighbour *test = removeNeighbourList(graph->adjList[from - 1], to, weight);
    if (test == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> (node,edge) : (%i,%i) , n'existe pas\n", to, weight);
    } else {
        graph->adjList[from - 1] = test;
    }
}

void viewGraph(struct Graph *graph) {
    printf("# maximum number of nodes\n");
    printf("%i\n", graph->nbMaxNodes);
    printf("# directed\n");
    printf("%s\n", (graph->isDirected ? "y" : "n"));
    printf("# node: neighbours\n");
    for (int i = 0; i < graph->nbMaxNodes; ++i) {
        if (graph->adjList[i] == NULL) {
            continue;
        }
        printf("%i: ", i + 1);
        viewList(graph->adjList[i]);
        printf("\n");
    }
}

void loadGraph(struct Graph *graph, char *path) {
    FILE *in;
    in = fopen(path, "r");
    if (!in) {
        fprintf(stderr, "ERROR : loadGraph() -> fopen()\n");
        return;
    }

    int position = 0;
    char buffer[200];
    int indice = -1;
    int nbMaxNodes = 0;
    bool isDirected = false;
    int *nodes = NULL;
    int *tabWeight = NULL;
    int *tabTo = NULL;

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

                isDirected = buffer[0] == 'y' ? true : false;

                break;
            default:
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
                int tailleChaine = strlen(buffer);
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

    createGraph(graph, nbMaxNodes, isDirected);
    for (int i = 0; i < indice - 4; ++i) {
        addNode(graph, nodes[i]);

    }
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
}

void saveGraph(struct Graph *graph, char *path) {
    FILE *out;
    out = fopen(path, "w+");
    if (!out) {
        fprintf(stderr, "ERROR : saveGraph() -> fopen()\n");
        return;
    }

    fprintf(out, "# maximum number of nodes\n");
    fprintf(out, "%i\n", graph->nbMaxNodes);
    fprintf(out, "# directed\n");
    fprintf(out, "%s\n", (graph->isDirected ? "y" : "n"));
    fprintf(out, "# node: neighbours\n");
    for (int i = 0; i < graph->nbMaxNodes; ++i) {
        if (graph->adjList[i] == NULL) {
            continue;
        }
        fprintf(out, "%i: ", i + 1);
        saveList(graph->adjList[i], out);
        fprintf(out, "\n");
    }
    fclose(out);
}

void quit(struct Graph *graph) {
    for (int i = 0; i < graph->nbMaxNodes; i++) {
        if (graph->adjList[i] == NULL) {
            continue;
        }
        graph->adjList[i] = removeList(graph->adjList[i]);
    }

    free(graph->adjList);
}