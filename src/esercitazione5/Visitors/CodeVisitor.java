package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.SymbolTable.SymbolType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;


public class CodeVisitor implements Visitor {
    private static SymbolTable symbolTable;
    private static FileWriter fileWriter;
    private static int tabs = 0;
    //Per gestire il return della function
    private static ID funId;

    @Override
    public Object visit(ProgramOp programOp) {

        Path path = Paths.get("source_files" + File.separator + "generated_c" + File.separator);
        try {
            if (!Files.exists(path))
                Files.createDirectory(path);

            File file = new File(path + File.separator + "out_c.c");

            file.createNewFile();

            fileWriter = new FileWriter(file);

            addLibrerie();
            fileWriter.write("\n");
            addPrototipiFunzioniDiSupporto();
            addFunzioniDiSupporto();

            symbolTable = programOp.getSymbolTable();

            //creo le struct per i tipi di ritorno delle funzioni
            programOp.getFunOpList().forEach(funOp -> {
                        if (funOp.getTypeList().size() > 1) {
                            try {
                                fileWriter.write("typedef struct {\n");
                                for (int i = 0; i < funOp.getTypeList().size(); i++) {
                                    Type type = funOp.getTypeList().get(i);
                                    fileWriter.write("\t");
                                    type.accept(this);
                                    fileWriter.write(" value" + i + ";\n");
                                }
                                fileWriter.write("} ");
                                funOp.getId().accept(this);
                                fileWriter.write("Struct;\n\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );

            //creo le firme dei metodi
            programOp.getFunOpList().forEach(funOp -> {
                        try {
                            symbolTable = funOp.getSymbolTable();
                            if (funOp.getTypeList().size() > 1) {
                                funOp.getId().accept(this);
                                fileWriter.write("Struct");
                            } else
                                funOp.getTypeList().get(0).accept(this);
                            fileWriter.write(" ");
                            funOp.getId().accept(this);
                            fileWriter.write(" (");
                            if (funOp.getProcFunParamOpList().size() > 1)
                                for (int i = 0; i < funOp.getProcFunParamOpList().size() - 1; i++) {
                                    funOp.getProcFunParamOpList().get(i).accept(this);
                                    fileWriter.write(", ");
                                }

                            //Non inserisco la virgola
                            if (!funOp.getProcFunParamOpList().isEmpty())
                                funOp.getProcFunParamOpList().get(funOp.getProcFunParamOpList().size() - 1).accept(this);

                            fileWriter.write(");\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

            programOp.getProcOpList().forEach(procOp -> {
                        try {
                            symbolTable = procOp.getSymbolTable();
                            if (procOp.getId().getValue().equals("main"))
                                return;
                            fileWriter.write("void ");
                            procOp.getId().accept(this);
                            fileWriter.write(" (");

                            if (procOp.getProcFunParamOpList().size() > 1)
                                for (int i = 0; i < procOp.getProcFunParamOpList().size() - 1; i++) {
                                    procOp.getProcFunParamOpList().get(i).accept(this);
                                    fileWriter.write(", ");
                                }

                            //Non inserisco la virgola
                            if (!procOp.getProcFunParamOpList().isEmpty())
                                procOp.getProcFunParamOpList().get(procOp.getProcFunParamOpList().size() - 1).accept(this);

                            fileWriter.write(");\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

            symbolTable = programOp.getSymbolTable();

            fileWriter.write("\n");
            programOp.getVarDeclOpList().forEach(varDeclOp -> varDeclOp.accept(this));
            fileWriter.write("\n");
            programOp.getFunOpList().forEach(funOp -> funOp.accept(this));
            fileWriter.write("\n");
            programOp.getProcOpList().forEach(procOp -> procOp.accept(this));
            fileWriter.write("\n");

            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        try {
            Iterator<ID> idIt = varDeclOp.getidList().iterator();
            //Inizializzazione con constanti
            if (varDeclOp.getType() == null) {
                Iterator<Const> constIt = varDeclOp.getConstList().iterator();

                while (idIt.hasNext() && constIt.hasNext()) {
                    ID id = idIt.next();
                    Const const1 = constIt.next();
                    if (const1.getType().getName().equals("String")) {
                        const1.getType().accept(this);
                        fileWriter.write(" ");
                        id.accept(this);
                        fileWriter.write(" = malloc(MAXCHAR);\n");
                        id.accept(this);
                        fileWriter.write(" = strncpy(");
                        id.accept(this);
                        fileWriter.write(",");
                        const1.accept(this);
                        fileWriter.write(", MAXCHAR)");
                    } else {
                        const1.getType().accept(this);
                        fileWriter.write(" ");
                        id.accept(this);
                        fileWriter.write(" = ");
                        const1.accept(this);
                    }
                    fileWriter.write(";\n");
                }
                //Inizializzazione con tipo
            } else {
                while (idIt.hasNext()) {
                    varDeclOp.getType().accept(this);
                    fileWriter.write(" ");
                    idIt.next().accept(this);
                    if (varDeclOp.getType().getName().equals("String")) {
                        fileWriter.write(" = malloc(MAXCHAR)");
                    }
                    fileWriter.write(";\n");

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        try {
            symbolTable = funOp.getSymbolTable();
            funId = funOp.getId();
            // Firma della funzione
            if (funOp.getTypeList().size() > 1) {
                //Se la funzione restituisce più tipi restituisco il tipo struct precedentemente creato
                funOp.getId().accept(this);
                fileWriter.write("Struct");
            } else
                funOp.getTypeList().get(0).accept(this);
            fileWriter.write(" ");
            funOp.getId().accept(this);
            fileWriter.write(" (");


            //Scrivo i parametri
            if (funOp.getProcFunParamOpList().size() > 1)
                for (int i = 0; i < funOp.getProcFunParamOpList().size() - 1; i++) {
                    funOp.getProcFunParamOpList().get(i).accept(this);
                    fileWriter.write(", ");
                }

            //Non inserisco la virgola perché è l'ultimo parametro
            if (!funOp.getProcFunParamOpList().isEmpty())
                funOp.getProcFunParamOpList().get(funOp.getProcFunParamOpList().size() - 1).accept(this);
            fileWriter.write(")");

            // Corpo della funzione
            funOp.getBodyOp().accept(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ProcOp procOp) {
        //firma della procedura
        try {
            symbolTable = procOp.getSymbolTable();

            fileWriter.write("void ");
            procOp.getId().accept(this);
            fileWriter.write(" (");

            //Scrivo i parametri
            if (procOp.getProcFunParamOpList().size() > 1)
                for (int i = 0; i < procOp.getProcFunParamOpList().size() - 1; i++) {
                    procOp.getProcFunParamOpList().get(i).accept(this);
                    fileWriter.write(", ");
                }

            //Non inserisco la virgola perché è l'ultimo parametro
            if (!procOp.getProcFunParamOpList().isEmpty())
                procOp.getProcFunParamOpList().get(procOp.getProcFunParamOpList().size() - 1).accept(this);

            fileWriter.write(")");

            //Corpo della procedura
            procOp.getBodyOp().accept(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        ++tabs;
        try {
            if (bodyOp.getSymbolTable() != null)
                symbolTable = bodyOp.getSymbolTable();

            fileWriter.write(" {\n");
            bodyOp.getVarDeclOpList().forEach(varDeclOp -> varDeclOp.accept(this));
            bodyOp.getStatList().forEach(stat -> stat.accept(this));
            fileWriter.write("}\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        symbolTable = symbolTable.getFather();
        --tabs;
        return null;
    }

    @Override
    public Object visit(ProcFunParamOp procFunParamOp) {
        try {
            procFunParamOp.getType().accept(this);
            fileWriter.write(" ");
            procFunParamOp.getId().accept(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ElifOp elifOp) {
        try {
            fileWriter.write("else if (");
            elifOp.getExpr().accept(this);
            fileWriter.write(") ");
            elifOp.getBodyOp().accept(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(Mode mode) {
        if (mode.getName().equals("out")) {
            try {
                fileWriter.write("*");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public Object visit(Type type) {
        try {
            switch (type.getName()) {
                case "Boolean":
                    fileWriter.write("bool");
                    break;
                case "Integer":
                    fileWriter.write("int");
                    break;
                case "Real":
                    fileWriter.write("double");
                    break;
                case "String":
                    fileWriter.write("char*");
                    break;
                default:
                    throw new RuntimeException("tipo non esistente");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(Stat stat) {
        return null;
    }

    @Override
    public Object visit(AssignOp assignOp) {
        try {
            Iterator<ID> idIt = assignOp.getIdList().iterator();
            Iterator<Expr> exprIt = assignOp.getExprList().iterator();
            int j = 0;
            while (idIt.hasNext() && exprIt.hasNext()) {
                Expr expr = exprIt.next();
                //Controllo se la funzione restituisce più valori
                if (expr instanceof CallFunOp callFunOp && symbolTable.returnTypeOfId(callFunOp.getId().getValue()).getOutTypeList().size() > 1) {
                    //Creo una nuova variabile struct dove assegnare il valore
                    callFunOp.getId().accept(this);
                    fileWriter.write("Struct ");
                    callFunOp.getId().accept(this);
                    //Se vengono chiamate più funzioni allora la variabile j garantisce l'unicità della variabile struct
                    fileWriter.write("Returned" + j + " = ");
                    callFunOp.accept(this);
                    fileWriter.write(";\n");

                    //Assegno ogni valore all'interno della struct alle rispettive variabili
                    for (int i = 0; i < symbolTable.returnTypeOfId(callFunOp.getId().getValue()).getOutTypeList().size(); i++) {
                        ID id = idIt.next();
                        //Controllo se è un puntatore
                        id.accept(this);
                        fileWriter.write(" = ");
                        callFunOp.getId().accept(this);
                        fileWriter.write("Returned" + j + ".value" + i);
                        fileWriter.write(";\n");
                    }
                    j++;
                } else {
                    ID id = idIt.next();
                    id.accept(this);
                    fileWriter.write(" = ");
                    expr.accept(this);
                    fileWriter.write(";\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        try {
            fileWriter.write("if (");
            //Condizione
            ifStatOp.getExpr().accept(this);
            fileWriter.write(")");
            //Corpo
            ifStatOp.getBodyOp().accept(this);

            ifStatOp.getElifOpList().forEach(elifOp -> elifOp.accept(this));
            if (!ifStatOp.getBodyOp2().getStatList().isEmpty()) {

                fileWriter.write("else\n");
                ifStatOp.getBodyOp2().accept(this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ProcCallOp procCallOp) {
        try {
            procCallOp.getId().accept(this);
            fileWriter.write("(");
            if (procCallOp.getExprList().size() > 1)
                for (int i = 0; i < procCallOp.getExprList().size() - 1; i++) {
                    //Controllo se viene passato per riferimento
                    if (procCallOp.getExprList().get(i) instanceof ID id && id.getMode() != null && id.getMode().getName().equals("out"))
                        fileWriter.write("&");
                    procCallOp.getExprList().get(i).accept(this);
                    fileWriter.write(", ");
                }

            //Non inserisco la virgola
            if (!procCallOp.getExprList().isEmpty()) {
                if (procCallOp.getExprList().get(procCallOp.getExprList().size() - 1) instanceof ID id && id.getMode() != null && id.getMode().getName().equals("out"))
                    fileWriter.write("&");
                procCallOp.getExprList().get(procCallOp.getExprList().size() - 1).accept(this);
            }
            fileWriter.write(");\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ReadOp readOp) {
        try {
            if (readOp.getExprList().size() > 1)
                for (int i = 0; i < readOp.getExprList().size() - 1; i++) {
                    if (readOp.getExprList().get(i) instanceof ID id) {
                        fileWriter.write("fflush(stdin);\n");
                        fileWriter.write("scanf(\"");
                        switch (symbolTable.returnTypeOfId(id.getValue()).getOutTypeList().get(0).getName()) {
                            case "String":
                                fileWriter.write("%s\", ");
                                readOp.getExprList().get(i).accept(this);
                                break;
                            case "Integer":
                                fileWriter.write("%d\", ");
                                fileWriter.write("&");
                                readOp.getExprList().get(i).accept(this);
                                break;
                            case "Real":
                                fileWriter.write("%lf\", ");
                                fileWriter.write("&");
                                readOp.getExprList().get(i).accept(this);
                                break;
                        }
                    } else {
                        fileWriter.write("printf(");
                        readOp.getExprList().get(i).accept(this);
                    }
                    fileWriter.write(");\n");
                }

            //Non inserisco la virgola
            if (!readOp.getExprList().isEmpty()) {
                if (readOp.getExprList().get(readOp.getExprList().size() - 1) instanceof ID id) {
                    fileWriter.write("fflush(stdin);\n");
                    fileWriter.write("scanf(\"");
                    switch (symbolTable.returnTypeOfId(id.getValue()).getOutTypeList().get(0).getName()) {
                        case "String":
                            fileWriter.write("%s\", ");
                            readOp.getExprList().get(readOp.getExprList().size() - 1).accept(this);
                            break;
                        case "Integer":
                            fileWriter.write("%d\", ");
                            fileWriter.write("&");
                            readOp.getExprList().get(readOp.getExprList().size() - 1).accept(this);
                            break;
                        case "Real":
                            fileWriter.write("%lf\", ");
                            fileWriter.write("&");
                            readOp.getExprList().get(readOp.getExprList().size() - 1).accept(this);
                            break;
                    }
                } else {
                    fileWriter.write("printf(");
                    readOp.getExprList().get(readOp.getExprList().size() - 1).accept(this);
                }
                fileWriter.write(");\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        try {
            if (returnOp.getExprList().size() > 1) {
                fileWriter.write(funId.getValue());
                fileWriter.write("Struct ");
                fileWriter.write(funId.getValue());
                fileWriter.write("StructReturnValue;\n");
                for (int i = 0; i < returnOp.getExprList().size(); i++) {
                    fileWriter.write(funId.getValue());
                    fileWriter.write("StructReturnValue.value" + i);
                    fileWriter.write(" = ");
                    returnOp.getExprList().get(i).accept(this);
                    fileWriter.write(";\n");
                }
            }
            fileWriter.write("return ");
            //Inserire il ritorno
            if (returnOp.getExprList().size() == 1)
                returnOp.getExprList().get(0).accept(this);
            else {
                fileWriter.write(funId.getValue());
                fileWriter.write("StructReturnValue");
            }
            fileWriter.write(";\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(WhileOp whileOp) {
        try {
            fileWriter.write("while(");
            whileOp.getExpr().accept(this);
            fileWriter.write(") ");
            whileOp.getBodyOp().accept(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) {
        try {
            fileWriter.write(" printf(\"");

            for (int i = 0; i < writeOp.getExprList().size(); i++) {
                writeOpTypes(writeOp.getExprList().get(i));
            }

            if (writeOp.getMode().getName().equals("writeReturn"))
                fileWriter.write("%s");
            fileWriter.write("\", ");
            if (writeOp.getExprList().size() > 1)
                for (int i = 0; i < writeOp.getExprList().size() - 1; i++) {
                    writeOp.getExprList().get(i).accept(this);
                    fileWriter.write(", ");
                }

            //Non inserisco la virgola
            if (!writeOp.getExprList().isEmpty())
                writeOp.getExprList().get(writeOp.getExprList().size() - 1).accept(this);
            if (writeOp.getMode().getName().equals("writeReturn"))
                fileWriter.write(", \"\\n\");\n");
            else
                fileWriter.write(");\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Object visit(Expr expr) {
        return null;
    }

    @Override
    public Object visit(CallFunOp callFunOp) {
        try {

            if (callFunOp.getModeExpr() != null && callFunOp.getModeExpr().getName().equals("PAR"))
                fileWriter.write("(");

            callFunOp.getId().accept(this);
            fileWriter.write("(");
            if (callFunOp.getExprList().size() > 1)
                for (int i = 0; i < callFunOp.getExprList().size() - 1; i++) {
                    callFunOp.getExprList().get(i).accept(this);
                    fileWriter.write(", ");
                }

            //Non inserisco la virgola
            if (!callFunOp.getExprList().isEmpty())
                callFunOp.getExprList().get(callFunOp.getExprList().size() - 1).accept(this);

            fileWriter.write(")");

            if (callFunOp.getModeExpr() != null && callFunOp.getModeExpr().getName().equals("PAR"))
                fileWriter.write(")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(Const const1) {
        try {
            if (const1.getModeExpr() != null && const1.getModeExpr().getName().equals("PAR")) {
                fileWriter.write("(");
                fileWriter.write(const1.getValue());
                fileWriter.write(")");
            } else
                fileWriter.write(const1.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ID id) {
        try {
            //Controllo se ci sono le parentesi
            if (id.getModeExpr() != null && id.getModeExpr().getName().equals("PAR")) {
                fileWriter.write("(");
                fileWriter.write(id.getValue());
                fileWriter.write(")");
                //Controllo se è un puntatore
            } else if (symbolTable.checkIfIsOut(id)) {
                fileWriter.write("*");
                fileWriter.write(id.getValue());
            } else
                fileWriter.write(id.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(Op op) {
        try {
            if (op.getModeExpr() != null && op.getModeExpr().getName().equals("PAR"))
                fileWriter.write("(");
            //Controlla se l'operazione avviene su stinghe
            if (!(checkString(op.getValueL()) || checkString(op.getValueR())))
                op.getValueL().accept(this);
            switch (op.getName()) {
                case "AddOp":
                    //Concatenazione di stringhe
                    if (checkString(op.getValueL()) || checkString(op.getValueR()))
                        concatOp(op);
                    else
                        fileWriter.write("+");
                    break;
                case "DivOp":
                    fileWriter.write("/");
                    break;
                case "DiffOp":
                    fileWriter.write("-");
                    break;
                case "MulOp":
                    fileWriter.write("*");
                    break;
                case "AndOp":
                    fileWriter.write("&&");
                    break;
                case "OrOp":
                    fileWriter.write("||");
                    break;
                case "EqOp":
                    //Comparazione di stringhe
                    if (checkString(op.getValueL()) && checkString(op.getValueR())) {
                        fileWriter.write("strncmp(");
                        op.getValueL().accept(this);
                        fileWriter.write(", ");
                        op.getValueR().accept(this);
                        fileWriter.write(", MAXCHAR) == 0");
                    } else
                        fileWriter.write("==");
                    break;
                case "NeOp":
                    //Comparazione di stringhe
                    if (checkString(op.getValueL()) && checkString(op.getValueR())) {
                        fileWriter.write("strncmp(");
                        op.getValueL().accept(this);
                        fileWriter.write(", ");
                        op.getValueR().accept(this);
                        fileWriter.write(", MAXCHAR) != 0 ");
                    } else
                        fileWriter.write("!=");
                    break;
                case "GtOp":
                    fileWriter.write(">");
                    break;
                case "GeOp":
                    fileWriter.write(">=");
                    break;
                case "LtOp":
                    fileWriter.write("<");
                    break;
                case "LeOp":
                    fileWriter.write("<=");
                    break;
                default:
                    throw new RuntimeException("Operazione non consentita: " + op.getName());
            }
            if (!(checkString(op.getValueL()) || checkString(op.getValueR())))
                op.getValueR().accept(this);

            if (op.getModeExpr() != null && op.getModeExpr().getName().equals("PAR"))
                fileWriter.write(")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(UOp uOp) {
        try {
            if (uOp.getModeExpr() != null && uOp.getModeExpr().getName().equals("PAR"))
                fileWriter.write("(");
            switch (uOp.getName()) {
                case "UMinusOp":
                    fileWriter.write("-");
                    break;
                case "NotOp":
                    fileWriter.write("!");
                    break;
                default:
                    throw new RuntimeException("Operazione non consentita: " + uOp.getName());
            }

            uOp.getValue().accept(this);
            if (uOp.getModeExpr() != null && uOp.getModeExpr().getName().equals("PAR"))
                fileWriter.write(")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addLibrerie() {
        try {
            fileWriter.write("#include <stdio.h>\n");

            fileWriter.write("#include <stdlib.h>\n");
            fileWriter.write("#include <string.h>\n");
            fileWriter.write("#include <math.h>\n");
            fileWriter.write("#include <unistd.h>\n");
            fileWriter.write("#include <stdbool.h>\n");
            fileWriter.write("#define MAXCHAR 512\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPrototipiFunzioniDiSupporto() {
        try {
            fileWriter.write("char* integer_to_str(int i);\n");
            fileWriter.write("char* real_to_str(double i);\n");
            fileWriter.write("char* bool_to_str(bool i);\n");
            fileWriter.write("char* str_concat(char* str1, char* str2);\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFunzioniDiSupporto() {
        try {
            fileWriter.write("\n//Funzioni di supporto \n");

            fileWriter.write("char* integer_to_str(int i){\n");

            fileWriter.write("int length= snprintf(NULL,0,\"%d\",i);\n");
            fileWriter.write("char* result=malloc(length+1);\n");
            fileWriter.write("snprintf(result,length+1,\"%d\",i);\n");
            fileWriter.write("return result;\n");
            fileWriter.write("}\n");

            fileWriter.write("char* real_to_str(double i){\n");
            fileWriter.write("int length= snprintf(NULL,0,\"%lf\",i);\n");
            fileWriter.write("char* result=malloc(length+1);\n");
            fileWriter.write("snprintf(result,length+1,\"%f\",i);\n");
            fileWriter.write("return result;\n");
            fileWriter.write("}\n");

            fileWriter.write("char* bool_to_str(bool i){\n");
            fileWriter.write("int length= snprintf(NULL,0,\"%d\",i);\n");
            fileWriter.write("char* result=malloc(length+1);\n");
            fileWriter.write("snprintf(result,length+1,\"%d\",i);\n");
            fileWriter.write("return result;\n");
            fileWriter.write("}\n");

            fileWriter.write("char* str_concat(char* str1, char* str2){\n");
            fileWriter.write("char* result=malloc(sizeof(char)*MAXCHAR);\n");
            fileWriter.write("result=strcat(result,str1);\n");
            fileWriter.write("result=strcat(result,str2);\n");
            fileWriter.write("return result;}\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Controlla se l'expr è di tipo stringa
    private boolean checkString(Expr expr) {
        if (expr instanceof Const const1 && const1.getType().getName().equals("String") ||
                expr instanceof ID id &&
                        symbolTable.returnTypeOfId(id.getValue()).getOutTypeList().get(0).getName().equals("String") ||
                expr instanceof CallFunOp callFunOp &&
                        symbolTable.returnTypeOfId(callFunOp.getId().getValue()).getOutTypeList().get(0).getName().equals("String"))
            return true;
        if (expr instanceof Op op) {
            return checkString(op.getValueL()) || checkString(op.getValueR());
        }
        return false;
    }

    //Effettua la concatenazione di stringhe
    private void concatOp(Op op) {
        try {
            fileWriter.write("str_concat(");
            if (checkString(op.getValueL()))
                op.getValueL().accept(this);
            else
                exprToString(op.getValueL());
            fileWriter.write(", ");
            if (checkString(op.getValueR()))
                op.getValueR().accept(this);
            else
                exprToString(op.getValueR());
            fileWriter.write(")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Effettua il cast dell'espressione in tipo stringa
    private void exprToString(Expr expr) {
        Type type;
        if (expr instanceof ID id)
            type = symbolTable.returnTypeOfId(id.getValue()).getOutTypeList().get(0);
        else if (expr instanceof Const const1)
            type = const1.getType();
        else if (expr instanceof CallFunOp callFunOp)
            type = symbolTable.returnTypeOfId(callFunOp.getId().getValue()).getOutTypeList().get(0);
        else
            throw new RuntimeException("Non è un'espressione valida");
        try {
            switch (type.getName()) {
                case "Integer":
                    fileWriter.write("integer_to_str(");
                    break;
                case "Real":
                    fileWriter.write("real_to_str(");
                    break;
                case "Boolean":
                    fileWriter.write("bool_to_str(");
                    break;
            }
            expr.accept(this);
            fileWriter.write(")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Funzione che permette di formattare i tipi per la printf
    private void writeOpTypes(Expr expr) {
        try {
            String nameType;
            //Il type visitor restituisce il tipo se l'espressione è un'operazione
            TypeVisitor typeVisitor = new TypeVisitor();
            TypeVisitor.symbolTable =symbolTable;
            if (expr instanceof Const const1)
                nameType = const1.getType().getName();
            else if (expr instanceof ID id)
                nameType = symbolTable.returnTypeOfId(id.getValue()).getOutTypeList().get(0).getName();
            else if (expr instanceof CallFunOp callFun)
                nameType = symbolTable.returnTypeOfId(callFun.getId().getValue()).getOutTypeList().get(0).getName();
            else if (expr instanceof Op op) {
                SymbolType symbolType = (SymbolType) typeVisitor.visit(op);
                nameType = symbolType.getOutTypeList().get(0).getName();
            } else if (expr instanceof UOp uOp) {
                SymbolType symbolType = (SymbolType) typeVisitor.visit(uOp);
                nameType = symbolType.getOutTypeList().get(0).getName();
            } else {
                throw new RuntimeException("Espressione non valida");
            }

            switch (nameType) {
                case "String":
                    fileWriter.write("%s ");
                    break;
                case "Boolean", "Integer":
                    fileWriter.write("%d ");
                    break;
                case "Real":
                    fileWriter.write("%lf ");
                    break;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
