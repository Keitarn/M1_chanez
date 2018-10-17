/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "Menu.h"

/**
 * initiateMenu calls a function that displays a welcome message, also allows you to choose the actions to do on the graph and call the corresponding functions
 */
void initiateMenu() {
    messageBienvenu();

    struct Graph *graph = NULL;
    bool stay = true;
    bool create = false;
    int res;
    int reponse;
    while (stay) {
        afficheListCommande();
        printf("Please select the desired order\n");
        scanf("%i", &reponse);
        viderBuffer();
        switch (reponse) {
            case 1:
                res = callCreate(&graph, create);
                if (res == 1) {
                    create = true;
                } else {
                    create = false;
                }
                break;
            case 2:
                callAddNode(&graph);
                break;
            case 3:
                callDeleteNode(&graph);
                break;
            case 4:
                callAddEdge(&graph);
                break;
            case 5:
                callDeleteEdge(&graph);
                break;
            case 6:
                quit(&graph);
                create = false;
                clear();
                break;
            case 7:
                callViewGraph(&graph);
                break;
            case 8:
                callSaveGraph(&graph);
                break;
            case 9:
                if (callLoadGraph(&graph) == 1) {
                    create = true;
                } else {
                    create = false;
                }
                break;
            case 10:
                quit(&graph);
                stay = false;
                break;
            default :
                printf("no orders are linked to this number\n");
                break;
        }
    }
}

/**
 * messageBienvenu displays a welcome ASCII art on the standard output
 */
void messageBienvenu() {
    printf("\n\n __          __  _                          \n"
           " \\ \\        / / | |                         \n"
           "  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___ \n"
           "   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\\n"
           "    \\  /\\  /  __/ | (_| (_) | | | | | |  __/\n"
           "     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|\n\n\n");
}

/**
 * afficheListCommande proposes a list of possible actions for the graph
 */
void afficheListCommande() {
    printf("1) Create a graph\n"
           "2) Add a node\n"
           "3) Delete a node\n"
           "4) Add an edge\n"
           "5) Delete an edge\n"
           "6) Delete a graph\n"
           "7) Display a graph\n"
           "8) Save a graph\n"
           "9) Load a graph\n"
           "10) Exit\n");
}

/**
 * viderBuffer allows to remove all characters present in the standard entry to avoid problems
 */
void viderBuffer() {
    int c;
    while ((c = getchar()) != EOF && c != '\n');

}

/**
 * newGraph allows to check when requesting graph creation, if a graph already exists, we make sure that the user wants to replace it
 * @return
 */
bool newGraph() {
    int rep = 0;
    clear();
    while (rep != 1 && rep != 2) {
        printf("Do you want to delete the current graph to create a new one ?\n1) yes\n2) no\n");
        scanf("%i", &rep);
        viderBuffer();
        if (rep == 1) {
            printf("The graph has been deleted\n\n");
            return true;
        }
    }
    printf("The graph has not been deleted\n\n");
    return false;

}

/**
 * clear makes it easier to read the terminal information
 */
void clear() {
    printf("\n###########################################\n\n");
}

/**
 * callCreate retrieves the necessary information and calls the graph creation function
 * @param graph
 * @param create
 * @return
 */
int callCreate(struct Graph **graph, bool create) {
    bool isDirected;
    int nbMaxNode;
    char nbMaxNodeS[50];
    char reponseS[50];
    char *endS;

    if (create) {
        if (!newGraph()) {
            return 1;
        }
    }
    printf("Enter the maximum number of nodes\n");
    scanf("%s", nbMaxNodeS);
    nbMaxNode = strtol(nbMaxNodeS,&endS,10);

    viderBuffer();

    int reponse = 0;
    while (reponse != 1 && reponse != 2) {
        printf("Is the graph oriented ?\n1) yes\n2) no\n");
        scanf("%s", reponseS);
        reponse = strtol(reponseS,&endS,10);
        viderBuffer();
    }
    if (reponse == 1) {
        isDirected = true;
    } else {
        isDirected = false;
    }
    clear();
    int res = createGraph(graph, nbMaxNode, isDirected);
    nbMaxNode = 0;
    return res;
}

/**
 * callAddNode retrieves the necessary information and calls the node addition function
 * @param graph
 */
void callAddNode(struct Graph **graph) {
    char NodeS[50];
    char *endS;
    int node;
    printf("Enter the node number to add\n");
    scanf("%s", NodeS);
    node = strtol(NodeS,&endS,10);
    viderBuffer();
    clear();
    addNode(graph, node);
}

/**
 * callDeleteNode  retrieves the necessary information and calls the node deletion function
 * @param graph
 */
void callDeleteNode(struct Graph **graph) {
    char NodeS[50];
    char *endS;
    int node;
    printf("Enter the node number to be deleted \n");
    scanf("%s", NodeS);
    node = strtol(NodeS,&endS,10);
    viderBuffer();
    clear();
    removeNode(graph, node);
}

/**
 * callViewGraph calls the graph display function
 * @param graph
 */
void callViewGraph(struct Graph **graph) {
    viewGraph(graph);
    printf("\n");
}

/**
 * callDeleteEdge retrieves the necessary information and calls the edge deletion function
 * @param graph
 */
void callDeleteEdge(struct Graph **graph) {
    int weight;
    int from;
    int to;
    char weightS[50];
    char fromS[50];
    char toS[50];
    char *endS;

    printf("Enter the number of the starting node\n");
    scanf("%s", fromS);
    from = strtol(fromS,&endS,10);
    viderBuffer();

    printf("Enter the weight of the edge\n");
    scanf("%s", weightS);
    weight = strtol(weightS,&endS,10);
    viderBuffer();


    printf("Enter the number of the arrival node\n");
    scanf("%s", toS);
    to = strtol(toS,&endS,10);
    viderBuffer();

    clear();
    removeEdge(graph, from, weight, to);
}

/**
 * callAddEdge retrieves the necessary information and calls the edge add function
 * @param graph
 */
void callAddEdge(struct Graph **graph) {
    int weight;
    int from;
    int to;
    char weightS[50];
    char fromS[50];
    char toS[50];
    char *endS;

    printf("Enter the number of the starting node\n");
    scanf("%s", fromS);
    from = strtol(fromS,&endS,10);
    viderBuffer();

    printf("Enter the weight of the edge\n");
    scanf("%s", weightS);
    weight = strtol(weightS,&endS,10);
    viderBuffer();


    printf("Enter the number of the arrival node\n");
    scanf("%s", toS);
    to = strtol(toS,&endS,10);
    viderBuffer();

    clear();
    addEdge(graph, from, weight, to);
}

/**
 * callLoadGraph retrieves the necessary information and calls the graph loading function
 * If the function has unexpected parameters or a problem returns -1 and if all is well passed return 1.
 * @param graph
 * @return
 */
int callLoadGraph(struct Graph **graph) {
    char nameFile[50];
    printf("Enter the name of the file to be loaded\n");
    scanf("%s", nameFile);
    viderBuffer();
    clear();
    return (loadGraph(graph, nameFile));
}

/**
 * callSaveGraph retrieves the necessary information and calls the backup function of a graph
 * @param graph
 */
void callSaveGraph(struct Graph **graph) {
    char nameFile[50];
    printf("Enter the name of the file to be saved\n");
    scanf("%s", nameFile);
    viderBuffer();
    clear();
    saveGraph(graph, nameFile);
}
