#include "../../include/moduloReviews.h"
#define True 1
#define False 0
#define line_size_reviews 100000


struct review{
    char* review_id;
    char* user_id;
    char* business_id;
    float stars;
    int useful;
    int funny;
    int cool;
    char* date;
    GHashTable* processedText;
};

char* getUserID (REVIEW r){
    char* result = strdup(r->user_id);
    return result;
}

char* getBusinessID (REVIEW r){
    char* result = strdup(r->business_id);
    return result;
}

float getStars(REVIEW r){
    return r->stars;
}

//função que copia uma ghashTable, serve como auxiliar do getReview
GHashTable* copyHash(GHashTable* original){
    GHashTable* result = g_hash_table_new(g_str_hash, g_str_equal);
    gpointer key;
    gpointer value;
    GHashTableIter iter;
    g_hash_table_iter_init (&iter, original);
    while(g_hash_table_iter_next(&iter,&key,&value)){
        gpointer newKey = strdup(key);
        gpointer newValue = strdup(value);
        g_hash_table_insert(result,newKey,newValue);
    }
    return result;
}


REVIEW getReview(char* review_id, GHashTable *ht){
    REVIEW result = malloc(sizeof(struct review));
    gpointer copy;
    gpointer orig_key;
    gboolean status;
    status = g_hash_table_lookup_extended(ht, review_id, &orig_key, &copy);
    if(!status){
        printf("ERRO, CHAVE NÃO EXISTENTE");
    }
    REVIEW value = (REVIEW)copy;
    result->user_id = strdup(value->user_id);
    result->review_id = strdup(value->review_id);
    result->business_id = strdup(value->business_id);
    result->stars = value->stars;
    result->useful = value->useful;
    result->funny = value->funny;
    result->cool = value->cool;
    result->date = strdup(value->date);
    result->processedText = copyHash(value->processedText);
    return result;
}


void freeReview (REVIEW r){
    free(r->review_id);
    free(r->user_id);
    free(r->business_id);
    free(r->date);
}

void freeReviewExtended (void* review){
    REVIEW r = (REVIEW) review;
    free(r->review_id);
    free(r->user_id);
    free(r->business_id);
    free(r->date);
    g_hash_table_destroy(r->processedText);
}

//função de verificação da data, serve como auxiliar para verificar a validade da secção da data numa review antes desta ser inserida na tabela
int checkDate(char* date){
    int dd,mm,yy;
    yy = atoi(strsep(&date,"-"));
    mm = atoi(strsep(&date,"-"));
    dd = atoi(date);
    //check year
    if(yy>=1900 && yy<=9999){
        //check month
        if(mm>=1 && mm<=12){
            //check days
            if((dd>=1 && dd<=31) && (mm==1 || mm==3 || mm==5 || mm==7 || mm==8 || mm==10 || mm==12))
                return True;
            else if((dd>=1 && dd<=30) && (mm==4 || mm==6 || mm==9 || mm==11))
                return True;
            else if((dd>=1 && dd<=28) && (mm==2))
                return True;
            else if(dd==29 && mm==2 && (yy%400==0 ||(yy%4==0 && yy%100!=0)))
                return True;
            else
                return False;
        }
        else{
            return False;
        }
    }
    return False;
}


//função de verificação da hora. Tal como a checkData, serve como auxiliar para verificar a validade da secção da data numa review antes desta ser inserida na tabela
int checkHour(char* time){
    int hour,min,sec;
    hour = atoi(strsep(&time,":"));
    min = atoi(strsep(&time,":"));
    sec = atoi(time);
    if(hour >= 0 &&  hour <= 23){
        if(min >= 0 && min < 60){
            if(sec >= 0 && sec < 60) return True;
        }
    }
    return False;
}

