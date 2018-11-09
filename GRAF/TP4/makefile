EXENAME := app
ARG :=

DIRECSRC := ./src
DIRECBIN := ./bin
DIRECOBJ := ./obj
DIRECLIB := ./lib

CC := gcc
CFLAGS := -Wall -Wextra -g

LIBS := -lm
INCLUDES :=

SRC := $(shell find $(DIRECSRC) -name '*.c')
OBJ := $(SRC:.c=.o)


all: $(EXENAME) install clean

$(EXENAME): $(DIRECSRC)/main.o $(DIRECSRC)/Menu.o lib
	@echo "** Building the game"
	$(CC) -o $(DIRECSRC)/$@ $(DIRECSRC)/main.o $(DIRECSRC)/Menu.o $(LIBS) $(DIRECSRC)/libgraph.a $(DIRECSRC)/liblist.a

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) -o $@ -c $<

install:
	@echo "** Installing..."
	sh ./create_directories.sh
	cp $(DIRECSRC)/$(EXENAME) $(DIRECBIN) || :
	cp $(OBJ) $(DIRECOBJ) || :
	cp $(DIRECSRC)/libgraph.a $(DIRECLIB) || :
	cp $(DIRECSRC)/liblist.a $(DIRECLIB) || :
	chmod u+x $(DIRECBIN)/$(EXENAME) || :

lib: libgraph.a liblist.a

libgraph.a: $(DIRECSRC)/Graph.o
	ar rcs $(DIRECSRC)/libgraph.a $(DIRECSRC)/Graph.o

liblist.a: $(DIRECSRC)/List.o
	ar rcs $(DIRECSRC)/liblist.a $(DIRECSRC)/List.o

valgrind:
	@echo "** Valgrind"
	valgrind --leak-check=full --leak-resolution=med  --track-origins=yes $(DIRECBIN)/$(EXENAME)

clean:
	@echo "** Removing object files and executable and libs in src..."
	rm -f $(OBJ)
	rm -f $(DIRECSRC)/$(EXENAME)
	rm -f $(DIRECSRC)/libgraph.a
	rm -f $(DIRECSRC)/liblist.a

cleanAll:
	@echo "** Removing object files, executable ..."
	rm -rf $(DIRECBIN)
	rm -rf $(DIRECOBJ)
	rm -rf $(DIRECLIB)
	rm -f $(OBJ)
	rm -f $(DIRECSRC)/$(EXENAME)
	rm -f $(DIRECSRC)/libgraph.a
	rm -f $(DIRECSRC)/liblist.a

execute:
	@echo "** start the game.."
	$(DIRECBIN)/$(EXENAME) $(ARG)
