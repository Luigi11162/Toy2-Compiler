package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.SymbolTable.SymbolType;
import esercitazione5.Visitors.OpTable.OpTableCombinations;

import java.util.*;

public class TypeVisitor implements Visitor {

    static SymbolTable symbolTable;

    @Override
    public Object visit(ProgramOp programOp) {
        if (programOp.getSymbolTable().getSymbolRowList().stream().noneMatch(symbolRow -> symbolRow.getName().equals("main"))) {
            throw new RuntimeException("Procedura main non dichiarata");
        }
        symbolTable = programOp.getSymbolTable();

        programOp.getVarDeclOpList().forEach(varDeclOp -> varDeclOp.accept(this));
        programOp.getProcOpList().forEach(procOp -> procOp.accept(this));
        programOp.getFunOpList().forEach(funOp -> funOp.accept(this));
        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        if(varDeclOp.getType() == null)
            if(varDeclOp.getidList().size() != varDeclOp.getConstList().size())
                throw new RuntimeException("Numero di id:" +varDeclOp.getidList().size()+" è diverso dal numero di costanti: "+varDeclOp.getConstList().size());
        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        symbolTable = funOp.getSymbolTable();
        funOp.getBodyOp().accept(this);
        return null;
    }

    @Override
    public Object visit(ProcOp procOp) {
        symbolTable = procOp.getSymbolTable();
        procOp.getBodyOp().accept(this);
        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        if (bodyOp.getSymbolTable()!= null)
            symbolTable = bodyOp.getSymbolTable();
        bodyOp.getStatList().forEach(stat -> stat.accept(this));
        return null;
    }

    @Override
    public Object visit(ProcFunParamOp procFunParamOp) {
        return null;
    }

    @Override
    public Object visit(ElifOp elifOp) {
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
        //Controllo che tutti gli id siano stati dichiarati
        assignOp.getIdList().forEach(
                id -> {
                    //Controllo se l'id è presente nella tabella dei simboli
                    if (!symbolTable.checkIdDeclared(id.getValue()))
                        throw new RuntimeException("ID: " + id.getValue() + " non è stato dichiarato");

                });

        //Controllo il mapping tra id ed il tipo rispettivo
        Iterator<ID> idIterator = assignOp.getIdList().iterator();
        Iterator<Expr> exprIterator = assignOp.getExprList().iterator();

        while (idIterator.hasNext() && exprIterator.hasNext()) {
            ID id = idIterator.next();
            SymbolType symbolType = (SymbolType) id.accept(this);
            Iterator<Type> typeIterator = symbolType.getOutTypeList().iterator();
            SymbolType exprSymbolType = (SymbolType) exprIterator.next().accept(this);
            Iterator<Type> exprSymbolTypeIterator = exprSymbolType.getOutTypeList().iterator();

            //Controllo se l'expr in questione ha più tipi di ritorno
            while (idIterator.hasNext() && exprSymbolTypeIterator.hasNext()) {
                Type type = exprSymbolTypeIterator.next();
                if (!typeIterator.next().getName().equals(type.getName()))
                    throw new RuntimeException("Il tipo dell'id: " + id.getValue() + " non coincide con il tipo della rispettiva espressione: " + type.getName());
            }
            if (exprSymbolTypeIterator.hasNext())
                throw new RuntimeException("Il numero di assegnazioni è diverso dal numero di id");
        }

        if ((idIterator.hasNext() || exprIterator.hasNext()))
            throw new RuntimeException("Il numero di assegnazioni è diverso dal numero di id");
        return null;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        ifStatOp.getExpr().accept(this);
        ifStatOp.getBodyOp().accept(this);
        ifStatOp.getElifOpList().forEach(elifOp -> elifOp.accept(this));
        ifStatOp.getBodyOp2().accept(this);
        return null;
    }

