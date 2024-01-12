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
        if (varDeclOp.getType() == null)
            if (varDeclOp.getidList().size() != varDeclOp.getConstList().size())
                throw new RuntimeException("Numero di id:" + varDeclOp.getidList().size() + " è diverso dal numero di costanti: " + varDeclOp.getConstList().size());
        return null;
    }

    @Override
    //Controllare match tipi con return
    public Object visit(FunOp funOp) {
        symbolTable = funOp.getSymbolTable();
        class ReturnCheck {
            private static boolean checkReturn(BodyOp bodyOp, FunOp funOp, Visitor visitor) {
                boolean flag = false;
                if (bodyOp.getSymbolTable() != null)
                    symbolTable = bodyOp.getSymbolTable();
                for (Stat stat : bodyOp.getStatList()) {
                    //Controlla se tutti i return restituiscono il numero di valori e di tipi corretti
                    if (stat.getName().equals("ReturnOp")) {
                        SymbolType symbolType = (SymbolType) stat.accept(visitor);
                        Iterator<Type> returnTypeIt = symbolType.getOutTypeList().iterator();
                        Iterator<Type> funTypeIt = funOp.getTypeList().iterator();

                        while (funTypeIt.hasNext() && returnTypeIt.hasNext()) {
                            Type funType = funTypeIt.next();
                            Type returnType = returnTypeIt.next();
                            if (!funType.getName().equals(returnType.getName()))
                                throw new RuntimeException("Funzione: " + funOp.getId().getValue() + ". Tipo del return: " + returnType.getName() + " non combacia con il tipo di ritorno della funzione: " + funType.getName());
                        }
                        if (funTypeIt.hasNext())
                            throw new RuntimeException("Funzione: " + funOp.getId().getValue() + ". La funzione si aspetta più elementi di ritorno.");
                        else if (returnTypeIt.hasNext())
                            throw new RuntimeException("Funzione: " + funOp.getId().getValue() + ". Il return ha più elementi di quanti se ne aspetta la funzione.");
                        flag = true;
                        //Se lo statement è un if controlla che il return, se non è presente nel body,
                        // debba essere presente e debba essere corretto in ogni body dell'if, elseif ed else
                    } else if (stat instanceof IfStatOp ifStatOp) {
                        flag = flag || ReturnCheck.checkReturn(ifStatOp.getBodyOp(), funOp, visitor)
                                && ReturnCheck.checkReturn(ifStatOp.getBodyOp2(), funOp, visitor)
                                && ifStatOp.getElifOpList().stream().allMatch(elifOp ->
                                ReturnCheck.checkReturn(elifOp.getBodyOp(), funOp, visitor));
                        //Se lo statement è un while controlla se il return è presente e corretto
                    } else if (stat instanceof WhileOp whileOp) {
                        flag = flag || ReturnCheck.checkReturn(whileOp.getBodyOp(), funOp, visitor);
                    }
                }
                return flag;
            }
        }
        if (!ReturnCheck.checkReturn(funOp.getBodyOp(), funOp, this)) {
            throw new RuntimeException("La funzione: " + funOp.getId().getValue() + " non ha return");
        }

        symbolTable = funOp.getSymbolTable();

        funOp.getBodyOp().accept(this);
        return null;
    }

    @Override
    public Object visit(ProcOp procOp) {
        symbolTable = procOp.getSymbolTable();
        class ReturnCheck {
            private static boolean checkReturn(BodyOp bodyOp) {
                boolean flag = false;
                if (bodyOp.getSymbolTable() != null)
                    symbolTable = bodyOp.getSymbolTable();
                for (Stat stat : bodyOp.getStatList()) {
                    if (stat.getName().equals("ReturnOp")) {
                        flag = true;
                    } else if (stat instanceof IfStatOp ifStatOp) {
                        flag = flag || ReturnCheck.checkReturn(ifStatOp.getBodyOp()) || ReturnCheck.checkReturn(ifStatOp.getBodyOp2())
                                || ifStatOp.getElifOpList().stream().anyMatch(elifOp -> ReturnCheck.checkReturn(elifOp.getBodyOp()));
                    } else if (stat instanceof WhileOp whileOp) {
                        flag = flag || ReturnCheck.checkReturn(whileOp.getBodyOp());
                    }
                }
                return flag;
            }
        }

        if (ReturnCheck.checkReturn(procOp.getBodyOp()))
            throw new RuntimeException("Procedura: " + procOp.getId().getValue() + " non può avere return");

        symbolTable = procOp.getSymbolTable();
        procOp.getBodyOp().accept(this);
        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        //Se non è una funzione o una procedura allora imposta il nuovo scope
        if (bodyOp.getSymbolTable() != null)
            symbolTable = bodyOp.getSymbolTable();

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
        //Controllo il mapping tra id e il tipo rispettivo
        Iterator<ID> idIterator = assignOp.getIdList().iterator();

        for (Expr expr : assignOp.getExprList()) {

            SymbolType exprSymbolType = (SymbolType) expr.accept(this);
            Iterator<Type> exprSymbolTypeIterator = exprSymbolType.getOutTypeList().iterator();

            //Controllo se l'expr in questione ha più tipi di ritorno
            while (idIterator.hasNext() && exprSymbolTypeIterator.hasNext()) {
                ID id = idIterator.next();
                if (!symbolTable.checkAssign(id)) {
                    throw new RuntimeException("Id: " + id.getValue() + " non può essere assegnato");
                }
                SymbolType symbolType = (SymbolType) id.accept(this);
                Iterator<Type> typeIterator = symbolType.getOutTypeList().iterator();
                Type type = exprSymbolTypeIterator.next();
                Type typeId = typeIterator.next();
                if (!typeId.getName().equals(type.getName()))
                    if (!typeId.getName().equals("Real") && type.getName().equals("Integer"))
                        throw new RuntimeException("Il tipo dell'id: " + id.getValue() + " non coincide con il tipo della rispettiva espressione: " + type.getName());
            }
            if (exprSymbolTypeIterator.hasNext()) {
                throw new RuntimeException("Il numero di assegnazioni è maggiore dal numero di id: " + assignOp.getIdList().size());
            }
        }

        if (idIterator.hasNext())
            throw new RuntimeException("Il numero di assegnazioni è minore dal numero di id: " + assignOp.getIdList().size());
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
        for (int j = 0; j < procCallOp.getExprList().size(); j++) {

            Expr expr = procCallOp.getExprList().get(j);

            SymbolType symbolTypeProcCall = (SymbolType) expr.accept(this);
            if (expr instanceof CallFunOp && symbolTypeProcCall.getOutTypeList().size() != 1)
                throw new RuntimeException("Chiamata a funzione non può essere passata come argomento perché restituisce più valori");

            //Controllo nella tabella dei simboli della procedura se i parametri vengono passati nel giusto modo
            for (int i = 0; i < procCallOp.getRoot().getChildCount(); i++) {
                if (procCallOp.getRoot().getChildAt(i) instanceof ProcOp procOp && procOp.getId().getValue().equals(procCallOp.getId().getValue()))
                    if (expr instanceof ID id) {
                        if ((id.getMode() == null || !id.getMode().getName().equals("out")) && procOp.getProcFunParamOpList().get(j).getMode().getName().equals("out"))
                            throw new RuntimeException("L'id " + id.getValue() + " deve essere passato per riferimento");
                        else if (id.getMode() != null && id.getMode().getName().equals("out") && !procOp.getProcFunParamOpList().get(j).getMode().getName().equals("out"))
                            throw new RuntimeException("L'id " + id.getValue() + " non deve essere passato per riferimento");
                    }
            }

            Iterator<Type> typeProcCallIt = symbolTypeProcCall.getOutTypeList().iterator();

            while (typeProcCallIt.hasNext() && typeIterator.hasNext()) {
                Type procCallType = typeProcCallIt.next();
                Type type = typeIterator.next();
                if (!procCallType.getName().equals(type.getName()))
                    throw new RuntimeException("Chiamata procedura: " + procCallOp.getId().getValue() + ". Il tipo: " + procCallType.getName() + " non combacia con il tipo: " + type.getName());
            }
            if (typeProcCallIt.hasNext()) {
                throw new RuntimeException("Chiamata procedura: " + procCallOp.getId().getValue() + ". Il numero dei tipi richiesti: " + symbolType.getInTypeList().size() + " è minore del numero dei tipi forniti");
            }
        }
        if (typeIterator.hasNext()) {
            throw new RuntimeException("Chiamata procedura: " + procCallOp.getId().getValue() + ". Il numero dei tipi richiesti: " + symbolType.getInTypeList().size() + " è maggiore del numero dei tipi forniti");
        }

        return null;
    }

    @Override
    public Object visit(ReadOp readOp) {
        readOp.getExprList().forEach(expr -> {
            if (!(expr instanceof ID) && expr.getModeExpr() != null && expr.getModeExpr().getName().equals("DOLLAR"))
                throw new RuntimeException("Lettura di un'espressione che non è un'id: " + expr.getName());
            else if (expr instanceof ID id && !symbolTable.checkAssign(id))
                throw new RuntimeException("Id: " + id.getValue() + " non può essere assegnato");
        });
        readOp.getExprList().forEach(expr -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        Optional<SymbolType> symbolTypeOptional = returnOp.getExprList().stream().map(expr -> (SymbolType) expr.accept(this)).
                reduce((symbolType, symbolType2) -> {
                            SymbolType symbolTypeNew = new SymbolType(new ArrayList<>(), new ArrayList<>());
                            symbolTypeNew.addOutTypeList(symbolType.getOutTypeList());
                            symbolTypeNew.addOutTypeList(symbolType2.getOutTypeList());
                            return symbolTypeNew;
                        }
                );
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
        for (Expr expr : callFunOp.getExprList()) {

            SymbolType symbolTypeCallFun = (SymbolType) expr.accept(this);
            if (expr instanceof CallFunOp && symbolTypeCallFun.getOutTypeList().size() != 1)
                throw new RuntimeException("Chiamata a funzione non può essere passata come argomento perché restituisce più valori");

            Iterator<Type> typeCallFunIt = symbolTypeCallFun.getOutTypeList().iterator();

            while (typeIterator.hasNext() && typeCallFunIt.hasNext()) {
                Type funCallType = typeCallFunIt.next();
                Type type = typeIterator.next();
                if (!funCallType.getName().equals(type.getName()))
                    throw new RuntimeException("Chiamata funzione: " + callFunOp.getId().getValue() + ". Il tipo: " + funCallType.getName() + " non combacia con il tipo: " + type.getName());
            }
            if (typeCallFunIt.hasNext())
                throw new RuntimeException("Chiamata funzione: " + callFunOp.getId().getValue() + ". Il numero dei tipi richiesti: " + symbolType.getInTypeList().size() + " è minore del numero dei tipi forniti");
        }
        if (typeIterator.hasNext())
            throw new RuntimeException("Chiamata funzione: " + callFunOp.getId().getValue() + ". Il numero dei tipi richiesti: " + symbolType.getInTypeList().size() + " è maggiore del numero dei tipi forniti");

        return symbolType;
    }

    @Override
    public Object visit(Const const1) {
        return switch ((String) const1.getType().accept(this)) {
            case "Real" -> new SymbolType(new ArrayList<>(List.of(new Type("Real"))));
            case "Integer" -> new SymbolType(new ArrayList<>(List.of(new Type("Integer"))));
            case "String" -> new SymbolType(new ArrayList<>(List.of(new Type("String"))));
            case "Boolean" -> new SymbolType(new ArrayList<>(List.of(new Type("Boolean"))));
            default -> throw new RuntimeException("Tipo non consentito: " + const1.getType().getName());
        };
    }

    @Override
    public Object visit(ID id) {
        symbolTable.checkAssign(id);
        return symbolTable.returnTypeOfId(id.getValue());
    }

    @Override
    public Object visit(Op op) {
        if (op.getValueL() instanceof CallFunOp callFunOp1 && symbolTable.returnTypeOfId(callFunOp1.getId().getValue()).getOutTypeList().size() > 1 || op.getValueR() instanceof CallFunOp callFunOp2 && symbolTable.returnTypeOfId(callFunOp2.getId().getValue()).getOutTypeList().size() > 1) {
            throw new RuntimeException("Non è possibile effettuare operazioni su chiamate a funzione con valori di ritorno multipli");
        }
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
                } catch (Exception ignored) {
                }
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
                } catch (Exception ignored) {
                }
            case "GtOp", "GeOp", "LtOp", "LeOp":
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
                throw new RuntimeException("Operazione non consentita: " + op.getName());
        }

    }

    @Override
    public Object visit(UOp uOp) {
        if (uOp.getValue() instanceof CallFunOp callFunOp && symbolTable.returnTypeOfId(callFunOp.getId().getValue()).getOutTypeList().size() > 1)
            throw new RuntimeException("Non è possibile effettuare operazioni su chiamate a funzione con valori di ritorno multipli");
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
            default -> throw new RuntimeException("Operazione non consentita: " + uOp.getName());
        };
    }
}
