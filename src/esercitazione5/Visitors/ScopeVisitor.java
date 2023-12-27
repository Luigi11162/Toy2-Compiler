package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;
import esercitazione5.SymbolTable.SymbolRow;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.SymbolTable.SymbolType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class ScopeVisitor implements Visitor {

    Stack<SymbolTable> symbolTableStack = new Stack<>();

    @Override
    public Object visit(ProgramOp programOp) {
        if (programOp.getSymbolTable() == null) {
            SymbolTable symbolTable = new SymbolTable("Global", new ArrayList<>());
            programOp.setSymbolTable(symbolTable);
            symbolTableStack.add(symbolTable);
        }
        programOp.getVarDeclOpList().forEach(varDeclOp -> varDeclOp.accept(this));
        programOp.getProcOpList().forEach(procOp -> procOp.accept(this));
        programOp.getFunOpList().forEach(funOp -> funOp.accept(this));

        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        SymbolTable symbolTable = symbolTableStack.pop();

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
        return null;
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
