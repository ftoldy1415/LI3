#include "../../include/sgr.h"
#define True 1
#define False 0

struct table{
    GPtrArray* nomes_campos;
    GPtrArray* info_campos;
    GPtrArray* max_colunas_campos;
    int current;
};

struct sgr{
    CatalogoUsers u;
    CatalogoReviews r;
    CatalogoBusiness b;
    GHashTable* starStats;
};


//função sobre o tipo de dados TABLE
TABLE initTable(){
    TABLE tb = malloc(sizeof(struct table));
    tb->nomes_campos = g_ptr_array_new_with_free_func(free);
    tb->info_campos = g_ptr_array_new();
    tb->max_colunas_campos = g_ptr_array_new_with_free_func(free);
    tb->current = 0;
    return tb;
}

void setInfoCampos(TABLE tb, int n_colunas){
    for (int i = 0; i < n_colunas; i++){
        GPtrArray* array = g_ptr_array_new_with_free_func(free);
        g_ptr_array_add(tb->info_campos, array);
    }
}

void incializaSizeColuna(TABLE tb, int n_cols){
    int *data;
    for (int i = 0; i < n_cols; i++){
        data = malloc(sizeof(int));
        (*data) = 0;
        g_ptr_array_add(tb->max_colunas_campos, data);
    }
}

void setSizeColuna (TABLE tb, int coluna, int valor){
    int *value;
    if (coluna >= (int) tb->max_colunas_campos->len){
        value = malloc(sizeof(int));
        *value = valor;
        g_ptr_array_add(tb->max_colunas_campos, value);
    }
    else{
        value = (int*) g_ptr_array_index(tb->max_colunas_campos, coluna);
        *value = valor;
    }
}

void insertNomeCampos(TABLE tb, int coluna, char * nome){
    g_ptr_array_add(tb->nomes_campos, strdup(nome));
}

void insertInfoCampos(TABLE tb, int linha, int coluna, char* data){
    GPtrArray* aux;
    aux = g_ptr_array_index(tb->info_campos,coluna);
    g_ptr_array_add(aux, strdup(data));
}

void setCurrent(TABLE tb, int valor){
    tb->current = valor;
}

int getCurrent(TABLE tb){
    return tb->current;
}

int getSizeColuna(TABLE tb, int coluna){
    int *value = g_ptr_array_index(tb->max_colunas_campos, coluna);
    return *value;
}

char* getNomeCampos (TABLE tb, int coluna){
    return strdup(g_ptr_array_index(tb->nomes_campos, coluna));
}

int get_TB_colunas(TABLE tb){
    int colunas;
    colunas = (int) tb->info_campos->len;
    return colunas;
}

int get_TB_linhas(TABLE tb){
    GPtrArray* aux = g_ptr_array_index(tb->info_campos,0);
    return (int) aux->len;
}

char* get_TB_elem(TABLE tb, int coluna ,int linha){
    char* result;
    GPtrArray* aux = g_ptr_array_index(tb->info_campos,coluna);
    result = strdup((char*)g_ptr_array_index(aux,linha));
    return result;
}

void free_TB(TABLE tb){
    GPtrArray* aux;
    //libertar o cabeçalho
    for(int i = 0; i < (int) tb->nomes_campos->len ; i++){
        free(g_ptr_array_index(tb->nomes_campos,i));
    }
    //libertar a info de cada campo
    for(int i = 0; i < (int)tb->info_campos->len ; i++){
        aux = g_ptr_array_index(tb->info_campos,i);
        g_ptr_array_free(aux, True);
    }
    //libertar o tamanho de cada coluna
    g_ptr_array_free(tb->max_colunas_campos,True);
}


SGR init_sgr(){
    return malloc(sizeof(struct sgr));
}

void free_sgr(SGR sgr){

    freeCatalogoUsers(sgr->u);
    freeCatalogoBusiness(sgr->b);
    freeCatalogoReviews(sgr->r);

    GArray* arr;
    CityStars cs;
    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init(&iter, sgr->starStats);

    while (g_hash_table_iter_next(&iter, &key, &value)){
        arr = (GArray*) value;
        for (int i = 0; i < (int) arr->len; i++){
            freeCityStars(g_array_index(arr, CityStars, i));
        }
        g_array_free(value, False);
    }
    g_hash_table_destroy(sgr->starStats);
}

