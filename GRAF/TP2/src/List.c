/**
 * @authors Florian Joriot, Bastien Chanez
 */

#include "List.h"

/**
 * addNeighbourList  adds a new neighbour to a list with the weight (weight) and the node (to) given as a parameter
 * The list is returned with the new neighbour added
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
 * removeNeighbourList removes from the list the neighbour having the weight (weight) and the node (to) given as a parameter
 * returns the list if an element has been deleted and NULL if the element did not exist
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
 * createList create a new list of neighbours with a neigbour with a weight of -1 and node -1
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
 * removeList delete all items from a chain list of neigbhour
 * Returns NULL once the deletion is complete
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
 * removeMultipleNeighbour removes all nodes from a string with the targeted node (to)
 * returns a pointer to the first item in the chained list
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
 * viewList allows you to display a chained list on the terminal
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
 * saveList saves a chained list in a file
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