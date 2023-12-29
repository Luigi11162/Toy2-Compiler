package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.Visitors.OpTable.OpTableCombinations;

public class TypeVisitor implements Visitor {

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
            case "RealConst" -> "Real";
            case "IntegerConst" -> "Integer";
            case "StringConst" -> "String";
            case "TrueConst", "FalseConst" -> "Boolean";
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
            case "AddOp", "DiffOp","MulOp", "DivOp":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + op.getName());
        }
        return null;
    }

    @Override
    public Object visit(UOp uOp) {
        return null;
    }
}
