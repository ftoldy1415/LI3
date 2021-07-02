#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <glib.h>
#include "../../include/interpretador.h"
#define line_size_fich 345000
#define LT -1
#define EQ 0
#define GT 1

//sugestão para o filter: pegar no valor a filtrar e tirar os espaços, bem como quando há a comparação com o valor em si, bem como meter para lower.

typedef struct vars_iterp{
    SGR sgr;
    char* name_sgr;
    GHashTable* vars;//hash de tables , cada key mapeia uma variavel
}*VARS;

 VARS init_vars(){
    VARS vs = malloc(sizeof(struct vars_iterp));
    vs->vars = g_hash_table_new_full(g_str_hash, g_str_equal, free, NULL);
    vs->sgr = NULL;
    vs->name_sgr = NULL;
    //definir função free table
    return vs;
}

void freeVars(VARS vs){
    TABLE tb;

    GHashTableIter iter;
    gpointer key, value;

    g_hash_table_iter_init(&iter, vs->vars);
    while (g_hash_table_iter_next(&iter, &key, &value)){
        tb = (TABLE) value;
        free_TB(value);
    }
    g_hash_table_destroy(vs->vars);
    free_sgr(vs->sgr);
    free(vs->name_sgr);
    free(vs);
}

TABLE filter(TABLE x, char* column_name, char* value, char* oper){
    TABLE result = NULL;
    int tb_col, cmp_col = -1, linhas ,inserir, size;
    tb_col = get_TB_colunas(x);
    linhas = get_TB_linhas(x);
    char* nome_coluna_atual, *elem, *data,*nome;
    char *cmp_value, *cmp_elem;
    cmp_value = strdup(value);
    strLower(cmp_value);
    for (int i = 0; i < tb_col; i++){
        nome_coluna_atual = getNomeCampos(x, i);
        if(strcmp(nome_coluna_atual, column_name) == 0) cmp_col = i;
    }
    if (cmp_col == -1){
        printf ("Nenhuma coluna com o nome %s\n", column_name);
    }
    else{
        result = initTable();
        setInfoCampos(result, tb_col);
        incializaSizeColuna(result, tb_col);
        for (int k = 0; k < tb_col; k++){
            nome = getNomeCampos(x, k);
            insertNomeCampos(result, k, nome);
            size = getSizeColuna(x, k);
            setSizeColuna(result, k, size);
            free(nome);
        }
        for (int i_linha = 0; i_linha < linhas; i_linha++){
            inserir = 0;
            elem = get_TB_elem(x, cmp_col, i_linha);
            cmp_elem = tiraEspacos(elem);
            strLower(cmp_elem);
            if (strcmp(oper, "LT") == 0){
                if (strcmp(cmp_value, cmp_elem) > 0) inserir = 1;
            }
            else if(strcmp(oper, "EQ") == 0){
                if (strcmp(cmp_value, cmp_elem) == 0) inserir = 1;
            }
            else if(strcmp(oper, "GT") == 0){
                if (strcmp(cmp_value, cmp_elem) < 0) inserir = 1;
            }
            else{
                printf ("%s não é um operador valido\n", oper);
            }
            if (inserir){
                for (int i_coluna = 0; i_coluna < tb_col; i_coluna++){
                    data = get_TB_elem(x, i_coluna, i_linha);
                    insertInfoCampos(result, 0, i_coluna, data);
                   free(data);
                }
            }
            free(cmp_elem);
        }
    }
    free(cmp_value);

    return result;
}

TABLE proj(TABLE x, char* cols){
    int tb_x_col = get_TB_colunas(x);
    int n_cols = 0, col, linhas, size, new_colunas = 0;
    char* data, *nome;
    TABLE result = NULL;
    for(int i = 0; cols[i] != '\0' ; i++){
        col = (int) cols[i];
        col -= 48;
        if (col < tb_x_col && col >= 0) n_cols++;
    }
    if(n_cols > 0){
        result = initTable();
        setInfoCampos(result, n_cols);
        incializaSizeColuna(result,n_cols);
        linhas = get_TB_linhas(x);
        for(int i = 0; cols[i] != '\0' ; i++){
            col = (int) cols[i];
            col -= 48;
            if (col >= 0 && col < tb_x_col){
                nome = getNomeCampos(x, col);
                insertNomeCampos(result, new_colunas, nome);
                for(int i_linhas = 0 ; i_linhas < linhas; i_linhas++){
                    data = get_TB_elem(x, col, i_linhas);
                    size = getSizeColuna(x, col);
                    insertInfoCampos(result, 0, new_colunas, data);
                    setSizeColuna(result, new_colunas, size);
                    free(data);
                }
                free(nome);
                new_colunas++;
            }
        }
    }
    return result;
}

