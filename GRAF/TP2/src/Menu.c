/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "Menu.h"
 /**
  * initiateMenu appel une fonction qui affiche un message de bienvenu, permet aussi de choisir les actions à faire sur le graph et appeller les fonctions correspondantes
  */
void initiateMenu() {
    messageBienvenu();

    struct Graph * graph = NULL;
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
                break;
            case 7:
                callViewGraph(&graph);
                break;
            case 8:
                callSaveGraph(&graph);
                break;
            case 9:
                callLoadGraph(&graph);
                break;
            case 10:
                quit(&graph);
                stay = false;
                break;
            default :
                printf("aucune commande n'est lié à ce numéro\n");
                break;
        }
    }
}

/**
 * messageBienvenu affiche un art ASCII de bienvenu sur la sortie standart
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
 * afficheListCommande propose une liste de choix d'actions possibles pour le graph
 */
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

/**
 * viderBuffer permet de retirer tous les caractères présent dans l'entré standart pour éviter les problèmes
 */
void viderBuffer() {
    int c;
    while ((c = getchar()) != EOF && c != '\n');

}

/**
 * newGraph permet de faire une vérification lors de la demande de création de graph, si un graph existe deja on s'assure que l'utilisateur veuille le remplacer
 * @return
 */
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
    }
    printf("Le graph n'a pas été supprimé\n\n");
    return false;

}

/**
 * clear permet de rendre plus lisible les informations du terminale
 */
void clear() {
    printf("\n###########################################\n\n");
}

/**
 * callCreate récupère les informations nécéssaires et appels la fonction de création de graph
 * @param graph
 * @param create
 * @return
 */
int callCreate(struct Graph **graph, bool create) {
    bool isDirected;
    int nbMaxNode ;
    if (create) {
        if (newGraph() == false) {
            return 1;
        }
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
    clear();
    int res = createGraph(graph, nbMaxNode, isDirected);
    nbMaxNode = 0;
    return res;
}

/**
 * callAddNode récupère les informations nécéssaires et appels la fonction d'ajout de node
 * @param graph
 */
void callAddNode(struct Graph **graph) {
    int node;
    printf("Entrez le numéro de node à ajouter\n");
    scanf("%i", &node);
    viderBuffer();
    clear();
    addNode(graph,node);
}

/**
 * callDeleteNode récupère les informations nécéssaires et appels la fonction de suppression de node
 * @param graph
 */
void callDeleteNode(struct Graph **graph) {
    int node;
    printf("Entrez le numéro de node à supprimer \n");
    scanf("%i", &node);
    viderBuffer();
    clear();
    addNode(graph,node);
}

/**
 * callViewGraph appel la fonction d'affichage du graph
 * @param graph
 */
void callViewGraph(struct Graph **graph) {
    viewGraph(graph);
}

/**
 *récupère les informations nécéssaires et appels la fonction de suppression de edge
 * @param graph
 */
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
    clear();
    removeEdge(graph,from,weight,to);
}

/**
 * callAddEdge récupère les informations nécéssaires et appels la fonction d'ajout de edge
 * @param graph
 */
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
    clear();
    addEdge(graph,from,weight,to);
}

/**
 * callLoadGraph récupère les informations nécéssaires et appels la fonction de chargement d'un graph
 * @param graph
 */
void callLoadGraph(struct Graph **graph) {
    char nameFile[50];
    printf("Entrez le nom du fichier a charger\n");
    scanf("%s", nameFile);
    viderBuffer();
    clear();
    loadGraph(graph,nameFile);
}

/**
 * callSaveGraph récupère les informations nécéssaires et appels la fonction de sauvegarde d'un graph
 * @param graph
 */
void callSaveGraph(struct Graph **graph) {
    char nameFile[50];
    printf("Entrez le nom du fichier a sauvegarder\n");
    scanf("%s", nameFile);
    viderBuffer();
    clear();
    saveGraph(graph,nameFile);
}
