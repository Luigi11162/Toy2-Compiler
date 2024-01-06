//Toy2.flex
//         ../jflex-1.8.2/bin/jflex -d src srcjflexcup/Toy2.flex
// Toy2 Language Processing
//
// Description of lexer for Toy2 language.
//
// Bacco-Valletta
package esercitazione5;
import java_cup.runtime.Symbol; //This is how we pass tokens to the parser

%%

%public
%class Lexer
%cupsym sym
%cup
%unicode
%char
%line
%column


Letter = [a-zA-Z]
Digit = [0-9]
ID = {Letter} ({Letter} | {Digit} | _ )*
Letteral = \" ~\"
Digits = {Digit}+
OptFraction = ("." {Digits})?
Numbers = {Digits} {OptFraction}
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Comment = "%" ~"%"
String_Error = \" [^\"]
Comment_Error = "%" [^"%"]
Error = [^]

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }

    private java_cup.runtime.Symbol installID(String value){
        return symbol(sym.ID, value);
    }
%}

%%

<YYINITIAL> "var" {return symbol(sym.VAR);}
<YYINITIAL> ":" {return symbol(sym.COLON);}
<YYINITIAL> "^=" {return symbol(sym.ASSIGN);}
<YYINITIAL> ";" {return symbol(sym.SEMI);}
<YYINITIAL> "," {return symbol(sym.COMMA);}
<YYINITIAL> "true" {return symbol(sym.TRUE);}
<YYINITIAL> "false" {return symbol(sym.FALSE);}
<YYINITIAL> "real" {return symbol(sym.REAL);}
<YYINITIAL> "integer" {return symbol(sym.INTEGER);}
<YYINITIAL> "string" {return symbol(sym.STRING);}
<YYINITIAL> "boolean" {return symbol(sym.BOOLEAN);}
<YYINITIAL> "return" {return symbol(sym.RETURN);}
<YYINITIAL> "func" {return symbol(sym.FUNCTION);}
<YYINITIAL> "->" {return symbol(sym.TYPERETURN);}
<YYINITIAL> "(" {return symbol(sym.LPAR);}
<YYINITIAL> ")" {return symbol(sym.RPAR);}
<YYINITIAL> "proc" {return symbol(sym.PROCEDURE);}
<YYINITIAL> "while" {return symbol(sym.WHILE);}
<YYINITIAL> "endproc" {return symbol(sym.ENDPROCEDURE);}
<YYINITIAL> "endfunc" {return symbol(sym.ENDFUNCTION);}
<YYINITIAL> "out" {return symbol(sym.OUT);}
<YYINITIAL> "-->" {return symbol(sym.WRITE);}
<YYINITIAL> "-->!" {return symbol(sym.WRITERETURN);}
<YYINITIAL> "$" {return symbol(sym.DOLLARSIGN);}
<YYINITIAL> "<--" {return symbol(sym.READ);}
<YYINITIAL> "if" {return symbol(sym.IF);}
<YYINITIAL> "then" {return symbol(sym.THEN);}
<YYINITIAL> "else" {return symbol(sym.ELSE);}
<YYINITIAL> "endif" {return symbol(sym.ENDIF);}
<YYINITIAL> "elseif" {return symbol(sym.ELIF);}
<YYINITIAL> "do" {return symbol(sym.DO);}
<YYINITIAL> "endwhile" {return symbol(sym.ENDWHILE);}
<YYINITIAL> "+" {return symbol(sym.PLUS);}
<YYINITIAL> "-" {return symbol(sym.MINUS);}
<YYINITIAL> "*" {return symbol(sym.TIMES);}
<YYINITIAL> "/" {return symbol(sym.DIV);}
<YYINITIAL> "=" {return symbol(sym.EQ);}
<YYINITIAL> "<>" {return symbol(sym.NE);}
<YYINITIAL> "<" {return symbol(sym.LT);}
<YYINITIAL> "<=" {return symbol(sym.LE);}
<YYINITIAL> ">" {return symbol(sym.GT);}
<YYINITIAL> ">=" {return symbol(sym.GE);}
<YYINITIAL> "&&" {return symbol(sym.AND);}
<YYINITIAL> "||" {return symbol(sym.OR);}
<YYINITIAL> "!" {return symbol(sym.NOT);}
<YYINITIAL> "\\" {return symbol(sym.ENDVAR);}
<YYINITIAL> "@" {return symbol(sym.REF);}

<YYINITIAL>{
    {ID} {return installID(yytext());}
    {Letteral} {return symbol(sym.STRING_CONST, yytext());}
    {Digits} {return symbol(sym.INTEGER_CONST, yytext());}
    {Numbers} {return symbol(sym.REAL_CONST, yytext());}
    {WhiteSpace} {}
    {Comment} {} //{return "Commento";}
}
<YYINITIAL> {Error} {throw new Error("Errore al carattere: "+yychar+" nella linea: "+yyline+" e colonna: "+yycolumn);}
<YYINITIAL> {String_Error} {throw new Error("Stringa costante non completata al carattere: "+yychar+" nella linea: "+yyline+" e colonna: "+yycolumn);}
<YYINITIAL> {Comment_Error} {throw new Error("Commento non chiuso al carattere: "+yychar+" nella linea: "+yyline+" e colonna: "+yycolumn);}