//query 1
SGR load_sgr(char *users, char *businesses, char *reviews){

    SGR sgr = init_sgr();
    sgr->u = readFileUsers(users);
    if (sgr->u == NULL){
        return NULL;
    }
    printf("Users loaded\n");
    //printCatalogoUsers(sgr->u);
    sgr->r = readFileReviews(reviews);
    if (sgr->r == NULL){
        return NULL;
    }
    printf("Reviews loaded\n");
    //printCatalogoReviews(sgr->r);
    sgr->b = readFileBusiness(businesses);
    if (sgr->b == NULL){
        return NULL;
    }
    printf("Businesses loaded\n");
    //printCatalogoBusiness(sgr->b);
    sgr->starStats = reviewsByCity (sgr->r,sgr->b);
    printf("Stats done\n");

    return sgr;
}

//query 2

TABLE businesses_started_by_letter (SGR sgr, char letter){
    GHashTable *names = get_businesses_by_letter(sgr->b, letter);
    GPtrArray* arr_info = g_ptr_array_new_with_free_func(free);
    TABLE newTable = initTable();
    g_ptr_array_add(newTable->info_campos,arr_info);
    char* str;
    int max_len, len, *insert_len_name;
    max_len = 0;
    GHashTableIter iter;
    gpointer key, value;
    g_ptr_array_add(newTable->nomes_campos,strdup("Nomes"));
    g_hash_table_iter_init(&iter,names);
    while(g_hash_table_iter_next(&iter,&key,&value)){
        str = (char*) value;
        len = strlen(str);
        if (len > (max_len)) max_len = len;
        g_ptr_array_add(arr_info, strdup(str));
    }
    insert_len_name = malloc(sizeof(int));
    (*insert_len_name) = max_len;
    g_ptr_array_add(newTable->max_colunas_campos, insert_len_name);
    g_hash_table_destroy(names);

    return newTable;
}

//query 3


TABLE business_info(SGR sgr, char *business_id){
    char* buf_stars = malloc(sizeof(char) * 4);
    char* buf_count = malloc(sizeof(char) * 8);
    //ir buscar valores
    int count, ignore;
    float stars;
    BUSINESS b = getBusiness(business_id, (GHashTable *) sgr->b);
    count = traverseReviewsForStars (sgr->r, business_id, &stars);
    char *city = getCityBusiness(b);
    char *name = getNameBusiness(b);
    char *state = getStateBusiness(b);
    if (gcvt(stars,2,buf_stars) == NULL) ignore = 0;
    int *len_name, *len_city, *len_estado , *len_stars, *len_count;



    //inicializa a table
    TABLE newTable = initTable();

    //cabeçalho
    g_ptr_array_add(newTable->nomes_campos,strdup("Nome"));
    g_ptr_array_add(newTable->nomes_campos,strdup("Cidade"));
    g_ptr_array_add(newTable->nomes_campos,strdup("Estado"));
    g_ptr_array_add(newTable->nomes_campos,strdup("Stars"));
    g_ptr_array_add(newTable->nomes_campos,strdup("Count"));

    //corpo
    GPtrArray* campo1 = g_ptr_array_new_with_free_func(free);
    g_ptr_array_add(campo1,name);
    g_ptr_array_insert(newTable->info_campos,0,campo1);
    len_name = malloc(sizeof(int));
    (*len_name) = strlen(name);
    g_ptr_array_insert(newTable->max_colunas_campos,0,len_name);

    GPtrArray* campo2 = g_ptr_array_new_with_free_func(free);
    g_ptr_array_add(campo2,city);
    g_ptr_array_insert(newTable->info_campos,1,campo2);
    len_city = malloc(sizeof(int));
    (*len_city) = strlen(city);
    g_ptr_array_insert(newTable->max_colunas_campos,1,len_city);

    GPtrArray* campo3 = g_ptr_array_new_with_free_func(free);
    g_ptr_array_add(campo3,state);
    g_ptr_array_insert(newTable->info_campos,2,campo3);
    len_estado = malloc(sizeof(int));
    (*len_estado) = 6;
    g_ptr_array_insert(newTable->max_colunas_campos,2,len_estado);

    GPtrArray* campo4 = g_ptr_array_new_with_free_func(free);
    g_ptr_array_add(campo4,buf_stars);
    g_ptr_array_insert(newTable->info_campos,3,campo4);
    len_stars = malloc(sizeof(int));
    (*len_stars) = 5;
    g_ptr_array_insert(newTable->max_colunas_campos,3,len_stars);

    GPtrArray* campo5 = g_ptr_array_new_with_free_func(free);
    sprintf(buf_count, "%d", count);
    g_ptr_array_add(campo5,buf_count);
    g_ptr_array_insert(newTable->info_campos,4,campo5);
    len_count = malloc(sizeof(int));
    (*len_count) = 5;
    g_ptr_array_insert(newTable->max_colunas_campos,4,len_count);


    freeBusiness(b);

    return newTable;
}




