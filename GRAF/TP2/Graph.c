#include "Graph.h"


int createGraph(struct Graph **graph, int nbMaxNodes, bool isDirected) {

    if (nbMaxNodes <= 0) {
        fprintf(stderr, "ERROR : createGraph() -> nbMaxNodes : %i, need a positive value: \n", nbMaxNodes);
        return -1;
    }
    if ((*graph) != NULL) {
        fprintf(stderr, "WARNING : createGraph() -> le graph a été remplacé\n");
        quit(graph);
    }

    (*graph) = (struct Graph *) malloc(sizeof(struct Graph *));
    (*graph)->isDirected = isDirected;
    (*graph)->nbMaxNodes = nbMaxNodes;
    (*graph)->adjList = (struct Neighbour **) malloc(nbMaxNodes * sizeof(struct Neighbour *));
    for(int i = 0; i < nbMaxNodes; i++){
        (*graph)->adjList[i] = NULL;
    }
    return 1;
}

int addNode(struct Graph **graph, int node) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : addNode() -> graph non cree\n");
        return -1;
    }

    if (!(0 < node && node <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, non comprise dans ]%i,%i]\n", node, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if ((*graph)->adjList[node - 1] != NULL) {
        fprintf(stderr, "ERROR : addNode() -> node : %i, existe déjà\n", node);
        return -1;
    }

    (*graph)->adjList[node - 1] = createList();
    return 1;
}

int addEdge(struct Graph **graph, int from, int weight, int to) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> graph non cree\n");
        return -1;
    }

    if (!(0 < from && from <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addEdge() -> from : %i, non comprise dans ]%i,%i]\n", from, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if (!(0 < to && to <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : addEdge() -> to : %i, non comprise dans ]%i,%i]\n", to, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if (weight <= 0) {
        fprintf(stderr, "ERROR : addEdge() -> weight : %i, poids incorrect\n", weight);
        return -1;
    }

    if ((*graph)->adjList[from - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> from : %i, n'existe pas\n", from);
        return -1;
    }

    if ((*graph)->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : addEdge() -> to : %i, n'existe pas\n", to);
        return -1;
    }

    struct Neighbour *parcours = (*graph)->adjList[from - 1];
    while (parcours->neighbour != -1) {
        if (parcours->weigh == weight && parcours->neighbour == to) {
            fprintf(stderr, "ERROR : addEdge(), existe déja\n");
            return -1;
        }
        parcours = parcours->nextNeighbour;
    }

    (*graph)->adjList[from - 1] = addNeighbourList((*graph)->adjList[from - 1], to, weight);
    if ((*graph)->isDirected == false && from != to) {
        (*graph)->adjList[to - 1] = addNeighbourList((*graph)->adjList[to - 1], from, weight);
    }
    return 1;
}

int removeNode(struct Graph **graph, int node) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : removeNode() -> graph non cree\n");
        return -1;
    }

    if (!(0 < node && node <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeNode() -> node : %i, non comprise dans ]%i,%i]\n", node, 0,
                (*graph)->nbMaxNodes);
        return -1;
    }

    if ((*graph)->adjList[node - 1] == NULL) {
        fprintf(stderr, "ERROR : removeNode() -> node : %i, n'existe pas\n", node);
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

int removeEdge(struct Graph **graph, int from, int weight, int to) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> graph non cree\n");
        return -1;
    }

    if (!(0 < from && from <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeEdge() -> from : %i, non comprise dans ]%i,%i]\n", from, 0, (*graph)->nbMaxNodes);
        return -1;
    }

    if (!(0 < to && to <= (*graph)->nbMaxNodes)) {
        fprintf(stderr, "ERROR : removeEdge() -> to : %i, non comprise dans ]%i,%i]\n", to, 0, (*graph)->nbMaxNodes);
        return -1;
    }

    if (weight <= 0) {
        fprintf(stderr, "ERROR : removeEdge() -> weight : %i, poids incorrect\n", weight);
        return -1;
    }

    if ((*graph)->adjList[from - 1] == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> from : %i, n'existe pas\n", from);
        return -1;
    }

    if ((*graph)->adjList[to - 1] == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> to : %i, n'existe pas\n", to);
        return -1;
    }

    struct Neighbour *test = removeNeighbourList((*graph)->adjList[from - 1], to, weight);
    if (test == NULL) {
        fprintf(stderr, "ERROR : removeEdge() -> (node,edge) : (%i,%i) , n'existe pas\n", to, weight);
    } else {
        (*graph)->adjList[from - 1] = test;
    }
    return 1;
}

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
    if (graph != NULL) {
        quit(graph);
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
    return 1;
}

int saveGraph(struct Graph **graph, char *path) {
    if ((*graph) == NULL) {
        fprintf(stderr, "ERROR : saveGraph() -> graph non cree\n");
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

void quit(struct Graph **graph) {
    if ((*graph) == NULL) {
        return;
    }

    (*graph)->nbMaxNodes = 0;
    for (int i = 0; i < (*graph)->nbMaxNodes; i++) {
        if ((*graph)->adjList[i] == NULL) {
            continue;
        }
        (*graph)->adjList[i] = removeList((*graph)->adjList[i]);
    }
    free((*graph)->adjList);
    free((*graph));
}
