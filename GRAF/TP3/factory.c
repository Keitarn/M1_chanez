/*
**********************************************************
*
*  Programme : chaine.c
*
*  ecrit par : Pierre Alain Masson et Jean-Marc Nicod
*
*  resume :    Resolution du probleme de la recherche du chemin optinal
*              dans une usine dotee de 2 chaines de montage
*              - resolution recursive (O(2^n))
*              - resolution par prog dynamique (0(n))
*              Instrumentation du code a faire pour tracer les
*              courbes des temps d'exécution en fonction du type de
*              la resolution et de la taille de chaine
*
*  date :      08/03/06
*
***********************************************************
*/

#include <stdio.h>
#include <stdlib.h>

/*
 * Fonction :    hasard
 * 
 * Parametres :  aucun
 *
 * Retour :      un nombre aleatoire inferieur a 21
 *
 * Description : appelle a la fonction random()
 *
 */
int hasard() {
    /* random fournit un nombre aleatoire compris entre 0 et (2**31)-1
     * par consequent, comme ( (2**31)-1 )/1E8 ~ 21 on ici des nombre
     * entre 0 et 21 */
    return(random()/100000000);
}

/*
 * Fonction :    min    
 * 
 * Parametres :  n1 et n2, deux entiers
 *
 * Retour :      la plus petite valeur entre les deux entiers passe en
 *               parametre. 
 *
 * Description : Juste un test 
 *
 */
int min(int n1,int n2) {
    if (n1 > n2)
	return(n2);
    return(n1);
}

/*
 * Fonction :    initChaine
 * 
 * Parametres :  n, la taille de la chaine,
 *               graine, la graine d'initialisation de la sequence
 *               alleatoire 
 *
 * Retour :      les valeurs d'entrees, de sorties de chaines ainsi
 *               que les tableaux de duree des taches et des transports
 *
 * Description : initialisation aleatoire des valeurs resultats.
 * 
 *               ATTENTION : les tableau ont ete dimensionne pour
 *               qu'on les utilise comme des tableaux qui commencent
 *               leurs indices a 1
 *
 */
void initChaine(int n, int graine, int * e, int * x,
                int *** pa, int *** pt, int ***pf) {
    
    int i; /* indice de parcours des tableaux des duree des taches ou
            * des durees de transports d'une chaine a l'autre. */ 

    /* allocation dynamique des chaines */
    /* le dimensionnement est superieur de 1 dans les 2 dimensions
     * pour pouvoir commencer la numerotation a 1 et non a 0, comme
     * dans l'enonce du TD */
    *pa = (int **) malloc ( 3 * sizeof(int *) );
    (*pa)[1] = (int *) malloc ( ( n + 1 ) * sizeof(int) ) ; 
    (*pa)[2] = (int *) malloc ( ( n + 1 ) * sizeof(int) ) ; 
    (*pt) = (int **) malloc ( 3 * sizeof(int *) );
    (*pt)[1] = (int *) malloc ( ( n + 1 ) * sizeof(int) ) ; 
    (*pt)[2] = (int *) malloc ( ( n + 1 ) * sizeof(int) ) ; 
    *pf = (int **) malloc ( 3 * sizeof(int *) );
    (*pf)[1] = (int *) malloc ( ( n + 1 ) * sizeof(int) ) ; 
    (*pf)[2] = (int *) malloc ( ( n + 1 ) * sizeof(int) ) ; 
    
    /* initialisation de la fonction random avec une graine */
    srandom((unsigned long) graine);
    /* intialisation des durees d'entree dans les chaines 1 et 2 */
    e[1] = hasard();
    e[2] = hasard();
    /* intialisation des durees de sortie des chaines 1 et 2 */
    x[1] = hasard();
    x[2] = hasard();
    /* initialisation des durees des taches et des de transport d'une
     * tache a l'autre */
    for ( i = 1 ; i <= n ; i ++ ) {
        (*pa)[1][i] = hasard();
        (*pa)[2][i] = hasard();
        (*pt)[1][i] = hasard();
        (*pt)[2][i] = hasard();
    }
}

/*
 * Fonction :    funcf1
 * 
 * Parametres :  les parametres de l'usine
 *
 * Retour :      la valeur du meilleur temps de traversee de l'usine de
 *               l'entree jusqu'au niveau n donne en parametre de la
 *               premiere chaine.
 *
 * Description : Ce calcul se fait recursivement en tenant compte des
 *               resultats donnes au niveau n-1 des deux chaines.
 *
 */
int funcf1(int n, int * e, int ** a, int ** t) {
    if ( n == 1 )
        return( e[1] + a[1][1] );
    return( min( funcf1( n-1, e, a, t ) + a[1][n],
                 funcf2( n-1, e, a, t ) + t[2][n-1] + a[1][n] ) );
}

/*
 * Fonction :    funcf2
 * 
 * Parametres :  les parametres de l'usine
 *
 * Retour :      la valeur du meilleur temps de traversee de l'usine de
 *               l'entree jusqu'au niveau n donne en parametre de la
 *               deuxieme chaine.
 *
 * Description : Ce calcul se fait recursivement en tenant compte des
 *               resultats donnes au niveau n-1 des deux chaines.
 *
 */