//query 4

TABLE businesses_reviewed (SGR sgr, char* user_id){
    GHashTable *names = traverseReviewsForID(sgr->r, user_id, sgr->b);

    gpointer key, value;

    GHashTableIter iter;
    g_hash_table_iter_init (&iter, names);

    char* strName;
    char* strID;
    //INICIALIZAÇÃO DA TABLE
    GPtrArray* arr_infoName = g_ptr_array_new_with_free_func(free);
    GPtrArray* arr_infoID = g_ptr_array_new_with_free_func(free);

    TABLE newTable = initTable();
    g_ptr_array_add(newTable->info_campos,arr_infoName);
    g_ptr_array_add(newTable->info_campos,arr_infoID);

    char* str;

    //cabeçalho
    g_ptr_array_add(newTable->nomes_campos,strdup("Nomes"));
    g_ptr_array_add(newTable->nomes_campos,strdup("ID"));

    //corpo
    int max_len_name = 0, max_len_id = 0, len_id, len_name, *insert_len_name, *insert_len_id;

    while (g_hash_table_iter_next (&iter, &key, &value)){
        strName = (char*) value;
        strID = (char*) key;
        len_name = strlen(strName);
        len_id = strlen(strID);

        if (len_name > max_len_name) max_len_name = len_name;
        if (len_id > max_len_id) max_len_id = len_id;
        g_ptr_array_add(arr_infoName, strdup(strName));
        g_ptr_array_add(arr_infoID, strdup(strID));
    }
    insert_len_name = malloc(sizeof(int));
    (*insert_len_name) = max_len_name;
    insert_len_id = malloc(sizeof(int));
    (*insert_len_id) = max_len_id;
    g_ptr_array_insert(newTable->max_colunas_campos,0, insert_len_name);
    g_ptr_array_insert(newTable->max_colunas_campos,1, insert_len_id);

    g_hash_table_destroy(names);

    return newTable;
}

//query 5
TABLE businesses_with_stars_and_city(SGR sgr, float stars, char *city){
    TABLE newTable = NULL;
    gpointer orig_key, orig_value;
    char* strName, *strID;
    strLower(city);
    if(g_hash_table_lookup_extended(sgr->starStats, city, &orig_key, &orig_value)){

        GArray *businesses = (GArray *) orig_value;

        //INICIALIZAÇÃO DA TABLE
        newTable = initTable();

        GPtrArray* arr_infoName = g_ptr_array_new_with_free_func(free);
        GPtrArray* arr_infoID = g_ptr_array_new_with_free_func(free);

        g_ptr_array_insert(newTable->info_campos,0,arr_infoID);
        g_ptr_array_insert(newTable->info_campos,1,arr_infoName);

        //cabeçalho
        g_ptr_array_add(newTable->nomes_campos,strdup("ID"));
        g_ptr_array_add(newTable->nomes_campos,strdup("Nomes"));

        int max_len_name = 0, max_len_id = 0, len_name, len_id , *insert_len_name,*insert_len_id;


        CityStars bus;
        BUSINESS aux;
        for(int i = 0; i < (int) businesses->len  && getMeanCS(bus = g_array_index(businesses, CityStars, i)) >= stars; i++){
            strID = getBusinessIdCS(bus);
            aux = getBusiness(strID,sgr->b);
            strName = getNameBusiness(aux);
            len_name = strlen(strName);
            len_id = strlen(strID);
            if (len_name > max_len_name) max_len_name = len_name;
            if (len_id > max_len_id) max_len_id = len_id;
            freeBusiness(aux);
            g_ptr_array_add(arr_infoID, strID);
            g_ptr_array_add(arr_infoName, strName);
        }
        insert_len_name = malloc(sizeof(int));
        (*insert_len_name) = max_len_name;
        insert_len_id = malloc(sizeof(int));
        (*insert_len_id) = max_len_id;
        g_ptr_array_insert(newTable->max_colunas_campos,0, insert_len_id);
        g_ptr_array_insert(newTable->max_colunas_campos,1, insert_len_name);

    }
    return newTable;
}

