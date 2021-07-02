#include <stdlib.h>
#include <stdio.h>
#include "string.h"
#include <glib.h>
#include <ctype.h>
#include "../../include/moduloUsers.h"
#include "../../include/moduloBusiness.h"
#include "../../include/moduloReviews.h"

#define True 1
#define False 0
#define line_size_users 345000


struct user{
   char* user_id;
   char* name;
   GHashTable *states;
};


void freeUser (void* user) {
    USERS u = (USERS) user;
    free(u->name);
    free(u->user_id);
    free(u);
}


void setState(char* user_id, char* state, CatalogoUsers table){
    gpointer key, key_states;
    gpointer value, values_states;
    USERS u;

    if(g_hash_table_lookup_extended(table, user_id, &key, &value)){
        u = (USERS) value;
        if(u->states != NULL){
            if ((int) g_hash_table_size(u->states) < 2 && !g_hash_table_lookup_extended(u->states, state, &key_states, NULL)){
                g_hash_table_insert(u->states, state, NULL);
            }
        }
        else {
            u->states = g_hash_table_new_full(g_str_hash, g_str_equal, free, freeUser);
            g_hash_table_insert (u->states, state, NULL);
        }
    }
    else{
        printf("user nao existe");
    }
}

USERS getUser(char* user_id, GHashTable *ht){
    USERS result = malloc(sizeof(struct user));
    gpointer copy = NULL;
    gpointer orig_key = NULL;
    gboolean status;
    status = g_hash_table_lookup_extended(ht, user_id, &orig_key, &copy);
    USERS value = copy;
    if(!status){
        printf("ERRO, CHAVE NÃO EXISTENTE");
    }
    result->user_id = strdup(value->user_id);
    result->name = strdup(value->name);
    return result;
}

CatalogoUsers readFileUsers(char* file_name){
    GHashTable *table = g_hash_table_new_full(g_str_hash, g_str_equal, free, freeUser);
    FILE *fp = fopen(file_name, "r");
    if(fp == NULL){
        printf("erro no ficheiro\n");
        return NULL;
    }
    char *key;
    char *buffer = malloc(sizeof(char) * line_size_users), *save_buffer;
    save_buffer = buffer;
    int i;
    int valido = True;
    char* ignore;
    ignore = fgets(buffer, line_size_users, fp);
    while (fgets(buffer, line_size_users, fp)){
        USERS u = malloc(sizeof(struct user));
        save_buffer = buffer;
        u->user_id = strdup(strsep(&buffer, ";"));
        if (strlen(u->user_id) != 22) valido = False;  //verificar a validade

        u->name = strdup(strsep(&buffer,";"));
        if (u->name == NULL) valido = False;           //verificar o campo nome

        u->states = NULL;

        i = 0;
        char* valor;

        // Adicionar à Hash
        if (valido){
            key = strdup(u->user_id);
            g_hash_table_insert(table, key, u);
        }
        else {
            freeUser(u);
        }
        buffer = save_buffer;
    }
    free(save_buffer);
    fclose(fp);
    return (CatalogoUsers) table;
}

GArray* traverseUsersForState (CatalogoUsers users){
    GArray* ids = g_array_new(False, False, sizeof(char*));
    int count = 0;
    char* id;
    USERS user;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init(&iter, (GHashTable*) users);

    while (g_hash_table_iter_next(&iter, &key, &value)){
        user = (USERS) value;
        if (user->states != NULL && g_hash_table_size(user->states) >= 2){
            id = strdup(user->user_id);
            g_array_append_val(ids, id);
        }
    }
    return ids;
}

void freeCatalogoUsers (CatalogoUsers u){
    g_hash_table_destroy((GHashTable *) u);
}
