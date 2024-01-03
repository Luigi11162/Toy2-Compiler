package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolRow;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.SymbolTable.SymbolType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ScopeVisitor implements Visitor {

    static SymbolTable symbolTableStatic;

    @Override
    public Object visit(ProgramOp programOp) {
        SymbolTable symbolTable = new SymbolTable(null, "Global", new ArrayList<>());
        if (programOp.getSymbolTable() == null) {
            programOp.setSymbolTable(symbolTable);
        }
        symbolTableStatic = symbolTable;
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
                                        new ArrayList<>(List.of(new Type("void")))
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
        if (!varDeclOp.getConstList().isEmpty()) {
            Iterator<ID> idIterator = varDeclOp.getidList().iterator();
            Iterator<Const> constIterator = varDeclOp.getConstList().iterator();
            while (idIterator.hasNext() && constIterator.hasNext()) {
                try {
                    symbolTableStatic.addSymbolRow(
                            new SymbolRow(
                                    idIterator.next().getValue(),
                                    "Var",
                                    new SymbolType(new ArrayList<>(List.of(new Type((String) constIterator.next().accept(this))))),
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
                    symbolTableStatic.addSymbolRow(
                            new SymbolRow(
                                    id.getValue(),
                                    "Var",
                                    new SymbolType(new ArrayList<>(List.of(varDeclOp.getType()))),
                                    ""
                            )
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        SymbolTable symbolTable = new SymbolTable(symbolTableStatic, "Func", new ArrayList<>());
        if (funOp.getSymbolTable() == null) {
            funOp.setSymbolTable(symbolTable);
        }

        funOp.getProcFunParamOpList().forEach(procFunParamOp -> {
            try {
                funOp.getSymbolTable().addSymbolRow(
                        new SymbolRow(
                                procFunParamOp.getId().getValue(),
                                "Param",
                                new SymbolType(new ArrayList<>(List.of(procFunParamOp.getType()))),
                                (String) procFunParamOp.getMode().accept(this)
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        symbolTableStatic = funOp.getSymbolTable();
        funOp.getBodyOp().accept(this);
        return null;
    }

    @Override
    public Object visit(ProcOp procOp) {
        SymbolTable symbolTable = new SymbolTable(symbolTableStatic, "Proc", new ArrayList<>());
        if (procOp.getSymbolTable() == null) {
            procOp.setSymbolTable(symbolTable);
        }

        procOp.getProcFunParamOpList().forEach(procFunParamOp -> {
            try {
                procOp.getSymbolTable().addSymbolRow(
                        new SymbolRow(
                                procFunParamOp.getId().getValue(),
                                "Param",
                                new SymbolType(new ArrayList<>(List.of(procFunParamOp.getType()))),
                                (String) procFunParamOp.getMode().accept(this)
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        symbolTableStatic = procOp.getSymbolTable();
        procOp.getBodyOp().accept(this);

        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        if (bodyOp.getSymbolTable() == null && !symbolTableStatic.getName().equals("Proc") && !symbolTableStatic.getName().equals("Func")) {
            bodyOp.setSymbolTable(symbolTableStatic);
        }

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
        symbolTableStatic = new SymbolTable(symbolTableStatic, "Elif", new ArrayList<>());

        elifOp.getExpr().accept(this);
        elifOp.getBodyOp().accept(this);

        return null;
    }

    @Override
    public Object visit(Mode mode) {
        return mode.getName();
    }

    @Override
    public Object visit(Type type) {
        return type.getName();
    }

    @Override
    public Object visit(Stat stat) {
        return null;
    }

    @Override
    public Object visit(AssignOp assignOp) {
        assignOp.getIdList().forEach(id -> id.accept(this));
        assignOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        symbolTableStatic = new SymbolTable(symbolTableStatic, "If", new ArrayList<>());

        ifStatOp.getExpr().accept(this);

        ifStatOp.getBodyOp().accept(this);

        ifStatOp.getElifOpList().forEach(elifOp -> elifOp.accept(this));

        symbolTableStatic = new SymbolTable(symbolTableStatic, "Else", new ArrayList<>());

        ifStatOp.getBodyOp2().accept(this);

        return null;
    }

    @Override
    public Object visit(ProcCallOp procCallOp) {
        procCallOp.getId().accept(this);
        procCallOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(ReadOp readOp) {
        readOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        returnOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(WhileOp whileOp) {
        symbolTableStatic = new SymbolTable(symbolTableStatic, "While", new ArrayList<>());

        whileOp.getExpr().accept(this);
        whileOp.getBodyOp().accept(this);

        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) {
        writeOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(Expr expr) {
        return null;
    }

    @Override
    public Object visit(CallFunOp callFunOp) {
        callFunOp.getId().accept(this);
        callFunOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(Const const1) {
        return const1.getType().accept(this);
    }

    @Override
    public Object visit(ID id) {
        return null;
    }

    @Override
    public Object visit(Op op) {
        op.getValueL().accept(this);
        op.getValueR().accept(this);
        return null;
    }

    @Override
    public Object visit(UOp uOp) {
        uOp.getValue().accept(this);
        return null;
    }
}
