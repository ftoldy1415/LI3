#include "../../include/stats.h"

#define True 1
#define False 0


struct cityStars{
    char* business_id;
    float mean;
    float stars;
    int count;
};
CityStars init_CS(){
    CityStars cs =  malloc(sizeof(struct cityStars));
    cs->mean = 0;
    cs->stars = 0;
    cs->count = 0;
    return cs;
}
void addStars_CS(CityStars cs, float stars){
    cs->stars  += stars;
}

void incrementCount_CS(CityStars cs){
    cs->count++;
}

void setMean_CS(CityStars cs , float mean){
    cs->mean = mean;
}

void setBusinessId_CS(CityStars cs , char* id){
    cs->business_id = strdup(id);
}

char* getBusinessIdCS(CityStars cs){
    return strdup(cs->business_id);
}

float getStarsCS(CityStars cs){
    return cs->stars;
}

int getCountCS(CityStars cs){
    return cs->count;
}

float getMeanCS(CityStars cs){
    return cs->mean;
}

void freeCityStars(void * v){
    CityStars cs = (CityStars) v;
    free(cs->business_id);
    free (cs);
}

CityStars copyCityStars(CityStars c){
    CityStars newStruct = malloc(sizeof(struct cityStars));
    newStruct->business_id = strdup(c->business_id);
    newStruct->mean = c->mean;
    newStruct->stars = c->stars;
    newStruct->count = c->count;
    return newStruct;
}

gint compareCityStars(gconstpointer a, gconstpointer b){
  //negativo se o primeiro vier antes do segundo
  //0 se forem iguais
  //positivo se o segundo vier primeiro
  int result = 0;
  CityStars* newA = (CityStars*) a;
  CityStars* newB = (CityStars*) b;
  if(((*newA)->mean - (*newB)->mean) < 0.0) result = 1;
  if(((*newA)->mean - (*newB)->mean) > 0.0) result = -1;
  return result;
}

GArray* convertHashToArray(GHashTable *reviewCities){

    GArray* array_bus;
    CityStars cs;
    GHashTableIter iter;
    gpointer key_bus, value_bus;
    int i;

    //percorrer a tabela das cidades e passar todos os businesses para um array, ordenar e colocar como novo valor esse array

    g_hash_table_iter_init(&iter, reviewCities);

    i = 0;
    array_bus = g_array_new(False, False, sizeof(CityStars));
    while (g_hash_table_iter_next(&iter, &key_bus, &value_bus)){
        cs = (CityStars) value_bus;
        g_array_append_val(array_bus, cs);
        i++;
    }
    g_array_sort(array_bus, compareCityStars);

    return array_bus;
}

GHashTable* reviewsByCity (CatalogoReviews r, CatalogoBusiness b){
    // abin ou linked list?
    int count = 0;
    //table que vai conter as cidades como keys e como values hash tables dos negocios nessas cidades e as estrelas correspondentes


    GHashTable *reviewCities = aux_data_from_reviews(r, b);

    GHashTableIter iter;
    gpointer key, value;

    // iterar a tabela das cidades

    // criar uma hash para armazenar os top n business por cidade
    GHashTable* final_table = g_hash_table_new_full(g_str_hash,g_str_equal,free, NULL);
    g_hash_table_iter_init(&iter,reviewCities);
    CityStars cs;
    GArray* insert;
    gpointer stolen_key;
    gpointer stolen_value;
    char* save_key;
    int sum = 0;
    while (g_hash_table_iter_next(&iter, &key, &value)){
        sum += g_hash_table_size(value);
        insert = convertHashToArray(value);
        g_hash_table_insert(final_table,strdup(key),insert);
    }
    g_hash_table_iter_init(&iter,reviewCities);
    while (g_hash_table_iter_next(&iter, &key, &value)){
        GHashTable* tb = (GHashTable*) value;
        g_hash_table_destroy(tb);
    }
    return final_table;
}
