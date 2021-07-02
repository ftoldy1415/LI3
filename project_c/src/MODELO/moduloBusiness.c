#include "../../include/moduloBusiness.h"

#define True 1
#define False 0
#define line_size_business 1024

struct business{
    char* business_id;
    char* name;
    char* city;
    char* state;
    GHashTable* categories;
};

/* Libertar a memória alocada para a estrutura BUSINESS*/
void freeBusiness (void* business){
    BUSINESS b = (BUSINESS) business;
    free(b->business_id);
    free(b->name);
    free(b->city);
    free(b->state); //state
    g_hash_table_destroy(b->categories);
    free(b);
}

char* getBusinessIdB(BUSINESS b){
    return strdup(b->business_id);
}

BUSINESS getBusiness(char* business_id, CatalogoBusiness b){
    GHashTable* ht = (GHashTable*) b;
    BUSINESS result = malloc(sizeof(struct business));
    gpointer copy;
    gpointer orig_key;
    gboolean status;
    status = g_hash_table_lookup_extended(ht, (gconstpointer) business_id, &orig_key, &copy);
    BUSINESS bus = (BUSINESS) copy;
    if(!status){
        printf("ERRO, CHAVE NÃO EXISTENTE");
    }
    else{
        result->business_id = strdup(bus->business_id);
        result->name = strdup(bus->name);
        result->city = strdup(bus->city);
        result->state = strdup(bus->state);

        GHashTable *new_table = g_hash_table_new_full(g_str_hash, g_str_equal, free, NULL);
        GHashTableIter iter;
        gpointer key;

        g_hash_table_iter_init(&iter, bus->categories);

        while (g_hash_table_iter_next(&iter, &key, NULL)){
            g_hash_table_insert(new_table, strdup(key), NULL);
        }
        result->categories = new_table;
    }
    return result;
}



CatalogoBusiness readFileBusiness(char* file_name){
    GHashTable *table = g_hash_table_new_full(g_str_hash, g_str_equal, free, freeBusiness);
    FILE *fp = fopen(file_name, "r");
    if(fp == NULL){
        printf("erro no ficheiro\n");
        return NULL;
    }
    char *ctg_ptr,*key;
    char *buffer = malloc(sizeof(char) * line_size_business), *save_buffer;
    save_buffer = buffer;
    int i = 0;
    int valido = True;
    char* ignore;
    ignore = fgets(buffer, line_size_business, fp);
    while (fgets(buffer, line_size_business, fp)){
        BUSINESS u = malloc(sizeof(struct business));
        save_buffer = buffer;
        u->business_id = strdup(strsep(&buffer, ";"));
        if(strlen(u->business_id) != 22) valido = False; // verificar a validade
        u->name = strdup(strsep(&buffer, ";"));
        if(strlen(u->name) <= 0) valido = False;  // verificar campo nome
        u->city = strdup(strsep(&buffer, ";"));
        if(strlen(u->city) <= 0) valido = False;  // verificar campo city
        u->state = strdup(strsep(&buffer, ";"));
        if(strlen(u->state) <= 0) valido = False; // verificar compo state

        // Criação de array dinamico
        GHashTable *categories = g_hash_table_new_full(g_str_hash, g_str_equal, free, NULL);
        while(buffer != NULL){
            ctg_ptr = strdup(strsep(&buffer, ",")); // separação de categoria
            ctg_ptr = strsep(&ctg_ptr,"\n");
            strLower(ctg_ptr);
            g_hash_table_insert(categories, ctg_ptr, NULL); // inserir a categoria no array
        }
        u->categories = categories;

        // Se algum dos campos anteriores nao for valido entao a informação é destruida
        if(valido){
            key = strdup(u->business_id);
            g_hash_table_insert(table, key, u);
        }
        else{
           freeBusiness(u);
        }
        buffer = save_buffer;
    }
    free(save_buffer);
    fclose(fp);
    return (CatalogoBusiness) table;
}

char* getNameBusiness (BUSINESS b){
    return strdup(b->name);
}

int existsCategory(BUSINESS bus, char* category){
    return g_hash_table_lookup_extended(bus->categories,category,NULL,NULL);
}

char* getCityBusiness (BUSINESS b){
    char* result = strdup(b->city);
    return result;
}

char* getStateBusiness (BUSINESS b){
    char* result = strdup(b->state);
    return result;
}

GHashTable* get_businesses_by_letter (CatalogoBusiness b, char letter){
    GHashTable *names = g_hash_table_new_full (g_str_hash, g_str_equal, free, free);
    int i, count = 0;
    char* id;
    char* name;

    GHashTableIter iter;
    gpointer key, value, insName, insId;


    g_hash_table_iter_init (&iter, (GHashTable*) b);
    while (g_hash_table_iter_next (&iter, &key, &value)){
        i = 0;
        id = strdup((char*) key);
        name = getNameBusiness((BUSINESS) value);
        while (isspace(id[i])) i++;
        if (tolower(letter) == tolower(name[i])){
            g_hash_table_insert(names, id, name);
        }
    }

    return names;
}

void freeCatalogoBusiness (CatalogoBusiness b){
    g_hash_table_destroy((GHashTable *) b);
}
