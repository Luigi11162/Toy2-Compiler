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
char* stampa (char* messaggio);
void sommac (int a, int d, double b, char* *size, double *result);

int c = 1;

char* stampa (char* messaggio) {
int i = 0;
while(i<4)  {
i = i+1;
 printf("%s",  "\n");
}
 printf("%s ", messaggio);
return "ok";
}

void sommac (int a, int d, double b, char* *size, double *result) {
*result = a+b+c+d;
if (*result>100) {
char* valore = malloc(MAXCHAR);
valore = strncpy(valore,"grande", MAXCHAR);
*size = valore;
}
else if (*result>50)  {
char* valore = malloc(MAXCHAR);
valore = strncpy(valore,"media", MAXCHAR);
*size = valore;
}
else
 {
char* valore = malloc(MAXCHAR);
valore = strncpy(valore,"piccola", MAXCHAR);
*size = valore;
}
}
void main () {
int a = 1;
double b = 2.2;
int x = 3;
char* taglia = malloc(MAXCHAR);
char* ans1 = malloc(MAXCHAR);
char* ans = malloc(MAXCHAR);
ans = strncpy(ans,"no", MAXCHAR);
double risultato = 0.0;
char* valore = malloc(MAXCHAR);
valore = strncpy(valore,"nok", MAXCHAR);
sommac(a, x, b, &taglia, &risultato);
valore = stampa(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat("la somma di ", integer_to_str(a)), " e "), real_to_str(b)), " incrementata di "), integer_to_str(c)), " è "), taglia));
valore = stampa(str_concat("ed è pari a ", real_to_str(risultato)));
printf("vuoi continuare? (si/no) - inserisci due volte la risposta");
fflush(stdin);
scanf("%s", ans);
fflush(stdin);
scanf("%s", ans1);
while(strncmp(ans, "si", MAXCHAR) == 0)  {
printf("inserisci un intero:");
fflush(stdin);
scanf("%d", &a);
printf("inserisci un reale:");
fflush(stdin);
scanf("%lf", &b);
sommac(a, x, b, &taglia, &risultato);
valore = stampa(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat("la somma di ", integer_to_str(a)), " e "), real_to_str(b)), " incrementata di "), integer_to_str(c)), " è "), taglia));
valore = stampa(str_concat(" ed è pari a ", real_to_str(risultato)));
printf("vuoi continuare? (si/no):\t");
fflush(stdin);
scanf("%s", ans);
}
 printf("%s",  "\n");
 printf("%s ", "ciao");
}

