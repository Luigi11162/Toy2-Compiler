package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolRow;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.SymbolTable.SymbolType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.stream.Collectors;

public class ScopeVisitor implements Visitor {

    static Stack<SymbolTable> symbolTableStack = new Stack<>();

    @Override
    public Object visit(ProgramOp programOp) {
        SymbolTable symbolTable = new SymbolTable("Global", new ArrayList<>());
        if (programOp.getSymbolTable() == null) {
            programOp.setSymbolTable(symbolTable);
        }
        symbolTableStack.add(symbolTable);
        programOp.getFunOpList().forEach(funOp -> {

            try {
                programOp.getSymbolTable().addSymbolRow(
                        new SymbolRow(
                                funOp.getId().getValue(),
                                "Method",
                                new SymbolType(
                                        funOp.getProcFunParamOpList().stream().map(ProcFunParamOp::getType).
                                                collect(Collectors.toCollection(ArrayList::new)),
                                        funOp.getTypeList()
                                ),
                                ""
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        programOp.getProcOpList().forEach(procOp -> {
            try {
                programOp.getSymbolTable().addSymbolRow(
                        new SymbolRow(
                                procOp.getId().getValue(),
                                "Method",
                                new SymbolType(
                                        procOp.getProcFunParamOpList().stream().map(ProcFunParamOp::getType).
                                                collect(Collectors.toCollection(ArrayList::new)),
                                        new Type("void")
                                ),
                                ""
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        programOp.getVarDeclOpList().forEach(varDeclOp -> varDeclOp.accept(this));
        programOp.getProcOpList().forEach(procOp -> procOp.accept(this));
        programOp.getFunOpList().forEach(funOp -> funOp.accept(this));

        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        SymbolTable symbolTable = symbolTableStack.pop();
        if (!varDeclOp.getConstList().isEmpty()) {
            Iterator<ID> idIterator = varDeclOp.getidList().iterator();
            Iterator<Const> constIterator = varDeclOp.getConstList().iterator();
            while (idIterator.hasNext() && constIterator.hasNext()) {
                try {
                    symbolTable.addSymbolRow(
                            new SymbolRow(
                                    idIterator.next().getValue(),
                                    "Var",
                                    new SymbolType(new Type((String) constIterator.next().accept(this))),
                                    ""
                            )
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (varDeclOp.getType() != null) {
            varDeclOp.getidList().forEach(id -> {
                try {
                    symbolTable.addSymbolRow(
                            new SymbolRow(
                                    id.getValue(),
                                    "Var",
                                    new SymbolType(varDeclOp.getType()),
                                    ""
                            )
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        symbolTableStack.push(symbolTable);
        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        SymbolTable symbolTable = new SymbolTable("Func", new ArrayList<>());
        if (funOp.getSymbolTable() == null) {
            funOp.setSymbolTable(symbolTable);
        }

        funOp.getProcFunParamOpList().forEach(procFunParamOp -> {
            try {
                funOp.getSymbolTable().addSymbolRow(
                        new SymbolRow(
                                procFunParamOp.getId().getValue(),
                                "Param",
                                new SymbolType(procFunParamOp.getType()),
                                procFunParamOp.getMode().getName()
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        symbolTableStack.add(funOp.getSymbolTable());
        funOp.getBodyOp().accept(this);
        return null;
    }

    @Override
    public Object visit(ProcOp procOp) {
        SymbolTable symbolTable = new SymbolTable("Proc", new ArrayList<>());
        if (procOp.getSymbolTable() == null) {
            procOp.setSymbolTable(symbolTable);
        }

        procOp.getProcFunParamOpList().forEach(procFunParamOp -> {
            try {
                procOp.getSymbolTable().addSymbolRow(
                        new SymbolRow(
                                procFunParamOp.getId().getValue(),
                                "Param",
                                new SymbolType(procFunParamOp.getType()),
                                procFunParamOp.getMode().getName()
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        symbolTableStack.add(procOp.getSymbolTable());
        procOp.getBodyOp().accept(this);

        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        SymbolTable symbolTable = symbolTableStack.pop();

        if (bodyOp.getSymbolTable() == null) {
            bodyOp.setSymbolTable(symbolTable);
        }

        symbolTableStack.add(symbolTable);
        bodyOp.getVarDeclOpList().forEach(varDeclOp -> varDeclOp.accept(this));
        bodyOp.getStatList().forEach(stat -> stat.accept(this));

        return null;
    }

    @Override
    public Object visit(ProcFunParamOp procFunParamOp) {
        return null;
    }

    @Override
    public Object visit(ElifOp elifOp) {

        symbolTableStack.add(new SymbolTable("Elif", new ArrayList<>()));

        elifOp.getBodyOp().accept(this);

        return null;
    }

    @Override
    public Object visit(Mode mode) {
        return null;
    }

    @Override
    public Object visit(Type type) {
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
        symbolTableStack.add(new SymbolTable("If", new ArrayList<>()));

        ifStatOp.getBodyOp().accept(this);

        symbolTableStack.add(new SymbolTable("Else", new ArrayList<>()));

        ifStatOp.getBodyOp2().accept(this);

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
        symbolTableStack.add(new SymbolTable("While", new ArrayList<>()));

        if (whileOp.getBodyOp() != null) {
            whileOp.getBodyOp().accept(this);
        }

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

        return switch (const1.getType().getName()) {
            case "RealConst" -> "Real";
            case "IntegerConst" -> "Integer";
            case "StringConst" -> "String";
            case "TrueConst", "FalseConst" -> "Boolean";
            default -> null;
        };

    }

    @Override
    public Object visit(ID id) {
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
}
