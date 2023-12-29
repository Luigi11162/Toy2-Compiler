package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.Visitors.OpTable.OpTableCombinations;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TypeVisitor implements Visitor {

    Stack<SymbolTable> symbolTableStack = new Stack<>();
    @Override
    public Object visit(ProgramOp programOp) {
        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
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
        return null;
    }

    @Override
    public Object visit(ElifOp elifOp) {
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
        return switch ((String) const1.getType().accept(this)) {
            case "RealConst" -> new Type("Real");
            case "IntegerConst" -> new Type("Integer");
            case "StringConst" -> new Type("String");
            case "TrueConst", "FalseConst" -> new Type("Boolean");
            default -> null;
        };
    }

    @Override
    public Object visit(ID id) {
        return id.getValue();
    }

    @Override
    public Object visit(Op op) {
        switch (op.getName()){
            case "AddOp":
                Type type = OpTableCombinations.checkCombination(
                        new ArrayList<>(
                                List.of(
                                        (Type) op.getValueL().accept(this),
                                        (Type) op.getValueR().accept(this)
                                )
                        ),
                        OpTableCombinations.EnumOpTable.CONCATOP
                );
                if (type != null)
                    return type;
            case "DiffOp", "MulOp", "DivOp":
                return OpTableCombinations.checkCombination(
                        new ArrayList<>(
                                List.of(
                                        (Type) op.getValueL().accept(this),
                                        (Type) op.getValueR().accept(this)
                                )
                        ),
                        OpTableCombinations.EnumOpTable.ARITOP
                );
            case "AndOp", "OrOp" :
                return OpTableCombinations.checkCombination(
                        new ArrayList<>(
                                List.of(
                                        (Type) op.getValueL().accept(this),
                                        (Type) op.getValueR().accept(this)
                                )
                        ),
                        OpTableCombinations.EnumOpTable.LOGICOP
                );
            case "Gt", "GeOp", "LtOp", "LeOp", "EqOp", "NeOp" :
                return OpTableCombinations.checkCombination(
                        new ArrayList<>(
                                List.of(
                                        (Type) op.getValueL().accept(this),
                                        (Type) op.getValueR().accept(this)
                                )
                        ),
                        OpTableCombinations.EnumOpTable.RELOP
                );
            default:
                return null;
        }

    }

    @Override
    public Object visit(UOp uOp) {
        return switch (uOp.getName()) {
            case "UMinusOp" -> OpTableCombinations.checkCombination(
                    new ArrayList<>(
                            List.of(
                                    (Type) uOp.getValue().accept(this)
                            )
                    ),
                    OpTableCombinations.EnumOpTable.UMINUSOP
            );
            case "NotOp" -> OpTableCombinations.checkCombination(
                    new ArrayList<>(
                            List.of(
                                    (Type) uOp.getValue().accept(this)
                            )
                    ),
                    OpTableCombinations.EnumOpTable.NOTOP
            );
            default -> null;
        };
    }
}