//Query 6
TABLE top_businesses_by_city(SGR sgr, int top){
    GHashTableIter iter;
    gpointer key, value;
    char* id, *name, *stars;
    BUSINESS aux_bus;
    CityStars cs;
    GArray* arr;
    g_hash_table_iter_init(&iter,sgr->starStats);

    /* INICIALIZAÇÃO DA TABLE */
    TABLE tb = initTable();
    g_ptr_array_insert(tb->nomes_campos,0,strdup("ID"));
    g_ptr_array_insert(tb->nomes_campos,1,strdup("NOME"));
    g_ptr_array_insert(tb->nomes_campos,2,strdup("STARS"));
    for(int i = 0; i < 3 ; i++){
        GPtrArray* aux = g_ptr_array_new_with_free_func(free);
        g_ptr_array_insert(tb->info_campos,i,aux);
    }

    int max_len_name = 0, max_len_id = 0, len_name, len_id, *len_stars , *insert_len_name, *insert_len_id;
    int size = 0, max = 0, sum = 0;

    while(g_hash_table_iter_next(&iter,&key, &value)){
        arr = (GArray *) value;
        GPtrArray* info_id = g_ptr_array_index(tb->info_campos,0);
        GPtrArray* info_name = g_ptr_array_index(tb->info_campos,1);
        GPtrArray* info_stars = g_ptr_array_index(tb->info_campos,2);
        size = arr->len;
        for(int i = 0; i < (int) arr->len && i < top ;i++){
            cs = g_array_index(arr, CityStars, i);
            id = getBusinessIdCS(cs);
            aux_bus = getBusiness(id,sgr->b);
            name = getNameBusiness(aux_bus);
            len_name = strlen(name);
            len_id = strlen(id);
            if (len_name > max_len_name) max_len_name = len_name;
            if (len_id > max_len_id) max_len_id = len_id;
            char* buf_stars = malloc(sizeof(char) * 4);
            int ignore;
            if (gcvt(getMeanCS(cs),2,buf_stars) == NULL) ignore = 0;
            g_ptr_array_add(info_id,id);
            g_ptr_array_add(info_name,name);
            g_ptr_array_add(info_stars,buf_stars);
            freeBusiness(aux_bus);
        }
        if (size > max) max = size;
        sum += size;
        len_stars = malloc(sizeof(int));
        (*len_stars) = 5;
        insert_len_id = malloc(sizeof(int));
        (*insert_len_id) = max_len_id;
        insert_len_name = malloc(sizeof(int));
        (*insert_len_name) = max_len_name;
        g_ptr_array_insert(tb->max_colunas_campos,0, insert_len_id);
        g_ptr_array_insert(tb->max_colunas_campos,1, insert_len_name);
        g_ptr_array_insert(tb->max_colunas_campos,2, len_stars);
    }
    return tb;
}


// query 7

TABLE international_users (SGR sgr){
    traverseReviewsForState(sgr->r, sgr->u, sgr->b);
    GArray* result = traverseUsersForState(sgr->u);

    /* INICIALIZAÇÃO DA TABLE */
    TABLE tb = initTable();
    g_ptr_array_insert(tb->nomes_campos,0,strdup("ID"));

    int max_len_id = 0, len_id, *insert_len_id;

    //CORPO
    GPtrArray* info_id = g_ptr_array_new_with_free_func(free);
    g_ptr_array_insert(tb->info_campos,0,info_id);
    for(int i = 0; i< (int) result->len; i++){
        char * idInsert = strdup(g_array_index(result, char*, i));
        len_id = strlen(idInsert);
        if (len_id > max_len_id) max_len_id = len_id;
        g_ptr_array_add(info_id,idInsert);

    }
    insert_len_id = malloc(sizeof(int));
    (*insert_len_id) = max_len_id;
    g_ptr_array_insert(tb->max_colunas_campos, 0, insert_len_id);
    g_array_free(result,True);
    return tb;
}

// query 8

