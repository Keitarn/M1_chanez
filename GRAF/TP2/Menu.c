/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "Menu.h"

void initiateMenu() {
    messageBienvenu();

    struct Graph * graph;
    bool stay = true;
    bool create = false;
    int res;
    int reponse;
    while (stay) {
        afficheListCommande();
        printf("Veuillez choisir la commande désiré\n");
        scanf("%i", &reponse);
        viderBuffer();
        switch (reponse) {
            case 1:
                res = callCreate(&graph, create);
                if(res == 1){
                    create = true;
                } else {
                    create = false;
                }
                break;
            case 2:
                callAddNode(&graph);
                clear();
                break;
            case 3:
                callDeleteNode(&graph);
                clear();
                break;
            case 4:
                callAddEdge(&graph);
                clear();
                break;
            case 5:
                callDeleteEdge(&graph);
                clear();
                break;
            case 6:
                quit(&graph);
                clear();
                break;
            case 7:
                callViewGraph(&graph);
                clear();
                break;
            case 8:
                callSaveGraph(&graph);
                clear();
                break;
            case 9:
                callLoadGraph(&graph);
                clear();
                break;
            case 10:
                quit(&graph);
                stay = false;
                clear();
                break;
            default :
                printf("aucune commande n'est lié à ce numéro\n");
                clear();
                break;
        }
    }
}

void messageBienvenu() {
    printf(" ____  _\n"
           "|  _ \\(_)\n"
           "| |_) |_  ___ _ ____   _____ _ __  _   _\n"
           "|  _ <| |/ _ \\ '_ \\ \\ / / _ \\ '_ \\| | | |\n"
           "| |_) | |  __/ | | \\ V /  __/ | | | |_| |\n"
           "|____/|_|\\___|_| |_|\\_/ \\___|_| |_|\\__,_|\n");
}

void afficheListCommande() {
    printf("1) Créer un graph\n"
           "2) AJouter une node\n"
           "3) Supprimer une node\n"
           "4) AJouter une edge\n"
           "5) Supprimer une edge\n"
           "6) Supprimer un graph\n"
           "7) Afficher un graph\n"
           "8) Sauvegarder un graph\n"
           "9) Charger un graph\n"
           "10) Quitter\n");
}

void viderBuffer() {
    int c;
    while ((c = getchar()) != EOF && c != '\n');

}

bool newGraph() {
    int rep = 0;
    clear();
    while (rep != 1 && rep != 2) {
        printf("voulez vous supprimez le graph actuel pour en créer un nouveau ?\n1) oui\n2) non\n");
        scanf("%i", &rep);
        viderBuffer();
        if (rep == 1) {
            printf("Le graph a été supprimé\n\n");
            return true;
        }
        if (rep == 2) {
            printf("Le graph n'a pas été supprimé\n\n");
            return false;
        }

    }

}

void clear() {
    //    char command[50];
    //    strcpy( command, "clear" );
    //    system(command);
    printf("\n\n\n\n\n\n\n\n\n\n");
}

int callCreate(struct Graph **graph, bool create) {
    bool isDirected;
    int nbMaxNode ;
    if (create) {
        if (newGraph() == false) {
            return 1;
        }
        quit(graph);
    }
        printf("Entrez le nombre de node maximale\n");
        scanf("%i", &nbMaxNode);
        viderBuffer();

    int reponse = 0;
    while (reponse != 1 && reponse != 2) {
        printf("Le graph est-il orienté ?\n1) oui\n2) non\n");
        scanf("%i", &reponse);
        viderBuffer();
    }
    if (reponse == 1) {
        isDirected = true;
    } else {
        isDirected = false;
    }
    int res = createGraph(graph, nbMaxNode, isDirected);
    nbMaxNode = 0;
    clear();
    return res;
}

void callAddNode(struct Graph **graph) {
    int node;
    printf("Entrez le numéro de node à ajouter\n");
    scanf("%i", &node);
    viderBuffer();
    addNode(graph,node);
}

void callDeleteNode(struct Graph **graph) {
    int node;
    printf("Entrez le numéro de node à supprimer \n");
    scanf("%i", &node);
    viderBuffer();
    addNode(graph,node);
}

void callViewGraph(struct Graph **graph) {
    viewGraph(graph);
}

void callDeleteEdge(struct Graph **graph) {
    int weight;
    int from;
    int to;
    printf("Entrez le numéro de la node de départ \n");
    scanf("%i", &from);
    viderBuffer();
    printf("Entrez le poids de la edge \n");
    scanf("%i", &weight);
    viderBuffer();
    printf("Entrez le numéro de la node d'arrivé \n");
    scanf("%i", &to);
    viderBuffer();
    removeEdge(graph,from,weight,to);
}

void callAddEdge(struct Graph **graph) {
    int weight;
    int from;
    int to;
    printf("Entrez le numéro de la node de départ\n");
    scanf("%i", &from);
    viderBuffer();
    printf("Entrez le poids de la edge\n");
    scanf("%i", &weight);
    viderBuffer();
    printf("Entrez le numéro de la node d'arrivé\n");
    scanf("%i", &to);
    viderBuffer();
    addEdge(graph,from,weight,to);
}

void callLoadGraph(struct Graph **graph) {
    char nameFile[50];
    printf("Entrez le nom du fichier a charger\n");
    scanf("%s", &nameFile);
    viderBuffer();
    loadGraph(graph,nameFile);
}

void callSaveGraph(struct Graph **graph) {
    char nameFile[50];
    printf("Entrez le nom du fichier a sauvegarder\n");
    scanf("%s", &nameFile);
    viderBuffer();
    saveGraph(graph,nameFile);
}
