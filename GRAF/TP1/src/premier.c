
/***********************************************************************
 * Programme : premier.c
 *
 * auteur :    JM Nicod
 * 
 * resume :    Repond a la question : "le nombre saisi est il premier ?"
 *
 * date :      21/02/05
 *
 ***********************************************************************
 */

#include <stdio.h>
#include <stdlib.h>
#include "fctPremier.h"
#include "saisie.h"

/*---------------------*/
/* Programme principal */
/*---------------------*/

int main() {
    
    int nombre;   /* nombre saisi au clavier */
    
    /* saisie du nombre a tester */
    saisie(&nombre);
 
    /* test permettant de savoir si le nombre saisi est premier */
    if (premier(nombre))
        printf("le nombre %d est premier\n",nombre);
    else
        printf("le nombre %d n'est pas premier\n",nombre);
    
}

