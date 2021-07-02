#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../../include/interface.h"
#include "../../include/sgr.h"
#include "../../include/interpretador.h"
#include <glib.h>


void show(TABLE tb, int current){
    int i_coluna = 0, linhas , i_linha = current, colunas, max_colunas;
    char* info;
    int limite = current+PAGE_SIZE;
    linhas = get_TB_linhas(tb);
    colunas = get_TB_colunas(tb);
    char* nome;
    int size = 0;
    for (int i = 0; i < colunas; i++){
        nome = getNomeCampos(tb, i);
        max_colunas = getSizeColuna(tb,i);
        size += max_colunas;
        if(i == 0) printf("|");
        printf("%-*.*s",max_colunas,max_colunas,nome);
        printf("|");
        free(nome);
    }
    printf ("\n");
    printf (" ");
    for (int i = 0; i < size + colunas - 1; i++){
        printf ("-");
    }
    printf ("\n");
    while (i_linha < limite && i_linha < linhas) {
        size = 0;
        while(i_coluna < colunas){
            max_colunas = getSizeColuna(tb,i_coluna);
            size += max_colunas;
            info = get_TB_elem(tb,i_coluna,i_linha);
            if(i_coluna == 0) printf("|");
            printf("%-*.*s",max_colunas,max_colunas,info);
            printf("|");
            free(info);
            i_coluna++;
        }
        printf ("\n");
        printf (" ");
        for (int i = 0; i < size + colunas - 1; i++){
            printf ("-");
        }
        printf ("\n");
        i_coluna = 0;
        i_linha++;
    }
    setCurrent(tb, i_linha);
    printf ("Número total de linhas: %d\n", linhas);
    printf ("Página atual: %d\n", current/PAGE_SIZE);
    printf("( < ) - prev | ( > ) - next | ( q ) - Regressar ao SGR\n");
}

void pagesHelp(){
    mi_enter_alt_screen();
    printf("\n|  * * * * * * * * * * * * * * * * PAGINAÇÃO * * * * * * * * * * * * * * * * * |\n");
    printf("COMANDOS:\n");
    printf("( < ) - prev | ( > ) - next\n( q ) - Regressar ao SGR\n");
}


void mainMenu(){
    mi_clear_screen();
    int a = 0, ignore;
    printf ("%sSistema de gestão e recomendações de negócios na plataforma Yelp%s\n",TC_BG_BLU,TC_NRM);
    printf ("Seguem-se as Funcionalidades deste programa:\n");
    printf ("1 - Aderir ao interpretador\n");
    printf ("2 - Ajuda\n");
    printf ("3 - Sair\n");
    while(a != 3){
        if ((ignore = scanf("%d",&a)) != 0){
            switch(a){
                case 1:
                    interp();
                    mi_exit_alt_screen();
                    break;
                case 2:
                    mi_exit_alt_screen();
                    ajudaMenu();
                    mi_exit_alt_screen();
                    break;
                case 3:
                    mi_exit_alt_screen();
                    exit(0);
                    break;
            }   
        }
    }
    mi_exit_alt_screen();
}

void enunciadosQuerys(){
    mi_enter_alt_screen();
    printf("Query 1 - load_sgr(ficheiro users, ficheiro businesses , ficheiro reviews )\n");
    printf("Query 2 - businesses_started_by_letter (sgr, letter)\n");
    printf("Query 3 - business_info(sgr, business_id)\n");
    printf("Query 4 - businesses_reviewed(sgr, user_id)\n");
    printf("Query 5 - businesses_with_stars_and_city( sgr, stars, city)\n");
    printf("Query 6 - top_businesses_by_city(sgr,top)\n");
    printf("Query 7 - international_users (SGR sgr)\n");
    printf("Query 8 - top_businesses_with_category(sgr, top, category)\n");
    printf("Query 9 - reviews_with_word (sgr, word)\n");
    printf("  q     - Voltar para o menu\n");
    while(getchar() != 'q');
    mi_exit_alt_screen();
    ajudaMenu();
}

void ajudaMenu(){
    mi_enter_alt_screen();
    int a, ignore;
    printf ("Opções de ajuda: \n");
    printf ("1 - Enunciados das queries\n");
    printf ("2 - Comandos\n");
    printf ("3 - Voltar ao menu principal\n");
    while( a != 3){
        if ((ignore = scanf("%d",&a)) != 0){
            switch(a){
                case 1:
                    enunciadosQuerys();
                    break;
                case 2:
                    //comandos();
                    break;
                case 3:
                    mi_exit_alt_screen();
                    mainMenu();
                    break;
            }
        }
    }
    mi_exit_alt_screen();
}

