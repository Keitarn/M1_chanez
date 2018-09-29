#include "Menu.h"

void initiateMenu(){
    messageBienvenu();

    struct Graph graph;
    bool stay = true;
    bool create = false;
    int reponse;
    while (stay){
        afficheListCommande();
        printf("Veuillez choisir la commande désiré\n");
        scanf("%i",&reponse);
        viderBuffer();
        switch (reponse) {
            case 1:
                if(create){
                    if(newGraph() == false){
                        break;
                    }
                }
                create = true;
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                stay = false;
                break;
            default :
                printf("aucune commande n'est lié à ce numéro\n");
                break;
        }
    }
}

void  messageBienvenu(){
    printf(" ____  _\n"
           "|  _ \\(_)\n"
           "| |_) |_  ___ _ ____   _____ _ __  _   _\n"
           "|  _ <| |/ _ \\ '_ \\ \\ / / _ \\ '_ \\| | | |\n"
           "| |_) | |  __/ | | \\ V /  __/ | | | |_| |\n"
           "|____/|_|\\___|_| |_|\\_/ \\___|_| |_|\\__,_|\n");
}

void afficheListCommande(){
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
    while((c=getchar()) != EOF && c != '\n');

}

bool newGraph(){
    char command[50];
    strcpy( command, "clear" );
    system(command);
}