    @Override
    public Object visit(ProcCallOp procCallOp) {
        SymbolType symbolType = (SymbolType) procCallOp.getId().accept(this);
        Iterator<Expr> procCallExprIt = procCallOp.getExprList().iterator();
        Iterator<Type> typeIterator = symbolType.getInTypeList().iterator();

        while (procCallExprIt.hasNext()) {
            SymbolType symbolTypeProcCall = (SymbolType) procCallExprIt.next().accept(this);
            Iterator<Type> typeProcCallIt = symbolTypeProcCall.getInTypeList().iterator();
            while (typeProcCallIt.hasNext() && typeIterator.hasNext()) {
                Type procCallType = typeProcCallIt.next();
                Type type = typeIterator.next();
                if(!procCallType.getName().equals(type.getName())){
                    throw new RuntimeException("Il tipo: "+procCallType.getName()+" non combacia con il tipo: "+type.getName());
                }
            }
        }
        if (typeIterator.hasNext()){
            throw new RuntimeException("Il numero dei tipi richiesti: "+symbolType.getInTypeList().size()+" non combacia con il numero dei tipi forniti");
        }
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
        SymbolType symbolType = (SymbolType) callFunOp.getId().accept(this);
        Iterator<Expr> callFunExprIt = callFunOp.getExprList().iterator();
        Iterator<Type> typeIterator = symbolType.getInTypeList().iterator();

        while (callFunExprIt.hasNext()) {
            SymbolType symbolTypeCallFun = (SymbolType) callFunExprIt.next().accept(this);
            Iterator<Type> typeCallFunIt = symbolTypeCallFun.getInTypeList().iterator();
            while (typeCallFunIt.hasNext() && typeIterator.hasNext()) {
                Type funCallType = typeCallFunIt.next();
                Type type = typeIterator.next();
                if(!funCallType.getName().equals(type.getName())){
                    throw new RuntimeException("Il tipo: "+funCallType.getName()+" non combacia con il tipo: "+type.getName());
                }
            }
        }
        if (typeIterator.hasNext()){
            throw new RuntimeException("Il numero dei tipi richiesti: "+symbolType.getInTypeList().size()+" non combacia con il numero dei tipi forniti");
        }
        return symbolType;
    }

    @Override
    public Object visit(Const const1) {
        return switch ((String) const1.getType().accept(this)) {
            case "RealConst" -> new SymbolType(new ArrayList<>(List.of(new Type("Real"))));
            case "IntegerConst" -> new SymbolType(new ArrayList<>(List.of(new Type("Integer"))));
            case "StringConst" -> new SymbolType(new ArrayList<>(List.of(new Type("String"))));
            case "TrueConst", "FalseConst" -> new SymbolType(new ArrayList<>(List.of(new Type("Boolean"))));
            default -> throw new RuntimeException("Tipo non consentito");
        };
    }

    @Override
    public Object visit(ID id) {
        return symbolTable.returnTypeOfId(id.getValue());
    }

    @Override
    public Object visit(Op op) {
        switch (op.getName()) {
            case "AddOp":
                try {
                    return OpTableCombinations.checkCombination(
                            new ArrayList<>(
                                    List.of(
                                            (SymbolType) op.getValueL().accept(this),
                                            (SymbolType) op.getValueR().accept(this)
                                    )
                            ),
                            OpTableCombinations.EnumOpTable.CONCATOP
                    );
                } catch (Exception ignored) {
                }
            case "DiffOp", "MulOp", "DivOp":
                return OpTableCombinations.checkCombination(
                        new ArrayList<>(
                                List.of(
                                        (SymbolType) op.getValueL().accept(this),
                                        (SymbolType) op.getValueR().accept(this)
                                )
                        ),
                        OpTableCombinations.EnumOpTable.ARITOP
                );
            case "AndOp", "OrOp":
                return OpTableCombinations.checkCombination(
                        new ArrayList<>(
                                List.of(
                                        (SymbolType) op.getValueL().accept(this),
                                        (SymbolType) op.getValueR().accept(this)
                                )
                        ),
                        OpTableCombinations.EnumOpTable.LOGICOP
                );
            case "EqOp", "NeOp":
                try {
                    return OpTableCombinations.checkCombination(
                            new ArrayList<>(
                                    List.of(
                                            (SymbolType) op.getValueL().accept(this),
                                            (SymbolType) op.getValueR().accept(this)
                                    )
                            ),
                            OpTableCombinations.EnumOpTable.COMPOP
                    );
                }catch (Exception ignored){}
            case "Gt", "GeOp", "LtOp", "LeOp":
                    return OpTableCombinations.checkCombination(
                            new ArrayList<>(
                                    List.of(
                                            (SymbolType) op.getValueL().accept(this),
                                            (SymbolType) op.getValueR().accept(this)
                                    )
                            ),
                            OpTableCombinations.EnumOpTable.RELOP
                    );
            default:
                throw new RuntimeException("Operazione non consentita");
        }

    }

    @Override
    public Object visit(UOp uOp) {
        return switch (uOp.getName()) {
            case "UMinusOp" -> OpTableCombinations.checkCombination(
                    new ArrayList<>(
                            List.of(
                                    (SymbolType) uOp.getValue().accept(this)
                            )
                    ),
                    OpTableCombinations.EnumOpTable.UMINUSOP
            );
            case "NotOp" -> OpTableCombinations.checkCombination(
                    new ArrayList<>(
                            List.of(
                                    (SymbolType) uOp.getValue().accept(this)
                            )
                    ),
                    OpTableCombinations.EnumOpTable.NOTOP
            );
            default -> throw new RuntimeException("Operazione non consentita");
        };
    }
}