TABLE top_businesses_with_category(SGR sgr, int top, char *category){
    char* buf_stars, *name_bus;
    char* bus_id;
    GHashTable* top_categories;
    GHashTableIter iter;
    gpointer key, value;
    GArray* final = g_array_new(False , False, sizeof(BizScore));
    GArray* array;
    BUSINESS bus;
    char* cmp_category = strdup(category);
    strLower(cmp_category);
    g_hash_table_iter_init(&iter, sgr->starStats);
    while (g_hash_table_iter_next(&iter, &key, &value)){
        array = (GArray*) value;
        CityStars cs;
        for (int i = 0; i < (int) array->len; i++){
            BizScore bs = init_BS();
            cs = g_array_index(array, CityStars, i);
            bus_id = getBusinessIdCS(cs);
            bus = getBusiness(bus_id, (GHashTable *) sgr->b);
            if (existsCategory(bus, cmp_category)){
                setBusinessId_BS(bs,bus_id);
                name_bus = getNameBusiness(bus);
                setName_BS(bs,name_bus);
                free(name_bus);
                setScore_BS(bs,getMeanCS(cs));
                g_array_append_val(final, bs);
            }
            free(bus);
            free(bus_id);
        }
    }
    free(cmp_category);

    // INICIALIZAÇÃO DA TABLE
    TABLE tb = initTable();
    g_ptr_array_insert(tb->nomes_campos,0,strdup("ID"));
    g_ptr_array_insert(tb->nomes_campos,1,strdup("NOME"));
    g_ptr_array_insert(tb->nomes_campos,2,strdup("STARS"));
    GPtrArray* info_id = g_ptr_array_new_with_free_func(free);
    GPtrArray* info_nome = g_ptr_array_new_with_free_func(free);
    GPtrArray* info_stars = g_ptr_array_new_with_free_func(free);
    g_ptr_array_insert(tb->info_campos,0,info_id);
    g_ptr_array_insert(tb->info_campos,1,info_nome);
    g_ptr_array_insert(tb->info_campos,2,info_stars);

    int max_len_id = 0, max_len_name = 0, len_id, len_name, *len_stars, *insert_len_name, *insert_len_id;

    for(int i = 0; i < (int) final->len && i < top; i++){
        buf_stars = malloc(sizeof(char) * 4);
        BizScore bs = g_array_index(final, BizScore, i);
        char * nameInsert = get_BizScore_Name(bs);
        char * idInsert = get_BizScore_ID(bs);
        len_id = strlen(idInsert);
        len_name = strlen(nameInsert);
        if (len_id > max_len_id) max_len_id = len_id;
        if (len_name > max_len_name) max_len_name = len_name;
        float stars = get_BizScore_Stars(bs);
        int ignore;
        if (gcvt(stars,2,buf_stars) == NULL) ignore = 0;
        g_ptr_array_add(info_id,idInsert);
        g_ptr_array_add(info_nome, nameInsert);
        g_ptr_array_add(info_stars, buf_stars);
        freeBizScore(bs);
    }
    len_stars = malloc(sizeof(int));
    (*len_stars) = 5;
    insert_len_id = malloc(sizeof(int));
    (*insert_len_id) = max_len_id;
    insert_len_name = malloc(sizeof(int));
    (*insert_len_name) = max_len_name;
    g_ptr_array_insert(tb->max_colunas_campos, 0, insert_len_id);
    g_ptr_array_insert(tb->max_colunas_campos, 1, insert_len_name);
    g_ptr_array_insert(tb->max_colunas_campos, 2, len_stars);
    return tb;
}

// query 9
TABLE reviews_with_word (SGR sgr, char *word){
    GArray* reviewsText = findReviewsText(sgr->r, word);

    /* INICIALIZAÇÃO DA TABLE */
    TABLE tb = initTable();
    g_ptr_array_insert(tb->nomes_campos,0,strdup("ID"));

    int max_len_id = 0, len_id, *insert_len_id;

    //CORPO
    GPtrArray* info_id = g_ptr_array_new_with_free_func(free);
    g_ptr_array_insert(tb->info_campos,0,info_id);
    for(int i = 0; i < (int) reviewsText->len; i++){
        char * idInsert = strdup(g_array_index(reviewsText, char*, i));
        len_id = strlen(idInsert);
        if (len_id > max_len_id) max_len_id = len_id;
        g_ptr_array_add(info_id,idInsert);
    }
    insert_len_id = malloc(sizeof(int));
    (*insert_len_id) = max_len_id;
    g_ptr_array_insert(tb->max_colunas_campos, 0, insert_len_id);
    g_array_free(reviewsText,True);
    return tb;
}