int funcf2( int n, int * e, int ** a, int ** t ) {
    if ( n == 1 )
        return ( e[2] + a[2][1] );
    return( min( funcf2( n-1, e, a, t) + a[2][n],
                 funcf1( n-1, e, a, t) + t[1][n-1] + a[2][n] ) );
}

/*
 * Fonction :    solRecursive
 * 
 * Parametres :  les parametres de l'usine
 *
 * Retour :      le meilleur de temps de traversee de l'usine entre
 *               l'entree et la sortie.
 *
 * Description : le meilleur temps de traversee de l'usine entre
 *               l'entree et la sortie est le min entre les meilleur
 *               temps de sortie des chaines 1 et 2 augmentes des
 *               temps de sortie. Ce calcul est recursif avec une
 *               complexite en O(2**n) si n est le nombre d'etage des
 *               chaines de montage.
 *
 */
int solRecursive( int n, int * e, int * x, int ** a, int ** t ) {
    return( min( funcf1( n, e, a, t) + x[1],
                 funcf2( n, e, a, t) + x[2] ) );
}

/*
 * Fonction :    solProgDyn
 * 
 * Parametres :  les parametres de l'usine
 *
 * Retour :      le meilleur de temps de traversee de l'usine entre
 *               l'entree et la sortie.
 *
 * Description : Le meilleur de temps de traversee de l'usine est
 *               calcul par progrgammation ascendante en commanencant
 *               par le debut des chaine. A l'etape j, on calcule sur
 *               chaque chaine i le meilleur temps de traversee du debut
 *               de l'usine si on sortait au niveau j de la chaine i
 *               en fonction des meileures temps calcules pour des
 *               chaines de longueur j-1.
 *               Le meilleur temps de traversee de l'usine entre
 *               l'entree et la sortie est le min entre les meilleur
 *               temps de sortie des chaines 1 et 2 augmentes des
 *               temps de sortie. Ce calcul repond au paradigme de la
 *               programmation dynamique avec une
 *               complexite en O(n) si n est le nombre d'etage des
 *               chaines de montage.
 *
 */
int solProgDyn(int n, int * e, int * x, int ** a, int ** t, int **f) {
    
    int j;
    
    f[1][1] = e[1] + a[1][1];
    f[2][1] = e[2] + a[2][1];
    
    for ( j = 2 ; j <= n ; j ++ ) {
	if ((f[1][j-1] + a[1][j]) <= (f[2][j-1] + t[2][j-1] + a[1][j]))
            f[1][j] = f[1][j-1] + a[1][j];
	else
            f[1][j] = f[2][j-1] + t[2][j-1] + a[1][j];
	if ((f[2][j-1] + a[2][j]) <= (f[1][j-1] + t[1][j-1] + a[2][j]))
            f[2][j] = f[2][j-1] + a[2][j];
	else
            f[2][j] = f[1][j-1] + t[1][j-1] + a[2][j];
    }
    
    if ((f[1][n] + x[1]) <= (f[2][n] + x[2]))
	return(f[1][n] + x[1]);
    else
	return(f[2][n] + x[2]);
    
}

/* ***************************************************************** */
/*                                     _            _             _  */
/*  _ __  _ __ ___   __ _   _ __  _ __(_)_ __   ___(_)_ __   __ _| | */
/* | '_ \| '__/ _ \ / _` | | '_ \| '__| | '_ \ / __| | '_ \ / _` | | */
/* | |_) | | | (_) | (_| | | |_) | |  | | | | | (__| | |_) | (_| | | */
/* | .__/|_|  \___/ \__, | | .__/|_|  |_|_| |_|\___|_| .__/ \__,_|_| */
/* |_|              |___/  |_|                       |_|             */
/*                                                                   */
/* ***************************************************************** */

int main(int argc, char * argv[]) {
    
    /* declarations */

    int i; /* indice de parcours des tableaux des duree des taches ou
            * des durees de transports d'une chaine a l'autre. */ 

    int sol;              /* valeur de la solution au probleme pose */
    int n;                /* taille de la chaine de montage */
    int graine;           /* graine d'intialisation de la fonction
                           * aleatoire */
    
    int e[3];        /* valeur d'entree dans les chaines de montage 1 et 2 */
    int x[3];        /* valeur de sortie dans les chaines de montage 1
                      * et 2 */
    int ** a;             /* duree des taches sur les chaines 1 et 2 */
    int ** t;             /* duree du transport entre les chaines 1 et 2 */
    int ** f;             /* f[i][j] duree de cheminement optimal sur les 2
                           * chaines pour arrivee en srotie de la tache
                           * j de la chaine i */

    typedef enum { UN, DEUX, TROIS, QUATRE, CINQ, SIX} numero;
    
    /* code */
    
    /* Usage */
    if (argc < 4) {
        printf("usage: factory random-seed d|r factory-size\n");
        exit(-1);
    }
    
    /* initialisation de la taille de chaine de montage */
    n = atoi(argv[3]);
    graine = atoi(argv[1]);


    
    /* initialisation de la chaine de montage alleatoirement avec la
       graine passee en parametre */
    initChaine(n, graine, e, x, &a, &t, &f);
       
    if ( argv[2][0] == 'r' ) {
	sol = solRecursive(n, e, x, a, t);
	printf("The recursive solution is %d\n",sol);
    }
    else {
	sol = solProgDyn(n, e, x, a, t, f);
	printf("The solution with dynamic programming is %d\n",sol);
    }
    return 0;
}
