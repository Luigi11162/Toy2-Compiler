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
void converti (double *gradi);



void converti (double *gradi) {
*gradi = *gradi*9/5+32;
}
void main () {
double temperatura_celsius;
double buffer;
printf("Inserire temperatura Celsius: ");
fflush(stdin);
scanf("%lf", &temperatura_celsius);
buffer = temperatura_celsius;
converti(&temperatura_celsius);
 printf("%lf %s %lf %s", buffer, "celsius equivalgono a: ", temperatura_celsius, "\n");
}

