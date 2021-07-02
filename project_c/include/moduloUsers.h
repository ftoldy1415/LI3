#ifndef __MODULOUSERS_H__
#define __MODULOUSERS_H__
#include <glib.h>

/**
 * Struct que armaza o tipo de dados User
 */
typedef struct user *USERS;

/**
 * GHashTable que armazena vários Users como valores com key igual aos seus respetivos ID's
 */
typedef GHashTable* CatalogoUsers;



/**
 * Função que lê a informação do ficheiro users
 */
CatalogoUsers readFileUsers(char* file_name);



/**
 * Função que altera o campo "state" da estrutura de dados users,
 * Auxiliar para a query 7.
 */ 
void setState(char* user_id, char* state, GHashTable* table);


/**
 * Função que destroi a informação no catálogo de Users
 */
void freeCatalogoUsers (CatalogoUsers u);


/**
 * Função que percorre o catalogo de users à procura de quais têm mais que 2 estados guardados na sua struct
 */
GArray* traverseUsersForState (CatalogoUsers users);

#endif