TABLE indexSearch(TABLE tb, char* var1, char* var2){
    TABLE result = NULL;
    int linha, coluna;
    linha = atoi(var1);
    coluna = atoi(var2);
    char* name, *value;
    int len;
    if (linha <= get_TB_linhas(tb) && coluna <= get_TB_colunas(tb)){
        result = initTable();
        setInfoCampos(result, 1);
        incializaSizeColuna(result, 1);
        name = getNomeCampos (tb, coluna);
        insertNomeCampos(result, coluna, name);
        value = get_TB_elem(tb, coluna, linha);
        len = strlen(value);
        insertInfoCampos(result, 0, 0, value);
        setSizeColuna(result, 0, len);
        free(name);
        free(value);
    }
    return result;
}

void toCSV(TABLE tb, char delim ,char* filepath){
    int i_coluna = 0, linhas , i_linha = 0, colunas, max_colunas;
    char* info;
    FILE *fptr = fopen(filepath,"w");
    linhas = get_TB_linhas(tb);
    colunas = get_TB_colunas(tb);
    while (i_coluna < colunas){
        info = getNomeCampos (tb, i_coluna);
        fwrite(info, strlen(info), 1, fptr);
        if (i_coluna != colunas-1) fprintf (fptr, "%c", delim);
        free(info);
        i_coluna++;
    }
    fprintf(fptr, "\n");
    i_coluna = 0;
    while (i_linha < linhas) {
        while(i_coluna < colunas){
            info = get_TB_elem(tb,i_coluna,i_linha);
            fwrite(info,strlen(info),1,fptr);
            if(i_coluna != colunas-1) fprintf(fptr,"%c",delim);
            free(info);
            i_coluna++;
        }
        fprintf(fptr,"\n");
        i_coluna = 0;
        i_linha++;
    }
    fclose(fptr);
}

TABLE fromCSV(char* filepath, char* delim){
    TABLE tb = initTable();
    FILE *fp = fopen(filepath, "r");
    if(fp == NULL){
        printf("erro no ficheiro\n");
        return NULL;
    }
    char *buffer = malloc(sizeof(char) * line_size_fich), *save_buffer;
    save_buffer = buffer;

    char* token, *ignore;
    ignore = fgets(buffer, line_size_fich, fp);
    buffer = strsep(&buffer, "\n");

    int i = 0;
    while (buffer != NULL){
        token = strsep(&buffer, delim);
        insertNomeCampos(tb, i, token);
        setSizeColuna(tb, i, strlen(token));
        i++;
    }

    buffer = save_buffer;
    int n_colunas = i, linhas = 0, colunas, size, tb_size;
    setInfoCampos(tb, n_colunas);
    //incializaSizeColuna(tb, n_colunas);
    while(fgets(buffer, line_size_fich, fp)){
        buffer = strsep(&buffer, "\n");
        colunas = 0;
        while (buffer != NULL){
            token = strsep(&buffer, delim);
            size = strlen(token);
            if(size > getSizeColuna(tb,colunas)){
                setSizeColuna(tb, colunas, size);
            }
            insertInfoCampos(tb, linhas, colunas, token);
            colunas++;
        }
        linhas++;
        buffer = save_buffer;
    }
    free(save_buffer);
    fclose(fp);
    return tb;
}

