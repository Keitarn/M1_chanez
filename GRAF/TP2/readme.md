##Place yourself in the main folder
###Launches the compialtion (generates libraries and uses them) and install (creates the folders necessary for moving, moves the file.o, the executable, lib.a in the right folders)
`make`
###Run the program
`make execute`
## lib (builds the libraries libgraph.a and liblist.a)
`make lib`
## install (creates the folders necessary for moving, moves the file.o, the executable, lib.a in the right folders)
`make install`
###Valgrind
`make valgrind`
###Clean (deletes the executable + lib.a + file.o in the folder "src")
`make clean`
###Clean All (deletes everything except the source files)
`make cleanAll`