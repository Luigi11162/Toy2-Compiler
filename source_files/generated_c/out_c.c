#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <unistd.h>
#include <stdbool.h>
#define MAXCHAR 512

char* integer_to_str(int i);
char* real_to_str(float i);
char* char_to_str(char i);
char* bool_to_str(bool i);
char* str_concat(char* str1, char* str2);
char* read_str();
int str_to_bool(char* expr);

//Funzioni di supporto 
char* integer_to_str(int i){
int length= snprintf(NULL,0,"%d",i);
char* result=malloc(length+1);
snprintf(result,length+1,"%d",i);
return result;
}
char* real_to_str(float i){
int length= snprintf(NULL,0,"%f",i);
char* result=malloc(length+1);
snprintf(result,length+1,"%f",i);
return result;
}
char* char_to_str(char i){
int length= snprintf(NULL,0,"%c",i);
char* result=malloc(length+1);
snprintf(result,length+1,"%c",i);
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

char* read_str(){
char* str=malloc(sizeof(char)*MAXCHAR);
scanf("%s",str);
return str;}

int str_to_bool(char* expr){
int i=0;
if ( (strcmp(expr, "true")==0) || (strcmp(expr, "1"))==0 )
i=1;
if ( (strcmp(expr, "false")==0) || (strcmp(expr, "0"))==0 )
i=0;
return i;}
typedef struct {
	int value0;
	int value1;
} moltiplicazioneStruct;

moltiplicazioneStruct moltiplicazione (int input1, int input2);
double divisione (int input1, int input2);
int somma_con_commento (int a, int b);
void somma (int input1, int input2, char** result);
void sottrazione (int input1, int input2, int* result);


moltiplicazioneStruct moltiplicazione (int input1, int input2) {
int result;
result = input1*input2;
moltiplicazioneStruct moltiplicazioneStructReturnValue;
moltiplicazioneStructReturnValue.value0 = result;
moltiplicazioneStructReturnValue.value1 = 2;
return moltiplicazioneStructReturnValue;
}
double divisione (int input1, int input2) {
double result;
if (input2==0) {
 printf("%s ", "Errore");
result = 0.0;
}
return 4.5;
}
int somma_con_commento (int a, int b) {
int risultato;
risultato = a+b;
if ((risultato)>4) {
int c;
return c;
}
else if (a>5)  {
return 4;
}
else
 {
int c;
return c;
}
}

void main () {
char* operazione = malloc(MAXCHAR);
int input1;
int input2;
int answer;
bool flag = true;
int result;
double resultReal;
while(flag==true)  {
int input8;
printf("Inserisci l'operazione da effettuare (somma, sottrazione, divisione, moltiplicazione)");
scanf("%s", operazione);
printf("Inserisci il primo input");
scanf("%d", &input1);
printf("Inserisci il secondo input");
scanf("%d", &input2);
if (strncmp(operazione, "somma", MAXCHAR) == 1) {
somma(input1, input2, &operazione);
}
else if (strncmp(operazione, "sottrazione", MAXCHAR) == 1)  {
sottrazione(input1, input2, &result);
}
else if (strncmp(operazione, "divisione", MAXCHAR) == 1)  {
resultReal = divisione(input1, input2);
}
else if (strncmp(operazione, "moltiplicazione", MAXCHAR) == 1)  {
moltiplicazioneStruct moltiplicazioneReturned0 = moltiplicazione(input1, input2);
result = moltiplicazioneReturned0.value0;
result = moltiplicazioneReturned0.value1;
}
 printf("%s %d ", "Il risultato e':", result);
printf("Vuoi continuare? (1 yes/0 no)");
scanf("%d", &answer);
if (answer==1) {
flag = true;
}
else
 {
flag = false;
}
}
}
void somma (int input1, int input2, char** result) {
*result = str_concat("input1", "input2");
}
void sottrazione (int input1, int input2, int* result) {
*result = input1*input2;
if (input1>0) {
}
}

