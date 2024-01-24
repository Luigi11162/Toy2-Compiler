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
void contaspazi ();



void contaspazi () {
int spazi = 0;
int altri = 0;
char* c = malloc(MAXCHAR);
fflush(stdin);
scanf("%s", c);
while(strncmp(c, "EOF", MAXCHAR) != 0 )  {
if ((strncmp(c, " ", MAXCHAR) == 0)) {
spazi = spazi+1;
}
else
 {
altri = altri+1;
}
fflush(stdin);
scanf("%s", c);
}
if ((spazi>altri)) {
 printf("%s %s", "Piu\' spazi", "\n");
}
else
 {
 printf("%s %s", "Piu\' altri caratteri", "\n");
}
}
void main () {
contaspazi();
}