void callFunction(VARS v, int id_function,char** args, char* var){
    gpointer key,value;
    switch (id_function)
    {
    case 0: //show
        if(g_hash_table_lookup_extended(v->vars,*args,&key,&value)) {
            pages(value);
        }
        else printf("ERRO:: %s não foi atribuido ou não é uma TABLE válida\n",*args);
        break;
    case 1: //load_sgr sem argumentos.
        if(v->sgr == NULL){
            v->sgr = load_sgr("./input_files/users_full.csv", "./input_files/business_full.csv", "./input_files/reviews_1M.csv");
            v->name_sgr = var;
        }
        else{
            free_sgr(v->sgr);
            v->sgr = load_sgr("./input_files/users_full.csv", "./input_files/business_full.csv", "./input_files/reviews_1M.csv");
            v->name_sgr = var;
        }
        break;
//load_sgr(./input_files/users_full.csv, ./input_files/business_full.csv, ./input_files/reviews_1M.csv);
    case 3:
        printf ("\ncall load_sgr com argumentos\n");
        if (v->sgr == NULL){
            free(v->sgr);
        }
        v->sgr = load_sgr(args[0], args[1], args[2]);
        if (v->sgr == NULL){
            printf ("Caminhos para os ficheiros inválidos\n");
        }
        else v->name_sgr = var;
        break;
    case 4:
        printf ("\ncall toCSV\n");
        if (g_hash_table_lookup_extended(v->vars, args[0], &key, &value)){
            TABLE result = (TABLE) value;
            toCSV(result, *args[1], args[2]);
        }
        else printf("ERRO:: %s não foi atribuido ou não é uma TABLE válida\n",args[0]);
        break;
    case 5:
        printf ("\ncall fromCSV\n");
        if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
            free_TB(value);
        }
        TABLE result = fromCSV(args[0], args[1]);
        if (result != NULL) g_hash_table_insert(v->vars, var, result);
        break;
    case 6:
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = businesses_started_by_letter(v->sgr,*args[1]);
            g_hash_table_insert(v->vars, var, result);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;

    case 7://business_info
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = business_info(v->sgr,args[1]);
            g_hash_table_insert(v->vars, var, result);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;

    case 8:
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = businesses_reviewed(v->sgr,args[1]);
            g_hash_table_insert(v->vars, var, result);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;

    case 9:
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = businesses_with_stars_and_city(v->sgr,atof(args[1]),args[2]);
            if(result != NULL ) g_hash_table_insert(v->vars, var, result);
            else printf ("\n%s nao é um cidade valida \n", args[2]);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;
    case 10:
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = top_businesses_by_city(v->sgr,atoi(args[1]));
            g_hash_table_insert(v->vars, var, result);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;
    case 11:
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = international_users (v->sgr);
            g_hash_table_insert(v->vars, var, result);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;
    case 12:
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = top_businesses_with_category(v->sgr, atoi(args[1]), args[2]);
            g_hash_table_insert(v->vars, var, result);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;
    case 13:
        if (v->name_sgr != NULL && strcmp(args[0],v->name_sgr) == 0){
            if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
                free_TB(value);
            }
            TABLE result = reviews_with_word(v->sgr, args[1]);
            g_hash_table_insert(v->vars, var, result);
        }
        else{
            printf ("\n%s não foi carregado\n", args[0]);
        }
        break;
    case 14: //indexação
        if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
            free_TB(value);
        }
        if (g_hash_table_lookup_extended(v->vars, args[0], &key, &value)){
            TABLE table_x = (TABLE) value;
            TABLE result_index = indexSearch(table_x, args[1], args[2]);
            if (result_index != NULL){
                g_hash_table_insert(v->vars, var, result_index);
            }
            else{
                printf ("Argumentos fora dos limites da table\n");
            }
        }
        else{
            printf ("\nVariavel %s não carregada.\n", args[0]);
        }
        break;
    case 15: //projeção
        if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
            free_TB(value);
        }
        if (g_hash_table_lookup_extended(v->vars, args[0], &key, &value)){
            TABLE table_x = (TABLE) value;
            TABLE result_index = proj(table_x, args[1]);
            if (result_index != NULL){
                g_hash_table_insert(v->vars, var, result_index);
            }
            else{
                printf ("Argumentos fora dos limites da table\n");
            }
        }
        else{
            printf ("\nVariavel %s não carregada.\n", args[0]);
        }
        break;
    case 16: //filter
        if (g_hash_table_lookup_extended(v->vars, var, &key, &value)){
            free_TB(value);
        }
        if (g_hash_table_lookup_extended(v->vars, args[0], &key, &value)){
            TABLE table_x = (TABLE) value;
            TABLE result_index = filter(table_x, args[1], args[2], args[3]);
            if (result_index != NULL){
                g_hash_table_insert(v->vars, var, result_index);
            }
            else{
                printf("Argumentos fora dos limites da table\n");
            }
        }
        else{
            printf("\nVariavel %s não carregada.\n", args[0]);
        }
        break;
    //TABLE filter(TABLE x, char* column_name, char* value, int oper);
    default:
        printf("!! ERRO !!\n");
        break;
    }
}

int isAlphaString(char* str){
    int result = 1;
    for(int i = 0; i < (int) strlen(str) && result; i++){
        if (!isalpha(str[i])) result = 0;
    }
    return result;
}


char** breakParam(char* param, int* n_args){
    char** new_param = malloc(sizeof(char*) * 50);
    char* token;
    int i = 0;
    token = strtok(param, ",");
    while (token != NULL){
        new_param[i] = strdup(token);
        token = strtok(NULL, ",");
        i++;
    }
    (*n_args) = i;
    return new_param;
}

int nextEqual(char* x, int n){
    int i, encontrou = 0, resultado;

    for(int i = n-1; x[i] != '\0' && !encontrou ;i++){
        if(x[i] == '=') encontrou = 1;
    }
    resultado = encontrou;
    return resultado;
}