//função que organiza o texto de uma review numa hashTable com o objetivo de eliminar repetições
GHashTable* processText (char* text){
    char* save_ptr, *key;
    GHashTable* text_table = g_hash_table_new_full(g_str_hash, g_str_equal, free, NULL);

    char delim[18] = " ,.;:-_&/()=?{}[]!";
    char *token;

    token = strtok(text, delim);

    while (token != NULL){
        key = strdup(token);

        if (!g_hash_table_contains(text_table, key)) g_hash_table_insert(text_table, key, NULL);

        //free(token);
        token = strtok(NULL, delim);
    }
    free(text);
    return text_table;
}

CatalogoReviews readFileReviews(char* review_name){
    GHashTable *table = g_hash_table_new_full(g_str_hash, g_str_equal, free,freeReviewExtended);
    FILE *fp = fopen(review_name, "r");
    if(fp == NULL){
        printf("erro no ficheiro\n");
        return NULL;
    }
    char *key,*testDate;
    char *buffer = malloc(sizeof(char) * line_size_reviews), *save_buffer;
    save_buffer = buffer;
    int valido = True;
    char* ignore;
    ignore = fgets(buffer, line_size_reviews, fp);
    while (fgets(buffer, line_size_reviews, fp)){
        save_buffer = buffer;
        REVIEW r = malloc(sizeof(struct review));
        r->review_id = strdup(strsep(&buffer,";"));
        if (strlen(r->review_id) != 22) valido = False;
        r->user_id = strdup(strsep(&buffer, ";"));
        if (strlen(r->user_id) !=22 ) valido = False;
        r->business_id = strdup(strsep(&buffer,";"));
        if (strlen(r->business_id) != 22) valido = False;
        r->stars = atof(strsep(&buffer, ";"));
        if (r->stars < 0 || r-> stars > 5.0) valido = False;
        r->useful = atoi(strsep(&buffer,";"));
        if (r->useful < 0) valido = False;
        r->funny = atoi(strsep(&buffer,";"));
        if (r->funny < 0) valido = False;
        r->cool = atoi(strsep(&buffer, ";"));
        if (r-> cool < 0) valido = False;
        r->date = strdup(strsep(&buffer,";"));
        testDate = strdup(r->date);
        if(!checkDate(strsep(&testDate," ")) || !checkHour(testDate)) valido = False;
        if(valido){
            r->processedText = processText(strdup(strsep(&buffer,"\n")));
            key = strdup(r->review_id);
            g_hash_table_insert(table,key,r);
        }
        else{
            freeReview(r);
        }
        buffer = save_buffer;
    }
    free(save_buffer);
    return (CatalogoReviews) table;
}



int traverseReviewsForStars ( CatalogoReviews r, char* business_id, float* resultado){
    int contador = 0;
    float starsCount = 0;

    gpointer key, value;
    GHashTableIter iter;

    g_hash_table_iter_init (&iter, (GHashTable *) r);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        REVIEW r = (REVIEW) value;
        if (!strcmp(r->business_id, business_id)){
            starsCount += r->stars;
            contador++;
        }
    }
    (*resultado) = starsCount/(float) contador;
    return contador;
}



GHashTable* traverseReviewsForID (CatalogoReviews reviews, char* user_id, CatalogoBusiness b){
    GHashTable *names = g_hash_table_new_full (g_str_hash, g_str_equal, free, free);
    char *id;
    char *name;

    gpointer key, value;
    GHashTableIter iter;

    g_hash_table_iter_init (&iter, (GHashTable *) reviews);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        REVIEW r = (REVIEW) value;
        BUSINESS busi;
        if (!strcmp(r->user_id, user_id)){
            id = strdup(r->business_id);
            busi = getBusiness(id,b);
            name = getNameBusiness(busi);
            freeBusiness(busi);
            g_hash_table_insert(names, id, name);
        }
    }

    return names;
}

void traverseReviewsForState (CatalogoReviews reviews, CatalogoUsers users, CatalogoBusiness business){
    char* bus_id;
    char* state;
    REVIEW review;
    BUSINESS bus;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init(&iter, (GHashTable *) reviews);

    while (g_hash_table_iter_next(&iter, &key, &value)){

        review = (REVIEW) value;
        bus = getBusiness(review->business_id, business); //alocado
        state = getStateBusiness(bus);                    //alocado
        freeBusiness(bus);
        setState(review->user_id, state, users);
    }
}

