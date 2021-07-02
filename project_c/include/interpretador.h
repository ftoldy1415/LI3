#ifndef INTERPRETADOR_H_
#define INTERPRETADOR_H_
#include "paginacao.h"
#include "sgr.h"
void interp();

void toCSV(TABLE tb, char delim ,char* filepath);
TABLE fromCSV(char* filepath, char* delim);

#endif
