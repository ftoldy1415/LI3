#include "../../include/paginacao.h"
#define True 1
#define False 0

int pages(TABLE tb){
    pagesHelp();
    char c;
    int current;
    while ((c = getchar()) != 'q'){
        switch (c){
            case '<':
                mi_clear_screen();
                current = getCurrent(tb) - (2 * PAGE_SIZE);
                if(current < 0) current = 0;
                show(tb, current);
                break;

            case '>':
                mi_clear_screen();
                current = getCurrent(tb);
                show(tb, current);
                break;
            default:
                break;
        }
    }
    mi_exit_alt_screen();
    return 0;
}
