# 
# trace de deux courbes sur une seule figure
# 
# 1. temps de calcul du meilleur passage d'un bout a l'autre de
#    l'usine en mode r�surcif.
# 2. temps de calcul du meilleur passage d'un bout � l'autre de
#    l'usine en mode recurcif.
#

set size 0.8,0.8
set key left

set xlabel "temps d'execution"
set ylabel "stations"
plot "factory.txt" using 1:4 t "algo iteratif" with lines, \
     "factory.txt" using 1:5 t "algo recursif" with lines

set terminal pdf

set output "perfChaine.pdf"

replot
