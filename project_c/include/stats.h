#ifndef _STATS_H_
#define _STATS_H_
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <ctype.h>
#include <glib.h>
#include "moduloBusiness.h"
#include "moduloReviews.h"


typedef struct cityStars *CityStars;

/**
 * Função que liberta a memoria ocupada por um elemento do tipo de dados CityStars
 */
void freeCityStars(void *);


/**
 * Função que organiza as reviews por cidade, ou seja, utiliza uma hashTable em que cada key é o nome de uma cidade e cada value é outra hashtable com os id's do business como key e o valor é do tipo CityStars. este valor é atualizado sempre que uma review pertencer a um business ja inserido na hashtable
 */
GHashTable* reviewsByCity (CatalogoReviews r, CatalogoBusiness b);


/**
 * Função de comparação de dois elementos do tipo de dados CityStars
 */
gint compareCityStars(gconstpointer a, gconstpointer b);


/**
 * Converte o conteúdo de uma GHashTable para um GArray
 */
GArray* convertHashToArray(GHashTable *reviewCities);


/**
 * Funções auxiliares para o tipo de dados CityStars
 */
CityStars init_CS();
void addStars_CS(CityStars cs, float stars);
void incrementCount_CS(CityStars cs);

/**
 * Setters para os parametros de um elemento do tipo CS
 */
void setMean_CS(CityStars cs , float mean);
void setBusinessId_CS(CityStars cs , char* id);


/**
 * Gets para os parametros de um elemento do tipo CS
 */
char* getBusinessIdCS(CityStars cs);
float getStarsCS(CityStars cs);
char* getNameCS(CityStars cs);
float getMeanCS(CityStars cs);
int getCountCS(CityStars cs);


/**
 * Função que imprime um elemento do tipo de dados CityStars
 */
void printCityStars (CityStars cs);


/**
 * Função de cópia de um elemento do tipo de dados CityStars
 */
CityStars copyCityStars(CityStars c);


/**
 * Passa todas as letras de uma string a minúsculas
 */
void strLower(char * str);
#endif