int checkCommand(VARS v , char * origin){
    char* copia = strdup(origin);
    char delim[20] = "=()[]";
    char** args;
    char* token;
    int i = 0;
    char* orig_sem_espacos = tiraEspacos(origin);
    char** array = malloc(sizeof(char*) * 150);
    token = strtok(orig_sem_espacos, delim);
    while (token != NULL){
        array[i] = strdup(token);
        token = strtok(NULL, delim);
        i++;
    }
    int parametros = i;
    int n_args;
    //comandos
    if(parametros == 2 && (strcmp(array[0],"show") == 0) && isAlphaString(array[1])){
        callFunction(v,0,&array[1], NULL);
    }
    else if(parametros == 2 && (strcmp(array[1],"load_sgr") == 0) && isAlphaString(array[0])){
        callFunction(v,1,NULL, array[0]);
    }
    else if(parametros == 3 && (strcmp(array[1],"load_sgr") == 0) && isAlphaString(array[0])){
        char** parameters = breakParam(array[2], &n_args);
        if (n_args != 3){
            printf ("Invalid number of arguments");
        }
        else{
            callFunction(v, 3, parameters, array[0]);
        }
    }
    else if (parametros == 2 && (strcmp(array[0], "toCSV") == 0)){
        char** parameters = breakParam(array[1], &n_args);
        if (n_args != 3){
            printf ("Invalid number of arguments");
        }
        else{
            if (!isAlphaString(parameters[0]) && strlen(parameters[1]) != 1){
                printf ("Error: Invalid parameters. Function should be called like this: toCSV(variable, 'delim', 'valid filepath')\n");
            }
            else{
                callFunction(v,4,parameters, NULL);
            }
        }
    }
    else if(parametros == 1 && (strcmp(array[0],"quit") == 0)){
        exit(0);
    }
    else if(parametros == 3 && isAlphaString(array[0]) && nextEqual(copia,strlen(array[0]))){
        char** parameters = breakParam(array[2], &n_args);
        if(strcmp(array[1],"fromCSV") == 0){
            if(n_args != 2){
                perror("Insuficient arguments");
            }
            else{
                callFunction(v, 5, parameters, array[0]);
            }
        }
        else if(strcmp(array[1],"businesses_started_by_letter") == 0){
            if(n_args != 2){
                perror("Insuficient arguments");
            }
            else{
                callFunction(v, 6, parameters, array[0]);
            }
        }
        else if (strcmp(array[1],"business_info") == 0){
            if(n_args != 2){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v,7,parameters,array[0]);
            }
        }
        else if (strcmp(array[1],"businesses_reviewed") == 0){
            if(n_args != 2){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v,8,parameters,array[0]);
            }
        }
        else if (strcmp(array[1],"businesses_with_stars_and_city") == 0){
            if(n_args != 3){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v,9,parameters,array[0]);
            }
        }
        else if (strcmp(array[1],"top_businesses_by_city") == 0){
            if(n_args != 2){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v,10,parameters,array[0]);
            }
        }
        else if (strcmp(array[1],"international_users") == 0){
            if(n_args != 1){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v,11,parameters,array[0]);
            }
        }
        else if (strcmp(array[1],"top_businesses_with_category") == 0){
            if(n_args != 3){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v,12,parameters,array[0]);
            }
        }
        else if (strcmp(array[1],"reviews_with_word") == 0){
            if(n_args != 2){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v,13,parameters,array[0]);
            }
        }
        else if (strcmp(array[1], "filter") == 0){
            if(n_args != 4){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v, 16,parameters,array[0]);
            }
        }
        else if (strcmp(array[1], "proj")==0){
            if(n_args != 2){
                perror("Wrong number of arguments");
            }
            else{
                callFunction(v, 15, parameters, array[0]);
            }
        }
    }
    else if(parametros == 4 && isAlphaString(array[0]) && nextEqual(copia,strlen(array[0])) && isdigit(*array[2]) && isdigit(*array[3])){
        char** parameters = malloc(sizeof(char*) * 3);
        parameters[0] = strdup(array[1]);
        parameters[1] = strdup(array[2]);
        parameters[2] = strdup(array[3]);

        callFunction(v, 14, parameters, array[0]);
    }
    else{
        printf("Comando inválido\n");
    }
    return 0;
}

void interp(){
    //ler uma linha do terminal
    VARS vars = init_vars();
    char* line = NULL,*save_line;
    size_t size = 50;
    char delim[20] = ";";
    char* token;
    char *array[100];
    int i, j, comandos, curr, result;
    save_line = line;
    char* buffer;
    printf("SGR$>");
    while (getline(&line, &size, stdin)){
        save_line = line;
        line = strsep(&line, "\n");
        i = 0;
        token = strtok(line, delim);
        while (token != NULL){
            array[i] = strdup(token);
            token = strtok(NULL, delim);
            i++;
        }
        comandos = 0;
        while(comandos < i){
            result = checkCommand(vars,array[comandos]);
            comandos++;
        }
        printf("SGR$>");
    }
    free(save_line);
    freeVars(vars);
}
