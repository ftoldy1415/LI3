#ifndef _INTERFACE_H_
#define _INTERFACE_H_
#include <glib.h>

#define PAGE_SIZE 9

void mainMenu();
void ajudaMenu();
void show();
void pagesHelp();
#define mi_clear_screen() puts("\x1B[2J")
#define mi_enter_alt_screen() puts("\033[?1049h\033[H")
#define mi_exit_alt_screen() puts("\033[?1049l")
#define TC_BG_BLU "\x1B[44m"
#define TC_BG_NRM "\x1B[40m"
#define TC_NRM  "\x1B[0m"

#endif
