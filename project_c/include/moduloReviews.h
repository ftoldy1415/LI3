#ifndef __MODULOREVIEWS_H__
#define __MODULOREVIEWS_H__
#include <stdlib.h>
#include <stdio.h>
#include "string.h"
#include <glib.h>
#include <ctype.h>
#include "moduloBusiness.h"
#include "moduloUsers.h"
typedef GHashTable* CatalogoReviews;
#include "stats.h"
#include "auxStructs.h"


/**
 * Struct que armazena o tipo de dados de uma Review
 */
typedef struct review *REVIEW;



/**
 * Lê o ficheiro das reviews e organiza os dados numa estrutra de dados CatalogoReview
 */
CatalogoReviews readFileReviews(char* review_name);


/**
 * Liberta a memória ocupada por um catálogo de Reviews
 */
void freeCatalogoReviews (CatalogoReviews r);


/**
 * Função que percorre o catálogo de reviews e calcula a pontuação de um business dado o seu business
 */
int traverseReviewsForStars (CatalogoReviews r, char* business_id, float* resultado);

/**
 * Função que percorre o catálogo de reviews para armazenar os dados dos négocios cuja review tem o mesmo user_id que o dado como argume
 */
GHashTable* traverseReviewsForID (CatalogoReviews reviews, char* user_id, CatalogoBusiness b);


/**
 * Função que define o parametro state dos users ao percorrer o catalogo de reviews
 */
void traverseReviewsForState (CatalogoReviews reviews, CatalogoUsers users, CatalogoBusiness business);


/**
 *  Função que guarda num ID as reviews cujo texto tenha a palavra fornecida como argumento
 */
GArray* findReviewsText (CatalogoReviews reviews, char* text);


/**
 * Função que copia uma Review de forma a preservar o encapsulamento
 */
REVIEW getReview(char* review_id, GHashTable *ht);


/**
 * Funções que copiam varios elementos de uma estrutura de dados review
 */
char* getUserID (REVIEW r);

char* getBusinessID (REVIEW r);

float getStars(REVIEW r);

char* getBusinessID (REVIEW r);
GHashTable* aux_data_from_reviews(CatalogoReviews r, CatalogoBusiness b);

#endif
