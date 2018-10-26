# 
# trace de deux courbes sur une seule figure
# 
# 1. temps de calcul du meilleur passage d'un bout a l'autre de
#    l'usine en mode résurcif.
# 2. temps de calcul du meilleur passage d'un bout à l'autre de
#    l'usine en mode recurcif.
#

set size 0.8,0.8
set key left

set xlabel "taille de l'usine"
set ylabel "temps en ms"
plot "chaine.txt" using 1:4 t "algo iteratif" with lines, \
     "chaine.txt" using 1:5 t "algo recursif" with lines

set terminal pdf

set output "perfChaine.pdf"

replot
