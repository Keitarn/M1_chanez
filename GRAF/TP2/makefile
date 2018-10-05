EXENAME := app
ARG :=

DIRECSRC := ./src
DIRECBIN := ./bin
DIRECOBJ := ./obj

CC := gcc
CFLAGS := -Wall -Wextra -g

LIBS := -lm
INCLUDES :=

SRC := $(shell find $(DIRECSRC) -name '*.c')
OBJ := $(SRC:.c=.o)


all: $(EXENAME) install clean execute

$(EXENAME): $(OBJ)
	@echo "** Building the game"
	$(CC) -o $(DIRECSRC)/$@ $^ $(LIBS)

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDES) -o $@ -c $<

install:
	@echo "** Installing..."
	sh ./create_directories.sh
	cp $(DIRECSRC)/$(EXENAME) $(DIRECBIN)
	cp $(OBJ) $(DIRECOBJ)
	chmod u+x $(DIRECBIN)/$(EXENAME)

valgrind:
	valgrind --leak-check=full --leak-resolution=med  --track-origins=yes $(DIRECBIN)/$(EXENAME)

clean:
	@echo "** Removing object files and executable..."
	rm -f $(OBJ)
	rm -f $(DIRECSRC)/$(EXENAME)

execute:
	@echo "** start the game.."
	$(DIRECBIN)/$(EXENAME) $(ARG)
