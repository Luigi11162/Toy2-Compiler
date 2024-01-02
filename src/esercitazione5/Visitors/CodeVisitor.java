package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;


public class CodeVisitor implements Visitor {
    private static SymbolTable symbolTable;
    private static File file;
    private static FileWriter fileWriter;

    @Override
    public Object visit(ProgramOp programOp) {

        Path path = Paths.get("source_files" + File.separator + "generated_c" + File.separator);
        try {
            if (!Files.exists(path))
                Files.createDirectory(path);

            file = new File(path + File.separator + "out_c.c");

            file.createNewFile();

            fileWriter = new FileWriter(file);

            addLibrerie();
            fileWriter.write("\n");

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
        Iterator<ID> idIt = varDeclOp.getidList().iterator();
        if (varDeclOp.getType() == null) {
            Iterator<Const> constIt = varDeclOp.getConstList().iterator();

            while (idIt.hasNext() && constIt.hasNext()) {
                ID id = idIt.next();
                Const const1 = constIt.next();
                const1.getType().accept(this);
                try {
                    fileWriter.write(" ");
                    id.accept(this);
                    fileWriter.write(" = ");
                    const1.accept(this);
                    fileWriter.write(";\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            while (idIt.hasNext()) {
                idIt.next().accept(this);
                try {
                    fileWriter.write(" ");
                    varDeclOp.getType().accept(this);
                    fileWriter.write(";\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        return null;
    }

    @Override
    public Object visit(ProcOp procOp) {
        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        return null;
    }

    @Override
    public Object visit(ProcFunParamOp procFunParamOp) {
        try {
            if (procFunParamOp.getMode().getName().equals("out"))
                fileWriter.write("out ");
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
        return null;
    }

    @Override
    public Object visit(Mode mode) {
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
        return null;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        return null;
    }

    @Override
    public Object visit(ProcCallOp procCallOp) {
        return null;
    }

    @Override
    public Object visit(ReadOp readOp) {
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        return null;
    }

    @Override
    public Object visit(WhileOp whileOp) {
        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) {
        return null;
    }

    @Override
    public Object visit(Expr expr) {
        return null;
    }

    @Override
    public Object visit(CallFunOp callFunOp) {
        return null;
    }

    @Override
    public Object visit(Const const1) {
        try {
            if (const1.getType().getName().equals("String"))
                fileWriter.write(" \"" + const1.getValue() + "\" ");
            else
                fileWriter.write(const1.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(ID id) {
        try {
            fileWriter.write(id.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object visit(Op op) {
        return null;
    }

    @Override
    public Object visit(UOp uOp) {
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

            fileWriter.write("char* real_to_str(float i);\n");
            fileWriter.write("char* char_to_str(char i);\n");
            fileWriter.write("char* bool_to_str(bool i);\n");
            fileWriter.write("char* str_concat(char* str1, char* str2);\n");
            fileWriter.write("char* read_str();\n");
            fileWriter.write("int str_to_bool(char* expr);\n");

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

            fileWriter.write("char* real_to_str(float i){\n");
            fileWriter.write("int length= snprintf(NULL,0,\"%f\",i);\n");
            fileWriter.write("char* result=malloc(length+1);\n");
            fileWriter.write("snprintf(result,length+1,\"%f\",i);\n");
            fileWriter.write("return result;\n");
            fileWriter.write("}\n");

            fileWriter.write("char* char_to_str(char i){\n");
            fileWriter.write("int length= snprintf(NULL,0,\"%c\",i);\n");
            fileWriter.write("char* result=malloc(length+1);\n");
            fileWriter.write("snprintf(result,length+1,\"%c\",i);\n");
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

            fileWriter.write("\n");
            fileWriter.write("char* read_str(){\n");
            fileWriter.write("char* str=malloc(sizeof(char)*MAXCHAR);\n");
            fileWriter.write("scanf(\"%s\",str);\n");
            fileWriter.write("return str;}\n");

            fileWriter.write("\n");
            fileWriter.write("int str_to_bool(char* expr){\n");
            fileWriter.write("int i=0;\n");
            fileWriter.write("if ( (strcmp(expr, \"true\")==0) || (strcmp(expr, \"1\"))==0 )\n");
            fileWriter.write("i=1;\n");
            fileWriter.write("if ( (strcmp(expr, \"false\")==0) || (strcmp(expr, \"0\"))==0 )\n");
            fileWriter.write("i=0;\n");
            fileWriter.write("return i;}\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
