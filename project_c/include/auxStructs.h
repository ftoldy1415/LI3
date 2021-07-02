#ifndef AUXSTRUCTS_H_
#define AUXSTRUCTS_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <glib.h>
#include <string.h>
#include <ctype.h>

typedef struct bizScore *BizScore;

/**
 * Inicia o tipo de dados BizScore, auxiliar na query 8, alocando memória para o mesmo
 */
BizScore init_BS();


/**
 * Liberta a memória associada a um elemento do tipo de dados BizScore
 */
void freeBizScore(BizScore bs);


/**
 * Funções gets dos varios parametros armazenados no tipo de dados BizScore
 */
char* get_BizScore_Name(BizScore bs);
char* get_BizScore_ID(BizScore bs);
float get_BizScore_Stars(BizScore bs);


/**
 * Função de comparação de dois elementos do tipo BizScore, auxiliar na ordenação do Array 
 */
gint compareBizScore(gconstpointer a, gconstpointer b);


/**
 * Funções de set de varios parametros do tipo de dados BizScore
 */
void setBusinessId_BS(BizScore bs, char* id);
void setName_BS(BizScore bs, char* name);
void setScore_BS(BizScore bs, float score);

/**
 * Passa todas as letras de uma string a minúsculas
 */
void strLower(char * str);

char* tiraEspacos(char* origin);

#endif
