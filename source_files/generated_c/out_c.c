#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <unistd.h>
#include <stdbool.h>
#define MAXCHAR 512

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
result=input1*input2;
moltiplicazioneStruct moltiplicazioneStructReturnValue;
moltiplicazioneStructReturnValue.value0 = result;
moltiplicazioneStructReturnValue.value1 = 2;
return moltiplicazioneStructReturnValue;
}
double divisione (int input1, int input2) {
double result;
if (input2==0) {
 printf("Errore");
result=0.0;
}
return 4.5;
}
int somma_con_commento (int a, int b) {
int risultato;
risultato=a+b;
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
double resultReal;
int result;
bool flag = true;
int input1;
int input2;
int answer;
char* operazione;
while(flag==true)  {
 scanf("Inserisci l'operazione da effettuare (somma, sottrazione, divisione, moltiplicazione)", operazione);
 scanf("Inserisci il primo input", &input1);
 scanf("Inserisci il secondo input", &input2);
if (operazione=="somma") {
somma(input1, input2, operazione)}
else if (operazione=="sottrazione")  {
sottrazione(input1, input2, result)}
else if (operazione=="divisione")  {
resultReal=divisione(input1, input2);
}
else if (operazione=="moltiplicazione")  {
result=moltiplicazione(input1, input2);
}
 printf("Il risultato e':", result, divisione(input1, input2));
 scanf("Vuoi continuare? (1 yes/0 no)", &answer);
if (answer==1) {
flag=true;
}
else
 {
flag=false;
}
int input8;
}
}
void somma (int input1, int input2, char** result) {
result=str_concat("input1", "input2");
}
void sottrazione (int input1, int input2, int* result) {
result=input1-input2;
if (input1>0) {
}
}

