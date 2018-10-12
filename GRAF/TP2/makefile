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

$(EXENAME): $(OBJ) lib
	@echo "** Building the game"
	$(CC) -o $(DIRECBIN)/$@ $(DIRECOBJ)/main.o $(DIRECOBJ)/Menu.o $(LIBS) $(DIRECLIB)/libgraph.a $(DIRECLIB)/liblist.a

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) -o $@ -c $<

install:
	@echo "** Installing..."
	sh ./create_directories.sh
	cp $(DIRECSRC)/$(EXENAME) $(DIRECBIN)
	cp $(OBJ) $(DIRECOBJ)
	chmod u+x $(DIRECBIN)/$(EXENAME)
	

lib: libgraph.a liblist.a

libgraph.a: $(DIRECOBJ)/Graph.o
	ar rcs $(DIRECLIB)/libgraph.a $(DIRECOBJ)/Graph.o

liblist.a: $(DIRECOBJ)/List.o
	ar rcs $(DIRECLIB)/liblist.a $(DIRECOBJ)/List.o

valgrind:
	@echo "** Valgrind"
	valgrind --leak-check=full --leak-resolution=med  --track-origins=yes $(DIRECBIN)/$(EXENAME)

cleansrc:
	@echo "** Removing object files and executable in src..."
	rm -f $(OBJ)
	rm -f $(DIRECSRC)/$(EXENAME)

clean:
	@echo "** Removing object files, executable and libs..."
	rm -f $(DIRECBIN)/*
	rm -f $(DIRECOBJ)/*
	rm -f $(DIRECLIB)/*
	rm -f $(OBJ)
	rm -f $(DIRECSRC)/$(EXENAME)

execute:
	@echo "** start the game.."
	$(DIRECBIN)/$(EXENAME) $(ARG)
