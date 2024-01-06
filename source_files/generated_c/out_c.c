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
typedef struct {
	int value0;
	int value1;
	int value2;
	int value3;
} functionStruct;

int moltiplicazione (int input1, int input2);
double divisione (int input1, int input2);
int somma_con_commento (int ac, int b);
functionStruct function ();
char* stampa (char* stringa);
void somma (int input1, int input2, int* result);
void sottrazione (int input1, int input2, int* result);


int moltiplicazione (int input1, int input2) {
int result;
result = input1*input2;
return result;
}
double divisione (int input1, int input2) {
double result;
if (input2==0) {
 printf("%s ", "Errore");
result = 0.0;
}
return result;
}
int somma_con_commento (int ac, int b) {
int risultato;
risultato = ac+b;
if ((risultato)>4) {
int c;
return c;
}
else if (ac>5)  {
return 4;
}
else
 {
int c;
return c;
}
}
functionStruct function () {
functionStruct functionStructReturnValue;
functionStructReturnValue.value0 = moltiplicazione(1, 2);
functionStructReturnValue.value1 = moltiplicazione(1, 2);
functionStructReturnValue.value2 = moltiplicazione(1, 2);
functionStructReturnValue.value3 = moltiplicazione(1, 2);
return functionStructReturnValue;
}
char* stampa (char* stringa) {
char* s = malloc(MAXCHAR);
s = strncpy(s,"ciao", MAXCHAR);
return stringa;
}

void main () {
char* operazione = malloc(MAXCHAR);
int input1;
int input2;
int answer;
bool flag = true;
int result;
char* ciao = malloc(MAXCHAR);
ciao = strncpy(ciao,"ciao", MAXCHAR);
double resultReal;
char* valore = malloc(MAXCHAR);
ciao = str_concat(str_concat(str_concat(bool_to_str(true), "ciao"), integer_to_str(8)), bool_to_str(true));
while(flag==true)  {
int input8;
printf("Inserisci l'operazione da effettuare (somma, sottrazione, divisione, moltiplicazione): ");
fflush(stdin);
scanf("%s", operazione);
printf("Inserisci il primo input: ");
fflush(stdin);
scanf("%d", &input1);
printf("Inserisci il secondo input: ");
fflush(stdin);
scanf("%d", &input2);
if (strncmp(operazione, "somma", MAXCHAR) == 0) {
somma(input1, input2, &result);
}
else if (strncmp(operazione, "sottrazione", MAXCHAR) == 0)  {
sottrazione(input1, input2, &result);
}
else if (strncmp(operazione, "divisione", MAXCHAR) == 0)  {
resultReal = divisione(input1, input2);
}
else if (strncmp(operazione, "moltiplicazione", MAXCHAR) == 0)  {
result = moltiplicazione(input1, input2);
}
else if (strncmp(operazione, "func", MAXCHAR) == 0)  {
int res1;
int res2;
int res3;
int res4;
functionStruct functionReturned0 = function();
res1 = functionReturned0.value0;
res2 = functionReturned0.value1;
res3 = functionReturned0.value2;
res4 = functionReturned0.value3;
}
else
 {
 printf("%s %s", "Operazione non consentita", "\n");
}
 printf("%s %d %s", str_concat("Il risultato e': ", "ciao
    FD"), 3+4, "\n");
printf("Vuoi continuare? (1 yes/0 no): ");
fflush(stdin);
scanf("%d", &answer);
if (answer==1) {
flag = true;
}
else
 {
flag = false;
}
}
valore = stampa(str_concat("ed è pari a", integer_to_str(result)));
}
void somma (int input1, int input2, int* result) {
*result = input1+input2;
}
void sottrazione (int input1, int input2, int* result) {
*result = input1-input2;
}