GArray* findReviewsText (CatalogoReviews reviews, char* text){
    GArray *resultado = g_array_new(True, False, sizeof(char*));

    char* aux_text = strdup(text);
    strLower(aux_text);

    GHashTableIter iter;
    gpointer key, value;
    gpointer lookup_key_ht, lookup_value_ht;
    char* resultado_key;

    g_hash_table_iter_init (&iter, (GHashTable *) reviews);

    while (g_hash_table_iter_next (&iter, &key, &value)){
        REVIEW r = (REVIEW) value;
        resultado_key = strdup(r->review_id);
        GHashTable* textTable = r->processedText;
        if (g_hash_table_lookup_extended(textTable, aux_text, &lookup_key_ht, &lookup_value_ht)){
            g_array_append_val(resultado, resultado_key);
        }
    }
    return resultado;
}

GHashTable* aux_data_from_reviews(CatalogoReviews r, CatalogoBusiness b){
    GHashTable* reviews = (GHashTable*) r;
    GHashTable* aux_data = g_hash_table_new_full(g_str_hash, g_str_equal, free, NULL);
    GHashTableIter iter;
    gpointer key, value;
    CityStars entry;
    REVIEW review;
    BUSINESS bus_lookup;
    char *new_key, *buss_id, *name_city, *name_bus;
    float mean;
    int count;
    gpointer lookup_key_ht, lookup_value_ht, city_key, city_value;
    CityStars newBusiness;
    GHashTable* city_table, *newCity;
    g_hash_table_iter_init(&iter, (GHashTable *) r); //iterar a table das reviews
    while (g_hash_table_iter_next (&iter, &key, &value)){
        count++;
        review = (REVIEW) value; //struct review
        buss_id = getBusinessID(review); //retirar da review o business id
        bus_lookup = getBusiness(buss_id, b); //usar o business_id para retirar da table dos business a struct business
        name_city = getCityBusiness(bus_lookup); //retirar o nome da cidade da struct business
        strLower(name_city);
        name_city = tiraEspacos(name_city);
        freeBusiness(bus_lookup);

        if(g_hash_table_lookup_extended (aux_data, name_city, &lookup_key_ht, &lookup_value_ht)){
            city_table = (GHashTable*) lookup_value_ht;

            if(g_hash_table_lookup_extended (city_table, buss_id, &city_key, &city_value)){
                entry = (CityStars) city_value;
                addStars_CS(entry,getStars(review));
                incrementCount_CS(entry);
                mean = getStarsCS(entry) / (float) getCountCS(entry);
                setMean_CS(entry,mean);
                free(buss_id); //se o business já existe na tabela esta memoria deve ser libertada já que não vai ser usada
            }
            else{
                newBusiness = init_CS();
                setBusinessId_CS(newBusiness,buss_id);
                addStars_CS(newBusiness ,getStars(review));
                incrementCount_CS(newBusiness);
                setMean_CS(newBusiness, getStars(review));
                g_hash_table_insert(city_table, strdup(buss_id), newBusiness);
                free(buss_id);
            }
            free(name_city); //se a cidade já existe na tabela , esta memoria nao vai ser usada , por isso pode ser libertada
        }
        else{
            newCity = g_hash_table_new_full(g_str_hash, g_str_equal, free, NULL);
            newBusiness = init_CS();
            setBusinessId_CS(newBusiness,buss_id);
            addStars_CS(newBusiness,getStars(review));
            incrementCount_CS(newBusiness);
            setMean_CS(newBusiness,getStars(review));
            g_hash_table_insert(newCity, strdup(buss_id), newBusiness);
            g_hash_table_insert(aux_data, name_city, newCity);
            free(buss_id);
        }
    }
    return aux_data;
 }


void freeCatalogoReviews (CatalogoReviews r){
    g_hash_table_destroy((GHashTable *) r);
}
