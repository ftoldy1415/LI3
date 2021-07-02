#ifndef __MODULOBUSINESS_H__
#define __MODULOBUSINESS_H__
#include <stdlib.h>
#include <stdio.h>
#include "string.h"
#include <glib.h>
#include <ctype.h>
#include "moduloUsers.h"
#include "auxStructs.h"

void freeBusiness (void* business);

//struct que armazena o tipo de dados de uma business
typedef struct business *BUSINESS;

//struct que armazena os Business todos
typedef  GHashTable* CatalogoBusiness;


//função que lê a informação do ficheiro business
CatalogoBusiness readFileBusiness(char* file_name);

//função que destroi o catalogo de business
void freeCatalogoBusiness (CatalogoBusiness b);

//função que devolve uma hashTable com todos os business que começam por um char letter,
//na resposta à query 3
GHashTable* get_businesses_by_letter (CatalogoBusiness b, char letter);

//função que copia um Business de forma a preservar o encapsulamento
BUSINESS getBusiness(char* business_id, CatalogoBusiness b);

//funções que copiam a cidade, estado e nome de um business de forma a preservar o encapsulamento da estrutura
char* getCityBusiness (BUSINESS b);
char* getStateBusiness (BUSINESS b);
char* getNameBusiness (BUSINESS b);
char* getBusinessIdB(BUSINESS b);

//função que verifica a existencia de uma categoria no array de categorias de um Business
int existsCategory(BUSINESS bus, char* category);
#endif
