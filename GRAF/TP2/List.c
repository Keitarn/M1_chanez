#include "List.h"

struct Neighbour *addNeighbourList(struct Neighbour *list, int to, int weight) {
    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->neighbour = to;
    neighbour->weigh = weight;
    neighbour->nextNeighbour = list;
    neighbour->previousNeighbour = list->previousNeighbour;
    list->previousNeighbour->nextNeighbour = neighbour;
    list->previousNeighbour = neighbour;
    list = neighbour;
}

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

struct Neighbour *createList() {
    struct Neighbour *neighbour = (struct Neighbour *) malloc(sizeof(struct Neighbour));
    neighbour->neighbour = -1;
    neighbour->weigh = -1;
    neighbour->nextNeighbour = neighbour;
    neighbour->previousNeighbour = neighbour;
    return neighbour;
}

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


void viewList(struct Neighbour *list) {
    struct Neighbour *parcours = list;
    while (parcours->neighbour != -1) {
        printf("(%i: %i), ", parcours->neighbour, parcours->weigh);
        parcours = parcours->nextNeighbour;
    }
}

void saveList(struct Neighbour *list, FILE *out) {
    struct Neighbour *parcours = list;
    while (parcours->neighbour != -1) {
        fprintf(out, "(%i: %i), ", parcours->neighbour, parcours->weigh);
        parcours = parcours->nextNeighbour;
    }
}