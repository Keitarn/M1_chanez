
/***********************************************************************
 * Programme : saisie.c
 *
 * auteur :    JM Nicod
 * 
 * resume :    Saisi un entier sans verification
 *
 * date :      21/02/05
 *
 ***********************************************************************
 */

#include <stdio.h>
#include <stdlib.h>
#include "saisie.h"

/*
 * Fonction :    saisie
 * 
 * Parametres :  int * nombre (parametre pointeur sur entier)
 *
 * Retour :      pas de retour
 *
 * Description : saisi un nombre au clavier sans verification
 *
 */
  
void
saisie(int *nombre) {
  
  printf("saisie d'un nombre : ");
  scanf("%d",nombre);

}
