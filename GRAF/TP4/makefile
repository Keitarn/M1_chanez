EXENAME := app
ARG :=

DIRECSRC := ./src
DIRECBIN := ./bin
DIRECOBJ := ./obj
DIRECINCLUDE := ./include
DIRECLIB := ./lib

CC := gcc
CFLAGS := -Wall -Wextra -g

LIBS := -lm -lgraph -llist
INCLUDES := -I $(DIRECINCLUDE) -L $(DIRECLIB)

SRC := $(shell find $(DIRECSRC) -name '*.c')
OBJ := $(SRC:.c=.o)


all: $(EXENAME) install #clean

$(EXENAME): $(OBJ)
	@echo "** Building the game"
	$(CC) -o $(DIRECSRC)/$@ $(LIBS) $^

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) -o $@ -c $<

install:
	@echo "** Installing..."
	sh ./create_directories.sh
	cp $(DIRECSRC)/$(EXENAME) $(DIRECBIN) || :
	cp $(OBJ) $(DIRECOBJ) || :
	chmod u+x $(DIRECBIN)/$(EXENAME) || :

valgrind:
	@echo "** Valgrind"
	valgrind --leak-check=full --leak-resolution=med  --track-origins=yes $(DIRECBIN)/$(EXENAME)

clean:
	@echo "** Removing object files and executable and libs in src..."
	rm -f $(OBJ)
	rm -f $(DIRECSRC)/$(EXENAME)

cleanAll:
	@echo "** Removing object files, executable ..."
	rm -rf $(DIRECBIN)
	rm -rf $(DIRECOBJ)
	rm -f $(OBJ)
	rm -f $(DIRECSRC)/$(EXENAME)

execute:
	@echo "** start the game.."
	$(DIRECBIN)/$(EXENAME) $(ARG)
