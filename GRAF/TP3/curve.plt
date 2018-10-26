# 2)Temps en fonction du nombre de messages a volume constant

# Pour sortir les courbes en LaTeX
set terminal latex

set output "courbe.tex"
set size 0.8,0.8
set key 20,160


set xlabel "taille des messages (centaine d'octets)"
set ylabel "temps en secondes"
plot "pvm.dat" using 1:3 t "pvm" with lines, \
     "cool.dat" using 1:3 t "cool" with lines, \
     "mpi.dat" using 1:3 t "mpi" with lines, \
     "socket.dat" using 1:2 t "socket" with lines




