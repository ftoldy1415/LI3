#ifndef __SGR_H__
#define __SGR_H__
#include <stdio.h>
#include <glib.h>
#include "moduloReviews.h"
#include "moduloUsers.h"
#include "moduloBusiness.h"
#include "stats.h"
#include "interface.h"
#include "auxStructs.h"
#include "interface.h"
#include <time.h>



typedef struct sgr *SGR;
typedef struct table *TABLE;


/**
 * Inicia um elemento do tipo SGR
 */
SGR init_sgr();


/**
 * Liberta a memória ocupada por uma SGR
 */
void free_sgr(SGR sgr);


/**
 * Get do número de colunas de uma tabela
 */
int get_TB_colunas(TABLE tb);


/**
 * Get do número de linhas de uma tabela
 */
int get_TB_linhas(TABLE tb);


/**
 * Get de um elemento de uma tabela 
 */
char* get_TB_elem(TABLE tb, int coluna ,int linha);


/**
 * Get do nome associado a uma coluna
 */
char* getNomeCampos (TABLE tb, int coluna);
int getCurrent(TABLE tb);


//Funções associadas ao tipo TABLE

/**
 * Liberta o espaço de memória ocupado por uma table
 */
void free_TB(TABLE tb);


/**
 * Inicia uma table nova
 */
TABLE initTable();
void setInfoCampos(TABLE tb, int n_colunas);


void incializaSizeColuna(TABLE tb, int valor);


/**
 * Insere o nome de um campo no array adequado na table
 */
void insertNomeCampos(TABLE tb, int coluna, char * nome);


/**
 * Insere a info de um campo no array adequado na table
 */
void insertInfoCampos(TABLE tb, int linha, int coluna, char* data);


int getSizeColuna(TABLE tb, int coluna);

/**
 * Define o tamanho de uma coluna da table
 */
void setSizeColuna (TABLE tb, int coluna, int valor);


/**
 * Função auxiliar na paginação
 */
void setCurrent(TABLE tb, int valor);


//QUERIES


/* query 1 */
/**
 * Função que carrega o SGR
 */
SGR load_sgr(char *users, char *businesses, char *reviews);


/* query 2 */
/**
 * Query que calcula uma tabela com todos os negócios cujo o nome começam com a letra passada como argumento
 */
TABLE businesses_started_by_letter(SGR sgr, char letter);


/* query 3 */
/**
 *  Query que fornece a informação relativa a um business_id fornecido pelo utilizador
 */
TABLE business_info(SGR sgr, char *business_id);


/* query 4 */
/**
 *  Query que calcula uma tabela com os nomes e os ids dos négocios aos quais o user_id fez uma avaliação
 */
TABLE businesses_reviewed(SGR sgr, char *user_id);


/* query 5 */

/**
 *  Query devolve uma tabela com os nomes e ids de todos os negócios numa dada cidade com pontuação superior à fornecida
 */
TABLE businesses_with_stars_and_city(SGR sgr, float stars, char *city);


/* query 6 */
/**
 * 
 */
TABLE top_businesses_by_city(SGR sgr, int top);


/* query 7 */
TABLE international_users (SGR sgr);


/* query 8 */
TABLE top_businesses_with_category(SGR sgr, int top, char *category);


/* query 9*/
TABLE reviews_with_word (SGR sgr, char *word);

int businesses_com_reviews(SGR sgr);

#endif
