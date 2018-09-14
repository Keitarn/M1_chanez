
/***********************************************************************
 * Programme : fctPremier.c
 *
 * auteur :    JM Nicod
 * 
 * resume :    donne la fonction permettant de savoir si un nombre est
 *             premier
 *
 * date :      21/02/05
 *
 ***********************************************************************
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "fctPremier.h"

/*
 * Fonction :    premier
 * 
 * Parametres :  int nombre (parametre entier)
 *
 * Retour :      1 si le nombre est premier, 0 sinon
 *
 * Description : cherche a un diviseur a nombre inferieur a sa racine
 *               carree
 *
 */
  
int 
premier(int nombre)
{
  /* Declaration */

  int diviseur;        /* diviseur possible pour nombre */
  int stop;            /* pseudo booleen pour l'arret de la recherche */
  int retour;          /* valeur du retour de la fonction */

  /* Code */  
  retour = 0;   /* par defaut nombre n'est pas premier */

  if (!(nombre % 2))
    /* nombre pair */
    retour = 0;
  else{
    diviseur = 3;  /* initialisation au premier diviseur possible pour
		      un nombre impair */
    stop = 0;      /* initialisation de la valeur d'arret de la boucle
		      de recherche du diviseur de nombre */
    
    /* test de tous les diviseurs impairs inferieurs a la racine du
       nombre */
    while ( ( diviseur < sqrt (nombre) ) && !stop ) 
      if (nombre % diviseur) 
	/* diviseur n'est pas un diviseur de nombre */
	diviseur = diviseur + 2;
      else
	stop = 1;

    if (stop == 1)
      retour = 0;
    else
      retour = 1;
  }

  return retour;
}

