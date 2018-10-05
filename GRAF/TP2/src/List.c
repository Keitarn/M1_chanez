/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "List.h"

/**
 * addNeighbourList ajoute un nouveau neighbour dans une liste avec le poids ( weight ) et la node ( to ) donnée en paramètre
 * La liste est retournée avec le nouveau neighbour ajouté
 * @param list
 * @param to
 * @param weight
 * @return
 */
struct Neighbour *addNeighbourList(struct Neighbour *list, int to, int weight) {
    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    if (neighbour == NULL) {
        fprintf(stderr, "Unable to allocate\n");
        exit(EXIT_FAILURE);
    }
    neighbour->neighbour = to;
    neighbour->weigh = weight;
    neighbour->nextNeighbour = list;
    neighbour->previousNeighbour = list->previousNeighbour;
    list->previousNeighbour->nextNeighbour = neighbour;
    list->previousNeighbour = neighbour;
    list = neighbour;
    return list;
}

/**
 * removeNeighbourList retire de la liste le neighbour ayant le poids ( weight ) et la node ( to ) donée en paramètre
 * retourne la liste si un élément à été supprimé et NULL si l'élément n'éxistait pas
 * @param list
 * @param to
 * @param weight
 * @return
 */
struct Neighbour *removeNeighbourList(struct Neighbour *list, int to, int weight) {
    int passage = 0;
    struct Neighbour *parcours = list;
    struct Neighbour *first = list;
    while (parcours->neighbour != -1) {
        passage++;
        if (parcours->weigh == weight && parcours->neighbour == to) {
            parcours->previousNeighbour->nextNeighbour = parcours->nextNeighbour;
            parcours->nextNeighbour->previousNeighbour = parcours->previousNeighbour;
            if (passage == 1) {
                first = parcours->nextNeighbour;
            }
            free(parcours);
            return first;
        }
        parcours = parcours->nextNeighbour;
    }
    return NULL;
}

/**
 * createList créer une nouvelle liste de neighbours possédant un neigbour ayant pour poids -1 et node -1
 * @return
 */
struct Neighbour *createList() {
    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    if (neighbour == NULL) {
        fprintf(stderr, "Unable to allocate\n");
        exit(EXIT_FAILURE);
    }
    neighbour->neighbour = -1;
    neighbour->weigh = -1;
    neighbour->nextNeighbour = neighbour;
    neighbour->previousNeighbour = neighbour;
    return neighbour;
}

/**
 * removeList supprimes tous les élément d'une liste chainé de neigbhour
 * Renvoie NULL une fois que la suppression est terminée
 * @param list
 * @return
 */
struct Neighbour *removeList(struct Neighbour *list) {
    struct Neighbour *current = list;
    struct Neighbour *next;
    while (current->neighbour != -1) {
        next = current->nextNeighbour;
        free(current);
        current = next;
    }
    free(current);
    return NULL;
}

/**
 * removeMultipleNeighbour retire toute les nodes d'une chaine possédant la node ciblé ( to )
 * retourne un pointeur sur le premier élément de la liste chainé
 * @param list
 * @param to
 * @return
 */
struct Neighbour *removeMultipleNeighbour(struct Neighbour *list, int to) {
    struct Neighbour *next;
    struct Neighbour *first = list;
    int passage = 0;
    while (list->neighbour != -1) {
        next = list->nextNeighbour;
        passage++;
        if (list->neighbour == to) {
            list->previousNeighbour->nextNeighbour = next;
            list->nextNeighbour->previousNeighbour = list->previousNeighbour;
            free(list);
            if (passage == 1) {
                first = next;
                passage = 0;
            }
        }
        list = next;
    }
    return first;
}

/**
 * viewList permet d'afficher une liste chainée sur le terminal
 * @param list
 */
void viewList(struct Neighbour *list) {
    struct Neighbour *parcours = list;
    while (parcours->neighbour != -1) {
        printf("(%i: %i), ", parcours->neighbour, parcours->weigh);
        parcours = parcours->nextNeighbour;
    }
}

/**
 * saveList enregistre une liste chainé dans un fichier
 * @param list
 * @param out
 */
void saveList(struct Neighbour *list, FILE *out) {
    struct Neighbour *parcours = list;
    while (parcours->neighbour != -1) {
        fprintf(out, "(%i: %i), ", parcours->neighbour, parcours->weigh);
        parcours = parcours->nextNeighbour;
    }
}