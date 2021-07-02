#include "../../include/auxStructs.h"

struct bizScore{
    char* business_id;
    char* name;
    float score;
};

BizScore init_BS(){
    BizScore bs = malloc(sizeof(struct bizScore));
    return bs;
}

void freeBizScore(BizScore bs){
    free(bs->business_id);
    free(bs->name);
    free(bs);
}


char* get_BizScore_Name(BizScore bs){
    return strdup(bs->name);
}

char* get_BizScore_ID(BizScore bs){
    return strdup(bs->business_id);
}

float get_BizScore_Stars(BizScore bs){
    return bs->score;
}

gint compareBizScore(gconstpointer a, gconstpointer b){
  int result = 0;
  BizScore* newA = (BizScore*) a;
  BizScore* newB = (BizScore*) b;
  if(((*newA)->score - (*newB)->score) < 0.0) result = 1;
  if(((*newA)->score - (*newB)->score) > 0.0) result = -1;
  return result;
}

void setBusinessId_BS(BizScore bs, char* id){
    bs->business_id = strdup(id);
}

void setName_BS(BizScore bs, char* name){
    bs->name = strdup(name);
}

void setScore_BS(BizScore bs, float score){
    bs->score = score;
}

void strLower(char * str){
    for(int i = 0; str[i] != '\0' ; i++){
      str[i] = tolower(str[i]);
    }
}

char* tiraEspacos(char* origin){
    int len = strlen(origin);
    int o = 0;
    int r = 0;

    char result[len];
    while (o < len){
        if (origin[o] != ' '){
            result[r] = origin[o];
            r++;
        }
        o++;
    }
    result[r] = '\0';

    char *final = strdup(result);

    return final;
}
