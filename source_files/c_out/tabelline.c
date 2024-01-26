#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <unistd.h>
#include <stdbool.h>
#define MAXCHAR 512

char* integer_to_str(int i);
char* real_to_str(double i);
char* bool_to_str(bool i);
char* str_concat(char* str1, char* str2);

//Funzioni di supporto 
char* integer_to_str(int i){
int length= snprintf(NULL,0,"%d",i);
char* result=malloc(length+1);
snprintf(result,length+1,"%d",i);
return result;
}
char* real_to_str(double i){
int length= snprintf(NULL,0,"%lf",i);
char* result=malloc(length+1);
snprintf(result,length+1,"%f",i);
return result;
}
char* bool_to_str(bool i){
int length= snprintf(NULL,0,"%d",i);
char* result=malloc(length+1);
snprintf(result,length+1,"%d",i);
return result;
}
char* str_concat(char* str1, char* str2){
char* result=malloc(sizeof(char)*MAXCHAR);
result=strcat(result,str1);
result=strcat(result,str2);
return result;}
void stampa_tabellina (int max_value);



void stampa_tabellina (int max_value) {
int i = 1;
int j = 1;
while(i<=max_value)  {
while(j<=max_value)  {
 printf("%d ", i*j);
j = j+1;
}
j = 1;
i = i+1;
 printf("%s %s", "", "\n");
}
}
void main () {
int size_tabellina;
printf("Inserisci la grandezza della tabellina: ");
fflush(stdin);
scanf("%d", &size_tabellina);
stampa_tabellina(size_tabellina);
}

