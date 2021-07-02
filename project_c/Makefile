CC = gcc
CFLAGS = -std=c99 -Wextra -O2 -D_GNU_SOURCE -g 
PKG = `pkg-config --cflags --libs glib-2.0`
SRC = src
OBJ = obj
INCS = include
#teste

all: mkfolders moduloBusiness moduloReviews moduloUsers stats sgr auxStructs interface interpretador pages  main program

mkfolders:
	$(shell mkdir obj)
	$(shell mkdir input_files)

moduloBusiness: $(SRC)/MODELO/moduloBusiness.c $(INCS)/moduloBusiness.h
	@$(CC) $(CFLAGS) -o $(OBJ)/moduloBusiness.o $(PKG) -c $(SRC)/MODELO/moduloBusiness.c

moduloReviews: $(SRC)/MODELO/moduloReviews.c $(INCS)/moduloReviews.h
	@$(CC) $(CFLAGS) -o $(OBJ)/moduloReviews.o $(PKG) -c $(SRC)/MODELO/moduloReviews.c

moduloUsers: $(SRC)/MODELO/moduloUsers.c $(INCS)/moduloUsers.h
	@$(CC) $(CFLAGS) -o $(OBJ)/moduloUsers.o $(PKG) -c $(SRC)/MODELO/moduloUsers.c

stats: $(SRC)/MODELO/stats.c $(INCS)/stats.h
	@$(CC) $(CFLAGS) -o $(OBJ)/stats.o $(PKG) -c $(SRC)/MODELO/stats.c

sgr: $(SRC)/CONTROLADOR/sgr.c $(INCS)/sgr.h
	@$(CC) $(CFLAGS) -o $(OBJ)/sgr.o $(PKG) -c $(SRC)/CONTROLADOR/sgr.c

auxStructs: $(SRC)/CONTROLADOR/auxStructs.c $(INCS)/auxStructs.h
	@$(CC)  $(CFLAGS) -o $(OBJ)/auxStructs.o $(PKG) -c $(SRC)/CONTROLADOR/auxStructs.c

interpretador: $(SRC)/INTERFACE/interpretador.c
	@$(CC) $(CFLAGS) -o $(OBJ)/interpretador.o $(PKG) -c $(SRC)/INTERFACE/interpretador.c

interface: $(SRC)/INTERFACE/interface.c $(INCS)/interface.h
	@$(CC) $(CFLAGS) -o $(OBJ)/interface.o $(PKG) -c $(SRC)/INTERFACE/interface.c

pages: $(SRC)/INTERFACE/paginacao.c $(INCS)/paginacao.h
	@$(CC) $(CFLAGS) -o $(OBJ)/paginacao.o $(PKG) -c $(SRC)/INTERFACE/paginacao.c

main: $(SRC)/main.c $(INCS)
	@$(CC) $(CFLAGS) -o $(OBJ)/main.o $(PKG) -c $(SRC)/main.c

program: $(SRC)/main.c $(OBJ)
	@$(CC) $(CFLAGS) $(OBJ)/*.o $(PKG) -o program

memcheck:
	valgrind --leak-check=yes ./program

.PHONY:clean
clean:
	rm -rf $(OBJ)/*.o program
