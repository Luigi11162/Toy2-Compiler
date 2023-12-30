package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.SymbolTable.SymbolType;
import esercitazione5.Visitors.OpTable.OpTableCombinations;

import java.util.*;
import java.util.stream.Collectors;

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
        if(varDeclOp.getType()==null)
            if(varDeclOp.getidList().size() != varDeclOp.getConstList().size())
                throw new RuntimeException("Numero di id:" +varDeclOp.getidList().size()+" è diverso dal numero di costanti: "+varDeclOp.getConstList().size());
        return null;
    }

    @Override
    //Controllare match tipi con return
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
        bodyOp.getVarDeclOpList().forEach(varDeclOp -> varDeclOp.accept(this));
        if (symbolTable.getName().equals("Func"))
            if (bodyOp.getStatList().stream().noneMatch(stat -> stat.getName().equals("ReturnOp")))
                throw new RuntimeException("Funzione non ha return");

        else if(symbolTable.getName().equals("Proc"))
            if (bodyOp.getStatList().stream().anyMatch(stat -> stat.getName().equals("ReturnOp")))
                throw new RuntimeException("Procedure non può avere return");

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
        //Controllo il mapping tra id ed il tipo rispettivo
        Iterator<ID> idIterator = assignOp.getIdList().iterator();

        for (Expr expr : assignOp.getExprList()) {

            SymbolType exprSymbolType = (SymbolType) expr.accept(this);
            Iterator<Type> exprSymbolTypeIterator = exprSymbolType.getOutTypeList().iterator();

            //Controllo se l'expr in questione ha più tipi di ritorno
            while (idIterator.hasNext() && exprSymbolTypeIterator.hasNext()) {
                ID id = idIterator.next();
                if(!symbolTable.checkAssign(id)){
                    throw new RuntimeException("Id: "+id.getValue()+" non può essere assegnato");
                }
                SymbolType symbolType = (SymbolType) id.accept(this);
                Iterator<Type> typeIterator = symbolType.getOutTypeList().iterator();
                Type type = exprSymbolTypeIterator.next();
                if (!typeIterator.next().getName().equals(type.getName()))
                    throw new RuntimeException("Il tipo dell'id: " + id.getValue() + " non coincide con il tipo della rispettiva espressione: " + type.getName());
            }
            if (exprSymbolTypeIterator.hasNext()) {
                throw new RuntimeException("Il numero di assegnazioni è maggiore dal numero di id: " + assignOp.getIdList().size());
            }
        }

        if (idIterator.hasNext())
            throw new RuntimeException("Il numero di assegnazioni è minore dal numero di id: "+assignOp.getIdList().size());
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
        //Iteratore tipi di controllo
        Iterator<Type> typeIterator = symbolType.getInTypeList().iterator();

        //Controllo che ogni parametro abbia lo stesso tipo del rispettivo parametro nella firma
        for (Expr expr : procCallOp.getExprList()) {

            SymbolType symbolTypeProcCall = (SymbolType) expr.accept(this);
            Iterator<Type> typeProcCallIt = symbolTypeProcCall.getOutTypeList().iterator();

            while (typeProcCallIt.hasNext() && typeIterator.hasNext()) {
                Type procCallType = typeProcCallIt.next();
                Type type = typeIterator.next();
                if(!procCallType.getName().equals(type.getName())){
                    throw new RuntimeException("Chiamata procedura: "+procCallOp.getId().getValue()+". Il tipo: "+procCallType.getName()+" non combacia con il tipo: "+type.getName());
                }
            }
            if (typeProcCallIt.hasNext()){
                throw new RuntimeException("Chiamata procedura: "+procCallOp.getId().getValue()+". Il numero dei tipi richiesti: "+symbolType.getInTypeList().size()+" è minore del numero dei tipi forniti");
            }
        }
        if (typeIterator.hasNext()){
            throw new RuntimeException("Chiamata procedura: "+procCallOp.getId().getValue()+". Il numero dei tipi richiesti: "+symbolType.getInTypeList().size()+" è maggiore del numero dei tipi forniti");
        }

        return null;
    }

    @Override
    public Object visit(ReadOp readOp) {
        readOp.getExprList().forEach(expr -> {
            if (!(expr instanceof ID) && expr.getName().contains("Dollar"))
                throw new RuntimeException("Lettura di un'espressione che non è un'id: "+expr.getName());
        });
        readOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        Optional<SymbolType> symbolTypeOptional = returnOp.getExprList().stream().map(expr -> (SymbolType) expr.accept(this)).
                reduce((symbolType, symbolType2) -> {
                    symbolType.addOutTypeList(symbolType2.getOutTypeList());
                    return symbolType;
                });
        if (symbolTypeOptional.isPresent())
            return symbolTypeOptional.get();
        else
            throw new RuntimeException("Il return deve restituire almeno un elemento");
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
        //Iteratore tipi di controllo
        Iterator<Type> typeIterator = symbolType.getInTypeList().iterator();

        //Controllo che ogni parametro abbia lo stesso tipo del rispettivo parametro nella firma
        for (Expr expr: callFunOp.getExprList()) {

            SymbolType symbolTypeCallFun = (SymbolType) expr.accept(this);
            Iterator<Type> typeCallFunIt = symbolTypeCallFun.getOutTypeList().iterator();

            while (typeIterator.hasNext() && typeCallFunIt.hasNext()) {
                Type funCallType = typeCallFunIt.next();
                Type type = typeIterator.next();
                if(!funCallType.getName().equals(type.getName())){
                    throw new RuntimeException("Chiamata funzione: "+callFunOp.getId().getValue()+". Il tipo: "+funCallType.getName()+" non combacia con il tipo: "+type.getName());
                }
            }
            if (typeCallFunIt.hasNext()){
                throw new RuntimeException("Chiamata funzione: "+callFunOp.getId().getValue()+". Il numero dei tipi richiesti: "+symbolType.getInTypeList().size()+" è minore del numero dei tipi forniti");
            }
        }
        if (typeIterator.hasNext()){
            throw new RuntimeException("Chiamata funzione: "+callFunOp.getId().getValue()+". Il numero dei tipi richiesti: "+symbolType.getInTypeList().size()+" è maggiore del numero dei tipi forniti");
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
            default -> throw new RuntimeException("Tipo non consentito: "+const1.getType().getName());
        };
    }

    @Override
    public Object visit(ID id) {
        return symbolTable.returnTypeOfId(id.getValue());
    }

    @Override
    public Object visit(Op op) {
        switch (op.getName()) {
            case "AddOp", "DivOp":
                try {
                    if (op.getName().equals("AddOp"))
                        return OpTableCombinations.checkCombination(
                                new ArrayList<>(
                                        List.of(
                                                (SymbolType) op.getValueL().accept(this),
                                                (SymbolType) op.getValueR().accept(this)
                                        )
                                ),
                                OpTableCombinations.EnumOpTable.CONCATOP
                        );
                    else {
                        return OpTableCombinations.checkCombination(
                                new ArrayList<>(
                                        List.of(
                                                (SymbolType) op.getValueL().accept(this),
                                                (SymbolType) op.getValueR().accept(this)
                                        )
                                ),
                                OpTableCombinations.EnumOpTable.DIVOP
                        );
                    }
                }catch (Exception ignored) {}
            case "DiffOp", "MulOp":
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
                throw new RuntimeException("Operazione non consentita: "+op.getName());
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
            default -> throw new RuntimeException("Operazione non consentita: "+uOp.getName());
        };
    }
}
